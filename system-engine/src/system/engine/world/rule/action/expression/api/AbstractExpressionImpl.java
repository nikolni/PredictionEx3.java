package system.engine.world.rule.action.expression.api;

import system.engine.world.execution.instance.enitty.api.EntityInstance;

public abstract class AbstractExpressionImpl implements Expression {
    protected String expressionStr="";
    protected EntityInstance expressionEntityInstance;

    public AbstractExpressionImpl(String expressionStrParam,EntityInstance expressionEntityInstance){
        expressionStr = expressionStrParam;
        this.expressionEntityInstance=expressionEntityInstance;
    }

    public String getExpressionStr(){
        return expressionStr;
    }

    public EntityInstance getExpressionEntityInstance() {
        return expressionEntityInstance;
    }
}





