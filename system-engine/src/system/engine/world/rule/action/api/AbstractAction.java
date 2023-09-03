package system.engine.world.rule.action.api;


import system.engine.world.definition.entity.api.EntityDefinition;
import system.engine.world.definition.entity.secondary.api.SecondaryEntityDefinition;

public abstract class AbstractAction implements Action {

    private final ActionType actionType;
    private final EntityDefinition primaryEntityDefinition;
    private SecondaryEntityDefinition secondaryEntityDefinition;

    protected AbstractAction(ActionType actionType, EntityDefinition primaryEntityDefinition,SecondaryEntityDefinition secondaryEntityDefinition){
        this.actionType = actionType;
        this.primaryEntityDefinition = primaryEntityDefinition;
       this.secondaryEntityDefinition=secondaryEntityDefinition;
    }


    @Override
    public ActionType getActionType() {
        return actionType;
    }

    @Override
    public EntityDefinition getContextPrimaryEntity() {
        return primaryEntityDefinition;
    }

    /*@Override
    public EntityDefinition getContextSecondEntity() {
        return secondEntityDefinition;
    }*/
}
