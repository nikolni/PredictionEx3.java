package dto.definition.property.instance;

import dto.definition.property.definition.PropertyDefinitionDTO;

public class PropertyInstanceDTO{

    private PropertyDefinitionDTO propertyDefinitionDTO;
    private Object value;

    public PropertyInstanceDTO(PropertyDefinitionDTO propertyDefinitionDTO, Object value) {
        this.propertyDefinitionDTO = propertyDefinitionDTO;
        this.value = value;
    }


    public PropertyDefinitionDTO getPropertyDefinition() {
        return propertyDefinitionDTO;
    }

    public Object getValue() {
        return value;
    }


}
