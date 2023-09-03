package system.engine.world.definition.entity.secondary.api;

import system.engine.world.definition.entity.api.EntityDefinition;
import system.engine.world.rule.action.impl.condition.ConditionAction;

public interface SecondaryEntityDefinition {
    public EntityDefinition getExtendsEntityDefinition();

    public String getSecEntityCount();

    public ConditionAction getSelectionCondition();
}
