package system.engine.world.definition.entity.manager.api;


import system.engine.world.definition.entity.api.EntityDefinition;
import system.engine.world.execution.instance.enitty.manager.api.EntityInstanceManager;
import system.engine.world.grid.api.WorldGrid;

import java.util.List;
import java.util.Map;

public interface EntityDefinitionManager {
    void addEntityDefinition(EntityDefinition entityDefinition);
    List<EntityDefinition> getDefinitions();
    EntityDefinition getEntityDefinitionByName(String name);
    EntityInstanceManager createEntityInstanceManager(WorldGrid worldGrid);
    EntityDefinitionManager copyFromMe();
}
