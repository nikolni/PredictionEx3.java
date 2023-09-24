package engine.per.file.engine.world.definition.entity.secondary.api;

import engine.per.file.engine.world.definition.entity.api.EntityDefinition;
import engine.per.file.engine.world.execution.instance.enitty.api.EntityInstance;
import engine.per.file.engine.world.execution.instance.environment.api.EnvVariablesInstanceManager;
import engine.per.file.engine.world.api.WorldInstance;
import engine.per.file.engine.world.rule.action.impl.condition.ConditionAction;

import java.util.List;

public interface SecondaryEntityDefinition {
     EntityDefinition getExtendsEntityDefinition();

     String getSecEntityCount();

     ConditionAction getSelectionCondition();
     List<EntityInstance> generateSecondaryEntityList(WorldInstance worldInstance,
                                                      EnvVariablesInstanceManager envVariablesInstanceManager, Integer tickNumber, List<EntityInstance> deadEntities);

    }
