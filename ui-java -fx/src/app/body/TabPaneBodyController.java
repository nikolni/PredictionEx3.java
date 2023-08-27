package app.body;

import app.AppController;
import app.body.screen1.Body1Controller;
import app.body.screen2.Body2Controller;
import app.body.screen3.Body3Controller;
import app.header.HeaderController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TabPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

public class TabPaneBodyController {

    @FXML
    private TabPane tabPaneBodyComponent;

    @FXML private HBox body1Component;
    @FXML private Body1Controller body1ComponentController;

    @FXML private HBox body2ComponentComponent;
    @FXML private Body2Controller body2ComponentController;

    @FXML private HBox body3ComponentComponent;
    @FXML private Body3Controller body3ComponentController;



    private AppController mainController;

    public void setMainController(AppController mainController) {
        this.mainController = mainController;
    }


    public void switchToTab1() {
        tabPaneBodyComponent.getSelectionModel().select(0);
    }

    public void switchToTab2() {
        tabPaneBodyComponent.getSelectionModel().select(1);
    }

    public void switchToTab3() {
        tabPaneBodyComponent.getSelectionModel().select(2);
    }
}
