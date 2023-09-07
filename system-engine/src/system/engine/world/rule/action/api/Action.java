package system.engine.world.rule.action.api;


import system.engine.world.definition.entity.api.EntityDefinition;
import system.engine.world.definition.entity.secondary.api.SecondaryEntityDefinition;
import system.engine.world.execution.instance.enitty.api.EntityInstance;
import system.engine.world.execution.instance.enitty.manager.api.EntityInstanceManager;
import system.engine.world.execution.instance.property.api.PropertyInstance;
import system.engine.world.rule.context.Context;

public interface Action {
    void executeAction(Context context);
    ActionType getActionType();
    EntityDefinition getContextPrimaryEntity();
    EntityDefinition getExtendsSecondaryEntityDefinition();
    SecondaryEntityDefinition getSecondaryEntityDefinition();

     EntityInstance checkByDefinitionIfPrimaryOrSecondary(Context context);
    Integer getSecondEntityQuantity();
}
