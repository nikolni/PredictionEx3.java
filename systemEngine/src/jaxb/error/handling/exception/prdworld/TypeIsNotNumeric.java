package jaxb.error.handling.exception.prdworld;

import system.engine.world.rule.enums.Type;

public class TypeIsNotNumeric extends RuntimeException{
    private final String EXCEPTION_MESSAGE = "ERROR- In rule:%s, action:%s, %s's type is not numeric";
    private String propOrExpressionStr;
    private String ruleName;
    private String actionName;


    public TypeIsNotNumeric(String propOrExpressionStr, String ruleName, String actionName) {
        this.propOrExpressionStr = propOrExpressionStr;
        this.ruleName = ruleName;
        this.actionName = actionName;
    }

    @Override
    public String getMessage() {
        return String.format(EXCEPTION_MESSAGE,ruleName,actionName,propOrExpressionStr);
    }
}
