package system.engine.world.rule.action.impl;

import system.engine.world.definition.entity.api.EntityDefinition;
import system.engine.world.definition.entity.secondary.api.SecondaryEntityDefinition;
import system.engine.world.rule.action.api.AbstractAction;
import system.engine.world.rule.action.api.ActionType;
import system.engine.world.rule.context.Context;

public class KillAction extends AbstractAction {

    public KillAction(EntityDefinition entityDefinition, SecondaryEntityDefinition secondaryEntityDefinition) {
        super(ActionType.KILL, entityDefinition,secondaryEntityDefinition);
    }

    @Override
    public void executeAction(Context context) {
        context.removeEntity(context.getPrimaryEntityInstance());
    }

}
