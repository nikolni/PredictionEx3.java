package app.body.screen2.start.Button;

import app.body.screen2.main.Body2Controller;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;


public class StartButtonController {
    @FXML
    private Button cancelButton;

    @FXML
    private Button continueButton;

    @FXML
    private FlowPane simulationEntitiesPopulationFlowPane;

    @FXML
    private FlowPane simulationEnvironmentInputsFlowPane;

    private Body2Controller callerController;
    private Stage primaryStage;

    public StartButtonController() {
        simulationEntitiesPopulationFlowPane = new FlowPane();
        simulationEnvironmentInputsFlowPane = new FlowPane();
    }

    @FXML
    void onCancelClick(MouseEvent event) {
        primaryStage.close();
    }

    @FXML
    void onContinueClick(MouseEvent event) {
        callerController.startSimulation();
        primaryStage.close();
    }

    public void setCallerController(Body2Controller callerController) {
        this.callerController = callerController;
    }

    public void setStage(Stage primaryStage){
        this.primaryStage = primaryStage;

    }

    public void setSimulationEntitiesPopulationFlowPane(FlowPane simulationEntitiesPopulationFlowPane) {
        this.simulationEntitiesPopulationFlowPane = simulationEntitiesPopulationFlowPane;
        simulationEntitiesPopulationFlowPane.setVisible(true);
    }

    public void setSimulationEnvironmentInputsFlowPane(FlowPane simulationEnvironmentInputsFlowPane) {
        this.simulationEnvironmentInputsFlowPane = simulationEnvironmentInputsFlowPane;
        simulationEnvironmentInputsFlowPane.setVisible(true);
    }
}
