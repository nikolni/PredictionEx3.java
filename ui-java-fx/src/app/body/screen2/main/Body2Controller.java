package app.body.screen2.main;

import app.body.screen2.task.RunSimulationTask;
import app.body.screen3.main.Body3Controller;
import app.body.screen3.simulation.progress.SimulationProgressController;
import app.body.screen2.start.Button.StartButtonController;
import app.body.screen2.tile.TileResourceConstants;
import app.body.screen2.tile.entity.EntityController;
import app.body.screen2.tile.environment.variable.EnvironmentVariableController;
import dto.api.DTOEnvVarsDefForUi;
import dto.api.DTOWorldGridForUi;
import dto.creation.CreateDTOEnvVarsForSE;
import dto.creation.CreateDTOPopulationForSE;
import dto.definition.property.definition.api.PropertyDefinitionDTO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import system.engine.api.SystemEngineAccess;

import java.io.IOException;

import java.util.*;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class Body2Controller {
    @FXML
    private FlowPane simulationEntitiesPopulationFlowPane;

    @FXML
    private FlowPane simulationEnvironmentInputsFlowPane;

    @FXML
    private Button clearButton;

    @FXML
    private Button startButton;

    @FXML
    private VBox vBoxComponent;

    private Map<String, EnvironmentVariableController> envVarNameToTileController;
    private Map<String, EntityController> entityNameToTileController;

    private Map<String, String> envVarNameToSelectedInitValue;
    private Map<String, String> entityNameToSelectedPopulationValue;

    private List<PropertyDefinitionDTO> envVarsList;
    private List<Object> initValues;

    private List<String> entitiesNames;
    private List<Integer> entitiesPopulations;
    private SystemEngineAccess systemEngine;
    private Body3Controller body3ComponentController;

    private Integer simulationsCounter = 0;



    private int maxPopulationQuantity;


    public Body2Controller() {
        simulationEntitiesPopulationFlowPane = new FlowPane();
        simulationEnvironmentInputsFlowPane = new FlowPane();
        envVarNameToTileController =new HashMap<>();
        entityNameToTileController = new HashMap<>();
        envVarNameToSelectedInitValue =new HashMap<>();
        entityNameToSelectedPopulationValue = new HashMap<>();
        simulationEntitiesPopulationFlowPane.getChildren().clear();
        simulationEnvironmentInputsFlowPane.getChildren().clear();
    }


    public void primaryInitialize() {
        VBox.setVgrow(vBoxComponent, Priority.ALWAYS);
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
                envVarNameToSelectedInitValue.put(propertyDefinitionDTO.getUniqueName(), null);
            }
            catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void createEntitiesPopulationChildrenInFlowPane(List<String> entitiesNames){
        DTOWorldGridForUi dtoWorldGridForUi = systemEngine.getDTOWorldGridForUi();
        maxPopulationQuantity = dtoWorldGridForUi.getGridRows() * dtoWorldGridForUi.getGridColumns();

        for(String entityName: entitiesNames){
            try{
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(TileResourceConstants.ENTITY_FXML_RESOURCE);
                Node singlePopulation = loader.load();

                EntityController entityController = loader.getController();
                entityController.setEntitiesNames(entitiesNames);
                entityController.setEntityNameLabel(entityName);
                entityController.setCallerController(this);
                entityNameToTileController.put(entityName, entityController);
                simulationEntitiesPopulationFlowPane.getChildren().add(singlePopulation);
                entityNameToSelectedPopulationValue.put(entityName, null);
            }
            catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public int getMaxPopulationQuantity() {
        return maxPopulationQuantity;
    }

    public Boolean isPopulationQuantityValid(int populationQuantity){
        return (maxPopulationQuantity - populationQuantity) >= 0;
    }

    public void decreaseMaxPopulationQuantity(int populationQuantity){
       maxPopulationQuantity -= populationQuantity;
    }
    public void increaseMaxPopulationQuantity(int populationQuantity){
        maxPopulationQuantity += populationQuantity;
    }


    @FXML
    void onClickClearButton(MouseEvent event) {
        clearScreen();
    }

    public void clearScreen(){
        simulationEntitiesPopulationFlowPane.getChildren().clear();
        simulationEnvironmentInputsFlowPane.getChildren().clear();
        createEnvVarsChildrenInFlowPane(envVarsList );
        createEntitiesPopulationChildrenInFlowPane(entitiesNames);

        /*for (String key : entityNameToTileController.keySet()) {
            EntityController entityController = entityNameToTileController.get(key);
            entityController.resetTextField();
        }

        for (String key : envVarNameToTileController.keySet()) {
            EnvironmentVariableController environmentVariableController = envVarNameToTileController.get(key);
            environmentVariableController.resetTextField();
        }*/
    }

    @FXML
    void onClickStartButton(MouseEvent event) {
        Stage primaryStage = new Stage();
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/app/body/screen2/start/Button/startButton.fxml"));
            ScrollPane root = loader.load();

            StartButtonController startButtonController = loader.getController();
            startButtonController.setCallerController(this);
            startButtonController.setStage(primaryStage);
            setMapsForStartButtonController();

            startButtonController.setSimulationEntitiesPopulationFlowPane(entityNameToSelectedPopulationValue);
            startButtonController.setSimulationEnvironmentInputsFlowPane(envVarNameToSelectedInitValue);


            Scene scene = new Scene(root, 620, 400);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Warning!");
            primaryStage.show();
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
        //primaryStage.close();
    }

    private void setMapsForStartButtonController(){
        for (String key : entityNameToTileController.keySet()) {
            EntityController entityController = entityNameToTileController.get(key);
            if(entityController.getPopulationValue() !=null){
                entityNameToSelectedPopulationValue.put(key, entityController.getPopulationValue().toString());
            }
            else{
                entityNameToSelectedPopulationValue.put(key, "");
            }
        }

        for (String key : envVarNameToTileController.keySet()) {
            EnvironmentVariableController environmentVariableController = envVarNameToTileController.get(key);
            if(environmentVariableController.getValueTextField() !=null){
                envVarNameToSelectedInitValue.put(key, environmentVariableController.getValueTextField().toString());
            }
            else{
                envVarNameToSelectedInitValue.put(key, "random");
            }
        }
    }
    public void startSimulation(){
        simulationsCounter++;

        systemEngine.updateEnvironmentVarDefinition(new CreateDTOEnvVarsForSE().getData(envVarNameToTileController, envVarsList));
        systemEngine.updateEntitiesPopulation(new CreateDTOPopulationForSE().getData(entityNameToTileController));
        systemEngine.addWorldInstance(simulationsCounter);
        body3ComponentController.addItemToSimulationListView(simulationsCounter);

        RunSimulationTask runSimulationTask = new RunSimulationTask(systemEngine, simulationsCounter,body3ComponentController);
        systemEngine.addTaskToQueue(runSimulationTask);
        clearScreen();

        /*try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/app/body/screen3/simulation/progress/simulationProgress.fxml"));
            VBox simulationProgressNode = loader.load();
            SimulationProgressController simulationProgressController = loader.getController();
            System.out.println("simulation num " + simulationsCounter + " with controller address " + simulationProgressController);
            body3ComponentController.addNewSimulationProgressToList(simulationProgressNode, simulationProgressController);
            body3ComponentController.addNewSimulationToSimulationsList(simulationsCounter);
            simulationProgressController.setBody3ComponentController(body3ComponentController);
            simulationProgressController.setSimulationIdLabel(simulationsCounter.toString());
            simulationProgressController.runSimulation(systemEngine, simulationsCounter);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }*/

    }




    public PropertyDefinitionDTO getEnvVarByName(String name) {
        for (PropertyDefinitionDTO propertyDefinitionDTO : envVarsList) {
            if (propertyDefinitionDTO.getUniqueName().equals(name)) {
                return propertyDefinitionDTO;
            }
        }
        throw new IllegalArgumentException("Can't find entity with name " + name);
    }

    public void setBody3Controller(Body3Controller body3ComponentController) {
        this.body3ComponentController = body3ComponentController;
    }
}

