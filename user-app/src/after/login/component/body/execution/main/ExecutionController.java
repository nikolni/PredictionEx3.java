package after.login.component.body.execution.main;

import after.login.component.body.execution.start.button.error.ErrorExecutionScreenController;
import after.login.component.body.execution.tile.environment.variable.EnvironmentVariableController;
import after.login.component.body.execution.start.button.good.StartButtonController;
import after.login.component.body.execution.tile.TileResourceConstants;
import after.login.component.body.execution.tile.entity.EntityController;
import after.login.component.body.execution.server.RequestsFromServer;
import after.login.component.body.running.main.ProgressAndResultController;
import after.login.dto.creation.CreateDTOEnvVarsForSE;
import after.login.dto.creation.CreateDTOPopulationForSE;
import after.login.main.UserController;
import dto.definition.property.definition.PropertyDefinitionDTO;
import dto.include.DTOIncludeForExecutionForUi;
import dto.primary.*;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.*;


public class ExecutionController {
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

    private final Map<String, EnvironmentVariableController> envVarNameToTileController;
    private final Map<String, EntityController> entityNameToTileController;

    private final Map<String, String> envVarNameToSelectedInitValue;
    private final Map<String, String> entityNameToSelectedPopulationValue;
    private List<PropertyDefinitionDTO> envVarsList;
    private List<String> entitiesNames;
    private ProgressAndResultController progressAndResultController;
    private Integer simulationsCounter = 0;
    private int maxPopulationQuantity;
    private String simulationName;
    private DTOWorldGridForUi dtoWorldGridForUi;
    private RequestsFromServer requestsFromServer = null;
    private String requestID;
    private UserController mainController;


    public ExecutionController() {
        //simulationEntitiesPopulationFlowPane = new FlowPane();
        //simulationEnvironmentInputsFlowPane = new FlowPane();
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
        DTOIncludeForExecutionForUi dtoIncludeForExecutionForUi = requestsFromServer.
                getDataForExecutionFromServer(simulationName);
        DTOEnvVarsDefForUi dtoEnvVarsDefForUi = dtoIncludeForExecutionForUi.getDtoEnvVarsDefForUi();
        createEnvVarsChildrenInFlowPane(envVarsList = dtoEnvVarsDefForUi.getEnvironmentVars());
        entitiesNames = dtoIncludeForExecutionForUi.getDtoNamesListForUi().getNames();
        dtoWorldGridForUi = dtoIncludeForExecutionForUi.getDtoWorldGridForUi();
        createEntitiesPopulationChildrenInFlowPane(entitiesNames);
    }
    public void disableController(){
        for(EnvironmentVariableController tile : envVarNameToTileController.values()){
            tile.disableTextField();
        }
        for(EntityController tile : entityNameToTileController.values()){
            tile.disableTextField();
        }
        startButton.setDisable(true);
        clearButton.setDisable(true);
    }
    public void enableController(){
        for(EnvironmentVariableController tile : envVarNameToTileController.values()){
            tile.enableTextField();
        }
        for(EntityController tile : entityNameToTileController.values()){
            tile.enableTextField();
        }
        startButton.setDisable(false);
        clearButton.setDisable(false);
    }

    public void setSimulationNameAndRequestID(String simulationName, String requestID) {
        this.simulationName = simulationName;
        this.requestID = requestID;
    }
    public void setMainController(UserController mainController) {
        this.mainController = mainController;
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
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/after/login/component/body/execution/start/button/good/startButton.fxml"));
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
            ErrorExecutionScreenController errorExecutionScreenController =loader.getController();
            if(entitiesNamesWithInvalidPopulation.size() > 0) {
                StringBuilder entitiesText = new StringBuilder();
                for (String entityName : entitiesNamesWithInvalidPopulation) {
                    entitiesText.append(entityName).append("\n");
                }
                errorExecutionScreenController.setEntitiesText(entitiesText.toString());

            }
            if(environmentVarsNames.size() > 0) {
                StringBuilder environmentVarsText = new StringBuilder();
                for (String envVarName : environmentVarsNames) {
                    environmentVarsText.append(envVarName).append("\n");
                }
                errorExecutionScreenController.setEnvVarsText(environmentVarsText.toString());
            }
            if(entitiesNamesWithInvalidPopulation.size() == 0){
                if(! invalidPopulationQuantity){
                    errorExecutionScreenController.setPopulationText("Valid!");
                }
                else{
                    errorExecutionScreenController.setPopulationText("Invalid population quantity!\n" +
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
        DTOEnvVarDefValuesForSE dtoEnvVarDefValuesForSE = new CreateDTOEnvVarsForSE().getData(envVarNameToTileController, envVarsList);
        DTOPopulationValuesForSE dtoPopulationValuesForSE = new CreateDTOPopulationForSE().getData(entityNameToTileController);
        requestsFromServer.postRequestExecutionToServer(dtoEnvVarDefValuesForSE, dtoPopulationValuesForSE, simulationName,
                mainController.getUserName(), requestID);
        progressAndResultController.addItemToSimulationListView(simulationsCounter);
        clearScreen();
    }

    public void setProgressAndResultController(ProgressAndResultController progressAndResultController) {
        this.progressAndResultController = progressAndResultController;
    }

    public void setTilesByRerun(Integer executionID){
        DTORerunValuesForUi dtoRerunValuesForUi= requestsFromServer.getRerunValuesFromServer(simulationName, executionID);
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

