package engine.per.file.engine.world.rule.action.expression.impl;

import engine.per.file.engine.world.execution.instance.enitty.api.EntityInstance;
import engine.per.file.engine.world.execution.instance.property.api.PropertyInstance;
import engine.per.file.engine.world.rule.action.expression.api.AbstractExpressionImpl;
import engine.per.file.engine.world.rule.context.Context;

public class ExpPropName extends AbstractExpressionImpl {

    private String propertyName;
    public ExpPropName(String expressionStrParam, EntityInstance expressionEntityInstance) {
        super(expressionStrParam, expressionEntityInstance);
        propertyName = expressionStrParam;
    }

    @Override
    public Object evaluateExpression(Context context) {
        PropertyInstance propertyInstance = expressionEntityInstance.getPropertyByName(propertyName);
        return propertyInstance.getValue();
    }
}
