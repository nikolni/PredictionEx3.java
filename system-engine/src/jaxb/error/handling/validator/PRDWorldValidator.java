package jaxb.error.handling.validator;

import jaxb.error.handling.exception.prdworld.*;
import jaxb.generated.*;
import system.engine.world.rule.enums.Type;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PRDWorldValidator {

    public void validatePRDWorld(PRDWorld prdWorld){
        checkEnvVariableWithSameName(prdWorld.getPRDEvironment());
        checkEntityPropertyWithTheSameName(prdWorld.getPRDEntities());
        checkIfEntityExistInAction(prdWorld);
        checkIfAllPropertiesExist(prdWorld);
        checkNumericFunctionsParamType(prdWorld);
    }
    //2
    public void checkEnvVariableWithSameName(PRDEvironment prdEvironment){
        Set<String> envVarNames=new HashSet<>();
        for(PRDEnvProperty prdEnvProperty: prdEvironment.getPRDEnvProperty()) {
            if (!envVarNames.add(prdEnvProperty.getPRDName())) {
                throw new DuplicateEnvironmentVarException(prdEnvProperty.getPRDName());
            }
        }
    }
    //3
    public void checkEntityPropertyWithTheSameName(PRDEntities prdEntities) {
        Set<String> entitysPropertiesNames = new HashSet<>();
        for (PRDEntity prdEntity : prdEntities.getPRDEntity()) {
            for (PRDProperty prdProperty : prdEntity.getPRDProperties().getPRDProperty()) {
                if (!entitysPropertiesNames.add(prdProperty.getPRDName())) {
                    throw new DuplicateEntitysPropertyNameException(prdEntity.getName(), prdProperty.getPRDName());
                }
            }
            entitysPropertiesNames.clear();
        }
    }
    //4
    public void checkIfEntityExistInAction(PRDWorld prdWorld){
        for(PRDRule prdRule: prdWorld.getPRDRules().getPRDRule())
            for(PRDAction prdAction:prdRule.getPRDActions().getPRDAction()){
                checkEntityInSingleAction(prdRule,prdAction,prdWorld.getPRDEntities().getPRDEntity());
            }
    }

    public void checkEntityInSingleAction(PRDRule prdRule,PRDAction prdAction,List<PRDEntity> prdEntityList){
        if(!prdEntityList.contains(getEntityByName(prdAction.getEntity(),prdEntityList)))
            throw new EntityNotExistException(prdRule.getName(),prdAction.getType(),prdAction.getEntity());
        if(prdAction.getType().equals("condition")){
            checkEntityInConditionAction(prdAction.getPRDCondition(),prdEntityList,prdRule);
            //check entity in PRDThen
            for(PRDAction thenPrdAction:prdAction.getPRDThen().getPRDAction())
                checkEntityInSingleAction(prdRule,thenPrdAction,prdEntityList);
            //check entity in PRDElse if exist
            if(prdAction.getPRDElse()!=null){
                for(PRDAction elsePrdAction:prdAction.getPRDElse().getPRDAction())
                    checkEntityInSingleAction(prdRule,elsePrdAction,prdEntityList);
            }
        }
    }

    public void checkEntityInConditionAction(PRDCondition prdCondition, List<PRDEntity> prdEntityList, PRDRule prdRule){
        if(prdCondition.getSingularity().equals("single")){
            if(!prdEntityList.contains(getEntityByName(prdCondition.getEntity(),prdEntityList)))
                throw new EntityNotExistException(prdRule.getName(),"condition",prdCondition.getEntity());
        }
        if(prdCondition.getSingularity().equals("multiple")){
            for(PRDCondition innerPrdCondition:prdCondition.getPRDCondition())
                checkEntityInConditionAction(innerPrdCondition,prdEntityList,prdRule);
        }
    }

    //5
    public void checkIfAllPropertiesExist(PRDWorld prdWorld) {
        boolean flag=false;
        List<PRDProperty> prdPropertyList = new ArrayList<>();
        for (PRDRule prdRule : prdWorld.getPRDRules().getPRDRule())
            for (PRDAction prdAction : prdRule.getPRDActions().getPRDAction())
                checkPropertyInSingleAction(prdRule,prdAction,prdWorld.getPRDEntities().getPRDEntity());
        }


        public void checkPropertyInSingleAction(PRDRule prdRule,PRDAction prdAction,List<PRDEntity> prdEntityList){
            if (prdAction.getProperty() != null) { //increase,decrease,set
                if(!isPropertyExistInEntity(prdAction.getProperty(),prdAction.getEntity(),prdEntityList))
                    throw new PropertNotExistInEntityException(prdRule.getName(),prdAction.getType(),prdAction.getProperty(),prdAction.getEntity());
            }
            if(prdAction.getType().equals("calculation")){
                if(!isPropertyExistInEntity(prdAction.getResultProp(),prdAction.getEntity(),prdEntityList))
                    throw new PropertNotExistInEntityException(prdRule.getName(),prdAction.getType(),prdAction.getResultProp(),prdAction.getEntity());
            }
            if(prdAction.getType().equals("condition")){
                //check the property in the condition section
                checkPropertyInConditionAction(prdAction.getPRDCondition(),prdEntityList,prdRule);
                //check actions in PRDThen
                for(PRDAction thenPrdAction:prdAction.getPRDThen().getPRDAction())
                    checkPropertyInSingleAction(prdRule,thenPrdAction,prdEntityList);
                //check actions in PRDElse if exist
                if(prdAction.getPRDElse()!=null){
                    for(PRDAction elsePrdAction:prdAction.getPRDElse().getPRDAction())
                        checkPropertyInSingleAction(prdRule,elsePrdAction,prdEntityList);
                }
            }
        }


        public void checkPropertyInConditionAction(PRDCondition prdCondition, List<PRDEntity> prdEntityList, PRDRule prdRule){
            if(prdCondition.getSingularity().equals("single")){
                if(!isPropertyExistInEntity(prdCondition.getProperty(),prdCondition.getEntity(),prdEntityList))
                    throw new PropertNotExistInEntityException(prdRule.getName(),"condition",prdCondition.getProperty(),prdCondition.getEntity());
            }
            if(prdCondition.getSingularity().equals("multiple")){
                for(PRDCondition innerPrdCondition:prdCondition.getPRDCondition())
                    checkPropertyInConditionAction(innerPrdCondition,prdEntityList,prdRule);
            }
        }

    public boolean isPropertyExistInEntity(String propertyName,String entityName,List<PRDEntity> prdEntityList){
        List<PRDProperty> prdPropertyList=getEntityByName(entityName, prdEntityList).getPRDProperties().getPRDProperty();
        for (PRDProperty prdProperty : prdPropertyList) {
            if (prdProperty.getPRDName().equals(propertyName))
                return true;
        }
        return false;
    }

    public PRDEntity getEntityByName(String entityName, List<PRDEntity> prdEntityList){
        for(PRDEntity prdEntity:prdEntityList)
            if(prdEntity.getName().equals(entityName))
                return prdEntity;
        return null;
    }

    //6
    public void checkNumericFunctionsParamType(PRDWorld prdWorld){
        for(PRDRule prdRule:prdWorld.getPRDRules().getPRDRule())
            for(PRDAction prdAction:prdRule.getPRDActions().getPRDAction()){
                checkNumericParamTypeInSingleAction(prdAction,prdRule,prdWorld.getPRDEvironment(),prdWorld.getPRDEntities().getPRDEntity());
            }
    }

    public void checkNumericParamTypeInSingleAction(PRDAction prdAction,PRDRule prdRule,PRDEvironment prdEvironment,List<PRDEntity> prdEntityList){
        if(prdAction.getType().equals("increase") || prdAction.getType().equals("decrease")){
            checkIncreaseDecreaseFunctionsParamTypes(prdRule,prdAction,prdEntityList,prdEvironment);
        }
        if(prdAction.getType().equals("calculation")){
            checkCalculationFunctionParamType(prdRule,prdAction,prdEntityList,prdEvironment);
        }
        if(prdAction.getType().equals("condition")){
            for(PRDAction thenPrdAction:prdAction.getPRDThen().getPRDAction())
                checkNumericParamTypeInSingleAction(thenPrdAction,prdRule,prdEvironment,prdEntityList);
            if(prdAction.getPRDElse()!=null){
                for(PRDAction elsePrdAction:prdAction.getPRDElse().getPRDAction())
                    checkNumericParamTypeInSingleAction(elsePrdAction,prdRule,prdEvironment,prdEntityList);
            }

        }
    }

    public void checkIncreaseDecreaseFunctionsParamTypes(PRDRule prdRule,PRDAction prdAction,List<PRDEntity> prdEntityList,PRDEvironment prdEvironment){
        //check the property Type
        Type enumPropertyType=getPropertyTypeOfEntity(prdAction.getProperty(),getEntityByName(prdAction.getEntity(),prdEntityList));
        checkIfExpressionOrPropertyIsNumeric(enumPropertyType,prdAction.getProperty(),prdRule.getName(),prdAction.getType());
        //check the by(expression) Type
        Type enumByType=getExpressionType(prdAction.getBy(),getEntityByName(prdAction.getEntity(),prdEntityList),prdEvironment);
        checkIfExpressionOrPropertyIsNumeric(enumByType,prdAction.getBy(),prdRule.getName(),prdAction.getType());
        //now prop and by are all numeric
        checkTwoTypes(enumPropertyType,enumByType,prdRule.getName(),prdAction.getType());
    }

    public void checkCalculationFunctionParamType(PRDRule prdRule,PRDAction prdAction,List<PRDEntity> prdEntityList,PRDEvironment prdEvironment){
        //check the result-prop type
        Type enumResultPropType=getPropertyTypeOfEntity(prdAction.getResultProp(),getEntityByName(prdAction.getEntity(),prdEntityList));
        checkIfExpressionOrPropertyIsNumeric(enumResultPropType,prdAction.getResultProp(),prdRule.getName(),prdAction.getType());
        //check Multiply
        if(prdAction.getPRDMultiply()!=null)
            checkMultiplyCalculation(prdAction.getPRDMultiply().getArg1(),prdAction.getPRDMultiply().getArg2(),enumResultPropType,prdRule,prdAction,prdEntityList,prdEvironment);
        //check divide
        if(prdAction.getPRDDivide()!=null)
            checkDivideCalculation(prdAction.getPRDDivide().getArg1(),prdAction.getPRDDivide().getArg2(),prdRule,prdAction,prdEntityList,prdEvironment);
    }

    public void checkMultiplyCalculation(String arg1Exp,String arg2Exp,Type enumResultPropType,PRDRule prdRule,PRDAction prdAction,List<PRDEntity> prdEntityList,PRDEvironment prdEvironment){
        Type enumArg1Type=getExpressionType(arg1Exp,getEntityByName(prdAction.getEntity(),prdEntityList),prdEvironment);
        checkIfExpressionOrPropertyIsNumeric(enumArg1Type,arg1Exp,prdRule.getName(),prdAction.getType());
        Type enumArg2Type=getExpressionType(arg2Exp,getEntityByName(prdAction.getEntity(),prdEntityList),prdEvironment);
        checkIfExpressionOrPropertyIsNumeric(enumArg2Type,arg2Exp,prdRule.getName(),prdAction.getType());
        if(enumResultPropType==Type.DECIMAL&&(enumArg1Type==Type.FLOAT||enumArg2Type==Type.FLOAT))
            throw new ForbiddenCastingInNumericFunctionException(prdRule.getName(),prdAction.getType());
    }

    public void checkDivideCalculation(String arg1Exp,String arg2Exp,PRDRule prdRule,PRDAction prdAction,List<PRDEntity> prdEntityList,PRDEvironment prdEvironment){
        Type enumArg1Type=getExpressionType(arg1Exp,getEntityByName(prdAction.getEntity(),prdEntityList),prdEvironment);
        checkIfExpressionOrPropertyIsNumeric(enumArg1Type,arg1Exp,prdRule.getName(),prdAction.getType());
        Type enumArg2Type=getExpressionType(arg2Exp,getEntityByName(prdAction.getEntity(),prdEntityList),prdEvironment);
        checkIfExpressionOrPropertyIsNumeric(enumArg2Type,arg2Exp,prdRule.getName(),prdAction.getType());
        if(arg2Exp.trim().equals("0")||arg2Exp.trim().equals("0.0"))
            throw new DeivideByZeroException(prdRule.getName(),prdAction.getType());
    }



    public void checkTwoTypes(Type propType,Type byType,String ruleName,String ActionName){
        if(propType==Type.DECIMAL&& byType==Type.FLOAT)
            throw new ForbiddenCastingInNumericFunctionException(ruleName,ActionName);
    }

    public boolean checkIfTypeIsNumeric(Type type){
        if(type!=Type.DECIMAL && type!=Type.FLOAT)
            return false;
        return true;
    }

    public void checkIfExpressionOrPropertyIsNumeric(Type type,String propOrExpressionStr,String ruleName,String ActionName){
        if(!checkIfTypeIsNumeric(type))
            throw new TypeIsNotNumeric(propOrExpressionStr,ruleName,ActionName);
    }

    public Type getExpressionType(String ExpressionStr,PRDEntity currentEntity,PRDEvironment prdEvironment){

        HelpingFunctionValidator helpingFunctionValidator=new HelpingFunctionValidator();
        //get the type if it is a function name
        Pattern pattern = Pattern.compile("^(random|environment|evaluate|percent|ticks)\\((.+)\\)$");
        Matcher matcher = pattern.matcher(ExpressionStr);
         if(matcher.matches()){
            switch(matcher.group(1)){
                case "random":
                    helpingFunctionValidator.checkRandomFunction(matcher.group(2));
                    return Type.DECIMAL;
                case "environment":
                    helpingFunctionValidator.checkEnvironmentFunction(matcher.group(2),prdEvironment);
                    return getEnvironmetVarType(matcher.group(2),prdEvironment);
            }
         }
         //get the type if it is a property
        for(PRDProperty prdProperty:currentEntity.getPRDProperties().getPRDProperty())
            if(prdProperty.getPRDName().equals(ExpressionStr))
                return getPropertyTypeOfEntity(ExpressionStr,currentEntity);

        //get the type if it is a free value
        return getType(ExpressionStr);
        }

        public Type getEnvironmetVarType(String envVarString,PRDEvironment prdEvironment){
        for(PRDEnvProperty prdEnvProperty:prdEvironment.getPRDEnvProperty())
            if(prdEnvProperty.getPRDName().equals(envVarString))
                return Type.valueOf(prdEnvProperty.getType().toUpperCase());
        return null;
        }

        public Type getPropertyTypeOfEntity(String propertyName,PRDEntity prdEntity){
            for(PRDProperty prdProperty:prdEntity.getPRDProperties().getPRDProperty())
                if(prdProperty.getPRDName().equals(propertyName))
                    return Type.valueOf(prdProperty.getType().toUpperCase());
            return null;
        }

    public static Type getType(String value) {
        Pattern integerPattern = Pattern.compile("^-?\\d+$");
        Pattern decimalPattern = Pattern.compile("^-?\\d+\\.\\d+$");
        Pattern booleanPattern = Pattern.compile("^(true|false)$", Pattern.CASE_INSENSITIVE);

        if (integerPattern.matcher(value).matches()) {
            return Type.DECIMAL;
        } else if (decimalPattern.matcher(value).matches()) {
            return Type.FLOAT;
        } else if (booleanPattern.matcher(value).matches()) {
            return Type.BOOLEAN;
        } else {
            return Type.STRING;
        }
    }




}


