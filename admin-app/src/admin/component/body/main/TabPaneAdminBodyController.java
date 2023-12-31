package admin.component.body.main;

import admin.component.body.execution.history.main.ExecutionsHistoryController;
import admin.main.AdminController;
import admin.component.body.allocation.main.AllocationsController;
import admin.component.body.management.main.ManagementController;
import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TabPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;


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
        setControllersForChildren();
    }



    public void setControllersForChildren(){
        managementComponentController.setMainController(mainAdminController);
        allocationsComponentController.setMainController(mainAdminController);
        initialChildren();
    }


    private void initialChildren(){
        executionHistoryComponentController.primaryInitialize();
    }


    public void switchToTab1() {
        tabPaneBodyComponent.getSelectionModel().select(0);
        //managementComponentController.setVisibleTab();
    }

    public void switchToTab2() {
        tabPaneBodyComponent.getSelectionModel().select(1);
        allocationsComponentController.startListRefresher();
    }

    public void switchToTab3() {
        tabPaneBodyComponent.getSelectionModel().select(2);
    }
}
