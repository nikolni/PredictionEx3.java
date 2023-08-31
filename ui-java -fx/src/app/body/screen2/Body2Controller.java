package app.body.screen2;

import app.body.screen2.start.Button.StartButtonController;
import app.body.screen2.tile.TileResourceConstants;
import app.body.screen2.tile.entity.EntityController;
import app.body.screen2.tile.environment.variable.EnvironmentVariableController;
import dto.api.DTOEnvVarsDefForUi;
import dto.creation.CreateDTOEnvVarsForSE;
import dto.creation.CreateDTOPopulationForSE;
import dto.definition.property.definition.api.PropertyDefinitionDTO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import system.engine.api.SystemEngineAccess;

import java.io.IOException;

import java.util.*;
import java.util.List;


public class Body2Controller {
    @FXML
    private FlowPane simulationEntitiesPopulationFlowPane;

    @FXML
    private FlowPane simulationEnvironmentInputsFlowPane;

    @FXML
    private Button clearButton;

    @FXML
    private Button startButton;

    private Map<String, EnvironmentVariableController> envVarNameToTileController;
    private Map<String, EntityController> entityNameToTileController;

    private List<PropertyDefinitionDTO> envVarsList;
    private List<Object> initValues;

    private List<String> entitiesNames;
    private List<Integer> entitiesPopulations;

    private SystemEngineAccess systemEngine;


    public Body2Controller() {
        simulationEntitiesPopulationFlowPane = new FlowPane();
        simulationEnvironmentInputsFlowPane = new FlowPane();
        envVarNameToTileController =new HashMap<>();
        entityNameToTileController = new HashMap<>();
        simulationEntitiesPopulationFlowPane.getChildren().clear();
        simulationEnvironmentInputsFlowPane.getChildren().clear();
    }


    public void primaryInitialize() {
        DTOEnvVarsDefForUi dtoEnvVarsDefForUi = systemEngine.getEVDFromSE();
        createEnvVarsChildrenInFlowPane(envVarsList = dtoEnvVarsDefForUi.getEnvironmentVars());
        initValues = new ArrayList<>(Collections.nCopies(envVarsList.size(), null));
        entitiesNames = systemEngine.getEntitiesNames().getNames();
        createEntitiesPopulationChildrenInFlowPane(entitiesNames);
    }


    public void setVisibleTab(){
        simulationEntitiesPopulationFlowPane.setVisible(true);
        simulationEnvironmentInputsFlowPane.setVisible(true);
    }

    public void setUnVisibleTab(){
        simulationEntitiesPopulationFlowPane.setVisible(false);
        simulationEnvironmentInputsFlowPane.setVisible(false);
    }

    public void setSystemEngine(SystemEngineAccess systemEngineAccess){
        this.systemEngine = systemEngineAccess;
    }

    private void createEnvVarsChildrenInFlowPane(List<PropertyDefinitionDTO> envVarsList){
        for(PropertyDefinitionDTO propertyDefinitionDTO: envVarsList){
            try{
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(TileResourceConstants.ENVIRONMENT_VAR_FXML_RESOURCE);
                Node singleEnvVar = loader.load();

                EnvironmentVariableController environmentVariableController = loader.getController();
                environmentVariableController.setEnvVarNameLabel(propertyDefinitionDTO.getUniqueName());
                environmentVariableController.setEnvVarTypeLabel(propertyDefinitionDTO.getType());
                envVarNameToTileController.put(propertyDefinitionDTO.getUniqueName(), environmentVariableController);
                simulationEnvironmentInputsFlowPane.getChildren().add(singleEnvVar);
            }
            catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void createEntitiesPopulationChildrenInFlowPane(List<String> entitiesNames){
        for(String entityName: entitiesNames){
            try{
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(TileResourceConstants.ENTITY_FXML_RESOURCE);
                Node singlePopulation = loader.load();

                EntityController entityController = loader.getController();
                entityController.setEntitiesNames(entitiesNames);
                entityController.setEntityNameLabel(entityName);
                entityNameToTileController.put(entityName, entityController);
                simulationEntitiesPopulationFlowPane.getChildren().add(singlePopulation);
            }
            catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }



    @FXML
    void onClickClearButton(MouseEvent event) {
        for (String key : entityNameToTileController.keySet()) {
            EntityController entityController = entityNameToTileController.get(key);
            entityController.resetTextField();
        }

        for (String key : envVarNameToTileController.keySet()) {
            EnvironmentVariableController environmentVariableController = envVarNameToTileController.get(key);
            environmentVariableController.resetTextField();
        }
    }

    @FXML
    void onClickStartButton(MouseEvent event) {
        Stage primaryStage = new Stage();
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/app/body/screen2/start/Button/startButton.fxml"));
            GridPane root = loader.load();

            StartButtonController startButtonController = loader.getController();
            startButtonController.setCallerController(this);
            startButtonController.setStage(primaryStage);


            Scene scene = new Scene(root, 500, 150);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Warning!");
            primaryStage.show();
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
        //primaryStage.close();
    }

    public void startSimulation(){
        systemEngine.updateEnvironmentVarDefinition(new CreateDTOEnvVarsForSE().getData(envVarNameToTileController, envVarsList));
        systemEngine.updateEntitiesPopulation(new CreateDTOPopulationForSE().getData(entityNameToTileController));
        systemEngine.runSimulation();
    }


    public PropertyDefinitionDTO getEnvVarByName(String name) {
        for (PropertyDefinitionDTO propertyDefinitionDTO : envVarsList) {
            if (propertyDefinitionDTO.getUniqueName().equals(name)) {
                return propertyDefinitionDTO;
            }
        }
        throw new IllegalArgumentException("Can't find entity with name " + name);
    }

}

