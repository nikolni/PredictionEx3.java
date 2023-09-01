package app.body.main;

import app.main.AppController;
import app.body.screen1.Body1Controller;
import app.body.screen2.main.Body2Controller;
import app.body.screen3.Body3Controller;
import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TabPane;
import javafx.scene.layout.HBox;
import system.engine.api.SystemEngineAccess;

public class TabPaneBodyController {

    @FXML
    private TabPane tabPaneBodyComponent;

    @FXML private HBox body1Component;
    @FXML private Body1Controller body1ComponentController;

    @FXML private ScrollPane body2Component;
    @FXML private Body2Controller body2ComponentController;

    @FXML private HBox body3Component;
    @FXML private Body3Controller body3ComponentController;


    private AppController mainController;


    public void setMainController(AppController mainController) {
        this.mainController = mainController;
    }

    public void setSystemEngineToChildren(SystemEngineAccess systemEngineAccess){
        body1ComponentController.setSystemEngine(systemEngineAccess);
        body2ComponentController.setSystemEngine(systemEngineAccess);
        //body3ComponentController.setSystemEngine(systemEngineAccess);
        initialChildren();

    }
    private void initialChildren(){
        body1ComponentController.primaryInitialize();
        body2ComponentController.primaryInitialize();
       // body3ComponentController.primaryInitialize();
    }


    public void switchToTab1() {
        tabPaneBodyComponent.getSelectionModel().select(0);
        body1ComponentController.setVisibleTab();
        body2ComponentController.setUnVisibleTab();
       // body3ComponentController.setUnVisibleTab();
    }

    public void switchToTab2() {
        tabPaneBodyComponent.getSelectionModel().select(1);
        //body2ComponentController.setVisibleTab();
        body1ComponentController.setUnVisibleTab();
        // body3ComponentController.setUnVisibleTab();
    }

    public void switchToTab3() {
        tabPaneBodyComponent.getSelectionModel().select(2);
        //body3ComponentController.setVisibleTab();
        body1ComponentController.setUnVisibleTab();
        body2ComponentController.setUnVisibleTab();
    }
}
