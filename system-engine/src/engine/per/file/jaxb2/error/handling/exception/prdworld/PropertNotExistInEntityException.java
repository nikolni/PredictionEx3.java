package engine.per.file.jaxb2.error.handling.exception.prdworld;

public class PropertNotExistInEntityException extends  RuntimeException{
    private final String EXCEPTION_MESSAGE = "ERROR- In rule %s, action:%s there is no such property name:%s in entity:%s";
    private String entityName;
    private String propertyName;
    private String actionName;
    private String ruleName;

    public PropertNotExistInEntityException(String ruleName,String actionName, String propertyName,String entityName) {
        this.entityName = entityName;
        this.propertyName = propertyName;
        this.actionName = actionName;
        this.ruleName=ruleName;
    }

    @Override
    public String getMessage() {
        return String.format(EXCEPTION_MESSAGE,ruleName,actionName,propertyName,entityName);

    }
}
