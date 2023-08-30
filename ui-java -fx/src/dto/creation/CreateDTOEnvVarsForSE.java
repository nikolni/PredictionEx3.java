package dto.creation;

import dto.api.DTOEnvVarDefValuesForSE;
import dto.definition.property.definition.api.PropertyDefinitionDTO;
import dto.impl.DTOEnvVarDefValuesForSEImpl;

import java.util.List;

public class CreateDTOEnvVarsForSE {

    public DTOEnvVarDefValuesForSE getData(List<Object> environmentVarInitValues, List<PropertyDefinitionDTO> propertyDefinitionDTOList) {
        return new DTOEnvVarDefValuesForSEImpl(environmentVarInitValues, propertyDefinitionDTOList);
    }
}
