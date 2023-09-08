package jaxb2.error.handling.validator;

import jaxb2.error.handling.exception.prdworld.EntityNotInContext;
import jaxb2.error.handling.exception.prdworld.EnvironmentWrongParamsException;
import jaxb2.error.handling.exception.prdworld.RandomWrongParamException;
import jaxb2.generated.PRDAction;
import jaxb2.generated.PRDEnvProperty;
import jaxb2.generated.PRDEnvironment;
import jaxb2.generated.PRDRule;

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

    public void checkEvaluateFunction(String input, PRDRule prdRule, PRDAction prdAction){
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
                if(!(entityName.equals(killEntityName) || entityName.equals(secondaryEntityName)))
                    throw new EntityNotInContext(prdRule.getName(),prdAction.getType(),entityName);
            }

        } else {
            throw new IllegalArgumentException("the argument for evaluate function is invalid!");
        }
    }
}
