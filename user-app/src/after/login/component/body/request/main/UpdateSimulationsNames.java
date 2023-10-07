package after.login.component.body.request.main;

import after.login.component.body.request.server.RequestsFromServer;
import javafx.application.Platform;

import java.util.List;

import static java.lang.Thread.sleep;

public class UpdateSimulationsNames implements Runnable{
    private final RequestsFromServer requestsFromServer;
    private final RequestController requestController;

    public UpdateSimulationsNames(RequestController requestController,
                                     RequestsFromServer requestsFromServer) {
        this.requestController = requestController;
        this.requestsFromServer = requestsFromServer;
        requestsFromServer.setSimulationNamesConsumer(this::useSimulationsNames);
    }

    @Override
    public void run() {
        while (Thread.currentThread().isAlive()) {
            requestsFromServer.getSimulationNamesFromServer();
            try {
                sleep(300);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void useSimulationsNames(List<String> simulationNamesConsumer){
        Platform.runLater(() -> requestController.setSimulationsNamesList(simulationNamesConsumer));
    }

}