package dto.creation;

import dto.api.DTOEnvVarsInsForUi;
import dto.definition.property.definition.api.PropertyDefinitionDTO;
import dto.definition.property.definition.impl.PropertyDefinitionDTOImpl;
import dto.definition.property.instance.api.PropertyInstanceDTO;
import dto.definition.property.instance.impl.PropertyInstanceDTOImpl;
import dto.impl.DTOEnvVarsInsForUiImpl;
import system.engine.world.definition.property.api.PropertyDefinition;
import system.engine.world.execution.instance.environment.api.EnvVariablesInstanceManager;
import system.engine.world.execution.instance.property.api.PropertyInstance;

import java.util.ArrayList;
import java.util.List;

public class CreateDTOEVIForUi {


    public DTOEnvVarsInsForUi getData(EnvVariablesInstanceManager envVariablesInstanceManager) {
        List<PropertyInstanceDTO> environmentVars= new ArrayList<>();

        for(PropertyInstance environmentVar : envVariablesInstanceManager.getEnvVarsList()){
            environmentVars.add(createEnvironmentVarDTO(environmentVar));
        }
        return new DTOEnvVarsInsForUiImpl(environmentVars);
    }

    private PropertyInstanceDTO createEnvironmentVarDTO(PropertyInstance environmentVar){
        PropertyDefinitionDTO propertyDefinitionDTO = createEnvironmentVarDTO (environmentVar.getPropertyDefinition());
        return new PropertyInstanceDTOImpl(propertyDefinitionDTO, environmentVar.getValue());
    }

    private PropertyDefinitionDTO createEnvironmentVarDTO(PropertyDefinition environmentVar){
        return  new PropertyDefinitionDTOImpl(environmentVar.getUniqueName(), environmentVar.getType().toString(),
                environmentVar.isRandomInitialized(), environmentVar.doesHaveRange(), environmentVar.getRange());
    }
}
