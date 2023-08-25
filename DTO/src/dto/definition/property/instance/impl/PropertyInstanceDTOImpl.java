package dto.definition.property.instance.impl;

import dto.definition.property.definition.api.PropertyDefinitionDTO;
import dto.definition.property.instance.api.PropertyInstanceDTO;

public class PropertyInstanceDTOImpl implements PropertyInstanceDTO {

    private PropertyDefinitionDTO propertyDefinitionDTO;
    private Object value;

    public PropertyInstanceDTOImpl(PropertyDefinitionDTO propertyDefinitionDTO, Object value) {
        this.propertyDefinitionDTO = propertyDefinitionDTO;
        this.value = value;
    }

    @Override
    public PropertyDefinitionDTO getPropertyDefinition() {
        return propertyDefinitionDTO;
    }

    @Override
    public Object getValue() {
        return value;
    }


}
