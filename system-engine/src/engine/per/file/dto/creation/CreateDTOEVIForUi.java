package engine.per.file.dto.creation;

import dto.definition.property.definition.PropertyDefinitionDTO;
import dto.definition.property.instance.PropertyInstanceDTO;
import dto.primary.DTOEnvVarsInsForUi;
import engine.per.file.engine.world.execution.instance.environment.api.EnvVariablesInstanceManager;
import engine.per.file.engine.world.execution.instance.property.api.PropertyInstance;
import engine.per.file.engine.world.definition.property.api.PropertyDefinition;

import java.util.ArrayList;
import java.util.List;

public class CreateDTOEVIForUi {


    public DTOEnvVarsInsForUi getData(EnvVariablesInstanceManager envVariablesInstanceManager) {
        List<PropertyInstanceDTO> environmentVars= new ArrayList<>();

        for(PropertyInstance environmentVar : envVariablesInstanceManager.getEnvVarsList()){
            environmentVars.add(createEnvironmentVarDTO(environmentVar));
        }
        return new DTOEnvVarsInsForUi(environmentVars);
    }

    private PropertyInstanceDTO createEnvironmentVarDTO(PropertyInstance environmentVar){
        PropertyDefinitionDTO propertyDefinitionDTO = createEnvironmentVarDTO (environmentVar.getPropertyDefinition());
        return new PropertyInstanceDTO(propertyDefinitionDTO, environmentVar.getValue());
    }

    private PropertyDefinitionDTO createEnvironmentVarDTO(PropertyDefinition environmentVar){
        return  new PropertyDefinitionDTO(environmentVar.getUniqueName(), environmentVar.getType().toString(),
                environmentVar.isRandomInitialized(), environmentVar.doesHaveRange(), environmentVar.getRange());
    }
}
