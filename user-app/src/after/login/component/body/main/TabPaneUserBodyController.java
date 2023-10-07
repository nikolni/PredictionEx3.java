package after.login.component.body.main;

import after.login.component.body.execution.main.ExecutionController;
import after.login.component.body.request.main.RequestController;
import after.login.component.body.running.main.ProgressAndResultController;
import after.login.component.body.simulation.details.main.SimulationsDetailsController;
import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TabPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import after.login.main.UserController;

public class TabPaneUserBodyController {

    @FXML
    private TabPane tabPaneBodyComponent;
    @FXML private HBox simulationDetailsComponent ;
    @FXML private SimulationsDetailsController simulationDetailsComponentController;
    @FXML private VBox requestsComponent;
    @FXML private RequestController requestsComponentController;

    @FXML private ScrollPane executionComponent;
    @FXML private ExecutionController executionComponentController;
    @FXML private HBox resultsComponent;
    @FXML private ProgressAndResultController resultsComponentController;

    private UserController mainUserController;


    public void setMainController(UserController mainUserController) {
        this.mainUserController = mainUserController;
        setControllersForChildren();
    }

    public void initialChildren(){
        resultsComponentController.primaryInitialize();
        requestsComponentController.primaryInitialize();
        simulationDetailsComponentController.primaryInitialize();
    }

     public void setControllersForChildren(){
        requestsComponentController.setExecutionController(executionComponentController);
        requestsComponentController.setMainController(mainUserController);
        executionComponentController.setProgressAndResultController(resultsComponentController);
        executionComponentController.setMainController(mainUserController);
        resultsComponentController.setMainController(mainUserController);
    }

    public void switchToTab1() {
        tabPaneBodyComponent.getSelectionModel().select(0);
        simulationDetailsComponentController.setVisibleTab();
    }

    //requests
    public void switchToTab2() {
        tabPaneBodyComponent.getSelectionModel().select(1);
    }

    //execution
    public void switchToTab3FromRequest() {
        executionComponentController.enableController();
        tabPaneBodyComponent.getSelectionModel().select(2);
    }
    public void switchToTab3FromHeader() {
        executionComponentController.disableController();
        tabPaneBodyComponent.getSelectionModel().select(2);
    }

    //results
    public void switchToTab4() {
        tabPaneBodyComponent.getSelectionModel().select(3);
    }

    public void onRerunClick(int executionID){
        executionComponentController.setTilesByRerun(executionID);

    }
}
