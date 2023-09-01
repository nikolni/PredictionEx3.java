package system.engine.world.definition.environment.variable.api;

import system.engine.world.definition.property.api.PropertyDefinition;
import system.engine.world.execution.instance.environment.api.EnvVariablesInstanceManager;
import system.engine.world.execution.instance.property.api.PropertyInstance;

import java.util.Collection;

public interface EnvVariablesDefinitionManager {
    void addEnvironmentVariable(PropertyDefinition propertyDefinition);
    Collection<PropertyDefinition> getEnvVariables();

    PropertyDefinition getEnvVar(String name);
}
