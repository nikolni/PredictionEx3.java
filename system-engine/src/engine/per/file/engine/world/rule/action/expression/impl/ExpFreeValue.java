package engine.per.file.engine.world.rule.action.expression.impl;

import engine.per.file.engine.world.execution.instance.enitty.api.EntityInstance;
import engine.per.file.engine.world.rule.action.expression.api.AbstractExpressionImpl;
import engine.per.file.engine.world.rule.context.Context;

public class ExpFreeValue extends AbstractExpressionImpl {
    Object expressionValue;

    public ExpFreeValue(String expressionStrParam, EntityInstance expressionEntityInstance) {
        super(expressionStrParam, expressionEntityInstance);
        boolean validBoolean = true;
        try{
            expressionValue = Integer.parseInt(expressionStr);
        }
        catch (Exception exception1){
            try{
                expressionValue = Float.parseFloat(expressionStr);
            }
            catch (Exception exception2)
            {
                if(expressionStr.equals("false") || expressionStr.equals("False") || expressionStr.equals("FALSE")) {
                    expressionValue = expressionStr;
                }
                else{
                    validBoolean = Boolean.parseBoolean(expressionStr);
                    if(!validBoolean){
                        expressionValue = expressionStr;
                    }
                    else{
                        expressionValue ="true";
                    }
                }
            }
        }
    }

    @Override
    public Object evaluateExpression(Context context) {
        return expressionValue;
    }
}
