package dto.impl;

import dto.api.DTOEnvVarDefValuesForSE;
import dto.definition.property.definition.api.PropertyDefinitionDTO;

import java.util.List;

public class DTOEnvVarDefValuesForSEImpl implements DTOEnvVarDefValuesForSE {

    private List<Object> environmentVarInitValues;
    private List<PropertyDefinitionDTO> propertyDefinitionDTOList;

    public DTOEnvVarDefValuesForSEImpl(List<Object> environmentVarInitValues, List<PropertyDefinitionDTO> propertyDefinitionDTOList){
        this.environmentVarInitValues = environmentVarInitValues;
        this.propertyDefinitionDTOList = propertyDefinitionDTOList;
    }

    @Override
    public List<Object> getEnvironmentVarInitValues() {
        return environmentVarInitValues;
    }

    @Override
    public List<PropertyDefinitionDTO> getPropertyDefinitionDTOList() {
        return propertyDefinitionDTOList;
    }
}
