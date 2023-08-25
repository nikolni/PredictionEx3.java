package ui.impl;

import system.engine.api.SystemEngineAccess;
import ui.api.MenuExecution;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Menu1 implements MenuExecution {
    @Override
    public void executeUserChoice(SystemEngineAccess systemEngine) {
        loadDataFromXml(systemEngine);
    }
    public void loadDataFromXml(SystemEngineAccess systemEngineAccess){
            System.out.println("Please enter an XML path \n");
            Scanner scanner = new Scanner(System.in);
            String userXMLpathInput = scanner.nextLine();
            try{
                systemEngineAccess.getXMLFromUser(userXMLpathInput);
                System.out.println("the XML file is valid and fully loaded");
            }
            catch(RuntimeException | JAXBException | FileNotFoundException e){
                System.out.println(e.getMessage());
            }

    }
}
