package system.engine.world.definition.entity.manager.api;


import system.engine.world.definition.entity.api.EntityDefinition;
import system.engine.world.execution.instance.enitty.api.EntityInstance;
import system.engine.world.execution.instance.enitty.manager.api.EntityInstanceManager;
import system.engine.world.execution.instance.environment.api.EnvVariablesInstanceManager;

import java.util.List;

public interface EntityDefinitionManager {
    void addEntityDefinition(EntityDefinition entityDefinition);
    List<EntityDefinition> getDefinitions();
    EntityDefinition getEntityDefinitionByName(String name);
    EntityInstanceManager createEntityInstanceManager();

}
