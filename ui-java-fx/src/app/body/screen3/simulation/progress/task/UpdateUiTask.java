package app.body.screen3.simulation.progress.task;

import app.body.screen3.simulation.progress.SimulationProgressController;

import dto.api.DTOSimulationProgressForUi;
import javafx.application.Platform;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.concurrent.Task;
import system.engine.api.SystemEngineAccess;

import static java.lang.Thread.sleep;

public class UpdateUiTask extends Task<Boolean> {

    private final SimulationProgressController currentSimulationController;
    private final SystemEngineAccess systemEngine;
    private final Integer simulationID;

    private final SimpleIntegerProperty secondsPast;
    private final SimpleIntegerProperty ticksPast;
    private final SimpleIntegerProperty entitiesLeft;
    private final Integer totalTicksNumber;

    public UpdateUiTask(SimulationProgressController currentSimulationController, SystemEngineAccess systemEngine, Integer simulationID) {
        this.currentSimulationController = currentSimulationController;
        this.systemEngine = systemEngine;
        this.simulationID = simulationID;

        this.secondsPast = new SimpleIntegerProperty(0);
        this.ticksPast = new SimpleIntegerProperty(0);
        this.entitiesLeft = new SimpleIntegerProperty(0);
        //this.isPaused =  new SimpleBooleanProperty(false);
        this.totalTicksNumber = systemEngine.getTotalTicksNumber();
    }

    @Override
    protected Boolean call()  {
        while(Thread.currentThread().isAlive()){
            updateSimulationProgress(systemEngine.getDtoSimulationProgressForUi(simulationID));
            try {
                sleep(200);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        return null;
    }

    public SimpleIntegerProperty getSecondsPastProperty() {
        return secondsPast;
    }
    public SimpleIntegerProperty getTicksPastProperty() {
        return ticksPast;
    }
    public SimpleIntegerProperty getEntitiesLeftProperty() {
        return entitiesLeft;
    }

    public void updateSimulationProgress(DTOSimulationProgressForUi dtoSimulationProgressForUi){
        Platform.runLater(() -> {
            updateMessage(dtoSimulationProgressForUi.getProgressMassage());
            updateProgress(dtoSimulationProgressForUi.getTicksPast(), totalTicksNumber);
            ticksPast.set(dtoSimulationProgressForUi.getTicksPast());
            secondsPast.set(dtoSimulationProgressForUi.getSecondsPast());
            currentSimulationController.updateEntitiesLeftGridPane(dtoSimulationProgressForUi.getEntitiesLeft());
            if(dtoSimulationProgressForUi.getProgressMassage().equals("Done!")){
                currentSimulationController.toggleTaskButtons(false);
                currentSimulationController.onTaskFinished();
            }
        });

        if(dtoSimulationProgressForUi.getProgressMassage().equals("Done!")){
            currentSimulationController.toggleTaskButtons(false);
        }
    }
}
