package system.engine.world.rule.action.expression.impl;

import system.engine.world.rule.context.Context;
import system.engine.world.execution.instance.enitty.api.EntityInstance;
import system.engine.world.rule.action.expression.api.AbstractExpressionImpl;

public class ExpFreeValue extends AbstractExpressionImpl{
    Object expressionValue;

    public ExpFreeValue(String expressionStrParam, EntityInstance primaryEntityInstance,
                        EntityInstance secondEntityInstance) {
        super(expressionStrParam, primaryEntityInstance,secondEntityInstance);
        try{
            expressionValue = Integer.parseInt(expressionStr);
        }
        catch (Exception exception1){
            try{
                expressionValue = Float.parseFloat(expressionStr);
            }
            catch (Exception exception2)
            {
                try{
                    expressionValue = Boolean.parseBoolean(expressionStr);
                }
                catch (Exception exception3)
                {
                    expressionValue = expressionStr;
                }
            }
        }
    }

    @Override
    public Object evaluateExpression(Context context) {
        return expressionValue;
    }
}
