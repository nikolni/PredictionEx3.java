package after.login.component.body.running.list.view.update;

import after.login.component.body.running.server.RequestsFromServer;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static java.lang.Thread.sleep;

public class UpdateListView implements Runnable{
    private final ListView<String> simulationsList;
    private final RequestsFromServer requestsFromServer = new RequestsFromServer();
    private final String userName;
    private Map<Integer, String> simulationIdToStatuses;

    public UpdateListView(ListView<String> simulationsList,String userName) {
        this.simulationsList = simulationsList;
        this.userName = userName;

        requestsFromServer.setSimulationsStatusesConsumer(this::useSimulationIdToStatuses);
    }

    @Override
    public void run() {
        while(Thread.currentThread().isAlive()) {
            List<Integer> executionsIdList = buildListFromExistingSimulations();
            requestsFromServer.getSimulationsStatusesFromServer(userName, executionsIdList);

            if(simulationIdToStatuses != null && simulationIdToStatuses.size() > 0) {
                for (Integer id : simulationIdToStatuses.keySet()) {
                    String status = simulationIdToStatuses.get(id);
                    Platform.runLater(() -> changeStatusOfSimulationsListItem(id, status));
                }
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
            String[] words = id.split("\\s+");
            executionsIdList.add(Integer.parseInt(words[2]));
        }
        return executionsIdList;
    }

    private void changeStatusOfSimulationsListItem(Integer simulationID, String simulationStatus) {
        int index = 0;
        ObservableList<String> items = simulationsList.getItems();
        if(simulationStatus.equals("terminated because of an error!")){
            for(String id : items){
                String[] words = id.split("\\s+");
                if(words[2].equals(simulationID.toString())){
                    items.set(index, "Simulation ID: " + simulationID + " (error)");
                }
                index++;
            }
        }
        else {
            if(! items.isEmpty()) {
                for(String id : items){
                    String[] words = id.split("\\s+");
                    if(words[2].equals(simulationID.toString())){
                        items.set(index, "Simulation ID: " + simulationID + " (" + simulationStatus + ")");
                    }
                    index++;
                }
            }
        }
    }
}
