package dto.definition.environment.variable.impl;

import dto.definition.environment.variable.api.EnvVariablesDefinitionDTOManager;
import dto.definition.property.definition.api.PropertyDefinitionDTO;

import java.util.Collection;
import java.util.Map;

public class EnvVariableDefinitionDTOManagerImpl implements EnvVariablesDefinitionDTOManager {

    private final Map<String, PropertyDefinitionDTO> propNameToPropDefinitionDTO;

    public EnvVariableDefinitionDTOManagerImpl(Map<String, PropertyDefinitionDTO> propNameToPropDefinitionDTO) {
        this.propNameToPropDefinitionDTO = propNameToPropDefinitionDTO;
    }

    @Override
    public Collection<PropertyDefinitionDTO> getEnvVariables() {
        return propNameToPropDefinitionDTO.values();
    }
}
