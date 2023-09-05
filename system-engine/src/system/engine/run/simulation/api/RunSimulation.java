package system.engine.run.simulation.api;

import dto.api.DTOSimulationProgressForUi;
import javafx.beans.property.SimpleBooleanProperty;
import system.engine.run.simulation.SimulationCallback;
import system.engine.world.api.WorldDefinition;
import system.engine.world.api.WorldInstance;
import system.engine.world.execution.instance.enitty.api.EntityInstance;
import system.engine.world.execution.instance.enitty.manager.api.EntityInstanceManager;
import system.engine.world.execution.instance.environment.api.EnvVariablesInstanceManager;
import system.engine.world.rule.api.Rule;

import java.util.List;

public interface RunSimulation {

    //void registerCallback(SimulationCallback callback);
    int[] runSimulationOnLastWorldInstance(WorldDefinition worldDefinition, WorldInstance worldInstance,
                                          EnvVariablesInstanceManager envVariablesInstanceManager,
                                            SimpleBooleanProperty isPaused);
    DTOSimulationProgressForUi getDtoSimulationProgressForUi();
}
