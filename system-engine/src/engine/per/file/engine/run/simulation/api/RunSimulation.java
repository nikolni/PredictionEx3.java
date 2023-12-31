package engine.per.file.engine.run.simulation.api;

import dto.primary.DTOSimulationProgressForUi;
import engine.per.file.engine.run.simulation.manager.IsSimulationPaused;
import engine.per.file.engine.world.api.WorldDefinition;
import engine.per.file.engine.world.api.WorldInstance;
import engine.per.file.engine.world.termination.condition.api.TerminationCondition;

import java.util.List;

public interface RunSimulation {

    int[] runSimulationOnLastWorldInstance(WorldDefinition worldDefinition, WorldInstance worldInstance);
    DTOSimulationProgressForUi getDtoSimulationProgressForUi();
    void setCanceled(boolean canceled);
    IsSimulationPaused getIsSimulationPaused();
    void setPaused(boolean paused);
    boolean getPaused();
}
