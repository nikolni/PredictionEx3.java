package engine.per.file.engine.world.definition.environment.variable.api;

import engine.per.file.engine.world.definition.property.api.PropertyDefinition;

import java.util.Collection;

public interface EnvVariablesDefinitionManager {
    void addEnvironmentVariable(PropertyDefinition propertyDefinition);
    Collection<PropertyDefinition> getEnvVariables();

    PropertyDefinition getEnvVar(String name);
}
