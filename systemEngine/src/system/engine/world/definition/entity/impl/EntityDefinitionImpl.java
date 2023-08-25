package system.engine.world.definition.entity.impl;

import system.engine.world.definition.entity.api.EntityDefinition;
import system.engine.world.definition.property.api.PropertyDefinition;

import java.util.ArrayList;
import java.util.List;

public class EntityDefinitionImpl implements EntityDefinition {

    private final String uniqueName;
    private final int population;
    private final List<PropertyDefinition> properties;

    public EntityDefinitionImpl(String uniqueName, int population) {
        this.uniqueName = uniqueName;
        this.population = population;
        properties = new ArrayList<>();
    }

    @Override
    public String getUniqueName() {
        return uniqueName;
    }

    @Override
    public int getPopulation() {
        return population;
    }

    @Override
    public List<PropertyDefinition> getProps() {
        return properties;
    }

    @Override
    public void addPropertyDefinition(PropertyDefinition propertyDefinition){
        properties.add(propertyDefinition);
    }

}
