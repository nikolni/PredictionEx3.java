package app;

import app.body.TabPaneBodyController;
import app.body.screen1.Body1Controller;
import app.header.HeaderController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TabPane;
import javafx.scene.layout.GridPane;

public class AppController {

    @FXML private GridPane headerComponent;
    @FXML private HeaderController headerComponentController;
    @FXML private TabPane tabPaneBodyComponent;
    @FXML private TabPaneBodyController tabPaneBodyComponentController;

    @FXML
    public void initialize() {
        if (headerComponentController != null && tabPaneBodyComponentController != null) {
            headerComponentController.setMainController(this);
            tabPaneBodyComponentController.setMainController(this);
        }
    }

    public void setHeaderComponentController(HeaderController headerComponentController) {
        this.headerComponentController = headerComponentController;
        headerComponentController.setMainController(this);
    }

    public void setBodyComponentController(TabPaneBodyController bodyComponentController) {
        this.tabPaneBodyComponentController = bodyComponentController;
        tabPaneBodyComponentController.setMainController(this);
    }

    public void onDetailsButtonClick(){
        tabPaneBodyComponentController.switchToTab1();
    }

    void onLoadFileButtonClick(ActionEvent event) {

    }

    void onNewExecutionButtonClick(ActionEvent event) {

    }

    void onQueueManagementButtonClick(ActionEvent event) {

    }

    void onResultButtonClick(ActionEvent event) {

    }
}
