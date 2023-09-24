package engine.per.file.engine.world.rule.action.impl.numeric.impl.calculation;

import engine.per.file.engine.world.creation.impl.expression.ExpressionCreationImpl;
import engine.per.file.engine.world.definition.entity.api.EntityDefinition;
import engine.per.file.engine.world.definition.entity.secondary.api.SecondaryEntityDefinition;
import engine.per.file.engine.world.execution.instance.enitty.api.EntityInstance;
import engine.per.file.engine.world.execution.instance.property.api.PropertyInstance;
import engine.per.file.engine.world.rule.action.impl.numeric.api.NumericVerify;
import engine.per.file.engine.world.rule.enums.Type;
import engine.per.file.engine.world.creation.api.ExpressionCreation;
import engine.per.file.engine.world.rule.context.Context;
import engine.per.file.engine.world.rule.action.expression.api.Expression;

public class MultiplyAction extends CalculationAction {
    public MultiplyAction(EntityDefinition entityDefinitionParam, SecondaryEntityDefinition secondaryEntityDefinition, String propertyNameParam, String expressionStrParam1, String expressionStrParam2){
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
                propertyInstance.setLastTickNumberOfValueUpdate(context.getTickNumber(), iResult);
                propertyInstance.setValue(iResult);

                break;
            case FLOAT:
                Float f1 = Float.parseFloat(expression1.evaluateExpression(context).toString());
                Float f2 = Float.parseFloat(expression2.evaluateExpression(context).toString());
                Float fResult = f1 * f2;
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


}