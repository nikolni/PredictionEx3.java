package after.login.component.body.running.main;

import after.login.component.body.running.server.RequestsFromServer;
import dto.primary.DTOSimulationEndingForUi;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Thread.sleep;

public class AddResultsControllers implements Runnable {
    private final RequestsFromServer requestsFromServer;
    private final List<Integer> executionDone;
    private final String userName;
    private final ProgressAndResultController progressAndResultController;

    public AddResultsControllers(RequestsFromServer requestsFromServer, String userName, ProgressAndResultController progressAndResultController) {
        this.requestsFromServer = requestsFromServer;
        this.userName = userName;
        this.progressAndResultController = progressAndResultController;
        executionDone = new ArrayList<>();
    }

    @Override
    public void run() {
        while (Thread.currentThread().isAlive()) {

            List<DTOSimulationEndingForUi> executionDoneFromServer = requestsFromServer.getSimulationEndingListFromServer(userName);

            for (DTOSimulationEndingForUi dtoSimulationEndingForUi : executionDoneFromServer) {
                boolean alreadyDone = false;
                for(Integer executionId : executionDone) {
                    if (dtoSimulationEndingForUi.getSimulationID() == executionId){
                        alreadyDone = true;
                        break;
                    }
                }
                if(!alreadyDone){
                    progressAndResultController.createAndAddNewSimulationResultToList(dtoSimulationEndingForUi);
                    executionDone.add(dtoSimulationEndingForUi.getSimulationID());
                    requestsFromServer.updateServerOnExecutionDoneForUpdateThreadPoolStatus();
                }
            }
            try {
                sleep(300);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

}