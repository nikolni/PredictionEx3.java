package system.engine.world.rule.action.impl;

import system.engine.world.definition.entity.api.EntityDefinition;
import system.engine.world.definition.entity.secondary.api.SecondaryEntityDefinition;
import system.engine.world.execution.instance.enitty.api.EntityInstance;
import system.engine.world.rule.action.api.AbstractAction;
import system.engine.world.rule.action.api.ActionType;
import system.engine.world.rule.context.Context;

public class ReplaceAction extends AbstractAction {
    private final EntityDefinition createEntityDefinition;
    private final String mode;


    public ReplaceAction(EntityDefinition primaryEntityDefinition, SecondaryEntityDefinition secondaryEntityDefinition,
                            EntityDefinition createEntityDefinition,String mode) {
        super(ActionType.REPLACE, primaryEntityDefinition, secondaryEntityDefinition);
        this.createEntityDefinition=createEntityDefinition;
        this.mode=mode;
    }

    @Override
    public void executeAction(Context context) {
        EntityInstance EntityInstanceToKill=checkByDefinitionIfPrimaryOrSecondary(context);
        if(EntityInstanceToKill==null) //cant execute the action
            return;
        if(mode.equals("scratch"))
            context.getEntityInstanceManager().createEntityInstanceFromScratch(createEntityDefinition,EntityInstanceToKill);
        else
            context.getEntityInstanceManager().createEntityInstanceFromDerived(createEntityDefinition,EntityInstanceToKill);
        context.getEntityInstanceManager().killEntity(EntityInstanceToKill.getId());
    }

    public String getCreateEntityDefinitionName() {
        return createEntityDefinition.getUniqueName();
    }

    public String getMode() {
        return mode;
    }


}
