package jaxb.error.handling.exception.prdworld;

public class ForbiddenCastingInNumericFunctionException extends RuntimeException{

    private final String EXCEPTION_MESSAGE = "ERROR- In rule:%s, action:%s, the expression type is float and the property type is integer";

    private String ruleName;
    private String actionName;

    public ForbiddenCastingInNumericFunctionException(String ruleName, String actionName) {
        this.ruleName = ruleName;
        this.actionName = actionName;
    }

    @Override
    public String getMessage() {
        return String.format(EXCEPTION_MESSAGE,ruleName,actionName);
    }
}
