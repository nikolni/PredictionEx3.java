package engine.per.file.engine.world.rule.action.api;


import engine.per.file.engine.world.definition.entity.api.EntityDefinition;
import engine.per.file.engine.world.definition.entity.secondary.api.SecondaryEntityDefinition;
import engine.per.file.engine.world.execution.instance.enitty.api.EntityInstance;
import engine.per.file.engine.world.rule.context.Context;

public abstract class AbstractAction implements Action {

    private final ActionType actionType;
    private final EntityDefinition primaryEntityDefinition;
    private SecondaryEntityDefinition secondaryEntityDefinition;

    protected AbstractAction(ActionType actionType, EntityDefinition primaryEntityDefinition,
                             SecondaryEntityDefinition secondaryEntityDefinition){
        this.actionType = actionType;
        this.primaryEntityDefinition = primaryEntityDefinition;
       this.secondaryEntityDefinition=secondaryEntityDefinition;
    }

    @Override
    public EntityInstance checkByDefinitionIfPrimaryOrSecondary(Context context){
        EntityInstance entityInstance=null;
        if(context.getPrimaryEntityInstance()==null)
            return entityInstance;
        if(context.getSecondEntityInstance()!=null){
            if(getContextPrimaryEntity().getUniqueName().equals(context.getPrimaryEntityInstance().getEntityDefinition().getUniqueName()))
                return context.getPrimaryEntityInstance();
            if(getContextPrimaryEntity().getUniqueName().equals(context.getSecondEntityInstance().getEntityDefinition().getUniqueName()))
                return context.getSecondEntityInstance();
        }
        else{
            if(getContextPrimaryEntity().getUniqueName().equals(context.getPrimaryEntityInstance().getEntityDefinition().getUniqueName()))
                return context.getPrimaryEntityInstance();
        }

        return entityInstance;
    }


    @Override
    public ActionType getActionType() {
        return actionType;
    }

    @Override
    public EntityDefinition getContextPrimaryEntity() {
        return primaryEntityDefinition;
    }
    public EntityDefinition getExtendsSecondaryEntityDefinition() {
        if(secondaryEntityDefinition!=null)
            return secondaryEntityDefinition.getExtendsEntityDefinition();
        return null;
    }

    @Override
    public SecondaryEntityDefinition getSecondaryEntityDefinition() {
        return secondaryEntityDefinition;
    }

    public void setSecondaryEntityDefinition(SecondaryEntityDefinition secondaryEntityDefinition) {
        this.secondaryEntityDefinition = secondaryEntityDefinition;
    }

    @Override
    public Integer getSecondEntityQuantity() {
        return Integer.parseInt(secondaryEntityDefinition.getSecEntityCount());
    }
}
