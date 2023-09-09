package app.body.screen2.task;

import app.body.screen3.main.Body3Controller;
import dto.api.DTOSimulationEndingForUi;
import javafx.application.Platform;
import system.engine.api.SystemEngineAccess;

public class RunSimulationTask implements Runnable{
    private final Integer simulationID;
    private final SystemEngineAccess systemEngineAccess;
    private final Body3Controller body3Controller;

    public RunSimulationTask(SystemEngineAccess systemEngineAccess,Integer simulationID, Body3Controller body3Controller) {
        this.systemEngineAccess = systemEngineAccess;
        this.simulationID = simulationID;
        this.body3Controller = body3Controller;
    }
    @Override
    public void run() {
        System.out.println("thread is running! simulation is  " + simulationID + "    " + Thread.currentThread());
        DTOSimulationEndingForUi dtoSimulationEndingForUi = systemEngineAccess.runSimulation(simulationID);
        Platform.runLater(() -> body3Controller.createAndAddNewSimulationResultToList(dtoSimulationEndingForUi));
    }
}
