package engine.per.file.jaxb2.error.handling.exception.file;


public class InvalidFileException extends RuntimeException{
    private final String EXCEPTION_MESSAGE = "ERROR- File path is not provided";

    @Override
    public String getMessage() {
        return EXCEPTION_MESSAGE;
    }
}
