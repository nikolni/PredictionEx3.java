package system.engine.world.rule.action.impl.condition;

import system.engine.world.creation.api.ExpressionCreation;
import system.engine.world.creation.impl.expression.ExpressionCreationImpl;
import system.engine.world.definition.entity.secondary.api.SecondaryEntityDefinition;
import system.engine.world.rule.action.impl.numeric.api.NumericVerify;
import system.engine.world.rule.context.Context;
import system.engine.world.execution.instance.property.api.PropertyInstance;
import system.engine.world.rule.action.api.Action;
import system.engine.world.rule.action.expression.api.Expression;
import system.engine.world.definition.entity.api.EntityDefinition;
import system.engine.world.rule.enums.Type;

public class SingleConditionAction extends ConditionAction {

    private EntityDefinition innerEntityDefinition;
    private final String propertyName;
    private final String expressionStr;
    private final String operator;

    public SingleConditionAction(String singularity, EntityDefinition primaryEntityDefinition,
                                 SecondaryEntityDefinition secondaryEntityDefinition, EntityDefinition innerEntityDefinition,
                                 String propertyNameParam, String operatorParam, String expressionParam) {
        super(singularity,primaryEntityDefinition,secondaryEntityDefinition);
        this.innerEntityDefinition = innerEntityDefinition;
        propertyName = propertyNameParam;
        operator = operatorParam;
        expressionStr =expressionParam;
    }


    @Override
    public void executeAction(Context context) {
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

    public boolean isConditionFulfilled(Context context) throws IllegalArgumentException{
        ExpressionCreation expressionCreation = new ExpressionCreationImpl();
        PropertyInstance propertyInstance = context.getPrimaryEntityInstance().getPropertyByName(propertyName);
        Expression expression = expressionCreation.craeteExpression(expressionStr, context.getPrimaryEntityInstance(), propertyName);
        Object propertyValue = propertyInstance.getValue();
        Object expressionValue = expression.evaluateExpression(context);
        Type propertyType = propertyInstance.getPropertyDefinition().getType();
        boolean result =false;

        if (!verifySuitableType(propertyType, expressionValue)) {
            throw new IllegalArgumentException("condition action can't operate with expression type different from type of property " + propertyName);
        }


        switch (operator) {
            case "=":
                result= propertyValue ==  expressionValue;
                break;
            case "!=":
                result= propertyValue !=  expressionValue;
                break;
            case "bt":
                result= caseBT(propertyInstance, propertyValue, propertyType, expressionValue);
                break;
            case "lt":
                result= caseLT(propertyInstance, propertyValue, propertyType, expressionValue);
                break;
        }
        return result;
    }


    private boolean caseBT(PropertyInstance propertyInstance, Object propertyValue,Type propertyType,
                           Object expressionValue) throws IllegalArgumentException {
        boolean result =false;

        if (!NumericVerify.verifyNumericPropertyType(propertyInstance)) {
            throw new IllegalArgumentException("bt operator can't operate on a none number property " + propertyName);
        }

        switch (propertyType) {
            case DECIMAL:
                result= (int) propertyValue > (int) expressionValue;
                break;
            case FLOAT:
                result= (float) propertyValue > (float) expressionValue;
                break;
        }
        return result;
    }

    private boolean caseLT(PropertyInstance propertyInstance, Object propertyValue,Type propertyType,
                           Object expressionValue) throws IllegalArgumentException {
        boolean result =false;

        if (!NumericVerify.verifyNumericPropertyType(propertyInstance)) {
            throw new IllegalArgumentException("lt operator can't operate on a none number property " + propertyName);
        }

        switch (propertyType) {
            case DECIMAL:
                result= (int) propertyValue < (int) expressionValue;
                break;
            case FLOAT:
                result= (float) propertyValue < (float) expressionValue;
                break;
        }
        return result;
    }

    private boolean verifySuitableType(Type propertyType, Object expressionVal) {
        boolean result =false;

        switch (propertyType) {
            case DECIMAL:
                result= (expressionVal instanceof Integer);
                break;
            case FLOAT:
                result= (expressionVal instanceof Float | expressionVal instanceof Integer);
                break;
            case BOOLEAN:
                result= (expressionVal instanceof Boolean | expressionVal instanceof String);
                break;
            case STRING:
                result= (expressionVal instanceof String);
                break;
        }
        return result;
    }

    public EntityDefinition getInnerEntityDefinition() {
        return innerEntityDefinition;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public String getExpressionStr() {
        return expressionStr;
    }

    public String getOperator() {
        return operator;
    }
}
