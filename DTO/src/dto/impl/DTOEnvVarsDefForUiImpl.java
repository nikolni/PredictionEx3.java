package dto.impl;

import dto.api.DTOEnvVarsDefForUi;
import dto.definition.property.definition.api.PropertyDefinitionDTO;

import java.util.List;

public class DTOEnvVarsDefForUiImpl implements DTOEnvVarsDefForUi {

    private final List<PropertyDefinitionDTO> environmentVars;

    public DTOEnvVarsDefForUiImpl(List<PropertyDefinitionDTO> environmentVars){
        this.environmentVars = environmentVars;
    }

    @Override
    public List<PropertyDefinitionDTO> getEnvironmentVars() {
        return environmentVars;
    }
}
