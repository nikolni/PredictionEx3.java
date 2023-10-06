package admin.component.body.management.thread.pool.update;

import admin.component.body.management.server.RequestsFromServer;
import admin.component.body.management.main.ManagementController;
import dto.primary.DTOThreadsPoolStatusForUi;
import javafx.application.Platform;

import java.util.List;
import java.util.Map;

import static java.lang.Thread.sleep;

public class UpdateThreadsPoolDetails implements Runnable {
    private final ManagementController managementController;
    private final RequestsFromServer requestsFromServer;

    public UpdateThreadsPoolDetails(RequestsFromServer requestsFromServer, ManagementController managementController) {
        this.managementController = managementController;
        this.requestsFromServer = requestsFromServer;
        requestsFromServer.setConsumer(this::update);
    }

    @Override
    public void run() {
        while (Thread.currentThread().isAlive()) {
            requestsFromServer.getThreadPoolStatusFromServer();

        }
        try {
            sleep(300);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private void update(DTOThreadsPoolStatusForUi dtoThreadsPoolStatusForUi){
        Platform.runLater(() -> {
            managementController.setWaitingLabel(dtoThreadsPoolStatusForUi.getQueueSize().toString());
            managementController.setCurrentlyExecutingLabel(dtoThreadsPoolStatusForUi.getActiveThreadCount().toString());
            managementController.setOverLabel(dtoThreadsPoolStatusForUi.getCompletedTaskCount().toString());
        });
    }
}
