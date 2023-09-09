package app.body.screen3.main;

import app.body.screen3.result.ResultsController;
import app.body.screen3.simulation.progress.SimulationProgressController;
import app.body.screen3.simulation.progress.task.UpdateUiTask;
import dto.api.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import system.engine.api.SystemEngineAccess;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Body3Controller {

    @FXML
    private ListView<String> simulationsList;

    /*@FXML
    private Label simulationTicksNumber;

    @FXML
    private Label simulationSecondsNumber;

    @FXML
    private GridPane entityDetailsTable;*/

    @FXML
    private Button stopButton;

    @FXML
    private Button pauseButton;

    @FXML
    private Button resumeButton;

    @FXML
    private Button rerunButton;
    @FXML
    private ScrollPane simulationProgressScrollPane;

    @FXML
    private ScrollPane simulationResultScrollPane;

    @FXML private HBox resultsComponent;
    @FXML private ResultsController resultsComponentController;
    @FXML private VBox simulationProgressComponent;
    @FXML private SimulationProgressController simulationProgressComponentController;
    private SystemEngineAccess systemEngine;
    private Map<Integer, HBox> simulationResultsNodesMap;
    private Map<Integer, ResultsController> simulationResultControllersMap;
    //private List<HBox> simulationResultsNodesList;
    //private List<ResultsController> simulationResultsControllerList;
    //private List<VBox> simulationProgressNodesList;
    //private List<SimulationProgressController> simulationProgressControllerList;
    private Thread oldUpdateUiThreadThread = null;


    public Body3Controller() {
        //this.simulationProgressNodesList = new ArrayList<>();
        //this.simulationResultsNodesList = new ArrayList<>();
        simulationResultScrollPane=new ScrollPane();
        simulationResultsNodesMap = new HashMap<>();
        simulationResultControllersMap = new HashMap<>();
    }


    public void primaryInitialize() {
        /*if (resultsComponent != null) {
            resultsComponentController.setBody3Controller(this);
        }

        DTOSimulationsTimeRunDataForUi simulationsTimeRunDataForUi = systemEngine.getSimulationsTimeRunDataFromSE();
        List<Integer> idList = simulationsTimeRunDataForUi.getIdList();
        for (Integer idNum : idList) {
            simulationsList.getItems().add("Simulation ID: " + idNum);
        }*/
        simulationsList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                handleSimulationListItemSelection(newValue);
            }
        });
    }

    public void addItemToSimulationListView(int idNum){
        simulationsList.getItems().add("Simulation ID: " + idNum);
    }

    public void addNewSimulationToSimulationsList(Integer simulationID){
        simulationsList.getItems().add("Simulation ID: " + simulationID);
    }

   /* public void addNewSimulationToSimulationsList(Integer simulationID){
        simulationsList.getItems().add("Simulation ID: " + simulationID);
    }*/



        private void handleSimulationListItemSelection(String selectedItem) {
            if(oldUpdateUiThreadThread !=null && oldUpdateUiThreadThread.isAlive()){
                System.out.println( "killing thread address   " + oldUpdateUiThreadThread);
                oldUpdateUiThreadThread.interrupt();
            }
            String[] words = selectedItem.split("\\s+");

            Integer simulationID = (Integer.parseInt(words[words.length - 1]));
            //simulationProgressScrollPane.setContent(simulationProgressNodesList.get(simulationID-1));
            UpdateUiTask updateUiTask = new UpdateUiTask(simulationProgressComponentController, systemEngine, simulationID);

            simulationProgressComponentController.setSystemEngine(systemEngine);
            simulationProgressComponentController.setSimulationIdLabel(simulationID.toString());
            simulationProgressComponentController.setSimulationID(simulationID);
            simulationProgressComponentController.bindUiTaskToUiUpLevelComponents(updateUiTask);
            simulationProgressComponentController.bindUiTaskToUiDownLevelComponents(updateUiTask);


            oldUpdateUiThreadThread  = new Thread(updateUiTask);
            oldUpdateUiThreadThread.start();
            System.out.println( "thread address for simulation id "+ simulationID + "   " + oldUpdateUiThreadThread);
            if(simulationResultsNodesMap.get(simulationID) != null){
                simulationResultScrollPane.setContent(simulationResultsNodesMap.get(simulationID));
                //simulationResultsNodesMap.get(simulationID).setVisible(true);
                ResultsController resultsController = simulationResultControllersMap.get(simulationID);
                //resultsController.getEntityPropTreeView().setVisible(true);
                //simulationResultScrollPane.setVisible(true);
                resultsController.handleSimulationSelection(simulationID,systemEngine);
            }
            //ResultsController resultsController = simulationResultControllersMap.get(simulationID);
            //resultsController.handleSimulationSelection(simulationID,systemEngine);
            /*for(DTOSimulationEndingForUi dtoSimulationEndingForUi: systemEngine.getDTOSimulationEndingForUiList()){
                if(dtoSimulationEndingForUi.getSimulationID()==simulationID){
                    simulationTicksNumber.setText(String.valueOf(dtoSimulationEndingForUi.getTerminationReason()[0]));
                    simulationSecondsNumber.setText(String.valueOf(dtoSimulationEndingForUi.getTerminationReason()[1]));
                    fillEntityInfoGridPane(simulationID);
                    resultsComponentController.handleSimulationSelection(simulationID,systemEngine);
                }
            }*/
        }

    /*public void fillEntityInfoGridPane(int simulationID){
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

    }*/

    /*public void setVisibleTab(){
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
    }*/


    public void setSystemEngine(SystemEngineAccess systemEngineAccess){
        this.systemEngine = systemEngineAccess;
        //this.resultsComponentController.setSystemEngine(systemEngineAccess);
    }


   /* public void addNewSimulationProgressToList(VBox simulationProgressNode, SimulationProgressController simulationProgressController) {
        simulationProgressNodesList.add(simulationProgressNode);
        simulationProgressControllerList.add(simulationProgressController);
        simulationResultsNodesList.add(null);
    }*/

    public void createAndAddNewSimulationResultToList(DTOSimulationEndingForUi dtoSimulationEndingForUi) {
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/app/body/screen3/result/results.fxml"));
            HBox simulationResultNode = loader.load();
            ResultsController simulationResultController = loader.getController();
            simulationResultController.setBody3Controller(this);
            simulationResultController.setSystemEngine(systemEngine);
            simulationResultController.primaryInitialize(dtoSimulationEndingForUi,systemEngine);
            Integer simulationID = dtoSimulationEndingForUi.getSimulationID();
            simulationResultsNodesMap.put(simulationID, simulationResultNode);
            simulationResultControllersMap.put(simulationID, simulationResultController);
            //simulationResultsNodesList.set(simulationID -1, simulationResultNode);
            //simulationResultsControllerList.add(simulationResultController);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}


