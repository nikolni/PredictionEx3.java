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

    private String userName;


    public void setUserName(String userName){
        this.userName = userName;
    }

    @FXML
    public void initialize() {
        if (headerComponentController != null && tabPaneUserBodyComponentController != null) {
            headerComponentController.setMainController(this);
            tabPaneUserBodyComponentController.setMainController(this);
        }
    }
    public String getUserName() {
        return userName;
    }


    public void onExecutionClickFromRequest() {
        tabPaneUserBodyComponentController.switchToTab3FromRequest();
    }
    public void onExecutionClickFromHeader() {
        tabPaneUserBodyComponentController.switchToTab3FromHeader();
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

    public void onRerunClick(int executionID){
        tabPaneUserBodyComponentController.switchToTab2();
        tabPaneUserBodyComponentController.onRerunClick(executionID);
    }

}
