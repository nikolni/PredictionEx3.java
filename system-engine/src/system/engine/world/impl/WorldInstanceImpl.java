package system.engine.world.impl;

import system.engine.world.api.WorldDefinition;
import system.engine.world.api.WorldInstance;
import system.engine.world.definition.entity.manager.api.EntityDefinitionManager;
import system.engine.world.execution.instance.enitty.manager.api.EntityInstanceManager;
import system.engine.world.execution.instance.environment.api.EnvVariablesInstanceManager;
import system.engine.world.grid.api.WorldGrid;

import java.time.LocalDateTime;

public class WorldInstanceImpl implements WorldInstance {
    private final int id;  //index in world instances list
    private final LocalDateTime simulationRunTime;
    private EntityInstanceManager entityInstanceManager;

    private final EnvVariablesInstanceManager envVariablesInstanceManager;
    //private final EntityDefinitionManager entityDefinitionManager;


    public WorldInstanceImpl(WorldDefinition worldDefinition, int id, WorldGrid worldGrid,
                             EnvVariablesInstanceManager envVariablesInstanceManager, EntityDefinitionManager entityDefinitionManager){
        this.id = id;
        this.entityInstanceManager = entityDefinitionManager.createEntityInstanceManager(worldGrid);
        simulationRunTime = LocalDateTime.now();
        this.envVariablesInstanceManager = envVariablesInstanceManager;
    }

    @Override
    public EntityInstanceManager getEntityInstanceManager() {
        return entityInstanceManager;
    }


    @Override
    public int getId() {
        return id;
    }

    @Override
    public LocalDateTime getSimulationRunTime() {
        return simulationRunTime;
    }

    @Override
    public EnvVariablesInstanceManager getEnvVariablesInstanceManager() {
        return envVariablesInstanceManager;
    }

}

