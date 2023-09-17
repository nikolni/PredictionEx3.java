package jaxb2.error.handling.validator;

import jaxb2.error.handling.exception.prdworld.EntityNotInContext;
import jaxb2.error.handling.exception.prdworld.EnvironmentWrongParamsException;
import jaxb2.error.handling.exception.prdworld.PropertNotExistInEntityException;
import jaxb2.error.handling.exception.prdworld.RandomWrongParamException;
import jaxb2.generated.*;
import system.engine.world.rule.enums.Type;

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









}
