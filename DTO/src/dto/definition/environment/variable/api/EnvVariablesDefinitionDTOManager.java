package dto.definition.environment.variable.api;

import dto.definition.property.definition.api.PropertyDefinitionDTO;
import java.util.Collection;

public interface EnvVariablesDefinitionDTOManager {
    Collection<PropertyDefinitionDTO> getEnvVariables();
}
