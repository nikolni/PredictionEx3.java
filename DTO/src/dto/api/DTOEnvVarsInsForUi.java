package dto.api;

import dto.definition.property.instance.api.PropertyInstanceDTO;

import java.util.List;

public interface DTOEnvVarsInsForUi {
    List<PropertyInstanceDTO> getEnvironmentVars();
}
