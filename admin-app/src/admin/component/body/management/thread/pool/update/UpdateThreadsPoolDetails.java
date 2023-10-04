package admin.component.body.management.thread.pool.update;

import admin.component.body.management.server.RequestsFromServer;
import admin.component.body.management.main.ManagementController;
import dto.primary.DTOThreadsPoolStatusForUi;
import javafx.application.Platform;

public class UpdateThreadsPoolDetails implements Runnable {
    private final ManagementController managementController;
    private final RequestsFromServer requestsFromServer;

    public UpdateThreadsPoolDetails(RequestsFromServer requestsFromServer, ManagementController managementController) {
        this.managementController = managementController;
        this.requestsFromServer = requestsFromServer;
    }

    @Override
    public void run() {
            DTOThreadsPoolStatusForUi dtoThreadsPoolStatusForUi = requestsFromServer.getThreadPoolStatusFromServer();
            Platform.runLater(() -> {
                managementController.setWaitingLabel(dtoThreadsPoolStatusForUi.getQueueSize().toString());
                managementController.setCurrentlyExecutingLabel(dtoThreadsPoolStatusForUi.getActiveThreadCount().toString());
                managementController.setOverLabel(dtoThreadsPoolStatusForUi.getCompletedTaskCount().toString());
            });
    }
}
