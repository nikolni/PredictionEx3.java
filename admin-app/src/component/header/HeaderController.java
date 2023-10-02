package component.header;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import main.AdminController;


public class HeaderController {

    @FXML
    private Button managementButton;

    @FXML
    private Button allocationsButton;

    @FXML
    private Button executionsHistoryButton;


    private AdminController mainAdminController;


    public void setMainController(AdminController mainAdminController) {
        this.mainAdminController = mainAdminController;
    }

    @FXML
    void onAllocationsClick(MouseEvent event) {
        mainAdminController.onAllocationsClick();
    }

    @FXML
    void onExecutionsHistoryClick(MouseEvent event) {
        mainAdminController.onExecutionsHistoryClick();
    }

    @FXML
    void onManagementClick(MouseEvent event) {
        mainAdminController.onManagementClick();
    }

}
