package engine.per.file.engine.world.rule.action.impl;

import engine.per.file.engine.world.definition.entity.api.EntityDefinition;
import engine.per.file.engine.world.definition.entity.secondary.api.SecondaryEntityDefinition;
import engine.per.file.engine.world.execution.instance.enitty.api.EntityInstance;
import engine.per.file.engine.world.rule.action.api.AbstractAction;
import engine.per.file.engine.world.rule.action.api.ActionType;
import engine.per.file.engine.world.rule.context.Context;

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
