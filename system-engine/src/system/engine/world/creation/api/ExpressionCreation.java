package system.engine.world.creation.api;

import system.engine.world.execution.instance.enitty.api.EntityInstance;
import system.engine.world.rule.action.expression.api.Expression;

public interface ExpressionCreation {
    Expression craeteExpression(String expressionStr, EntityInstance entityInstance,
                                EntityInstance secondEntityInstance, String propertyName);
}
