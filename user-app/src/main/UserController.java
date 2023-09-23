package main;

import component.body.main.TabPaneUserBodyController;
import component.header.HeaderController;
import javafx.fxml.FXML;
import javafx.scene.control.TabPane;
import javafx.scene.layout.VBox;
import system.engine.api.SystemEngineAccess;
import system.engine.impl.SystemEngineAccessImpl;

public class UserController {

    @FXML private VBox headerComponent;
    @FXML private HeaderController headerComponentController;
    @FXML private TabPane tabPaneUserBodyComponent;
    @FXML private TabPaneUserBodyController tabPaneUserBodyComponentController;



    private SystemEngineAccess systemEngine;

    public UserController(){
        systemEngine = new SystemEngineAccessImpl();

        /*try {
            systemEngine.getXMLFromUser("C:/Users/maaya/javaProjects/PredictionsEX2_2.9/ex1-cigarets.xml");
        } catch (JAXBException e) {
            throw new RuntimeException(e);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }*/
    }

    @FXML
    public void initialize() {
        if (headerComponentController != null && tabPaneAdminBodyComponentController != null) {
            headerComponentController.setMainController(this);
            tabPaneAdminBodyComponentController.setMainController(this);
        }
        headerComponentController.setSystemEngine(systemEngine);
    }

    public SystemEngineAccess getSystemEngine() {
        return systemEngine;
    }



    public void onRerunClick(int simulationID){
        tabPaneUserBodyComponentController.switchToTab2();
        tabPaneUserBodyComponentController.onRerunClick( simulationID);
    }
}
