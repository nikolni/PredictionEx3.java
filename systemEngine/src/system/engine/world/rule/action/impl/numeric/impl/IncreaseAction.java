package system.engine.world.rule.action.impl.numeric.impl;

import system.engine.world.creation.api.ExpressionCreation;
import system.engine.world.creation.impl.expression.ExpressionCreationImpl;
import system.engine.world.definition.entity.api.EntityDefinition;
import system.engine.world.rule.action.api.AbstractAction;
import system.engine.world.rule.action.api.ActionType;
import system.engine.world.rule.action.expression.api.Expression;
import system.engine.world.rule.action.impl.numeric.api.NumericVerify;
import system.engine.world.rule.context.Context;
import system.engine.world.rule.enums.Type;
import system.engine.world.execution.instance.property.api.PropertyInstance;


public class IncreaseAction extends AbstractAction implements NumericVerify {
    private final String propertyName;
    private final String expressionStr;

    public IncreaseAction(EntityDefinition entityDefinitionParam, String propertyNameParam, String expressionStrParam) {
        super(ActionType.INCREASE, entityDefinitionParam);
        propertyName = propertyNameParam;
        expressionStr =expressionStrParam;
    }

    @Override
    public void executeAction(Context context) throws IllegalArgumentException{
        ExpressionCreation expressionCreation = new ExpressionCreationImpl();
        PropertyInstance propertyInstance = (context.getPrimaryEntityInstance()).getPropertyByName(propertyName);
        Expression expression = expressionCreation.craeteExpression(expressionStr, context.getPrimaryEntityInstance(), propertyName);
        Type type = propertyInstance.getPropertyDefinition().getType();

        if (!NumericVerify.verifyNumericPropertyType(propertyInstance)){
            throw new IllegalArgumentException("increase action can't operate on a none number property " + propertyName);
        }
        if (!NumericVerify.verifyNumericExpressionValue(expression, context) |
                !NumericVerify.verifySuitableType(type,expression, context) ) {
            throw new IllegalArgumentException("can't cast expression value to type of property " + propertyName);
        }

        switch (type) {
            case DECIMAL:
                Integer i1 = Type.DECIMAL.convert(propertyInstance.getValue());
                Integer i2 = (Integer) (expression.evaluateExpression(context));
                Integer iResult = i1 + i2;
                if(propertyInstance.getPropertyDefinition().doesHaveRange()){
                    Integer iMaxRange = (Integer)propertyInstance.getPropertyDefinition().getRange().get(1);
                    if(iResult > iMaxRange){
                        iResult = iMaxRange;
                    }
                }
                propertyInstance.setValue(iResult);
                break;
            case FLOAT:
                Float f1 = Type.FLOAT.convert(propertyInstance.getValue());
                Float f2 = (Float) (expression.evaluateExpression(context));
                Float fResult = f1 + f2;
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

    public String getPropertyName() {
        return propertyName;
    }

    public String getExpressionStr() {
        return expressionStr;
    }


}