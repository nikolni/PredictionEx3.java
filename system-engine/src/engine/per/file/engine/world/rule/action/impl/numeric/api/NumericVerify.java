package engine.per.file.engine.world.rule.action.impl.numeric.api;

import engine.per.file.engine.world.execution.instance.property.api.PropertyInstance;
import engine.per.file.engine.world.rule.enums.Type;
import engine.per.file.engine.world.rule.context.Context;
import engine.per.file.engine.world.rule.action.expression.api.Expression;

public interface NumericVerify {
    static boolean verifyNumericPropertyType(PropertyInstance propertyInstance) {
        return
                Type.DECIMAL.equals(propertyInstance.getPropertyDefinition().getType()) || Type.FLOAT.equals(propertyInstance.getPropertyDefinition().getType());
    }

    static boolean verifyNumericExpressionValue(Expression expression, Context context) {
        return ((expression.evaluateExpression(context)) instanceof Integer) | ((expression.evaluateExpression(context)) instanceof Float);
    }

    static boolean verifySuitableType(Type propertyType, Expression expression, Context context) {
        Object expressionVal = expression.evaluateExpression(context);
        switch (propertyType) {
            case DECIMAL:
                return (expressionVal instanceof Integer);
            case FLOAT:
                return (expressionVal instanceof Float | expressionVal instanceof Integer);
        }
        return false;
    }
}
