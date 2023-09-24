package app.body.screen2.main;

import app.body.screen2.start.button.error.ErrorScreen2Controller;
import app.body.screen2.task.RunSimulationTask;
import app.body.screen3.main.Body3Controller;
import app.body.screen2.start.button.good.StartButtonController;
import app.body.screen2.tile.TileResourceConstants;
import app.body.screen2.tile.entity.EntityController;
import app.body.screen2.tile.environment.variable.EnvironmentVariableController;
import dto.primary.DTOEnvVarsDefForUi;
import dto.primary.DTORerunValuesForUi;
import dto.primary.DTOWorldGridForUi;
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
import javafx.scene.layout.*;
import javafx.stage.Stage;
import engine.per.file.engine.api.SystemEngineAccess;
import engine.per.file.engine.world.definition.entity.manager.api.EntityDefinitionManager;
import engine.per.file.engine.world.execution.instance.environment.api.EnvVariablesInstanceManager;

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

    @FXML
    private VBox vBoxComponent;

    private Map<String, EnvironmentVariableController> envVarNameToTileController;
    private Map<String, EntityController> entityNameToTileController;

    private Map<String, String> envVarNameToSelectedInitValue;
    private Map<String, String> entityNameToSelectedPopulationValue;

    private List<PropertyDefinitionDTO> envVarsList;
    private List<Object> initValues;

    private List<String> entitiesNames;
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
        simulationsCounter = 0;
        simulationEntitiesPopulationFlowPane.getChildren().clear();
        simulationEnvironmentInputsFlowPane.getChildren().clear();
        envVarNameToTileController.clear();
        entityNameToTileController.clear();
        envVarNameToSelectedInitValue.clear();
        entityNameToSelectedPopulationValue.clear();
        VBox.setVgrow(vBoxComponent, Priority.ALWAYS);
        DTOEnvVarsDefForUi dtoEnvVarsDefForUi = systemEngine.getEVDFromSE();
        createEnvVarsChildrenInFlowPane(envVarsList = dtoEnvVarsDefForUi.getEnvironmentVars());
        initValues = new ArrayList<>(Collections.nCopies(envVarsList.size(), null));
        entitiesNames = systemEngine.getEntitiesNames().getNames();
        createEntitiesPopulationChildrenInFlowPane(entitiesNames);
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
    }

    @FXML
    void onClickStartButton(MouseEvent event) {
        if(checkInputsValidity()){
            Stage primaryStage = new Stage();
            try{
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/app/body/screen2/start/button/good/startButton.fxml"));
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
        }
    }

    private boolean checkInputsValidity(){
        List<String> entitiesNamesWithInvalidPopulation = new ArrayList<>();
        List<String> environmentVarsNames = new ArrayList<>();
        int populationQuantity=0;
        boolean invalidPopulationQuantity = false;

        for (String key : entityNameToTileController.keySet()) {
            EntityController entityController = entityNameToTileController.get(key);
            if(! entityController.isPopulationQuantityValidity()){
                entitiesNamesWithInvalidPopulation.add(key);
            }
            else{
                populationQuantity += entityController.getPopulationValue();
            }
        }
        if(populationQuantity > maxPopulationQuantity){
            invalidPopulationQuantity = true;
        }

        for (String key : envVarNameToTileController.keySet()) {
            EnvironmentVariableController environmentVariableController = envVarNameToTileController.get(key);
            if(! environmentVariableController.isInputValid()){
                environmentVarsNames.add(key);
            }
        }

        if(entitiesNamesWithInvalidPopulation.size() > 0 || environmentVarsNames.size() > 0 || invalidPopulationQuantity){
            showErrorWindow(entitiesNamesWithInvalidPopulation, environmentVarsNames, invalidPopulationQuantity);
            return false;
        }
        return true;
    }

    private void showErrorWindow( List<String> entitiesNamesWithInvalidPopulation,
                                  List<String> environmentVarsNames,
                                  boolean invalidPopulationQuantity ){
        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(TileResourceConstants.ERROR_SCREEN_FXML_RESOURCE);
            GridPane root = loader.load();
            ErrorScreen2Controller errorScreen2Controller  =loader.getController();
            if(entitiesNamesWithInvalidPopulation.size() > 0) {
                StringBuilder entitiesText = new StringBuilder();
                for (String entityName : entitiesNamesWithInvalidPopulation) {
                    entitiesText.append(entityName).append("\n");
                }
                errorScreen2Controller.setEntitiesText(entitiesText.toString());

            }
            if(environmentVarsNames.size() > 0) {
                StringBuilder environmentVarsText = new StringBuilder();
                for (String envVarName : environmentVarsNames) {
                    environmentVarsText.append(envVarName).append("\n");
                }
                errorScreen2Controller.setEnvVarsText(environmentVarsText.toString());
            }
            if(entitiesNamesWithInvalidPopulation.size() == 0){
                if(! invalidPopulationQuantity){
                    errorScreen2Controller.setPopulationText("Valid!");
                }
                else{
                    errorScreen2Controller.setPopulationText("Invalid population quantity!\n" +
                            "Max total quantity is " + maxPopulationQuantity);
                }
            }


            Stage primaryStage = new Stage();
            Scene scene = new Scene(root, 550, 300);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Error Window");
            primaryStage.show();
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
    private void setMapsForStartButtonController(){
        for (String key : entityNameToTileController.keySet()) {
            EntityController entityController = entityNameToTileController.get(key);
            if(entityController.getPopulationValue() !=null){
                entityNameToSelectedPopulationValue.put(key, entityController.getPopulationValue().toString());
            }
            else{
                Integer population = 0;
                entityNameToSelectedPopulationValue.put(key, population.toString());
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

        EnvVariablesInstanceManager envVariablesInstanceManager = systemEngine.updateEnvironmentVarDefinition(
                new CreateDTOEnvVarsForSE().getData(envVarNameToTileController, envVarsList));
        EntityDefinitionManager entityDefinitionManager = systemEngine.updateEntitiesPopulation(new CreateDTOPopulationForSE().getData(entityNameToTileController));
        systemEngine.addWorldInstance(simulationsCounter, envVariablesInstanceManager, entityDefinitionManager);
        body3ComponentController.addItemToSimulationListView(simulationsCounter);

        RunSimulationTask runSimulationTask = new RunSimulationTask(systemEngine, simulationsCounter,body3ComponentController);
        systemEngine.addTaskToQueue(runSimulationTask);
        clearScreen();
    }

    public void setBody3Controller(Body3Controller body3ComponentController) {
        this.body3ComponentController = body3ComponentController;
    }

    public void setTilesByRerun(Integer simulationID){
        DTORerunValuesForUi dtoRerunValuesForUi= systemEngine.getValuesForRerun(simulationID);
        Map<String, Object> environmentVarsValues =dtoRerunValuesForUi.getEnvironmentVarsValues();
        Map<String, Integer> entitiesPopulations = dtoRerunValuesForUi.getEntitiesPopulations();

        for (String key : entityNameToTileController.keySet()) {
            EntityController entityController = entityNameToTileController.get(key);
            entityController.setPopulationTextField(entitiesPopulations.get(key).toString());
        }

        for (String key : envVarNameToTileController.keySet()) {
            EnvironmentVariableController environmentVariableController = envVarNameToTileController.get(key);
            if(environmentVarsValues.get(key) != null){
                environmentVariableController.setValueTextField(environmentVarsValues.get(key).toString());
            }
            else{
                environmentVariableController.setValueTextField("");
            }

        }
    }
}

