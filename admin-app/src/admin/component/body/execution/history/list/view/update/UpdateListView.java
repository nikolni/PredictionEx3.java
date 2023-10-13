package admin.component.body.execution.history.list.view.update;

import admin.component.body.execution.history.main.ExecutionsHistoryController;
import admin.component.body.execution.history.server.RequestsFromServer;
import dto.primary.DTOSimulationEndingForUi;
import javafx.application.Platform;
import javafx.scene.control.ListView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static java.lang.Thread.sleep;

public class UpdateListView implements Runnable{
    private final ListView<String> simulationsListView;
    private final RequestsFromServer requestsFromServer;
    private final List<Integer> executionDone;
    private final ExecutionsHistoryController executionsHistoryController;

    public UpdateListView(ListView<String> simulationsListView, RequestsFromServer requestsFromServer,
                          ExecutionsHistoryController executionsHistoryController) {
        this.executionsHistoryController = executionsHistoryController;
        this.simulationsListView = simulationsListView;
        executionDone = new ArrayList<>();

        this.requestsFromServer = requestsFromServer;
        requestsFromServer.setExecutionsResultsConsumer(this::useSimulationsResults);
    }

    @Override
    public void run() {
        while(Thread.currentThread().isAlive()) {
            requestsFromServer.getExecutionsResultsFromServer();

            try {
                sleep(300);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
    private void useSimulationsResults(Map<String, DTOSimulationEndingForUi> simulationsListConsumer){
        List<String> allExecutionsList = new ArrayList<>();
        allExecutionsList.addAll(simulationsListConsumer.keySet());

        Platform.runLater(() -> {
            simulationsListView.getItems().clear();
            simulationsListView.getItems().addAll(allExecutionsList);
        });

        for (String str : simulationsListConsumer.keySet()) {
            DTOSimulationEndingForUi dtoSimulationEndingForUi = simulationsListConsumer.get(str);
            String[] words = str.split("\\s+");
            String userName = words[3].replaceAll("\\(user", "");

            boolean alreadyDone = false;
            for (Integer executionId : executionDone) {
                if (dtoSimulationEndingForUi.getSimulationID() == executionId) {
                    alreadyDone = true;
                    break;
                }
            }
            if (!alreadyDone) {
                Platform.runLater(() -> executionsHistoryController.createAndAddNewSimulationResultToList(
                        dtoSimulationEndingForUi, userName));
                executionDone.add(dtoSimulationEndingForUi.getSimulationID());
            }
        }
    }


}
