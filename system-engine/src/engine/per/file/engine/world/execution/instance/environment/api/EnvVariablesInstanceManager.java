package engine.per.file.engine.world.execution.instance.environment.api;


import engine.per.file.engine.world.execution.instance.property.api.PropertyInstance;
import engine.per.file.engine.world.definition.environment.variable.api.EnvVariablesDefinitionManager;

import java.util.List;

public interface EnvVariablesInstanceManager {
    PropertyInstance getEnvVar(String name);
    List<PropertyInstance> getEnvVarsList();
    void addPropertyInstance(PropertyInstance propertyInstance);
    EnvVariablesDefinitionManager getEnvVariablesDefinitionManager();
}
