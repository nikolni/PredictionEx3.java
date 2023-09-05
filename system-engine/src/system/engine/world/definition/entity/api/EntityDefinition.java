package system.engine.world.definition.entity.api;


import system.engine.world.definition.property.api.PropertyDefinition;

import java.util.List;

public interface EntityDefinition {
    String getUniqueName();
    int getPopulation();
    void setPopulation(Integer population);

    List<PropertyDefinition> getProps();
    void addPropertyDefinition(PropertyDefinition propertyDefinition);
}
