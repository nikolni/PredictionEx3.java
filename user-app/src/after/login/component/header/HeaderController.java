package after.login.component.header;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import after.login.main.UserController;

public class HeaderController {

    @FXML
    private Button simulationDetailsButton;

    @FXML
    private Button requestButton;

    @FXML
    private Button executionButton;

    @FXML
    private Button resultsButton;
    private UserController mainController;

    public void setMainController(UserController mainController){
        this.mainController = mainController;
    }

    @FXML
    void onExecutionClick(MouseEvent event) {
        mainController.onExecutionClickFromHeader();
    }

    @FXML
    void onRequestClick(MouseEvent event) {
        mainController.onRequestClick();
    }

    @FXML
    void onResultsClick(MouseEvent event) {
        mainController.onResultsClick();
    }

    @FXML
    void onSimulationDetailsClick(MouseEvent event) {
        mainController.onSimulationDetailsClick();
    }

}
