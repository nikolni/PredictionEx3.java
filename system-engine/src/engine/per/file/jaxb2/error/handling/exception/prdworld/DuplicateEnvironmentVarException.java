package engine.per.file.jaxb2.error.handling.exception.prdworld;

public class DuplicateEnvironmentVarException extends RuntimeException{
    private final String EXCEPTION_MESSAGE = "ERROR- There are Environment Variables with the same name: %s";
    private String envVarName;


    public DuplicateEnvironmentVarException(String envVarName) {
        this.envVarName = envVarName;

    }
    @Override
    public String getMessage() {
        return String.format(EXCEPTION_MESSAGE,envVarName);
    }
}
