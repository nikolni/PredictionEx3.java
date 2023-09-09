package jaxb2.error.handling.validator;

import jaxb2.error.handling.exception.prdworld.EntityNotInContext;
import jaxb2.error.handling.exception.prdworld.EnvironmentWrongParamsException;
import jaxb2.error.handling.exception.prdworld.PropertNotExistInEntityException;
import jaxb2.error.handling.exception.prdworld.RandomWrongParamException;
import jaxb2.generated.*;

import java.util.List;

public class HelpingFunctionValidator {
    public static boolean isInteger(String input) {
        try {
            Integer.parseInt(input);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public void checkRandomFunction(String input){
        if(!isInteger(input))
            throw new RandomWrongParamException();
    }

    public void checkEnvironmentFunction(String input, PRDEnvironment prdEnvironment){
        boolean flag=false;
        for(PRDEnvProperty prdEnvProperty:prdEnvironment.getPRDEnvProperty())
            if(prdEnvProperty.getPRDName().equals(input))
                flag=true;
        if(!flag)
            throw new EnvironmentWrongParamsException(input);
    }

    public void checkEvaluateFunction(String input, PRDRule prdRule, PRDAction prdAction, List<PRDEntity> prdEntityList){
        String entityName;
        String propertyName;
        String secondaryEntityName=null;

        String[] parts = input.split("\\.");
        if (parts.length == 2) {
            entityName = parts[0];
            propertyName = parts[1];
            if(prdAction.getPRDSecondaryEntity()!=null)
                secondaryEntityName=prdAction.getPRDSecondaryEntity().getEntity();
            if(prdAction.getType().equals("replace")){
                String killEntityName=prdAction.getKill();
                checkIfEntityInContext(entityName,killEntityName,secondaryEntityName,prdRule,prdAction);

            }
            else if(prdAction.getType().equals("proximity")){
                String sourceEntity=prdAction.getPRDBetween().getSourceEntity();
                checkIfEntityInContext(entityName,sourceEntity,secondaryEntityName,prdRule,prdAction);

            }
            else{
                String primaryEntity=prdAction.getEntity();
                checkIfEntityInContext(entityName,primaryEntity,secondaryEntityName,prdRule,prdAction);
            }
            //here entity is in context
            PRDWorldValidator prdWorldValidator=new PRDWorldValidator();
            if(!prdWorldValidator.isPropertyExistInEntity(propertyName,entityName,prdEntityList))
                throw new PropertNotExistInEntityException(prdRule.getName(),prdAction.getType(),propertyName,entityName);
        } else
            throw new IllegalArgumentException("the argument for evaluate function is invalid!");
    }



    public void checkIfEntityInContext(String checkedEntityName,String primaryEntityName,String secondaryEntityName,
                              PRDRule prdRule,PRDAction prdAction){
        if(!(checkedEntityName.equals(primaryEntityName) || checkedEntityName.equals(secondaryEntityName)))
            throw new EntityNotInContext(prdRule.getName(),prdAction.getType(),checkedEntityName);

    }
}
