package app.body.screen2;

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
import javafx.scene.layout.FlowPane;
import system.engine.api.SystemEngineAccess;
import system.engine.impl.SystemEngineAccessImpl;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.io.IOException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class Body2Controller{
    @FXML
    private FlowPane simulationEntitiesPopulationFlowPane;

    @FXML
    private FlowPane simulationEnvironmentInputsFlowPane;

    @FXML
    private Button clearButton;
    @FXML
    private Button startButton;

    private List<PropertyDefinitionDTO> envVarsList;
    private List<Object> initValues;

    private List<String> entitiesNames;
    private List<Integer> entitiesPopulations;

    private SystemEngineAccess systemEngine = new SystemEngineAccessImpl();


    public Body2Controller(){
        simulationEntitiesPopulationFlowPane = new FlowPane();
        simulationEnvironmentInputsFlowPane = new FlowPane();
    }

    @FXML
    public void initialize() {
        DTOEnvVarsDefForUi dtoEnvVarsDefForUi= systemEngine.getEVDFromSE();
        createEnvVarsChildrenInFlowPane(envVarsList = dtoEnvVarsDefForUi.getEnvironmentVars());
        initValues = new ArrayList<>(Collections.nCopies(envVarsList.size(), null));
        entitiesNames = systemEngine.getEntitiesNames().getNames();
        createEntitiesPopulationChildrenInFlowPane(entitiesNames);
    }

    private void createEnvVarsChildrenInFlowPane(List<PropertyDefinitionDTO> envVarsList){
        for(PropertyDefinitionDTO propertyDefinitionDTO: envVarsList){
            try{
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(TileResourceConstants.ENVIRONMENT_VAR_FXML_RESOURCE);
                Node singleEnvVar = loader.load();

                EnvironmentVariableController environmentVariableController = loader.getController();
                environmentVariableController.setInitValues(initValues);
                environmentVariableController.setEnvVarsList(envVarsList);
                environmentVariableController.setEnvVarNameLabel(propertyDefinitionDTO.getUniqueName());
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
                Node singleProperty = loader.load();

                EntityController entityController = loader.getController();
                entityController.setEntitiesNames(entitiesNames);
                entityController.setEntitiesPopulations(entitiesPopulations);
                entityController.setEntityNameLabel(entityName);
                simulationEntitiesPopulationFlowPane.getChildren().add(singleProperty);
            }
            catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }


    @FXML
    void onClickClearButton(MouseEvent event) {


    }

    @FXML
    void onClickStartButton(MouseEvent event) {
        systemEngine.updateEnvironmentVarDefinition(new CreateDTOEnvVarsForSE().getData(initValues, envVarsList));
        systemEngine.updateEntitiesPopulation(new CreateDTOPopulationForSE().getData(entitiesNames, entitiesPopulations));
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

