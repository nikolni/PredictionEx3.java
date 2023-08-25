package jaxb.error.handling.exception.prdworld;

public class PropertNotExistInEntityException extends  RuntimeException{
    private final String EXCEPTION_MESSAGE = "ERROR- In the rule:%s, action:%s there is no such property name:%s in entity:%s";
    private String entityName;
    private String propertyName;
    private String ruleName;
    private String actionName;

    public PropertNotExistInEntityException(String ruleName,String actionName, String propertyName,String entityName) {
        this.entityName = entityName;
        this.propertyName = propertyName;
        this.ruleName = ruleName;
        this.actionName = actionName;
    }

    @Override
    public String getMessage() {
        return String.format(EXCEPTION_MESSAGE,ruleName,actionName,propertyName,entityName);

    }
}
