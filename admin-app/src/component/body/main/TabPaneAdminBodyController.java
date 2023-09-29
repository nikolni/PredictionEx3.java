package component.body.main;

import component.body.allocation.main.AllocationsController;
import component.body.execution.history.main.ExecutionsHistoryController;
import component.body.management.main.ManagementController;
import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TabPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import main.AdminController;
import engine.per.file.engine.api.SystemEngineAccess;

public class TabPaneAdminBodyController {

    @FXML
    private TabPane tabPaneBodyComponent;

    @FXML private VBox managementComponent;
    @FXML private ManagementController managementComponentController;

    @FXML private ScrollPane allocationsComponent;
    @FXML private AllocationsController allocationsComponentController;

    @FXML private HBox executionHistoryComponent;
    @FXML private ExecutionsHistoryController executionHistoryComponentController;

    private AdminController mainAdminController;


    public void setMainController(AdminController mainAdminController) {
        this.mainAdminController = mainAdminController;
    }

    public void setSystemEngineToChildren(SystemEngineAccess systemEngineAccess){
        managementComponentController.setSystemEngine(systemEngineAccess);
        allocationsComponentController.setSystemEngine(systemEngineAccess);
        executionHistoryComponentController.setSystemEngine(systemEngineAccess);
        //body3ComponentController.setMainController(mainController);
        initialChildren();
    }
    private void initialChildren(){
        managementComponentController.primaryInitialize();
        allocationsComponentController.primaryInitialize();
        executionHistoryComponentController.primaryInitialize();
        //body2ComponentController.setBody3Controller(body3ComponentController);
    }


    public void switchToTab1() {
        tabPaneBodyComponent.getSelectionModel().select(0);
        managementComponentController.setVisibleTab();
    }

    public void switchToTab2() {
        tabPaneBodyComponent.getSelectionModel().select(1);
    }

    public void switchToTab3() {
        tabPaneBodyComponent.getSelectionModel().select(2);
    }
}
