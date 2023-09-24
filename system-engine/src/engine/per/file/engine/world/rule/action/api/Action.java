package engine.per.file.engine.world.rule.action.api;


import engine.per.file.engine.world.definition.entity.api.EntityDefinition;
import engine.per.file.engine.world.definition.entity.secondary.api.SecondaryEntityDefinition;
import engine.per.file.engine.world.execution.instance.enitty.api.EntityInstance;
import engine.per.file.engine.world.rule.context.Context;

public interface Action {
    void executeAction(Context context);
    ActionType getActionType();
    EntityDefinition getContextPrimaryEntity();
    EntityDefinition getExtendsSecondaryEntityDefinition();
    SecondaryEntityDefinition getSecondaryEntityDefinition();

     EntityInstance checkByDefinitionIfPrimaryOrSecondary(Context context);
    Integer getSecondEntityQuantity();
}
