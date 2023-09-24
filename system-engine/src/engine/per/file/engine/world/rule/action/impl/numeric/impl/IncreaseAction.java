package engine.per.file.engine.world.rule.action.impl.numeric.impl;

import engine.per.file.engine.world.creation.impl.expression.ExpressionCreationImpl;
import engine.per.file.engine.world.definition.entity.api.EntityDefinition;
import engine.per.file.engine.world.definition.entity.secondary.api.SecondaryEntityDefinition;
import engine.per.file.engine.world.execution.instance.enitty.api.EntityInstance;
import engine.per.file.engine.world.execution.instance.property.api.PropertyInstance;
import engine.per.file.engine.world.rule.action.api.AbstractAction;
import engine.per.file.engine.world.rule.action.api.ActionType;
import engine.per.file.engine.world.rule.action.impl.numeric.api.NumericVerify;
import engine.per.file.engine.world.rule.enums.Type;
import engine.per.file.engine.world.creation.api.ExpressionCreation;
import engine.per.file.engine.world.rule.action.expression.api.Expression;
import engine.per.file.engine.world.rule.context.Context;


public class IncreaseAction extends AbstractAction implements NumericVerify {
    private final String propertyName;
    private final String expressionStr;

    public IncreaseAction(EntityDefinition entityDefinitionParam, SecondaryEntityDefinition secondaryEntityDefinition, String propertyNameParam, String expressionStrParam) {
        super(ActionType.INCREASE, entityDefinitionParam,secondaryEntityDefinition);
        propertyName = propertyNameParam;
        expressionStr =expressionStrParam;
    }

    @Override
    public void executeAction(Context context) throws IllegalArgumentException{
        ExpressionCreation expressionCreation = new ExpressionCreationImpl();
        EntityInstance actionEntityInstance=checkByDefinitionIfPrimaryOrSecondary(context);
        if(actionEntityInstance==null) //cant execute the action
            return;
        PropertyInstance propertyInstance=actionEntityInstance.getPropertyByName(propertyName);

        Expression expression = expressionCreation.craeteExpression(expressionStr,actionEntityInstance);
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
                propertyInstance.setLastTickNumberOfValueUpdate(context.getTickNumber(),iResult);
                propertyInstance.setValue(iResult);

                break;
            case FLOAT:
                Float f1 = Float.parseFloat(propertyInstance.getValue().toString());
                Float f2 = Float.parseFloat(expression.evaluateExpression(context).toString());
                Float fResult = f1 + f2;
                if(propertyInstance.getPropertyDefinition().doesHaveRange()){
                    Float fMaxRange = Float.parseFloat(propertyInstance.getPropertyDefinition().getRange().get(1).toString());
                    if(fResult > fMaxRange){
                        fResult = fMaxRange;
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