package engine.per.file.engine.world.rule.context;


import engine.per.file.engine.world.execution.instance.enitty.api.EntityInstance;
import engine.per.file.engine.world.execution.instance.enitty.manager.api.EntityInstanceManager;
import engine.per.file.engine.world.execution.instance.environment.api.EnvVariablesInstanceManager;
import engine.per.file.engine.world.execution.instance.property.api.PropertyInstance;

import java.util.List;

public interface Context {
    EntityInstance getPrimaryEntityInstance();
    EntityInstance getSecondEntityInstance();
    void setSecondEntityInstance(EntityInstance secondEntityInstance);
    void removeEntity(EntityInstance entityInstance);
    PropertyInstance getEnvironmentVariable(String name);
    Integer getTickNumber();
    EntityInstanceManager getEntityInstanceManager();
    EnvVariablesInstanceManager getEnvVariablesInstanceManager();
    List<EntityInstance> getEntitiesToKill();

}
