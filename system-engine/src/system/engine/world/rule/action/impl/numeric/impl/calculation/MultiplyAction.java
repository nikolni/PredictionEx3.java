package system.engine.world.rule.action.impl.numeric.impl.calculation;

import system.engine.world.creation.api.ExpressionCreation;
import system.engine.world.creation.impl.expression.ExpressionCreationImpl;
import system.engine.world.definition.entity.secondary.api.SecondaryEntityDefinition;
import system.engine.world.rule.action.impl.numeric.api.NumericVerify;
import system.engine.world.rule.context.Context;
import system.engine.world.rule.enums.Type;
import system.engine.world.execution.instance.property.api.PropertyInstance;
import system.engine.world.definition.entity.api.EntityDefinition;
import system.engine.world.rule.action.expression.api.Expression;

public class MultiplyAction extends CalculationAction {
    public MultiplyAction(EntityDefinition entityDefinitionParam, SecondaryEntityDefinition secondaryEntityDefinition, String propertyNameParam, String expressionStrParam1, String expressionStrParam2){
        super(entityDefinitionParam,secondaryEntityDefinition, propertyNameParam, expressionStrParam1, expressionStrParam2);
    }

    @Override
    public void executeAction(Context context) throws IllegalArgumentException{
        ExpressionCreation expressionCreation = new ExpressionCreationImpl();
        PropertyInstance propertyInstance = context.getPrimaryEntityInstance().getPropertyByName(resultPropName);
        Expression expression1 = expressionCreation.craeteExpression(expressionStrArg1, context.getPrimaryEntityInstance(),
                context.getSecondEntityInstance(),resultPropName);
        Expression expression2 = expressionCreation.craeteExpression(expressionStrArg2, context.getPrimaryEntityInstance(),
                context.getSecondEntityInstance(),resultPropName);
        Type type = propertyInstance.getPropertyDefinition().getType();

        if (!NumericVerify.verifyNumericPropertyType(propertyInstance)){
            throw new IllegalArgumentException("multiply action can't operate on a none number property " + resultPropName);
        }
        if (!NumericVerify.verifyNumericExpressionValue(expression1, context) |
                (!NumericVerify.verifyNumericExpressionValue(expression2, context)) |
                !NumericVerify.verifySuitableType(type,expression1, context) |
                !NumericVerify.verifySuitableType(type,expression2, context)) {
            throw new IllegalArgumentException("can't cast one of expression value to type of property " + resultPropName);
        }

        switch (type) {
            case DECIMAL:
                Integer i1 = (Integer)(expression1.evaluateExpression(context));
                Integer i2 = (Integer)(expression2.evaluateExpression(context));
                Integer iResult = i1 * i2;
                if(propertyInstance.getPropertyDefinition().doesHaveRange()){
                    Integer iMaxRange = (Integer)propertyInstance.getPropertyDefinition().getRange().get(1);
                    if(iResult > iMaxRange){
                        iResult = iMaxRange;
                    }
                }

                propertyInstance.setValue(iResult);
                break;
            case FLOAT:
                Float f1 = (Float)(expression1.evaluateExpression(context));
                Float f2 = (Float)(expression2.evaluateExpression(context));
                Float fResult = f1 * f2;
                if(propertyInstance.getPropertyDefinition().doesHaveRange()){
                    Float fMaxRange = (Float)propertyInstance.getPropertyDefinition().getRange().get(1);
                    if(fResult > fMaxRange){
                        fResult = fMaxRange;
                    }
                }

                propertyInstance.setValue(fResult);
                break;
        }
    }


}