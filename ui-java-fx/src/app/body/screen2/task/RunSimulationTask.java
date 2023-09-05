package app.body.screen2.task;

import app.body.screen2.main.Body2Controller;
import app.body.screen2.task.context.api.SimulationRunTaskContext;
import app.body.screen2.task.exception.TaskIsCanceledException;
import app.body.screen3.simulation.progress.SimulationProgressController;
import dto.api.DTOSimulationEndingForUi;
import dto.api.DTOSimulationProgressForUi;
import javafx.application.Platform;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.concurrent.Task;
import system.engine.api.SystemEngineAccess;
import system.engine.run.simulation.SimulationCallback;

import java.util.function.Consumer;

public class RunSimulationTask extends Task<Boolean> implements SimulationCallback {
    private SimpleIntegerProperty secondsPast;
    private SimpleIntegerProperty ticksPast;
    private SimpleIntegerProperty entitiesLeft;

    private Integer simulationID;
    private SimpleBooleanProperty isPaused;
    private SimpleBooleanProperty isCanceled;
    private SystemEngineAccess systemEngineAccess;
    private Consumer<Runnable> onCancel;
    private Integer totalTicksNumber;

    private SimulationProgressController simulationProgressController;

    public RunSimulationTask(SimulationRunTaskContext simulationRunTaskContext,
                             SimulationProgressController simulationProgressController) {
        this.totalTicksNumber = simulationRunTaskContext.getTotalTicksNumber();
        this.secondsPast = simulationRunTaskContext.getSecondsPast();
        this.ticksPast = simulationRunTaskContext.getTicksPast();
        this.entitiesLeft = simulationRunTaskContext.getEntitiesLeft();
        this.systemEngineAccess = simulationRunTaskContext.getSystemEngineAccess();
        this.onCancel = simulationRunTaskContext.getOnCancel();
        this.isPaused = simulationRunTaskContext.getIsPausedProperty();
        this.simulationID = simulationRunTaskContext.getSimulationID();
        this.simulationProgressController = simulationProgressController;
    }


    @Override
    protected Boolean call() throws Exception {
        updateMessage("Simulation is running...");
        updateProgress(0, totalTicksNumber);
        try{
            DTOSimulationEndingForUi dtoSimulationEndingForUi = systemEngineAccess.runSimulation(this, isPaused, simulationID);
            simulationProgressController.addNewSimulationResultToBody3Controller(dtoSimulationEndingForUi);

        } catch (TaskIsCanceledException e) {
            updateMessage("Cancelled!");
            onCancel.accept(null);
        }
        updateMessage("Done...");
        return Boolean.TRUE;
    }


    @Override
    protected void cancelled() {
        updateMessage("Cancelled!");
        super.cancelled();
    }

    @Override
    public void onUpdateWhileSimulationRunning(DTOSimulationProgressForUi dtoSimulationProgressForUi) {
        updateMessage("Simulation is running...");
        Platform.runLater(() -> {
            updateProgress(dtoSimulationProgressForUi.getTicksPast(), totalTicksNumber);
            ticksPast.set(dtoSimulationProgressForUi.getTicksPast());
            secondsPast.set(dtoSimulationProgressForUi.getSecondsPast());
            entitiesLeft.set(dtoSimulationProgressForUi.getEntitiesLeft());
        });
    }

    @Override
    public void onUpdateWhileSimulationIsPaused() {
        updateMessage("Simulation is paused...");
    }
}
