package system.engine.world.definition.entity.impl;

import system.engine.world.definition.entity.api.EntityDefinition;
import system.engine.world.definition.property.api.PropertyDefinition;

import java.util.ArrayList;
import java.util.List;

public class EntityDefinitionImpl implements EntityDefinition {

    private final String uniqueName;

    private final List<PropertyDefinition> properties;

    public EntityDefinitionImpl(String uniqueName) {
        this.uniqueName = uniqueName;
        properties = new ArrayList<>();
    }

    @Override
    public String getUniqueName() {
        return uniqueName;
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
