package system.engine.world.execution.instance.environment.api;


import system.engine.world.execution.instance.property.api.PropertyInstance;

import java.util.List;

public interface EnvVariablesInstanceManager {
    PropertyInstance getEnvVar(String name);
    List<PropertyInstance> getEnvVarsList();
    void addPropertyInstance(PropertyInstance propertyInstance);
}
