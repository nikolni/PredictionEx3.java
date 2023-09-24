package engine.per.file.jaxb2.error.handling.exception.prdworld;

public class EntityNotInContext extends  RuntimeException{
    private final String EXCEPTION_MESSAGE = "ERROR- in rule: %s, action:%s, entity:%s in not in context";

    private String ruleName;
    private String actionName;
    private String entityName;

    public EntityNotInContext(String ruleName,String actionName,String entityName) {
        this.ruleName=ruleName;
        this.actionName = actionName;
        this.entityName=entityName;
    }

    @Override
    public String getMessage() {
        return String.format(EXCEPTION_MESSAGE,ruleName,actionName,entityName);
    }
}
