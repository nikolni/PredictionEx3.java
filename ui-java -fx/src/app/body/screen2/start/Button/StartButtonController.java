package app.body.screen2.start.Button;

import app.body.screen2.Body2Controller;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;


public class StartButtonController {
    @FXML
    private Button cancelButton;

    @FXML
    private Button continueButton;

    private Body2Controller callerController;
    private Stage primaryStage;

    @FXML
    void onCancelClick(MouseEvent event) {
        primaryStage.close();
    }

    @FXML
    void onContinueClick(MouseEvent event) {
        callerController.startSimulation();
    }

    public void setCallerController(Body2Controller callerController) {
        this.callerController = callerController;
    }

    public void setStage(Stage primaryStage){
        this.primaryStage = primaryStage;

    }
}
