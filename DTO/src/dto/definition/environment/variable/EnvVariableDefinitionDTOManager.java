package dto.definition.environment.variable;

import dto.definition.property.definition.PropertyDefinitionDTO;

import java.util.Collection;
import java.util.Map;

public class EnvVariableDefinitionDTOManager{

    private final Map<String, PropertyDefinitionDTO> propNameToPropDefinitionDTO;

    public EnvVariableDefinitionDTOManager(Map<String, PropertyDefinitionDTO> propNameToPropDefinitionDTO) {
        this.propNameToPropDefinitionDTO = propNameToPropDefinitionDTO;
    }

    public Collection<PropertyDefinitionDTO> getEnvVariables() {
        return propNameToPropDefinitionDTO.values();
    }
}
