package system.engine.world.definition.entity.secondary.api;

import system.engine.world.api.WorldInstance;
import system.engine.world.definition.entity.api.EntityDefinition;
import system.engine.world.execution.instance.enitty.api.EntityInstance;
import system.engine.world.execution.instance.environment.api.EnvVariablesInstanceManager;
import system.engine.world.rule.action.impl.condition.ConditionAction;

import java.util.List;

public interface SecondaryEntityDefinition {
     EntityDefinition getExtendsEntityDefinition();

     String getSecEntityCount();

     ConditionAction getSelectionCondition();
     List<EntityInstance> generateSecondaryEntityList(WorldInstance worldInstance,
                                                      EnvVariablesInstanceManager envVariablesInstanceManager, Integer tickNumber,List<EntityInstance> deadEntities);

    }
