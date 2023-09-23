package main;

import component.body.main.TabPaneAdminBodyController;
import component.header.HeaderController;
import dto.primary.DTOThreadsPoolStatusForUi;
import javafx.fxml.FXML;
import javafx.scene.control.TabPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import system.engine.api.SystemEngineAccess;
import system.engine.impl.SystemEngineAccessImpl;

public class AdminController {

    @FXML private VBox headerComponent;
    @FXML private HeaderController headerComponentController;
    @FXML private TabPane tabPaneAdminBodyComponent;
    @FXML private TabPaneAdminBodyController tabPaneAdminBodyComponentController;



    private SystemEngineAccess systemEngine;

    public AdminController(){
        systemEngine = new SystemEngineAccessImpl();

        /*try {
            systemEngine.getXMLFromUser("C:/Users/maaya/javaProjects/PredictionsEX2_2.9/ex1-cigarets.xml");
        } catch (JAXBException e) {
            throw new RuntimeException(e);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }*/
    }

    @FXML
    public void initialize() {
        if (headerComponentController != null && tabPaneAdminBodyComponentController != null) {
            headerComponentController.setMainController(this);
            tabPaneAdminBodyComponentController.setMainController(this);
        }
        headerComponentController.setSystemEngine(systemEngine);
    }

    public SystemEngineAccess getSystemEngine() {
        return systemEngine;
    }


    public void onAllocationsClick(MouseEvent event) {
        tabPaneAdminBodyComponentController.switchToTab2();
    }

    public void onExecutionsHistoryClick(MouseEvent event) {
        tabPaneAdminBodyComponentController.switchToTab3();
    }

    public void onManagementClick(MouseEvent event) {
        tabPaneAdminBodyComponentController.switchToTab1();
    }


}
