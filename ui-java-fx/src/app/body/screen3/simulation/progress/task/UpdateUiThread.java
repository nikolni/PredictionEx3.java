package app.body.screen3.simulation.progress.task;

import app.body.screen3.simulation.progress.SimulationProgressController;

import system.engine.api.SystemEngineAccess;

public class UpdateUiThread extends Thread {

    private final SimulationProgressController currentSimulationController;
    private final SystemEngineAccess systemEngine;
    private final Integer simulationID;

    public UpdateUiThread(SimulationProgressController currentSimulationController, SystemEngineAccess systemEngine, Integer simulationID) {
        this.currentSimulationController = currentSimulationController;
        this.systemEngine = systemEngine;
        this.simulationID = simulationID;
    }


    @Override
    public void run() {
        while(isAlive()){
            currentSimulationController.updateSimulationProgress(systemEngine.getDtoSimulationProgressForUi(simulationID));
            try {
                sleep(200);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
