package after.login.dto.creation;

import after.login.component.body.execution.tile.environment.variable.EnvironmentVariableController;
import dto.definition.property.definition.PropertyDefinitionDTO;
import dto.primary.DTOEnvVarDefValuesForSE;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CreateDTOEnvVarsForSE {

    public DTOEnvVarDefValuesForSE getData(Map<String, EnvironmentVariableController> envVarNameToTileController,
                                                   List<PropertyDefinitionDTO> propertyDefinitionDTOList) {

        List<Object> environmentVarInitValues= new ArrayList<>();

        for (PropertyDefinitionDTO propertyDefinitionDTO : propertyDefinitionDTOList) {
            Object value= envVarNameToTileController.get(propertyDefinitionDTO.getUniqueName()).getValueTextField();
            environmentVarInitValues.add(value) ;
        }
        return new DTOEnvVarDefValuesForSE(environmentVarInitValues, propertyDefinitionDTOList);
    }
}
