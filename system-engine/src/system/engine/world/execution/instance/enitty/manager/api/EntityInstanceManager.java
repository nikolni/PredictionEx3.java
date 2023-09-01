package system.engine.world.execution.instance.enitty.manager.api;


import system.engine.world.execution.instance.enitty.api.EntityInstance;
import system.engine.world.definition.entity.api.EntityDefinition;

import java.util.List;

public interface EntityInstanceManager {

    void create(EntityDefinition entityDefinition);
    List<EntityInstance> getInstances();

    List<EntityInstance> getInstancesBeforeKill();
    int getEntityPopulationAfterRunning(String entityDefinitionName);
    void killEntity(int id);
}
