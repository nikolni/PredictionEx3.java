package after.login.main;

import after.login.component.body.main.TabPaneUserBodyController;
import after.login.component.header.HeaderController;
import javafx.fxml.FXML;
import javafx.scene.control.TabPane;
import javafx.scene.layout.VBox;
import engine.per.file.engine.api.SystemEngineAccess;
import engine.per.file.engine.impl.SystemEngineAccessImpl;

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
        if (headerComponentController != null && tabPaneUserBodyComponentController != null) {
            headerComponentController.setMainController(this);
            tabPaneUserBodyComponentController.setMainController(this);
        }
    }

    public void onExecutionClick() {

    }

    public void onRequestClick() {
        tabPaneUserBodyComponentController.switchToTab2();
    }

    public void onResultsClick() {
        tabPaneUserBodyComponentController.switchToTab4();
    }

    public void onSimulationDetailsClick() {
        tabPaneUserBodyComponentController.switchToTab1();
    }


        public void onRerunClick(int simulationID){
        tabPaneUserBodyComponentController.switchToTab2();
        tabPaneUserBodyComponentController.onRerunClick( simulationID);
    }
}
