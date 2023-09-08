package jaxb2.error.handling.validator;



import jaxb2.error.handling.exception.file.InvalidFileException;
import jaxb2.error.handling.exception.file.InvalidXmlFileException;

import java.io.File;
import java.io.FileNotFoundException;

public class FileValidator {
    //1
    public void validateXmlFile(String filePath) throws FileNotFoundException {
        // Check if the file path is provided
        if (filePath == null || filePath.isEmpty()) {
            throw new InvalidFileException();
        }
        // Create a File object from the given file path
        File file = new File(filePath);

        // Check if the file exists
        if (!file.exists()) {
            throw new FileNotFoundException("File not found: " + filePath);
        }

        // Check if the file is an XML file
        if (!file.getName().toLowerCase().endsWith(".xml")) {
            throw new InvalidXmlFileException("Invalid XML file: " + filePath);
        }
    }
}
