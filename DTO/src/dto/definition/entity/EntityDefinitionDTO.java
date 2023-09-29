package dto.definition.entity;

import dto.definition.property.definition.PropertyDefinitionDTO;

import java.util.List;

public class EntityDefinitionDTO {

    private final String uniqueName;
    private final int population;
    private final List<PropertyDefinitionDTO> propertiesDTO;

    public EntityDefinitionDTO(String uniqueName, int population, List<PropertyDefinitionDTO> propertiesDTO) {
        this.uniqueName = uniqueName;
        this.population = population;
        this.propertiesDTO = propertiesDTO;
    }


    public String getUniqueName() {
        return uniqueName;
    }

    public int getPopulation() {
        return population;
    }

    public List<PropertyDefinitionDTO> getProps() {
        return propertiesDTO;
    }
}
