package app.body.screen2.task;

import app.body.screen2.task.context.Context;
import app.body.screen2.task.exception.TaskIsCanceledException;
import dto.api.DTOSimulationProgressForUi;
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
    private SimpleBooleanProperty isPaused;
    private SimpleBooleanProperty isResumed;
    private SystemEngineAccess systemEngineAccess;
    private Consumer<Runnable> onCancel;
    private Integer totalTicksNumber;

    public RunSimulationTask(Context context) {
        this.totalTicksNumber = context.getTotalTicksNumber();
        this.secondsPast = context.getSecondsPast();
        this.ticksPast = context.getTicksPast();
        this.entitiesLeft = context.getEntitiesLeft();
        this.systemEngineAccess = context.getSystemEngineAccess();
        this.onCancel = context.getOnCancel();
        this.isPaused = context.getIsPausedProperty();
    }


    @Override
    protected Boolean call() throws Exception {
        updateMessage("Simulation is running...");
        updateProgress(0, totalTicksNumber);
        try{
            systemEngineAccess.runSimulation(this, isPaused);

        } catch (TaskIsCanceledException e) {
            onCancel.accept(null);
        }
        updateMessage("Done...");
        return Boolean.TRUE;
    }


    @Override
    protected void cancelled() {
        super.cancelled();
        updateMessage("Cancelled!");
    }

    @Override
    public void onUpdate(DTOSimulationProgressForUi dtoSimulationProgressForUi) {
        ticksPast.set(dtoSimulationProgressForUi.getTicksPast());
        secondsPast.set(dtoSimulationProgressForUi.getSecondsPast());
        entitiesLeft.set(dtoSimulationProgressForUi.getEntitiesLeft());
    }
}
