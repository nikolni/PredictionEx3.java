package dto.api;

import dto.definition.property.definition.api.PropertyDefinitionDTO;

import java.util.List;

public interface DTOEnvVarDefValuesForSE {

    List<Object> getEnvironmentVarInitValues();
    List<PropertyDefinitionDTO> getPropertyDefinitionDTOList();
}

