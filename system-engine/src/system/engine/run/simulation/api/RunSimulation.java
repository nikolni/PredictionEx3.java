package system.engine.run.simulation.api;

import dto.primary.DTOSimulationProgressForUi;
import system.engine.run.simulation.manager.IsSimulationPaused;
import system.engine.world.api.WorldDefinition;
import system.engine.world.api.WorldInstance;

public interface RunSimulation {

    int[] runSimulationOnLastWorldInstance(WorldDefinition worldDefinition, WorldInstance worldInstance);
    DTOSimulationProgressForUi getDtoSimulationProgressForUi();
    void setCanceled(boolean canceled);
    IsSimulationPaused getIsSimulationPaused();
    void setPaused(boolean paused);
    boolean getPaused();
}
