package engine.per.file.engine.world.creation.api;

import engine.per.file.engine.world.execution.instance.enitty.api.EntityInstance;
import engine.per.file.engine.world.rule.action.expression.api.Expression;

public interface ExpressionCreation {
    Expression craeteExpression(String expressionStr, EntityInstance expressionEntityInstance);
}
