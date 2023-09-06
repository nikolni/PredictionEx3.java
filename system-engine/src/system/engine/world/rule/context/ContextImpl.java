package system.engine.world.rule.context;

import system.engine.world.execution.instance.enitty.api.EntityInstance;
import system.engine.world.execution.instance.environment.api.EnvVariablesInstanceManager;
import system.engine.world.execution.instance.property.api.PropertyInstance;

import java.util.List;

public class ContextImpl implements Context {

    private final EntityInstance primaryEntityInstance;
    private EntityInstance secondEntityInstance;
    private final EnvVariablesInstanceManager envVariablesInstanceManager;
    private List<EntityInstance> entitiesToKill;
    private Integer tickNumber = 0;

   /* public ContextImpl(EntityInstance primaryEntityInstance,
                       EnvVariablesInstanceManager envVariablesInstanceManager,
                       List<EntityInstance> entitiesToKill) {
        this.primaryEntityInstance = primaryEntityInstance;
        //this.secondEntityInstance = secondEntityInstance;
        this.envVariablesInstanceManager  =  envVariablesInstanceManager;
        this.entitiesToKill = entitiesToKill;
    }*/
    public ContextImpl(EntityInstance primaryEntityInstance, EntityInstance secondEntityInstance,
                       EnvVariablesInstanceManager envVariablesInstanceManager,
                       List<EntityInstance> entitiesToKill, Integer tickNumber) {
        this.primaryEntityInstance = primaryEntityInstance;
        this.secondEntityInstance = secondEntityInstance;
        this.envVariablesInstanceManager  =  envVariablesInstanceManager;
        this.entitiesToKill = entitiesToKill;
        this.tickNumber =  tickNumber;
    }

    @Override
    public EntityInstance getPrimaryEntityInstance() {
        return primaryEntityInstance;
    }

    @Override
    public void removeEntity(EntityInstance entityInstance) {
        entitiesToKill.add(entityInstance);
    }

    @Override
    public PropertyInstance getEnvironmentVariable(String name) {
        return envVariablesInstanceManager.getEnvVar(name);
    }

    @Override
    public EntityInstance getSecondEntityInstance() {
        return secondEntityInstance;
    }

    @Override
    public void setSecondEntityInstance(EntityInstance secondEntityInstance) {
        this.secondEntityInstance = secondEntityInstance;
    }

    @Override
    public Integer getTickNumber() {
        return tickNumber;
    }
}
