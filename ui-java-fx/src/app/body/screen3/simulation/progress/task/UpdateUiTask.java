package app.body.screen3.simulation.progress.task;

import app.body.screen3.simulation.progress.SimulationProgressController;

import dto.api.DTOSimulationProgressForUi;
import dto.definition.termination.condition.impl.ByUserTerminationConditionDTOImpl;
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

    public void updateSimulationProgress(DTOSimulationProgressForUi dtoSimulationProgressForUi) {
        updateMessage(dtoSimulationProgressForUi.getProgressMassage());
        if (!(systemEngine.getTerminationConditions() instanceof ByUserTerminationConditionDTOImpl)) {
            updateProgress(dtoSimulationProgressForUi.getTicksPast(), totalTicksNumber);
        } else {
            updateProgress(dtoSimulationProgressForUi.getTicksPast(), totalTicksNumber);
        }
        Platform.runLater(() -> {
            ticksPast.set(dtoSimulationProgressForUi.getTicksPast());
            secondsPast.set(dtoSimulationProgressForUi.getSecondsPast());

            currentSimulationController.updateEntitiesLeftGridPane(dtoSimulationProgressForUi.getEntitiesLeft());
            if (dtoSimulationProgressForUi.getProgressMassage().equals("Getting ready...") ||
                    dtoSimulationProgressForUi.getProgressMassage().equals("Done!")) {
                currentSimulationController.toggleTaskButtons(false);
            }
            else if(dtoSimulationProgressForUi.getProgressMassage().equals("Running!")){
                currentSimulationController.toggleTaskButtons(true);
            }
            else if(dtoSimulationProgressForUi.getProgressMassage().equals("Paused!")){
                currentSimulationController.setPauseButtonDisable(false);
                currentSimulationController.setResumeButtonDisable(true);
                currentSimulationController.setStopButtonDisable(true);
            }

        });
    }
}
