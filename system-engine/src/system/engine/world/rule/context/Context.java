package system.engine.world.rule.context;


import system.engine.world.execution.instance.enitty.api.EntityInstance;
import system.engine.world.execution.instance.property.api.PropertyInstance;

public interface Context {
    EntityInstance getPrimaryEntityInstance();
    EntityInstance getSecondEntityInstance();
    void setSecondEntityInstance(EntityInstance secondEntityInstance);
    void removeEntity(EntityInstance entityInstance);
    PropertyInstance getEnvironmentVariable(String name);
    Integer getTickNumber();

}
