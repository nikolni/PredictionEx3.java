package system.engine.world.rule.action.api;


import system.engine.world.definition.entity.api.EntityDefinition;

public abstract class AbstractAction implements Action {

    private final ActionType actionType;
    private final EntityDefinition primaryEntityDefinition;

    protected AbstractAction(ActionType actionType, EntityDefinition primaryEntityDefinition) {
        this.actionType = actionType;
        this.primaryEntityDefinition = primaryEntityDefinition;
    }

    @Override
    public ActionType getActionType() {
        return actionType;
    }

    @Override
    public EntityDefinition getContextEntity() {
        return primaryEntityDefinition;
    }
}
