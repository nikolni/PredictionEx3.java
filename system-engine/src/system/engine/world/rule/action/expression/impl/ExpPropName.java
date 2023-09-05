package system.engine.world.rule.action.expression.impl;

import system.engine.world.rule.context.Context;
import system.engine.world.execution.instance.enitty.api.EntityInstance;
import system.engine.world.execution.instance.property.api.PropertyInstance;
import system.engine.world.rule.action.expression.api.AbstractExpressionImpl;

public class ExpPropName extends AbstractExpressionImpl {

    private String propertyName;
    public ExpPropName(String expressionStrParam, EntityInstance primaryEntityInstance,
                       EntityInstance secondEntityInstance) {
        super(expressionStrParam, primaryEntityInstance, secondEntityInstance);
        propertyName = expressionStrParam;
    }

    @Override
    public Object evaluateExpression(Context context) {
        PropertyInstance propertyInstance = primaryEntityInstance.getPropertyByName(propertyName);
        return propertyInstance.getValue();
    }
}
