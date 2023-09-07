package jaxb2.error.handling.exception.prdworld;

public class DuplicateEntitysPropertyNameException extends RuntimeException{
    private final String EXCEPTION_MESSAGE = "ERROR- The entity:%s has properties with the same name: %s";
    private String entityName;
    private String propertyName;

    public DuplicateEntitysPropertyNameException(String entityName, String propertyName) {
        this.entityName = entityName;
        this.propertyName = propertyName;
    }

    @Override
    public String getMessage() {
        return String.format(EXCEPTION_MESSAGE,entityName,propertyName);
    }
}
