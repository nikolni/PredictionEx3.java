package system.engine.world.rule.action.api;


import system.engine.world.definition.entity.api.EntityDefinition;

public abstract class AbstractAction implements Action {

    private final ActionType actionType;
    private final EntityDefinition primaryEntityDefinition;
    //private final EntityDefinition secondEntityDefinition;

    protected AbstractAction(ActionType actionType, EntityDefinition primaryEntityDefinition
                             ) {
        this.actionType = actionType;
        this.primaryEntityDefinition = primaryEntityDefinition;
       // this.secondEntityDefinition = secondEntityDefinition;
    }

    /*
    protected AbstractAction(ActionType actionType, EntityDefinition primaryEntityDefinition,
                             EntityDefinition secondEntityDefinition) {
        this.actionType = actionType;
        this.primaryEntityDefinition = primaryEntityDefinition;
        this.secondEntityDefinition = secondEntityDefinition;
    }
     */

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
