package app;

import app.body.TabPaneBodyController;
import app.body.screen1.Body1Controller;
import app.header.HeaderController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TabPane;
import javafx.scene.layout.GridPane;
import system.engine.api.SystemEngineAccess;
import system.engine.impl.SystemEngineAccessImpl;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;

public class AppController {

    @FXML private GridPane headerComponent;
    @FXML private HeaderController headerComponentController;
    @FXML private TabPane tabPaneBodyComponent;
    @FXML private TabPaneBodyController tabPaneBodyComponentController;



    private SystemEngineAccess systemEngine;

    public AppController(){
        systemEngine = new SystemEngineAccessImpl();

        try {
            systemEngine.getXMLFromUser("C:/Users/nikolnisanov/Documents/JAVA/predictionsEx2New/ex1-cigarets.xml");
        } catch (JAXBException e) {
            throw new RuntimeException(e);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void initialize() {
        if (headerComponentController != null && tabPaneBodyComponentController != null) {
            headerComponentController.setMainController(this);
            tabPaneBodyComponentController.setMainController(this);
        }
        tabPaneBodyComponentController.setSystemEngineToChildren(systemEngine);
    }

    public SystemEngineAccess getSystemEngine() {
        return systemEngine;
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

    public void onLoadFileButtonClick() {

    }

    public void onNewExecutionButtonClick() {
        tabPaneBodyComponentController.switchToTab2();
    }

    public void onQueueManagementButtonClick() {

    }

    public void onResultButtonClick() {

    }
}
