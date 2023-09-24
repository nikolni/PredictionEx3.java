package after.login.component.body.execution.task;

import after.login.component.body.running.main.ProgressAndResultController;
import dto.primary.DTOSimulationEndingForUi;
import javafx.application.Platform;
import engine.per.file.engine.api.SystemEngineAccess;

public class RunSimulationTask implements Runnable{
    private final Integer simulationID;
    private final SystemEngineAccess systemEngineAccess;
    private final ProgressAndResultController progressAndResultController;

    public RunSimulationTask(SystemEngineAccess systemEngineAccess,Integer simulationID, ProgressAndResultController progressAndResultController) {
        this.systemEngineAccess = systemEngineAccess;
        this.simulationID = simulationID;
        this.progressAndResultController = progressAndResultController;
    }
    @Override
    public void run() {
        DTOSimulationEndingForUi dtoSimulationEndingForUi = systemEngineAccess.runSimulation(simulationID);
        Platform.runLater(() -> progressAndResultController.createAndAddNewSimulationResultToList(dtoSimulationEndingForUi));
    }
}
