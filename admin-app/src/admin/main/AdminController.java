package admin.main;

import admin.component.body.main.TabPaneAdminBodyController;
import admin.component.header.HeaderController;
import javafx.fxml.FXML;
import javafx.scene.control.TabPane;
import javafx.scene.layout.VBox;


public class AdminController {

    @FXML private VBox headerComponent;
    @FXML private HeaderController headerComponentController;
    @FXML private TabPane tabPaneAdminBodyComponent;
    @FXML private TabPaneAdminBodyController tabPaneAdminBodyComponentController;

    @FXML
    public void initialize() {
        if (headerComponentController != null && tabPaneAdminBodyComponentController != null) {
            headerComponentController.setMainController(this);
            tabPaneAdminBodyComponentController.setMainController(this);
        }

    }




    public void onAllocationsClick() {
        tabPaneAdminBodyComponentController.switchToTab2();
    }

    public void onExecutionsHistoryClick() {
        tabPaneAdminBodyComponentController.switchToTab3();
    }

    public void onManagementClick() {
        tabPaneAdminBodyComponentController.switchToTab1();
    }


}
