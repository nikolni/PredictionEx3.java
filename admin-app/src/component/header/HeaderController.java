package component.header;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import main.AdminController;
import engine.per.file.engine.api.SystemEngineAccess;

public class HeaderController {

    @FXML
    private Button managementButton;

    @FXML
    private Button allocationsButton;

    @FXML
    private Button executionsHistoryButton;

    private SystemEngineAccess systemEngine;
    private AdminController mainAdminController;

    public void setSystemEngine(SystemEngineAccess systemEngineAccess){
        this.systemEngine = systemEngineAccess;
    }
    public void setMainController(AdminController mainAdminController) {
        this.mainAdminController = mainAdminController;
    }

    @FXML
    void onAllocationsClick(MouseEvent event) {

    }

    @FXML
    void onExecutionsHistoryClick(MouseEvent event) {

    }

    @FXML
    void onManagementClick(MouseEvent event) {

    }

}
