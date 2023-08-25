package dto.definition.property.instance.api;


import dto.definition.property.definition.api.PropertyDefinitionDTO;

public interface PropertyInstanceDTO {
    PropertyDefinitionDTO getPropertyDefinition();
    Object getValue();

}
