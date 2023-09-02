package app.body.screen3;

import app.body.screen1.Body1Controller;
import app.body.screen3.result.ResultsController;
import dto.api.*;
import dto.definition.entity.api.EntityDefinitionDTO;
import dto.definition.property.definition.api.PropertyDefinitionDTO;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TreeItem;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import system.engine.api.SystemEngineAccess;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Body3Controller {

    @FXML
    private ListView<String> simulationsList;

    @FXML
    private Label simulationTicksNumber;

    @FXML
    private Label simulationSecondsNumber;

    @FXML
    private GridPane entityDetailsTable;

    @FXML
    private Button stopButton;

    @FXML
    private Button pauseButton;

    @FXML
    private Button resumeButton;

    @FXML
    private Button rerunButton;

    @FXML private HBox resultsComponent;
    @FXML private ResultsController resultsComponentController;

    private SystemEngineAccess systemEngine;


    public void primaryInitialize() {
        if (resultsComponent != null) {
            resultsComponentController.setBody3Controller(this);
        }

        DTOSimulationsTimeRunDataForUi simulationsTimeRunDataForUi = systemEngine.getSimulationsTimeRunDataFromSE();
        List<Integer> idList = simulationsTimeRunDataForUi.getIdList();
        for (Integer idNum : idList) {
            simulationsList.getItems().add("Simulation ID: " + idNum);
        }
        simulationsList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                handleSimulationListItemSelection(newValue);
            }
        });
    }



        private void handleSimulationListItemSelection(String selectedItem) {
            String[] words = selectedItem.split("\\s+");
            int simulationID = (Integer.parseInt(words[words.length - 1]))-1;
            for(DTOSimulationEndingForUi dtoSimulationEndingForUi: systemEngine.getDTOSimulationEndingForUiList()){
                if(dtoSimulationEndingForUi.getSimulationID()==simulationID){
                    simulationTicksNumber.setText(String.valueOf(dtoSimulationEndingForUi.getTerminationReason()[0]));
                    simulationSecondsNumber.setText(String.valueOf(dtoSimulationEndingForUi.getTerminationReason()[1]));
                    fillEntityInfoGridPane(simulationID+1);
                    resultsComponentController.handleSimulationSelection(simulationID+1,systemEngine);
                }
            }
        }

    public void fillEntityInfoGridPane(int simulationID){
        DTOEntitiesAfterSimulationByQuantityForUi entitiesAfterSimulationForUi= systemEngine.getEntitiesDataAfterSimulationRunningByQuantity(simulationID);
        List<String> entitiesNames = entitiesAfterSimulationForUi.getEntitiesNames();
        List<Integer> entitiesPopulationAfterSimulation =entitiesAfterSimulationForUi.getEntitiesPopulationAfterSimulation();
        entityDetailsTable.getChildren().clear();

        for (int i = 0; i < entitiesNames.size(); i++) {
            String entityName = entitiesNames.get(i);
            Integer population = entitiesPopulationAfterSimulation.get(i);

            Label nameLabel = new Label(entityName);
            Label populationLabel = new Label(Integer.toString(population));

            entityDetailsTable.add(nameLabel, 0, i);
            entityDetailsTable.add(populationLabel, 1, i);
        }

    }

    public void setVisibleTab(){
        simulationsList.setVisible(true);
        simulationTicksNumber.setVisible(true);
        simulationSecondsNumber.setVisible(true);
        entityDetailsTable.setVisible(true);
        resultsComponent.setVisible(true);
    }

    public void setUnVisibleTab(){
        simulationsList.setVisible(false);
        simulationTicksNumber.setVisible(false);
        simulationSecondsNumber.setVisible(false);
        entityDetailsTable.setVisible(false);
        resultsComponent.setVisible(false);
    }


    public void setSystemEngine(SystemEngineAccess systemEngineAccess){
        this.systemEngine = systemEngineAccess;
    }


}


