package engine.per.file.engine.world.api;

import engine.per.file.engine.world.execution.instance.enitty.manager.api.EntityInstanceManager;
import engine.per.file.engine.world.execution.instance.environment.api.EnvVariablesInstanceManager;

import java.time.LocalDateTime;

public interface WorldInstance {
    EntityInstanceManager getEntityInstanceManager();

    LocalDateTime getSimulationRunTime();

    int getId();
    EnvVariablesInstanceManager getEnvVariablesInstanceManager();

}
