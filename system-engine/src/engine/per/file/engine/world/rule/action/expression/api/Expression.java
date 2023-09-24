package engine.per.file.engine.world.rule.action.expression.api;

import engine.per.file.engine.world.rule.context.Context;

public interface Expression {
    public abstract Object evaluateExpression(Context context);
}
