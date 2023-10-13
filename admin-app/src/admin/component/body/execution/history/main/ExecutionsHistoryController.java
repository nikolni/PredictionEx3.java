package admin.component.body.execution.history.main;

import admin.component.body.execution.history.list.view.update.UpdateListView;
import admin.component.body.execution.history.result.ResultsController;
import admin.component.body.execution.history.server.RequestsFromServer;
import admin.main.AdminController;

import admin.util.constants.Constants;
import dto.primary.DTOSimulationEndingForUi;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ExecutionsHistoryController {

    @FXML
    private ListView<String> simulationsDoneDetailsList;

    @FXML
    private ScrollPane simulationResultScrollPane;

    private final Map<Integer, HBox> simulationResultsNodesMap;
    private final Map<Integer, ResultsController> simulationResultControllersMap;
    private final RequestsFromServer requestsFromServer = new RequestsFromServer();
    private Integer executionChosenID;
    public ExecutionsHistoryController() {
        simulationResultScrollPane=new ScrollPane();
        simulationResultsNodesMap = new HashMap<>();
        simulationResultControllersMap = new HashMap<>();
    }

    public void primaryInitialize() {
        simulationsDoneDetailsList.getItems().clear();
        simulationResultScrollPane.setContent(null);
        simulationsDoneDetailsList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                handleSimulationListItemSelection(newValue);
            }
        });

        UpdateListView updateListView = new UpdateListView(simulationsDoneDetailsList, requestsFromServer, this);
        new Thread(updateListView).start();

        requestsFromServer.setSimulationsStatusesConsumer(this::useSimulationIdToStatuses);
    }

    private void handleSimulationListItemSelection(String selectedItem) {
        simulationResultScrollPane.setContent(null);

        String[] words = selectedItem.split("\\s+");

        executionChosenID = (Integer.parseInt(words[2]));


        requestsFromServer.getSimulationsStatusesFromServer();


    }
    private void useSimulationIdToStatuses(Map<Integer, String> simulationsStatusesConsumer){
        boolean flag = false;
        for (Integer id : simulationsStatusesConsumer.keySet()) {
            if(id.equals(executionChosenID) && simulationsStatusesConsumer.get(id).equals("terminated because of an error!")){
                Constants.popUpWindow("The chosen execution has no results because\n it ended after an error occurred!", "No result!");
                flag  = true;
            }
        }
        if(simulationResultsNodesMap.get(executionChosenID) != null && !flag){
            simulationResultScrollPane.setContent(simulationResultsNodesMap.get(executionChosenID));
            ResultsController resultsController = simulationResultControllersMap.get(executionChosenID);
            resultsController.handleSimulationSelection(executionChosenID);
        }
    }

    public void createAndAddNewSimulationResultToList(DTOSimulationEndingForUi dtoSimulationEndingForUi, String userName) {
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/admin/component/body/execution/history/result/results.fxml"));
            HBox simulationResultNode = loader.load();
            ResultsController simulationResultController = loader.getController();
            simulationResultController.setMembers(userName, requestsFromServer);
            simulationResultController.primaryInitialize();
            Integer simulationID = dtoSimulationEndingForUi.getSimulationID();

            simulationResultsNodesMap.put(simulationID, simulationResultNode);
            simulationResultControllersMap.put(simulationID, simulationResultController);

            Object selectedItem = simulationsDoneDetailsList.getSelectionModel().getSelectedItem();
            String selectedString = selectedItem != null ? selectedItem.toString() : "";

            if(selectedItem != null){
                String[] words = selectedString.split("\\s+");
                String str = words[2];
                if(str.equals(simulationID.toString())){
                    simulationsDoneDetailsList.getSelectionModel().clearSelection();
                }
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
