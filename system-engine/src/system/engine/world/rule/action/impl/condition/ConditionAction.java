package system.engine.world.rule.action.impl.condition;

import system.engine.world.definition.entity.api.EntityDefinition;
import system.engine.world.rule.action.api.AbstractAction;
import system.engine.world.rule.action.api.Action;
import system.engine.world.rule.action.api.ActionType;
import system.engine.world.rule.context.Context;

import java.util.ArrayList;
import java.util.List;

public abstract class ConditionAction extends AbstractAction {
    protected String singularity;
    protected List<Action> thenActionList;
    protected List<Action> elseActionList;

    public ConditionAction(String singularity, EntityDefinition entityDefinitionParam) {
        super(ActionType.CONDITION, entityDefinitionParam);
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

//    public List<AbstractAction> getThenActionList() {
//        return thenActionList;
//    }
//
//    public List<AbstractAction> getElseActionList() {
//        return elseActionList;
//    }
}
