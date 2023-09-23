package component.body.management.update.thread.status;

import component.body.management.main.ManagementController;
import component.header.HeaderController;
import dto.primary.DTOThreadsPoolStatusForUi;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import system.engine.api.SystemEngineAccess;

public class UpdateThreadsPoolDetails implements Runnable {
    private ManagementController managementController;
    private SystemEngineAccess systemEngine;

    public UpdateThreadsPoolDetails(ManagementController managementController, SystemEngineAccess systemEngine) {
        this.managementController = managementController;
        this.systemEngine = systemEngine;
    }

    @Override
    public void run() {
            DTOThreadsPoolStatusForUi dtoThreadsPoolStatusForUi = systemEngine.getThreadsPoolStatus();
            Platform.runLater(() -> {
                managementController.setWaitingLabel(dtoThreadsPoolStatusForUi.getQueueSize().toString());
                managementController.setCurrentlyExecutingLabel(dtoThreadsPoolStatusForUi.getActiveThreadCount().toString());
                managementController.setOverLabel(dtoThreadsPoolStatusForUi.getCompletedTaskCount().toString());
            });
    }
}
