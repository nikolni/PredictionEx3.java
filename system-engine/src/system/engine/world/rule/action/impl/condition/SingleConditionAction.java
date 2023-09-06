package system.engine.world.rule.action.impl.condition;

import system.engine.world.creation.api.ExpressionCreation;
import system.engine.world.creation.impl.expression.ExpressionCreationImpl;
import system.engine.world.definition.entity.secondary.api.SecondaryEntityDefinition;
import system.engine.world.execution.instance.enitty.api.EntityInstance;
import system.engine.world.rule.action.expression.impl.ExpFuncName;
import system.engine.world.rule.action.expression.impl.ExpPropName;
import system.engine.world.rule.action.impl.numeric.api.NumericVerify;
import system.engine.world.rule.context.Context;
import system.engine.world.execution.instance.property.api.PropertyInstance;
import system.engine.world.rule.action.api.Action;
import system.engine.world.rule.action.expression.api.Expression;
import system.engine.world.definition.entity.api.EntityDefinition;
import system.engine.world.rule.enums.Type;

public class SingleConditionAction extends ConditionAction {

    private final EntityDefinition innerEntityDefinition;
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
        if(context.getPrimaryEntityInstance()==null)
            return;
        if(getSecondaryEntityDefinition()!=null) //check if a single condition can be execute
            if(context.getSecondEntityInstance()==null&&innerEntityDefinition.getUniqueName().equals(getExtendsSecondaryEntityDefinition().getUniqueName()))
                return;

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
        EntityInstance actionEntityInstance=checkByDefinitionIfPrimaryOrSecondary(context);
        //need to add actionEntityInstance to every Expression creation
        Expression propertyExp=expressionCreation.craeteExpression(propertyName, context.getPrimaryEntityInstance(),
                context.getSecondEntityInstance());
        Expression expression = expressionCreation.craeteExpression(expressionStr, context.getPrimaryEntityInstance(),
                context.getSecondEntityInstance());

        Object expressionValue = expression.evaluateExpression(context);
        Type propertyType;
        Object propertyValue = propertyExp.evaluateExpression(context);
        if(propertyExp instanceof ExpFuncName || propertyExp instanceof ExpPropName)
            propertyType=convertToType(propertyValue);
        else //free value
            propertyType=Type.STRING;

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
                result= caseBT(propertyValue, propertyType, expressionValue);
                break;
            case "lt":
                result= caseLT(propertyValue, propertyType, expressionValue);
                break;
        }
        return result;
    }

    public Type convertToType(Object obj) {
        if (obj instanceof Float || obj instanceof Double) {
            return Type.FLOAT;
        } else if (obj instanceof String) {
            return Type.STRING;
        } else if (obj instanceof Boolean) {
            return Type.BOOLEAN;
        } else {
            throw new IllegalArgumentException("Unsupported data type");
        }
    }


    private boolean caseBT(Object propertyValue,Type propertyType, Object expressionValue) throws IllegalArgumentException {
        boolean result =false;
        if (!propertyType.equals(Type.FLOAT)) {
            throw new IllegalArgumentException("bt operator can't operate on a none number property " + propertyName);
        }

        switch (propertyType) {
           /* case DECIMAL:
                result= (int) propertyValue > (int) expressionValue;
                break;*/
            case FLOAT:
                result= (float) propertyValue > (float) expressionValue;
                break;
        }
        return result;
    }

    private boolean caseLT(Object propertyValue,Type propertyType, Object expressionValue) throws IllegalArgumentException {
        boolean result =false;

        if (!propertyType.equals(Type.FLOAT)) {
            throw new IllegalArgumentException("lt operator can't operate on a none number property " + propertyName);
        }

        switch (propertyType) {
            /*case DECIMAL:
                result= (int) propertyValue < (int) expressionValue;
                break;*/
            case FLOAT:
                result= (float) propertyValue < (float) expressionValue;
                break;
        }
        return result;
    }

    private boolean verifySuitableType(Type propertyType, Object expressionVal) {
        boolean result =false;

        switch (propertyType) {
            /*case DECIMAL:
                result= (expressionVal instanceof Integer);
                break;*/
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
