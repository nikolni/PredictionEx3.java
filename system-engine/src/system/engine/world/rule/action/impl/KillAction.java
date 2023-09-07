package system.engine.world.rule.action.impl;

import system.engine.world.definition.entity.api.EntityDefinition;
import system.engine.world.definition.entity.secondary.api.SecondaryEntityDefinition;
import system.engine.world.execution.instance.enitty.api.EntityInstance;
import system.engine.world.execution.instance.enitty.manager.api.EntityInstanceManager;
import system.engine.world.execution.instance.property.api.PropertyInstance;
import system.engine.world.rule.action.api.AbstractAction;
import system.engine.world.rule.action.api.ActionType;
import system.engine.world.rule.context.Context;

public class KillAction extends AbstractAction {

    public KillAction(EntityDefinition entityDefinition, SecondaryEntityDefinition secondaryEntityDefinition) {
        super(ActionType.KILL, entityDefinition,secondaryEntityDefinition);
    }

    @Override
    public void executeAction(Context context) {

        EntityInstance actionEntityInstance=checkByDefinitionIfPrimaryOrSecondary(context);
        if(actionEntityInstance==null) //cant execute the action
            return;
        context.removeEntity(actionEntityInstance);

    }


}
