package engine.per.file.engine.world.rule.action.impl.condition;

import engine.per.file.engine.world.definition.entity.api.EntityDefinition;
import engine.per.file.engine.world.definition.entity.secondary.api.SecondaryEntityDefinition;
import engine.per.file.engine.world.rule.action.api.AbstractAction;
import engine.per.file.engine.world.rule.action.api.Action;
import engine.per.file.engine.world.rule.action.api.ActionType;
import engine.per.file.engine.world.rule.context.Context;

import java.util.ArrayList;
import java.util.List;

public abstract class ConditionAction extends AbstractAction {
    protected String singularity;
    protected List<Action> thenActionList;
    protected List<Action> elseActionList;

    public ConditionAction(String singularity, EntityDefinition entityDefinitionParam, SecondaryEntityDefinition secondaryEntityDefinition) {
        super(ActionType.CONDITION, entityDefinitionParam,secondaryEntityDefinition);
        this.singularity = singularity;
        thenActionList = new ArrayList<>();
        elseActionList = new ArrayList<>();
    }

    public void addActionToThenList(Action action){thenActionList.add(action);}
    public void addActionToElseList(Action action){elseActionList.add(action);}
    public abstract boolean isConditionFulfilled(Context context);

    public String getSingularity() {
        return singularity;
    }

  public Integer getThenActionsNumber() {
       return thenActionList.size();}

    public Integer getElseActionsNumber() {
        return elseActionList.size();
    }
}
