package jaxb2.error.handling.validator;

import jaxb2.generated.*;
import system.engine.world.rule.enums.Type;
import jaxb2.error.handling.exception.prdworld.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PRDWorldValidator {
    public void validatePRDWorld(PRDWorld prdWorld){
        checkEnvVariableWithSameName(prdWorld.getPRDEnvironment());
        checkEntityPropertyWithTheSameName(prdWorld.getPRDEntities());
        checkIfEntityExistInAction(prdWorld);
        checkIfAllEntitiesInContext(prdWorld);
        checkIfAllPropertiesExist(prdWorld);
        checkNumericFunctionsParamType(prdWorld);
    }
    //2
    public void checkEnvVariableWithSameName(PRDEnvironment prdEvironment){
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

    public void checkEntityInSingleAction(PRDRule prdRule, PRDAction prdAction, List<PRDEntity> prdEntityList){
        if(!prdEntityList.contains(getEntityByName(getActionEntityName(prdAction),prdEntityList)))
            throw new EntityNotExistException(prdRule.getName(),prdAction.getType(),prdAction.getEntity());

        if(prdAction.getType().equals("replace")){
            if(!prdEntityList.contains(getEntityByName(prdAction.getCreate(),prdEntityList)))
                throw new EntityNotExistException(prdRule.getName(),prdAction.getType(),prdAction.getCreate());
        }
        if(prdAction.getType().equals("proximity")){
            if(!prdEntityList.contains(getEntityByName(prdAction.getPRDBetween().getTargetEntity(),prdEntityList)))
                throw new EntityNotExistException(prdRule.getName(),prdAction.getType(),prdAction.getPRDBetween().getTargetEntity());
            for(PRDAction proximityPrdAction: prdAction.getPRDActions().getPRDAction())
                checkEntityInSingleAction(prdRule,proximityPrdAction,prdEntityList);
        }
        if(prdAction.getPRDSecondaryEntity()!=null){
            if(!prdEntityList.contains(getEntityByName(prdAction.getPRDSecondaryEntity().getEntity(),prdEntityList)))
                throw new EntityNotExistException(prdRule.getName(),prdAction.getType(),prdAction.getPRDSecondaryEntity().getEntity());
        }
        //check entity in actions with actions list
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
        for (PRDRule prdRule : prdWorld.getPRDRules().getPRDRule())
            for (PRDAction prdAction : prdRule.getPRDActions().getPRDAction())
                checkPropertyInSingleAction(prdRule,prdAction,prdWorld.getPRDEntities().getPRDEntity());
    }


    public void checkPropertyInSingleAction(PRDRule prdRule,PRDAction prdAction,List<PRDEntity> prdEntityList){
        if (prdAction.getProperty() != null) { //increase,decrease,set
            if(!isPropertyExistInEntity(prdAction.getProperty(),prdAction.getEntity(),prdEntityList))
                throw new PropertNotExistInEntityException(prdRule.getName(),prdAction.getType(),prdAction.getProperty(),prdAction.getEntity());
        }
        else if(prdAction.getType().equals("calculation")){
            if(!isPropertyExistInEntity(prdAction.getResultProp(),prdAction.getEntity(),prdEntityList))
                throw new PropertNotExistInEntityException(prdRule.getName(),prdAction.getType(),prdAction.getResultProp(),prdAction.getEntity());
        }
        else if(prdAction.getType().equals("condition")){
            //check actions in PRDThen
            for(PRDAction thenPrdAction:prdAction.getPRDThen().getPRDAction())
                checkPropertyInSingleAction(prdRule,thenPrdAction,prdEntityList);
            //check actions in PRDElse if exist
            if(prdAction.getPRDElse()!=null){
                for(PRDAction elsePrdAction:prdAction.getPRDElse().getPRDAction())
                    checkPropertyInSingleAction(prdRule,elsePrdAction,prdEntityList);
            }
        }
        else if(prdAction.getType().equals("proximity")){
            for(PRDAction proximityPrdAction: prdAction.getPRDActions().getPRDAction())
                checkPropertyInSingleAction(prdRule,proximityPrdAction,prdEntityList);
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

    public String getActionEntityName(PRDAction prdAction){
        if(prdAction.getType().equals("replace"))
            return prdAction.getKill();
        else if(prdAction.getType().equals("proximity"))
            return prdAction.getPRDBetween().getSourceEntity();
        else
            return prdAction.getEntity();
    }

    //6
    public void checkNumericFunctionsParamType(PRDWorld prdWorld){
        for(PRDRule prdRule:prdWorld.getPRDRules().getPRDRule())
            for(PRDAction prdAction:prdRule.getPRDActions().getPRDAction())
                checkNumericParamTypeInSingleAction(prdAction,prdAction,prdRule,prdWorld.getPRDEnvironment(),prdWorld.getPRDEntities().getPRDEntity());
    }

    public void checkNumericParamTypeInSingleAction(PRDAction innerPrdAction,PRDAction bigPrdAction,PRDRule prdRule,PRDEnvironment prdEvironment,List<PRDEntity> prdEntityList){
        if(innerPrdAction.getType().equals("increase") || innerPrdAction.getType().equals("decrease")){
            checkIncreaseDecreaseFunctionsParamTypes(prdRule,innerPrdAction,bigPrdAction,prdEntityList,prdEvironment);
        }
        else if(innerPrdAction.getType().equals("calculation")){
            checkCalculationFunctionParamType(prdRule,innerPrdAction,bigPrdAction,prdEntityList,prdEvironment);
        }
        else if(innerPrdAction.getType().equals("condition")){
            for(PRDAction thenPrdAction:innerPrdAction.getPRDThen().getPRDAction())
                checkNumericParamTypeInSingleAction(thenPrdAction,bigPrdAction,prdRule,prdEvironment,prdEntityList);
            if(innerPrdAction.getPRDElse()!=null){
                for(PRDAction elsePrdAction:innerPrdAction.getPRDElse().getPRDAction())
                    checkNumericParamTypeInSingleAction(elsePrdAction,bigPrdAction,prdRule,prdEvironment,prdEntityList);
            }
        }
        else if(innerPrdAction.getType().equals("proximity")){
            //check of
            Type enumOfType=getExpressionTypeByAction(innerPrdAction.getPRDEnvDepth().getOf(),innerPrdAction.getPRDBetween().getSourceEntity(),bigPrdAction,prdEntityList,prdEvironment);
            checkIfExpressionOrPropertyIsNumeric(enumOfType,innerPrdAction.getPRDEnvDepth().getOf(),prdRule.getName(),bigPrdAction.getType());
            //check actions list
            for(PRDAction proxomityPrdAction:innerPrdAction.getPRDActions().getPRDAction())
                checkNumericParamTypeInSingleAction(proxomityPrdAction,bigPrdAction,prdRule,prdEvironment,prdEntityList);
        }
    }
    public Type getExpressionTypeByAction(String expressionStr,String currEntityName,PRDAction bigPrdAction,List<PRDEntity> prdEntityList,PRDEnvironment prdEvironment){
        Type enumByType;
        if(bigPrdAction.getType().equals("proximity"))
            enumByType=getExpressionType(expressionStr,getEntityByName(currEntityName,prdEntityList),
                    getEntityByName(bigPrdAction.getPRDBetween().getSourceEntity(),prdEntityList),getEntityByName(bigPrdAction.getPRDBetween().getTargetEntity(),prdEntityList),prdEvironment,bigPrdAction,prdEntityList);
        else
            enumByType=getExpressionType(expressionStr,getEntityByName(currEntityName,prdEntityList),
                    getEntityByName(bigPrdAction.getEntity(),prdEntityList),getEntityByName(bigPrdAction.getPRDSecondaryEntity().getEntity(),prdEntityList),prdEvironment,bigPrdAction,prdEntityList);
        return enumByType;
    }

    public void checkIncreaseDecreaseFunctionsParamTypes(PRDRule prdRule,PRDAction currPrdAction,PRDAction bigPrdAction,List<PRDEntity> prdEntityList,PRDEnvironment prdEvironment){
        //check the property Type
        Type enumPropertyType=getPropertyTypeOfEntity(currPrdAction.getProperty(),getEntityByName(currPrdAction.getEntity(),prdEntityList));
        checkIfExpressionOrPropertyIsNumeric(enumPropertyType,currPrdAction.getProperty(),prdRule.getName(),bigPrdAction.getType());
        //check the by(expression) Type
        Type enumByType=getExpressionTypeByAction(currPrdAction.getBy(),currPrdAction.getEntity(),bigPrdAction,prdEntityList,prdEvironment);
        checkIfExpressionOrPropertyIsNumeric(enumByType,currPrdAction.getBy(),prdRule.getName(),bigPrdAction.getType());

    }


    public void checkCalculationFunctionParamType(PRDRule prdRule,PRDAction currPrdAction,PRDAction bigPrdAction,List<PRDEntity> prdEntityList,PRDEnvironment prdEvironment){
        //check the result-prop type
        Type enumResultPropType=getPropertyTypeOfEntity(currPrdAction.getResultProp(),getEntityByName(currPrdAction.getEntity(),prdEntityList));
        checkIfExpressionOrPropertyIsNumeric(enumResultPropType,currPrdAction.getResultProp(),prdRule.getName(),bigPrdAction.getType());
        //check Multiply
        if(currPrdAction.getPRDMultiply()!=null)
            checkMultiplyCalculation(currPrdAction.getPRDMultiply().getArg1(),currPrdAction.getPRDMultiply().getArg2(),prdRule,currPrdAction,bigPrdAction,prdEntityList,prdEvironment);
        //check divide
        if(currPrdAction.getPRDDivide()!=null)
            checkDivideCalculation(currPrdAction.getPRDDivide().getArg1(),currPrdAction.getPRDDivide().getArg2(),prdRule,currPrdAction,bigPrdAction,prdEntityList,prdEvironment);
    }

    public void checkMultiplyCalculation(String arg1Exp,String arg2Exp,PRDRule prdRule,PRDAction currAction,PRDAction bigAction,List<PRDEntity> prdEntityList,PRDEnvironment prdEvironment){
        Type enumArg1Type=getExpressionTypeByAction(arg1Exp,currAction.getEntity(),bigAction,prdEntityList,prdEvironment);
        checkIfExpressionOrPropertyIsNumeric(enumArg1Type,arg1Exp,prdRule.getName(),bigAction.getType());
        Type enumArg2Type=getExpressionTypeByAction(arg2Exp,currAction.getEntity(),bigAction,prdEntityList,prdEvironment);
        checkIfExpressionOrPropertyIsNumeric(enumArg2Type,arg2Exp,prdRule.getName(),bigAction.getType());
    }

    public void checkDivideCalculation(String arg1Exp,String arg2Exp,PRDRule prdRule,PRDAction currAction,PRDAction bigAction,List<PRDEntity> prdEntityList,PRDEnvironment prdEvironment){
        Type enumArg1Type=getExpressionTypeByAction(arg1Exp,currAction.getEntity(),bigAction,prdEntityList,prdEvironment);
        checkIfExpressionOrPropertyIsNumeric(enumArg1Type,arg1Exp,prdRule.getName(),bigAction.getType());
        Type enumArg2Type=getExpressionTypeByAction(arg2Exp,currAction.getEntity(),bigAction,prdEntityList,prdEvironment);
        checkIfExpressionOrPropertyIsNumeric(enumArg2Type,arg2Exp,prdRule.getName(),bigAction.getType());
        if(arg2Exp.trim().equals("0")||arg2Exp.trim().equals("0.0"))
            throw new DeivideByZeroException(prdRule.getName(),bigAction.getType());
    }

    //checking entities in context
    public void checkIfAllEntitiesInContext(PRDWorld prdWorld){
        for(PRDRule prdRule:prdWorld.getPRDRules().getPRDRule())
            for(PRDAction prdAction:prdRule.getPRDActions().getPRDAction())
                checkEntityContextInSingleAction(prdAction,prdAction,prdRule);
    }

    //helping functions


    public void checkEntityContextInSingleAction(PRDAction innerPrdAction,PRDAction bigPrdAction,PRDRule prdRule){
        if(!innerPrdAction.getType().equals("proximity") && !innerPrdAction.getType().equals("condition")){
            if(bigPrdAction.getType().equals("condition"))
                checkIfEntityInContextByName(getActionEntityName(innerPrdAction),bigPrdAction.getEntity(),bigPrdAction.getPRDSecondaryEntity().getEntity(),bigPrdAction,prdRule);
            else if(bigPrdAction.getType().equals("proximity"))
                checkIfEntityInContextByName(getActionEntityName(innerPrdAction),bigPrdAction.getPRDBetween().getSourceEntity(),bigPrdAction.getPRDBetween().getTargetEntity(),bigPrdAction,prdRule);
        }
        else if(innerPrdAction.getType().equals("condition")){
            checkEntityContextInConditionAction(innerPrdAction.getEntity(),innerPrdAction.getPRDSecondaryEntity().getEntity(),innerPrdAction.getPRDCondition(),bigPrdAction,prdRule);
            for(PRDAction thenPrdAction:innerPrdAction.getPRDThen().getPRDAction())
                checkEntityContextInSingleAction(thenPrdAction,bigPrdAction,prdRule);
            if(innerPrdAction.getPRDElse()!=null){
                for(PRDAction elsePrdAction:innerPrdAction.getPRDElse().getPRDAction())
                    checkEntityContextInSingleAction(elsePrdAction,bigPrdAction,prdRule);
            }
        }
        else if(innerPrdAction.getType().equals("proximity")){
            //check actions list
            for(PRDAction proxomityPrdAction:innerPrdAction.getPRDActions().getPRDAction())
                checkEntityContextInSingleAction(proxomityPrdAction,bigPrdAction,prdRule);
        }
    }

    public void checkEntityContextInConditionAction(String primaryEntityName,String secondaryEntityName,PRDCondition prdCondition,PRDAction bigAction,PRDRule prdRule){
        if(prdCondition.getSingularity().equals("single")){
            checkIfEntityInContextByName(prdCondition.getEntity(),primaryEntityName,secondaryEntityName,bigAction,prdRule);
        }
        if(prdCondition.getSingularity().equals("multiple")){
            for(PRDCondition innerPrdCondition:prdCondition.getPRDCondition())
                checkEntityContextInConditionAction(primaryEntityName,secondaryEntityName,innerPrdCondition,bigAction,prdRule);
        }
    }

    public void checkIfEntityInContextByName(String checkEntityName,String primaryEntityName,String secondEntityName,PRDAction bigAction,PRDRule prdRule){
        if (!(checkEntityName.equals(primaryEntityName) || checkEntityName.equals(secondEntityName)))
            throw new EntityNotInContext(prdRule.getName(),bigAction.getType(),checkEntityName);
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

    public Type getExpressionType(String ExpressionStr,PRDEntity currentEntity,PRDEntity primaryEntity,PRDEntity secondEntity,PRDEnvironment prdEvironment,PRDAction prdAction,List<PRDEntity> prdEntityList){

        HelpingFunctionValidator helpingFunctionValidator=new HelpingFunctionValidator();
        //get the type if it is a function name
        Pattern pattern = Pattern.compile("^(random|environment|evaluate|percent|ticks)\\((.+)\\)$");
        Matcher matcher = pattern.matcher(ExpressionStr);
        if(matcher.matches()){
            switch(matcher.group(1)){
                case "random":
                    helpingFunctionValidator.checkRandomFunction(matcher.group(2));
                    return Type.FLOAT;
                case "environment":
                    helpingFunctionValidator.checkEnvironmentFunction(matcher.group(2),prdEvironment);
                    return getEnvironmetVarType(matcher.group(2),prdEvironment);
                case "evaluate":
                    return checkEvaluateTickFunction("evaluate",matcher.group(2),primaryEntity,secondEntity,prdAction,prdEntityList);
                case "percent":
                    checkPercentFunction(matcher.group(2),currentEntity,primaryEntity,secondEntity,prdEvironment,prdAction,prdEntityList);
                    return Type.FLOAT;
                case "tick":
                    return checkEvaluateTickFunction("tick",matcher.group(2),primaryEntity,secondEntity,prdAction,prdEntityList);

            }
        }
        //get the type if it is a property
        for(PRDProperty prdProperty:currentEntity.getPRDProperties().getPRDProperty())
            if(prdProperty.getPRDName().equals(ExpressionStr))
                return getPropertyTypeOfEntity(ExpressionStr,currentEntity);

        //get the type if it is a free value
        return getType(ExpressionStr);
    }

    public Type getEnvironmetVarType(String envVarString,PRDEnvironment prdEvironment){
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

    public Type checkEvaluateTickFunction(String func,String input,PRDEntity primaryEntity,PRDEntity secondEntity,PRDAction prdAction,List<PRDEntity> prdEntityList){
        String entityName;
        String propertyName;

        String[] parts = input.split("\\.");
        if (parts.length == 2) {
            entityName = parts[0];
            propertyName = parts[1];

            if(getEntityByName(entityName,prdEntityList)==null)
               throw new EntityNotExistException("",prdAction.getType(),entityName);
            checkIfEntityInContext(prdAction,entityName,primaryEntity,secondEntity);

            //here entity is in context
            if(!isPropertyExistInEntity(propertyName,entityName,prdEntityList))
                throw new PropertNotExistInEntityException("",prdAction.getType(),propertyName,entityName);

            if(func.equals("evaluate"))
                return getPropertyTypeOfEntity(propertyName,getEntityByName(entityName,prdEntityList));
            else
                return Type.FLOAT;

        } else
            throw new IllegalArgumentException("the format of evaluate/tick function is invalid!");

    }

    public void checkPercentFunction(String input,PRDEntity currentEntity,PRDEntity primaryEntity,PRDEntity secondEntity,PRDEnvironment prdEvironment,PRDAction prdAction,List<PRDEntity> prdEntityList){
        String exp1;
        String exp2;

        String[] parts = input.split("\\,");
        if (parts.length == 2) {
            exp1 = parts[0];
            exp2 = parts[1];
           Type exp1Type=getExpressionType(exp1,currentEntity,primaryEntity,secondEntity,prdEvironment,prdAction,prdEntityList);
           Type exp2Type=getExpressionType(exp2,currentEntity,primaryEntity,secondEntity,prdEvironment,prdAction,prdEntityList);
            checkIfExpressionOrPropertyIsNumeric(exp1Type,exp1,"", prdAction.getType());
            checkIfExpressionOrPropertyIsNumeric(exp2Type,exp2,"", prdAction.getType());
        } else
            throw new IllegalArgumentException("the format of evaluate/tick function is invalid!");
    }

    public void checkIfEntityInContext(PRDAction prdAction,String entityName,PRDEntity primaryEntity,PRDEntity secondEntity){
        if (!(entityName.equals(primaryEntity.getName()) || entityName.equals(secondEntity.getName())))
            throw new EntityNotInContext("",prdAction.getType(),entityName);
    }

}
