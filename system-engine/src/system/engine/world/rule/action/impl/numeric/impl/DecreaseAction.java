package system.engine.world.rule.action.impl.numeric.impl;

import system.engine.world.creation.api.ExpressionCreation;
import system.engine.world.creation.impl.expression.ExpressionCreationImpl;
import system.engine.world.definition.entity.api.EntityDefinition;
import system.engine.world.definition.entity.secondary.api.SecondaryEntityDefinition;
import system.engine.world.execution.instance.enitty.api.EntityInstance;
import system.engine.world.execution.instance.enitty.manager.api.EntityInstanceManager;
import system.engine.world.rule.action.api.AbstractAction;
import system.engine.world.rule.action.api.ActionType;
import system.engine.world.rule.action.expression.api.Expression;
import system.engine.world.rule.action.impl.numeric.api.NumericVerify;
import system.engine.world.rule.context.Context;
import system.engine.world.rule.enums.Type;
import system.engine.world.execution.instance.property.api.PropertyInstance;

public class DecreaseAction extends AbstractAction implements NumericVerify {

    private final String propertyName;
    private final String expressionStr;

    public DecreaseAction(EntityDefinition entityDefinitionParam, SecondaryEntityDefinition secondaryEntityDefinition,String propertyNameParam, String expressionStrParam) {
        super(ActionType.DECREASE, entityDefinitionParam,secondaryEntityDefinition);
        propertyName = propertyNameParam;
        expressionStr = expressionStrParam;
    }

    @Override
    public void executeAction(Context context) throws IllegalArgumentException{
        ExpressionCreation expressionCreation = new ExpressionCreationImpl();
        EntityInstance actionEntityInstance=checkByDefinitionIfPrimaryOrSecondary(context);
        if(actionEntityInstance==null) //cant execute the action
            return;
        PropertyInstance propertyInstance=actionEntityInstance.getPropertyByName(propertyName);

        Expression expression = expressionCreation.craeteExpression(expressionStr, actionEntityInstance);
        Type type = propertyInstance.getPropertyDefinition().getType();

        if (!NumericVerify.verifyNumericPropertyType(propertyInstance)){
            throw new IllegalArgumentException("decrease action can't operate on a none number property " + propertyName);
        }
        if (!NumericVerify.verifyNumericExpressionValue(expression, context) |
                !NumericVerify.verifySuitableType(type,expression, context) ) {
            throw new IllegalArgumentException("can't cast expression value to type of property " + propertyName);
        }

        switch (type) {

            case DECIMAL:
                Integer i1 = Type.DECIMAL.convert(propertyInstance.getValue());
                Integer i2 = (Integer) (expression.evaluateExpression(context));
                Integer iResult = i1 - i2;
                if(propertyInstance.getPropertyDefinition().doesHaveRange()){
                    Integer iMinRange = (Integer)propertyInstance.getPropertyDefinition().getRange().get(0);
                    if(iResult < iMinRange){
                        iResult = iMinRange;
                    }
                }
                propertyInstance.setLastTickNumberOfValueUpdate(context.getTickNumber(), iResult);
                propertyInstance.setValue(iResult);

                break;
            case FLOAT:
                Float f1 = Float.parseFloat(propertyInstance.getValue().toString());
                Float f2 = Float.parseFloat(expression.evaluateExpression(context).toString());
                Float fResult = f1 - f2;
                if(propertyInstance.getPropertyDefinition().doesHaveRange()){
                    Float fMinRange = Float.parseFloat(propertyInstance.getPropertyDefinition().getRange().get(0).toString());
                    if(fResult < fMinRange){
                        fResult = fMinRange;
                    }
                }
                propertyInstance.setLastTickNumberOfValueUpdate(context.getTickNumber(), fResult);
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
