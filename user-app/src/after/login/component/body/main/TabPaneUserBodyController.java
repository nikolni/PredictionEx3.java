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
import engine.per.file.engine.api.SystemEngineAccess;

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
    }

   /* public void setSystemEngineToChildren(SystemEngineAccess systemEngineAccess){
        simulationDetailsComponentController.setSystemEngine(systemEngineAccess);
        requestsComponentController.setSystemEngine(systemEngineAccess);
        executionComponentController.setSystemEngine(systemEngineAccess);
        resultsComponentController.setSystemEngine(systemEngineAccess);
        initialChildren();
    }*/
    private void initialChildren(){
        resultsComponentController.primaryInitialize();
    }

     public void setControllersForChildren(SystemEngineAccess systemEngineAccess){
        requestsComponentController.setExecutionController(executionComponentController);
         requestsComponentController.setMainController(mainUserController);
        executionComponentController.setProgressAndResultController(resultsComponentController);
    }

    public void switchToTab1() {
        tabPaneBodyComponent.getSelectionModel().select(0);
        simulationDetailsComponentController.setVisibleTab();
    }

    public void switchToTab2() {
        tabPaneBodyComponent.getSelectionModel().select(1);
    }

    public void switchToTab3(SystemEngineAccess systemEngineAccess) {
        tabPaneBodyComponent.getSelectionModel().select(2);
        executionComponentController.primaryInitialize(systemEngineAccess);
    }
    public void switchToTab4() {
        tabPaneBodyComponent.getSelectionModel().select(3);
    }

    public void onRerunClick(int simulationID){
        executionComponentController.setTilesByRerun(simulationID);

    }
}
