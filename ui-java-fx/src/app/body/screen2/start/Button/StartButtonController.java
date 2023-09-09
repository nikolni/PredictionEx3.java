package app.body.screen2.start.Button;

import app.body.screen2.main.Body2Controller;
import app.body.screen2.tile.TileResourceConstants;
import app.body.screen2.tile.entity.start.button.EntityStartButtonController;
import app.body.screen2.tile.environment.variable.EnvironmentVariableController;
import app.body.screen2.tile.environment.variable.start.button.EnvironmentVariableStartButtonController;
import dto.definition.property.definition.api.PropertyDefinitionDTO;
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
        //callerController.clearScreen();
        primaryStage.close();
    }

    public void setCallerController(Body2Controller callerController) {
        this.callerController = callerController;
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
