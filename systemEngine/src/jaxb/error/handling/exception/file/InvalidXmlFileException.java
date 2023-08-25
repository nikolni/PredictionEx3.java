package jaxb.error.handling.exception.file;

public class InvalidXmlFileException extends  RuntimeException{
    private final String EXCEPTION_MESSAGE = "ERROR- Invalid XML file: %s";
    private String filePath;

    public InvalidXmlFileException(String filePath) {
        this.filePath = filePath;
    }
    @Override
    public String getMessage() {
        return String.format(EXCEPTION_MESSAGE, filePath);
    }
}
