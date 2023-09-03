package system.engine.world.rule.action.impl.condition;

import system.engine.world.definition.entity.secondary.api.SecondaryEntityDefinition;
import system.engine.world.rule.context.Context;
import system.engine.world.definition.entity.api.EntityDefinition;
import system.engine.world.rule.action.api.Action;

import java.util.ArrayList;
import java.util.List;

public class MultipleConditionAction extends ConditionAction {
    private final String logical;
    private List<ConditionAction> conditionsCollection;

    public MultipleConditionAction(String singularity, EntityDefinition entityDefinitionParam, SecondaryEntityDefinition secondaryEntityDefinition, String logicalParam) {
        super(singularity,entityDefinitionParam,secondaryEntityDefinition);
        logical = logicalParam;
        conditionsCollection = new ArrayList<>();
    }

    @Override
    public void executeAction(Context context) throws IllegalArgumentException {
        try {
            if (isConditionFulfilled(context)) {
                for (Action action : thenActionList) {
                    action.executeAction(context);
                }
            }
            else{
                for (Action action : elseActionList) {
                    action.executeAction(context);
                }
            }
        }
            catch (IllegalArgumentException e){
                throw e;
            }

        }


    public boolean isConditionFulfilled(Context context) {
        boolean isConditionFulfilled= false;

        switch (logical) {
            case "or":
                isConditionFulfilled = false;
                for (ConditionAction conditionAction : conditionsCollection) {
                    isConditionFulfilled |= conditionAction.isConditionFulfilled(context);
                }
                break;
            case "and":
                isConditionFulfilled = true;
                for (ConditionAction conditionAction : conditionsCollection) {
                    isConditionFulfilled &=conditionAction.isConditionFulfilled(context);
                }
                break;
        }
        return isConditionFulfilled;
    }

    public void addConditionToConditionsCollection(ConditionAction conditionAction){
        conditionsCollection.add(conditionAction);
    }
}
