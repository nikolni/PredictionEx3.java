package after.login.component.body.running.list.view.update;

import after.login.component.body.running.server.RequestsFromServer;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import static java.lang.Thread.sleep;

public class UpdateListView implements Runnable{
    private final ListView<String> simulationsList;
    private final RequestsFromServer requestsFromServer;
    private final String userName;
    private Map<Integer, String> simulationIdToStatuses;

    public UpdateListView(ListView<String> simulationsList, RequestsFromServer requestsFromServer,
                          String userName) {
        this.simulationsList = simulationsList;
        this.userName = userName;

        this.requestsFromServer = requestsFromServer;
        requestsFromServer.setSimulationsStatusesConsumer(this::useSimulationIdToStatuses);
    }

    @Override
    public void run() {
        while(Thread.currentThread().isAlive()) {
            List<Integer> executionsIdList = buildListFromExistingSimulations();
            requestsFromServer.getSimulationsStatusesFromServer(userName, executionsIdList);


            for (Integer id : simulationIdToStatuses.keySet()) {
                String status = simulationIdToStatuses.get(id);
                Platform.runLater(() -> changeStatusOfSimulationsListItem(id, status));
            }
            try {
                sleep(300);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
    private void useSimulationIdToStatuses(Map<Integer, String> simulationsStatusesConsumer){
        simulationIdToStatuses = simulationsStatusesConsumer;
    }

    private List<Integer> buildListFromExistingSimulations(){
        List<Integer> executionsIdList = new ArrayList<>();
        ObservableList<String> items = simulationsList.getItems();
        for(String id : items){
            executionsIdList.add(Integer.parseInt(id));
        }
        return executionsIdList;
    }

    private void changeStatusOfSimulationsListItem(Integer simulationID, String simulationStatus) {
        ObservableList<String> items = simulationsList.getItems();
        if(simulationStatus.equals("terminated because of an error!")){
            for(String id : items){
                if(id.equals(simulationID.toString())){
                    items.set(simulationID -1, "Simulation ID: " + simulationID + " (error)");
                }
            }
        }
        else {
            if(! items.isEmpty()) {
                for(String id : items){
                    if(id.equals(simulationID.toString())){
                        items.set(simulationID - 1, "Simulation ID: " + simulationID + " (" + simulationStatus + ")");
                    }
                }
            }
        }
    }
}
