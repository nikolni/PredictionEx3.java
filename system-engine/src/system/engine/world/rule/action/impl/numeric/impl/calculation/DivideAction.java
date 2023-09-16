package system.engine.world.rule.action.impl.numeric.impl.calculation;

import system.engine.world.creation.api.ExpressionCreation;
import system.engine.world.creation.impl.expression.ExpressionCreationImpl;
import system.engine.world.definition.entity.secondary.api.SecondaryEntityDefinition;
import system.engine.world.execution.instance.enitty.api.EntityInstance;
import system.engine.world.execution.instance.enitty.manager.api.EntityInstanceManager;
import system.engine.world.rule.action.impl.numeric.api.NumericVerify;
import system.engine.world.rule.context.Context;
import system.engine.world.execution.instance.property.api.PropertyInstance;
import system.engine.world.definition.entity.api.EntityDefinition;
import system.engine.world.rule.action.expression.api.Expression;

public class DivideAction extends CalculationAction {
    public DivideAction(EntityDefinition entityDefinitionParam, SecondaryEntityDefinition secondaryEntityDefinition, String propertyNameParam, String expressionStrParam1, String expressionStrParam2){
        super(entityDefinitionParam,secondaryEntityDefinition, propertyNameParam, expressionStrParam1, expressionStrParam2);
    }

    @Override
    public void executeAction(Context context) throws IllegalArgumentException{
        ExpressionCreation expressionCreation = new ExpressionCreationImpl();
        EntityInstance actionEntityInstance=checkByDefinitionIfPrimaryOrSecondary(context);
        if(actionEntityInstance==null) //cant execute the action
            return;
        PropertyInstance propertyInstance=actionEntityInstance.getPropertyByName(resultPropName);

        Expression expression1 = expressionCreation.craeteExpression(expressionStrArg1, actionEntityInstance);
        Expression expression2 = expressionCreation.craeteExpression(expressionStrArg2, actionEntityInstance);
        //can assume that property type is float

        if (!NumericVerify.verifyNumericExpressionValue(expression1, context) |
        (!NumericVerify.verifyNumericExpressionValue(expression2, context)) ) {
            throw new IllegalArgumentException("can't cast one of expression value to type of property " + resultPropName);
        }

        Float f1 = Float.parseFloat(expression1.evaluateExpression(context).toString());
        Float f2 = Float.parseFloat(expression2.evaluateExpression(context).toString());
        if(f2 == 0f){
            throw new IllegalArgumentException("can't divide by zero!");
        }
        Float fResult = f1 / f2;
        if(propertyInstance.getPropertyDefinition().doesHaveRange()){
            Float fMinRange = Float.parseFloat(propertyInstance.getPropertyDefinition().getRange().get(0).toString());;
            if(fResult < fMinRange){
                fResult = fMinRange;
            }
        }
        propertyInstance.setLastTickNumberOfValueUpdate(context.getTickNumber(), fResult);
        propertyInstance.setValue(fResult);
        }
}


