package dto.definition.entity.impl;

import dto.definition.entity.api.EntityDefinitionDTO;
import dto.definition.property.definition.api.PropertyDefinitionDTO;

import java.util.List;

public class EntityDefinitionDTOImpl implements EntityDefinitionDTO {

    private final String uniqueName;
    private final int population;
    private final List<PropertyDefinitionDTO> propertiesDTO;

    public EntityDefinitionDTOImpl(String uniqueName, int population, List<PropertyDefinitionDTO> propertiesDTO) {
        this.uniqueName = uniqueName;
        this.population = population;
        this.propertiesDTO = propertiesDTO;
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
    public List<PropertyDefinitionDTO> getProps() {
        return propertiesDTO;
    }

}
