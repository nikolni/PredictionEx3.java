package jaxb.error.handling.validator;

import jaxb.error.handling.exception.prdworld.EnvironmentWrongParamsException;
import jaxb.error.handling.exception.prdworld.RandomWrongParamException;
import jaxb.generated.PRDEnvProperty;
import jaxb.generated.PRDEvironment;

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

    public void checkEnvironmentFunction(String input, PRDEvironment prdEvironment){
        boolean flag=false;
        for(PRDEnvProperty prdEnvProperty:prdEvironment.getPRDEnvProperty())
            if(prdEnvProperty.getPRDName().equals(input))
                flag=true;
        if(!flag)
            throw new EnvironmentWrongParamsException(input);
    }


}
