package ui.dto.creation;

import dto.api.DTOEnvVarDefValuesForSE;
import dto.definition.property.definition.api.PropertyDefinitionDTO;
import dto.impl.DTOEnvVarDefValuesForSEImpl;

import java.util.List;

public class CreateDTOMenu3ForSE {

    public DTOEnvVarDefValuesForSE getDataForMenu3(List<Object> environmentVarInitValues, List<PropertyDefinitionDTO> propertyDefinitionDTOList) {
        return new DTOEnvVarDefValuesForSEImpl(environmentVarInitValues, propertyDefinitionDTOList);
    }
}
