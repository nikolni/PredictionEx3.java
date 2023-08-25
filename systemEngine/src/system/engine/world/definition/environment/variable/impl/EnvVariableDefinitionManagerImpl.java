package system.engine.world.definition.environment.variable.impl;

import system.engine.world.definition.environment.variable.api.EnvVariablesDefinitionManager;
import system.engine.world.definition.property.api.PropertyDefinition;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class EnvVariableDefinitionManagerImpl implements EnvVariablesDefinitionManager {

    private final Map<String, PropertyDefinition> propNameToPropDefinition;

    public EnvVariableDefinitionManagerImpl() {
        propNameToPropDefinition = new HashMap<>();
    }

    @Override
    public void addEnvironmentVariable(PropertyDefinition propertyDefinition) {
        propNameToPropDefinition.put(propertyDefinition.getUniqueName(), propertyDefinition);
    }


    @Override
    public PropertyDefinition getEnvVar(String name) {
        if (!propNameToPropDefinition.containsKey(name)) {
            throw new IllegalArgumentException("Can't find env variable with name " + name);
        }
        return propNameToPropDefinition.get(name);
    }
    @Override
    public Collection<PropertyDefinition> getEnvVariables() {
        return propNameToPropDefinition.values();
    }
}
