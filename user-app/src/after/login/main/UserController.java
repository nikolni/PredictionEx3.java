package after.login.main;

import after.login.component.body.main.TabPaneUserBodyController;
import after.login.component.header.HeaderController;
import javafx.fxml.FXML;
import javafx.scene.control.TabPane;
import javafx.scene.layout.VBox;

public class UserController {

    @FXML private VBox headerComponent;
    @FXML private HeaderController headerComponentController;
    @FXML private TabPane tabPaneUserBodyComponent;
    @FXML private TabPaneUserBodyController tabPaneUserBodyComponentController;

    private String userName;
    private boolean duringExecution = false;


    public void setUserName(String userName){
        this.userName = userName;
    }
    public void endExecutionInitialization(){
        duringExecution = false;
    }
    @FXML
    public void initialize() {
        if (headerComponentController != null && tabPaneUserBodyComponentController != null) {
            headerComponentController.setMainController(this);
            tabPaneUserBodyComponentController.setMainController(this);
        }
    }
    public void initialPrimaryForTabPaneComponents(){
        tabPaneUserBodyComponentController.initialChildren();
    }
    public String getUserName() {
        return userName;
    }


    public void onExecutionClickFromRequest() {
        tabPaneUserBodyComponentController.switchToTab3FromRequest();
        duringExecution = true;
    }
    public void onExecutionClickFromHeader() {
        if(duringExecution){
            tabPaneUserBodyComponentController.switchToTab3FromRequest();
        }
        else{
            tabPaneUserBodyComponentController.switchToTab3FromHeader();
        }
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
        tabPaneUserBodyComponentController.switchToTab3FromRequest();
        tabPaneUserBodyComponentController.onRerunClick(executionID);
    }
    public void onStartClickFromExecution(Integer requestIndexInRequestController){
        tabPaneUserBodyComponentController.onStartClickFromExecution(requestIndexInRequestController);
    }

}
