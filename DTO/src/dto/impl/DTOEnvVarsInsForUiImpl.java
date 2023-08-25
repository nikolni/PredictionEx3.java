package dto.impl;

import dto.api.DTOEnvVarsInsForUi;
import dto.definition.property.instance.api.PropertyInstanceDTO;

import java.util.List;

public class DTOEnvVarsInsForUiImpl implements DTOEnvVarsInsForUi {
    private final List<PropertyInstanceDTO> environmentVars;

    public DTOEnvVarsInsForUiImpl(List<PropertyInstanceDTO> environmentVars){
        this.environmentVars = environmentVars;
    }

    @Override
    public List<PropertyInstanceDTO> getEnvironmentVars() {
        return environmentVars;
    }
}
