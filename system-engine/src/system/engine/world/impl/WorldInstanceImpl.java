package system.engine.world.impl;

import system.engine.world.api.WorldDefinition;
import system.engine.world.api.WorldInstance;
import system.engine.world.execution.instance.enitty.manager.api.EntityInstanceManager;

import java.time.LocalDateTime;

public class WorldInstanceImpl implements WorldInstance {
    private final int id;  //index in world instances list
    private final LocalDateTime simulationRunTime;
    private EntityInstanceManager entityInstanceManager;


    public WorldInstanceImpl(WorldDefinition worldDefinition, int id){
        this.id = id;
        this.entityInstanceManager = worldDefinition.getEntityDefinitionManager().createEntityInstanceManager();
        simulationRunTime = LocalDateTime.now();
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
}

