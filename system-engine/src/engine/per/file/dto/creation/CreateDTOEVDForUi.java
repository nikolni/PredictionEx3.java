package engine.per.file.dto.creation;

import dto.definition.property.definition.PropertyDefinitionDTO;
import dto.primary.DTOEnvVarsDefForUi;
import engine.per.file.engine.world.definition.property.api.PropertyDefinition;
import engine.per.file.engine.world.api.WorldDefinition;

import java.util.ArrayList;
import java.util.List;

public class CreateDTOEVDForUi {

    public DTOEnvVarsDefForUi getData(WorldDefinition worldDefinition) {
        List<PropertyDefinitionDTO> environmentVars= new ArrayList<>();

        for(PropertyDefinition environmentVar : worldDefinition.getEnvVariablesDefinitionManager().getEnvVariables()){
            environmentVars.add(createEnvironmentVarDTO(environmentVar));
        }
        return new DTOEnvVarsDefForUi(environmentVars);
    }

    private PropertyDefinitionDTO createEnvironmentVarDTO(PropertyDefinition environmentVar){
        return  new PropertyDefinitionDTO(environmentVar.getUniqueName(), environmentVar.getType().toString(),
                environmentVar.isRandomInitialized(), environmentVar.doesHaveRange(), environmentVar.getRange());
    }
}
