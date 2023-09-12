package system.engine.world.execution.instance.enitty.manager.api;


import system.engine.world.definition.entity.manager.api.EntityDefinitionManager;
import system.engine.world.execution.instance.enitty.api.EntityInstance;
import system.engine.world.definition.entity.api.EntityDefinition;
import system.engine.world.grid.api.WorldGrid;
import system.engine.world.grid.impl.WorldGridImpl;

import java.util.List;
import java.util.Map;

public interface EntityInstanceManager {

    EntityInstance create(EntityDefinition entityDefinition, WorldGrid worldGrid);
    List<EntityInstance> getInstances();

    List<EntityInstance> getInstancesBeforeKill();

    void changeGridByKillEntity(EntityInstance newEntityInstance, EntityInstance killEntityInstance);

    int getEntityPopulationAfterRunning(String entityDefinitionName);
    void killEntity(int id);
    Map<String, List<EntityInstance>> getEntityInstanceByEntityDef();

    Map<Integer, Integer> getNumOfEntitiesLeftByTicks();

    void setNumOfEntitiesLestByTicks(Integer currentTick, Integer numOfInstances);

    void createEntityInstanceFromScratch(EntityDefinition entityDefinitionToCreate, EntityInstance entityInstanceToKill);
    void createEntityInstanceFromDerived(EntityDefinition entityDefinitionToCreate,EntityInstance derivedEntityInstance);


    Map<String, Integer> getEntitiesPopulationAfterSimulationRunning();
    void updateMembersAfterNewEntity(EntityDefinition entityDefinitionToCreate);
    EntityDefinitionManager getEntityDefinitionManager();
}
