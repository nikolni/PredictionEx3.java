package engine.per.file.jaxb2.error.handling.exception.prdworld;

public class EnvironmentWrongParamsException extends RuntimeException{
    private final String EXCEPTION_MESSAGE = "ERROR- There is no Environment Variables with the name: %s";
    private String envVarName;

    public EnvironmentWrongParamsException(String envVarName) {
        this.envVarName = envVarName;
    }

    @Override
    public String getMessage() {
        return String.format(EXCEPTION_MESSAGE,envVarName);
    }
}
