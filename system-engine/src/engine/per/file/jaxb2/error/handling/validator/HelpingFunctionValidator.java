package engine.per.file.jaxb2.error.handling.validator;

import engine.per.file.jaxb2.error.handling.exception.prdworld.EnvironmentWrongParamsException;
import engine.per.file.jaxb2.error.handling.exception.prdworld.RandomWrongParamException;
import engine.per.file.jaxb2.generated.PRDEnvProperty;
import engine.per.file.jaxb2.generated.PRDEnvironment;

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









}
