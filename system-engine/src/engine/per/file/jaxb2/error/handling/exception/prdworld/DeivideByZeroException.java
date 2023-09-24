package engine.per.file.jaxb2.error.handling.exception.prdworld;

public class DeivideByZeroException extends RuntimeException{
    private final String EXCEPTION_MESSAGE = "ERROR- in rule: %s, action:%s there is division by zero";
    private String ruleName;
    private String actionName;

    public DeivideByZeroException(String ruleName, String actionName) {
        this.ruleName = ruleName;
        this.actionName = actionName;
    }

    @Override
    public String getMessage() {
        return String.format(EXCEPTION_MESSAGE,ruleName,actionName);
    }
}
