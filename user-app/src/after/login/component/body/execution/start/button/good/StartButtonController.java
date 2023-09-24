package after.login.component.body.execution.start.button.good;

import after.login.component.body.execution.main.ExecutionController;
import after.login.component.body.execution.tile.entity.start.button.EntityStartButtonController;
import after.login.component.body.execution.tile.TileResourceConstants;
import after.login.component.body.execution.tile.environment.variable.start.button.EnvironmentVariableStartButtonController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Map;


public class StartButtonController {
    @FXML
    private Button cancelButton;

    @FXML
    private Button continueButton;

    @FXML
    private FlowPane simulationEntitiesPopulationFlowPane;

    @FXML
    private FlowPane simulationEnvironmentInputsFlowPane;

    private ExecutionController callerController;
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

    public void setCallerController(ExecutionController executionController) {
        this.callerController = executionController;
    }

    public void setStage(Stage primaryStage){
        this.primaryStage = primaryStage;

    }

    public void setSimulationEntitiesPopulationFlowPane(Map<String, String> entityNameToSelectedPopulationValue) {
        for(String key : entityNameToSelectedPopulationValue.keySet()){
            try{
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(TileResourceConstants.ENTITY_START_BUTTON_FXML_RESOURCE);
                Node singleEnvVar = loader.load();

                EntityStartButtonController entityStartButtonController = loader.getController();
                entityStartButtonController.setEntityNameLabel(key);
                entityStartButtonController.setPopulationValueLabel(entityNameToSelectedPopulationValue.get(key));

                simulationEntitiesPopulationFlowPane.getChildren().add(singleEnvVar);
            }
            catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void setSimulationEnvironmentInputsFlowPane(Map<String, String> envVarNameToSelectedInitValue) {
        for(String key : envVarNameToSelectedInitValue.keySet()){
            try{
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(TileResourceConstants.ENVIRONMENT_VAR_START_BUTTON_FXML_RESOURCE);
                Node singleEnvVar = loader.load();

                EnvironmentVariableStartButtonController environmentVariableController = loader.getController();
                environmentVariableController.setEnvVarNameLabel(key);
                environmentVariableController.setValueLabel(envVarNameToSelectedInitValue.get(key));
                simulationEnvironmentInputsFlowPane.getChildren().add(singleEnvVar);
            }
            catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
