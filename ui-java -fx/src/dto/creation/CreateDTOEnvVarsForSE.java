package dto.creation;

import app.body.screen2.tile.environment.variable.EnvironmentVariableController;
import dto.api.DTOEnvVarDefValuesForSE;
import dto.definition.property.definition.api.PropertyDefinitionDTO;
import dto.impl.DTOEnvVarDefValuesForSEImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CreateDTOEnvVarsForSE {

    /*public DTOEnvVarDefValuesForSE getData(List<Object> environmentVarInitValues, List<PropertyDefinitionDTO> propertyDefinitionDTOList) {
        return new DTOEnvVarDefValuesForSEImpl(environmentVarInitValues, propertyDefinitionDTOList);
    }*/

    public DTOEnvVarDefValuesForSE getData(Map<String, EnvironmentVariableController> envVarNameToTileController,
                                           List<PropertyDefinitionDTO> propertyDefinitionDTOList) {

        List<Object> environmentVarInitValues= new ArrayList<>();

        for (PropertyDefinitionDTO propertyDefinitionDTO : propertyDefinitionDTOList) {
            Object value= envVarNameToTileController.get(propertyDefinitionDTO.getUniqueName()).getValueTextField();
            environmentVarInitValues.add(value) ;
        }
        return new DTOEnvVarDefValuesForSEImpl(environmentVarInitValues, propertyDefinitionDTOList);
    }
}
