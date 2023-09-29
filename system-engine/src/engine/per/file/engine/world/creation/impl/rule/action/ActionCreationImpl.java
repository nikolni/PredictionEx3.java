package engine.per.file.engine.world.creation.impl.rule.action;

import engine.per.file.engine.world.creation.api.ActionCreation;
import engine.per.file.engine.world.definition.entity.api.EntityDefinition;
import engine.per.file.engine.world.definition.entity.secondary.api.SecondaryEntityDefinition;
import engine.per.file.engine.world.rule.action.api.Action;
import engine.per.file.engine.world.rule.action.impl.KillAction;
import engine.per.file.engine.world.rule.action.impl.ProximityAction;
import engine.per.file.engine.world.rule.action.impl.ReplaceAction;
import engine.per.file.engine.world.rule.action.impl.SetAction;
import engine.per.file.engine.world.rule.action.impl.numeric.impl.DecreaseAction;
import engine.per.file.engine.world.rule.action.impl.numeric.impl.IncreaseAction;
import engine.per.file.engine.world.rule.action.impl.numeric.impl.calculation.DivideAction;
import engine.per.file.engine.world.rule.action.impl.numeric.impl.calculation.MultiplyAction;

public class ActionCreationImpl implements ActionCreation {
    @Override
    public Action createActionIncrease(EntityDefinition entityDefinition, SecondaryEntityDefinition secondaryEntityDefinition,String propertyName, String expressionStr){
        return new IncreaseAction(entityDefinition,secondaryEntityDefinition ,propertyName, expressionStr);
    }
    @Override
    public Action createActionDecrease(EntityDefinition entityDefinition,SecondaryEntityDefinition secondaryEntityDefinition, String propertyName, String expressionStr){
        return new DecreaseAction(entityDefinition,secondaryEntityDefinition ,propertyName, expressionStr);
    }

    @Override
    public Action createActionCalculationDivide(EntityDefinition entityDefinition,SecondaryEntityDefinition secondaryEntityDefinition, String resultPropertyName,
                String expressionStr1,String expressionStr2 ){
        return new DivideAction(entityDefinition,secondaryEntityDefinition, resultPropertyName, expressionStr1, expressionStr2);
    }
    @Override
    public Action createActionCalculationMultiply(EntityDefinition entityDefinition,SecondaryEntityDefinition secondaryEntityDefinition, String resultPropertyName,
                String expressionStr1,String expressionStr2){
        return new MultiplyAction(entityDefinition,secondaryEntityDefinition, resultPropertyName, expressionStr1, expressionStr2);
    }

    @Override
    public Action createActionSet(EntityDefinition entityDefinition,SecondaryEntityDefinition secondaryEntityDefinition, String propertyName, String expressionStr){
        return new SetAction(entityDefinition,secondaryEntityDefinition, propertyName, expressionStr);
    }
    @Override
    public Action createActionKill(EntityDefinition entityDefinition,SecondaryEntityDefinition secondaryEntityDefinition){
        return new KillAction(entityDefinition,secondaryEntityDefinition);
    }
    @Override
    public Action createActionReplace(EntityDefinition primaryEntityDefinition, SecondaryEntityDefinition secondaryEntityDefinition,
                                      EntityDefinition createEntityDefinition,String mode){
        return new ReplaceAction(primaryEntityDefinition,secondaryEntityDefinition,createEntityDefinition,mode);
    }
    @Override
    public ProximityAction createActionProximity(EntityDefinition entityDefinitionParam, SecondaryEntityDefinition secondaryEntityDefinition,
                                        String of, EntityDefinition targetEntityDefinition){
        return new ProximityAction(entityDefinitionParam,secondaryEntityDefinition,of,targetEntityDefinition);
    }
}
