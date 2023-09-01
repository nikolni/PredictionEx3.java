package system.engine.run.simulation.api;

import system.engine.world.api.WorldDefinition;
import system.engine.world.api.WorldInstance;
import system.engine.world.execution.instance.enitty.api.EntityInstance;
import system.engine.world.execution.instance.enitty.manager.api.EntityInstanceManager;
import system.engine.world.execution.instance.environment.api.EnvVariablesInstanceManager;
import system.engine.world.rule.api.Rule;

import java.util.List;

public interface RunSimulation {

    String runSimulationOnLastWorldInstance(WorldDefinition worldDefinition, WorldInstance worldInstance,
                                          EnvVariablesInstanceManager envVariablesInstanceManager);
}
