package system.engine.world.execution.instance.enitty.manager.impl;

import system.engine.world.definition.entity.api.EntityDefinition;
import system.engine.world.definition.entity.manager.api.EntityDefinitionManager;
import system.engine.world.definition.property.api.PropertyDefinition;
import system.engine.world.execution.instance.enitty.api.EntityInstance;
import system.engine.world.execution.instance.enitty.impl.EntityInstanceImpl;
import system.engine.world.execution.instance.enitty.manager.api.EntityInstanceManager;
import system.engine.world.execution.instance.property.api.PropertyInstance;
import system.engine.world.execution.instance.property.impl.PropertyInstanceImpl;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EntityInstanceManagerImpl implements EntityInstanceManager {
    private int count;   //all instances of all entities
    private List<EntityInstance> instances;
    private List<EntityInstance> instancesBeforeKill;
    private Map<String, Integer> entitiesPopulationAfterSimulationRunning;


    public EntityInstanceManagerImpl(EntityDefinitionManager entityDefinitionManager) {
        count = 0;
        int entityDefinitionCount = 0;
        instances = new ArrayList<>();
        instancesBeforeKill = new ArrayList<>();
        entitiesPopulationAfterSimulationRunning = new HashMap<>();
        for (EntityDefinition entityDefinition: entityDefinitionManager.getDefinitions()){
            for(int i = 0; i<entityDefinition.getPopulation(); i++) {
                create(entityDefinition);
            }
            entitiesPopulationAfterSimulationRunning.put(entityDefinitionManager.getDefinitions().get(entityDefinitionCount).getUniqueName(),
                    entityDefinitionManager.getDefinitions().get(entityDefinitionCount).getPopulation());
        }
        instancesBeforeKill.addAll(instances);
    }

    @Override
    public int getEntityPopulationAfterRunning(String entityDefinitionName){
        return entitiesPopulationAfterSimulationRunning.get(entityDefinitionName);
    }

    @Override
    public void create(EntityDefinition entityDefinition) {

        count++;
        EntityInstance newEntityInstance = new EntityInstanceImpl(entityDefinition, count);
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
