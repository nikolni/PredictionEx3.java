package engine.per.file.engine.world.execution.instance.environment.impl;

import engine.per.file.engine.world.execution.instance.environment.api.EnvVariablesInstanceManager;
import engine.per.file.engine.world.execution.instance.property.api.PropertyInstance;
import engine.per.file.engine.world.execution.instance.property.impl.PropertyInstanceImpl;
import engine.per.file.engine.world.definition.environment.variable.api.EnvVariablesDefinitionManager;
import engine.per.file.engine.world.definition.property.api.PropertyDefinition;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EnvVariablesInstanceManagerImpl implements EnvVariablesInstanceManager {
    private final Map<String, PropertyInstance> envVariables;

    private final EnvVariablesDefinitionManager envVariablesDefinitionManager;

    public EnvVariablesInstanceManagerImpl(EnvVariablesDefinitionManager envVariablesDefinitionManager) {
        envVariables = new HashMap<>();
        for (PropertyDefinition propertyDefinition : envVariablesDefinitionManager.getEnvVariables()){
            Object value = propertyDefinition.generateValue();
            PropertyInstance newPropertyInstance = new PropertyInstanceImpl(propertyDefinition, value);
            this.addPropertyInstance(newPropertyInstance);
        }
        this.envVariablesDefinitionManager = envVariablesDefinitionManager;
    }

    @Override
    public PropertyInstance getEnvVar(String name) {
        if (!envVariables.containsKey(name)) {
            throw new IllegalArgumentException("Can't find env variable with name " + name);
        }
        return envVariables.get(name);
    }

    @Override
    public List<PropertyInstance> getEnvVarsList() {
        List<PropertyInstance> list = new ArrayList<>();
        list.addAll(envVariables.values());
        return list;
    }

    @Override
    public void addPropertyInstance(PropertyInstance propertyInstance) {
        envVariables.put(propertyInstance.getPropertyDefinition().getUniqueName(), propertyInstance);
    }

    @Override
    public EnvVariablesDefinitionManager getEnvVariablesDefinitionManager() {
        return envVariablesDefinitionManager;
    }

}
