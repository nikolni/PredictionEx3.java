package engine.per.file.engine.world.definition.entity.manager.api;


import engine.per.file.engine.world.definition.entity.api.EntityDefinition;
import engine.per.file.engine.world.execution.instance.enitty.manager.api.EntityInstanceManager;
import engine.per.file.engine.world.grid.api.WorldGrid;

import java.util.List;

public interface EntityDefinitionManager {
    void addEntityDefinition(EntityDefinition entityDefinition);
    List<EntityDefinition> getDefinitions();
    EntityDefinition getEntityDefinitionByName(String name);
    EntityInstanceManager createEntityInstanceManager(WorldGrid worldGrid);
    EntityDefinitionManager copyFromMe();
}
