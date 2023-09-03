package system.engine.world.creation.impl.rule.action;

import system.engine.world.creation.api.ActionCreation;
import system.engine.world.definition.entity.api.EntityDefinition;
import system.engine.world.definition.entity.secondary.api.SecondaryEntityDefinition;
import system.engine.world.rule.action.api.Action;
import system.engine.world.rule.action.impl.KillAction;
import system.engine.world.rule.action.impl.SetAction;
import system.engine.world.rule.action.impl.numeric.impl.DecreaseAction;
import system.engine.world.rule.action.impl.numeric.impl.IncreaseAction;
import system.engine.world.rule.action.impl.numeric.impl.calculation.DivideAction;
import system.engine.world.rule.action.impl.numeric.impl.calculation.MultiplyAction;

public class ActionCreationImpl implements ActionCreation {

    public Action createActionIncrease(EntityDefinition entityDefinition, SecondaryEntityDefinition secondaryEntityDefinition,String propertyName, String expressionStr){
        return new IncreaseAction(entityDefinition,secondaryEntityDefinition ,propertyName, expressionStr);
    }

    public Action createActionDecrease(EntityDefinition entityDefinition,SecondaryEntityDefinition secondaryEntityDefinition, String propertyName, String expressionStr){
        return new DecreaseAction(entityDefinition,secondaryEntityDefinition ,propertyName, expressionStr);
    }


    public Action createActionCalculationDivide(EntityDefinition entityDefinition,SecondaryEntityDefinition secondaryEntityDefinition, String resultPropertyName,
                String expressionStr1,String expressionStr2 ){
        return new DivideAction(entityDefinition,secondaryEntityDefinition, resultPropertyName, expressionStr1, expressionStr2);
    }

    public Action createActionCalculationMultiply(EntityDefinition entityDefinition,SecondaryEntityDefinition secondaryEntityDefinition, String resultPropertyName,
                String expressionStr1,String expressionStr2){
        return new MultiplyAction(entityDefinition,secondaryEntityDefinition, resultPropertyName, expressionStr1, expressionStr2);
    }


    public Action createActionSet(EntityDefinition entityDefinition,SecondaryEntityDefinition secondaryEntityDefinition, String propertyName, String expressionStr){
        return new SetAction(entityDefinition,secondaryEntityDefinition, propertyName, expressionStr);
    }

    public Action createActionKill(EntityDefinition entityDefinition,SecondaryEntityDefinition secondaryEntityDefinition){
        return new KillAction(entityDefinition,secondaryEntityDefinition);
    }
}
