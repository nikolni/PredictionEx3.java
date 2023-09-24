package engine.per.file.engine.world.execution.instance.enitty.manager.api;


import engine.per.file.engine.world.execution.instance.enitty.api.EntityInstance;
import engine.per.file.engine.world.definition.entity.manager.api.EntityDefinitionManager;
import engine.per.file.engine.world.definition.entity.api.EntityDefinition;
import engine.per.file.engine.world.grid.api.WorldGrid;

import java.util.List;
import java.util.Map;

public interface EntityInstanceManager {

    EntityInstance create(EntityDefinition entityDefinition, WorldGrid worldGrid);
    List<EntityInstance> getInstances();

    Map<String, List<EntityInstance>> getStartEntityInstanceByEntityDef();

    List<EntityInstance> getInstancesBeforeKill();

    void changeGridByKillEntity(EntityInstance newEntityInstance, EntityInstance killEntityInstance);

    int getEntityPopulationAfterRunning(String entityDefinitionName);
    void killEntity(int id);
    Map<String, List<EntityInstance>> getEntityInstanceByEntityDef();

    Map<Integer, Integer> getNumOfEntitiesLeftByTicks();


    Float getConsistencyByEntityAndPropertyName(String entityName, String propertyName);

    void setNumOfEntitiesLestByTicks(Integer currentTick, Integer numOfInstances);

    int getWorldPopulation();

    void createEntityInstanceFromScratch(EntityDefinition entityDefinitionToCreate, EntityInstance entityInstanceToKill);
    void createEntityInstanceFromDerived(EntityDefinition entityDefinitionToCreate,EntityInstance derivedEntityInstance);


    Map<String, Integer> getEntitiesPopulationAfterSimulationRunning();
    void updateMembersAfterNewEntity(EntityDefinition entityDefinitionToCreate);
    EntityDefinitionManager getEntityDefinitionManager();
    WorldGrid getWorldGrid();
}
