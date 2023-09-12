package system.engine.world.definition.entity.impl;

import system.engine.world.definition.entity.api.EntityDefinition;
import system.engine.world.definition.property.api.PropertyDefinition;

import java.util.ArrayList;
import java.util.List;

public class EntityDefinitionImpl implements EntityDefinition {

    private final String uniqueName;
    private final List<PropertyDefinition> properties;
    private Integer populationQuantity = 0;

    public EntityDefinitionImpl(String uniqueName) {
        this.uniqueName = uniqueName;
        properties = new ArrayList<>();
    }
    public EntityDefinitionImpl(String uniqueName, List<PropertyDefinition> properties, Integer populationQuantity) {
        this.uniqueName = uniqueName;
        this.properties =properties;
       this.populationQuantity = populationQuantity;
    }

    @Override
    public String getUniqueName() {
        return uniqueName;
    }

    @Override
    public int getPopulation() {
        return populationQuantity;
    }

    @Override
    public void setPopulation(Integer population) {
        this.populationQuantity  =population;
    }


    @Override
    public List<PropertyDefinition> getProps() {
        return properties;
    }

    @Override
    public void addPropertyDefinition(PropertyDefinition propertyDefinition){
        properties.add(propertyDefinition);
    }

    @Override
    public EntityDefinition copyFromMe(){
         String uniqueNameCopy = uniqueName;
         List<PropertyDefinition> propertiesCopy = properties;
         Integer populationQuantityCopy = 0;

         return new EntityDefinitionImpl(uniqueName, properties, populationQuantityCopy);
    }
}
