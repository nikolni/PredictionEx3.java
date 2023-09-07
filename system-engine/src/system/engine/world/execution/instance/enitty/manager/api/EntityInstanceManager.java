package system.engine.world.execution.instance.enitty.manager.api;


import system.engine.world.execution.instance.enitty.api.EntityInstance;
import system.engine.world.definition.entity.api.EntityDefinition;
import system.engine.world.grid.api.WorldGrid;
import system.engine.world.grid.impl.WorldGridImpl;

import java.util.List;
import java.util.Map;

public interface EntityInstanceManager {

    void create(EntityDefinition entityDefinition, WorldGrid worldGrid);
    List<EntityInstance> getInstances();

    List<EntityInstance> getInstancesBeforeKill();
    int getEntityPopulationAfterRunning(String entityDefinitionName);
    void killEntity(int id);
    Map<String, List<EntityInstance>> getEntityInstanceByEntityDef();
    void createEntityInstanceFromScratch(EntityDefinition entityDefinitionToCreate);
    void createEntityInstanceFromDerived(EntityDefinition entityDefinitionToCreate,EntityInstance derivedEntityInstance);
    Map<String, Integer> getEntitiesPopulationAfterSimulationRunning();
}
