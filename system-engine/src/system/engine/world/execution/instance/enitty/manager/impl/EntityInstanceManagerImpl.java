package system.engine.world.execution.instance.enitty.manager.impl;

import system.engine.world.definition.entity.api.EntityDefinition;
import system.engine.world.definition.entity.manager.api.EntityDefinitionManager;
import system.engine.world.definition.property.api.PropertyDefinition;
import system.engine.world.execution.instance.enitty.api.EntityInstance;
import system.engine.world.execution.instance.enitty.impl.EntityInstanceImpl;
import system.engine.world.execution.instance.enitty.manager.api.EntityInstanceManager;
import system.engine.world.execution.instance.property.api.PropertyInstance;
import system.engine.world.execution.instance.property.impl.PropertyInstanceImpl;
import system.engine.world.grid.api.WorldGrid;


import java.util.*;
import java.util.stream.Collectors;

public class EntityInstanceManagerImpl implements EntityInstanceManager {
    private int count;   //all instances of all entities
    private final List<EntityInstance> instances;
    private final List<EntityInstance> instancesBeforeKill;
    private final Map<String, Integer> entitiesPopulationAfterSimulationRunning;
    private Map<String,List<EntityInstance>> entityInstanceByEntityDef;


    public EntityInstanceManagerImpl(EntityDefinitionManager entityDefinitionManager, WorldGrid worldGrid) {
        count = 0;
        int entityDefinitionCount = 0;
        instances = new ArrayList<>();
        instancesBeforeKill = new ArrayList<>();
        entitiesPopulationAfterSimulationRunning = new HashMap<>();
        entityInstanceByEntityDef=new HashMap<>();
        for (EntityDefinition entityDefinition: entityDefinitionManager.getDefinitions()){
            for(int i = 0; i<entityDefinition.getPopulation(); i++) {
                create(entityDefinition, worldGrid);
            }
            entitiesPopulationAfterSimulationRunning.put(entityDefinitionManager.getDefinitions().get(entityDefinitionCount).getUniqueName(),
                    entityDefinitionManager.getDefinitions().get(entityDefinitionCount).getPopulation());
        }
        instancesBeforeKill.addAll(instances);
        entityInstanceByEntityDef = instances.stream()
                .collect(Collectors.groupingBy(entityInstance -> entityInstance.getEntityDefinition().getUniqueName()));
    }

    @Override
    public int getEntityPopulationAfterRunning(String entityDefinitionName){
        return entitiesPopulationAfterSimulationRunning.get(entityDefinitionName);
    }

    @Override
    public void create(EntityDefinition entityDefinition, WorldGrid worldGrid) {

        count++;
        EntityInstance newEntityInstance = new EntityInstanceImpl(entityDefinition, count, worldGrid);
        instances.add(newEntityInstance);

        for (PropertyDefinition propertyDefinition : entityDefinition.getProps()) {
            Object value = propertyDefinition.generateValue();
            PropertyInstance newPropertyInstance = new PropertyInstanceImpl(propertyDefinition, value);
            newEntityInstance.addPropertyInstance(newPropertyInstance);
        }
    }

    @Override
    public List<EntityInstance> getInstances() {
        return instances;
    }

    public Map<String, List<EntityInstance>> getEntityInstanceByEntityDef() {
        return entityInstanceByEntityDef;
    }

    @Override
    public List<EntityInstance> getInstancesBeforeKill() {
        for(EntityInstance entityInstance : instances){
            if(entityInstance != null){
                int id = entityInstance.getId();
                instancesBeforeKill.set(id -1, instances.get(id-1));
            }
        }
        return instancesBeforeKill;
    }

    @Override
    public void killEntity(int id) {
        String entityDefinitionName = instances.get(id - 1).getEntityDefinition().getUniqueName();
        int oldPopulation = entitiesPopulationAfterSimulationRunning.get(entityDefinitionName);
        entitiesPopulationAfterSimulationRunning.put(entityDefinitionName, oldPopulation - 1);
        instancesBeforeKill.set(id -1, instances.get(id-1));
        instances.set(id-1, null);
    }
}
