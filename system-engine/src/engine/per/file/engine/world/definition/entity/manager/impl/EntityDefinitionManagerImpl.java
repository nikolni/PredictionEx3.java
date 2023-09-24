package engine.per.file.engine.world.definition.entity.manager.impl;

import engine.per.file.engine.world.definition.entity.api.EntityDefinition;
import engine.per.file.engine.world.definition.entity.manager.api.EntityDefinitionManager;
import engine.per.file.engine.world.execution.instance.enitty.manager.api.EntityInstanceManager;
import engine.per.file.engine.world.execution.instance.enitty.manager.impl.EntityInstanceManagerImpl;
import engine.per.file.engine.world.grid.api.WorldGrid;

import java.util.ArrayList;
import java.util.List;

public class EntityDefinitionManagerImpl implements EntityDefinitionManager {
    private final List<EntityDefinition> definitions;

    public EntityDefinitionManagerImpl() {
        definitions = new ArrayList<>();
    }
    public EntityDefinitionManagerImpl(List<EntityDefinition> definitions) {
        this.definitions = definitions;
    }
    @Override
    public void addEntityDefinition(EntityDefinition entityDefinition) {
        definitions.add(entityDefinition);
    }

    @Override
    public List<EntityDefinition> getDefinitions() {
        return definitions;
    }

    @Override
    public EntityDefinition getEntityDefinitionByName(String name) {
        for (EntityDefinition entityDefinition : definitions) {
            if (entityDefinition.getUniqueName().equals(name)) {
                return entityDefinition;
            }
        }
        throw new IllegalArgumentException("Can't find entity with name " + name);
    }

    @Override
    public EntityInstanceManager createEntityInstanceManager(WorldGrid worldGrid) {
        return new EntityInstanceManagerImpl(this, worldGrid);
    }
    @Override
    public EntityDefinitionManager copyFromMe(){
        List<EntityDefinition> definitionsNew = new ArrayList<>();
        for (EntityDefinition entityDefinition : definitions) {
            definitionsNew.add(entityDefinition.copyFromMe());
        }

        return new EntityDefinitionManagerImpl(definitionsNew);
    }
}

