package dto.api;

import dto.definition.property.definition.api.PropertyDefinitionDTO;
import java.util.List;

public interface DTOEnvVarsDefForUi {
    List<PropertyDefinitionDTO> getEnvironmentVars();
}
