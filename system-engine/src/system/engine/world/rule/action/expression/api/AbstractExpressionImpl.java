package system.engine.world.rule.action.expression.api;

import system.engine.world.execution.instance.enitty.api.EntityInstance;

public abstract class AbstractExpressionImpl implements Expression {
    protected String expressionStr="";
    protected EntityInstance primaryEntityInstance;
    protected EntityInstance secondEntityInstance;

    public AbstractExpressionImpl(String expressionStrParam, EntityInstance primaryEntityInstance,
                                  EntityInstance secondEntityInstance){
        expressionStr = expressionStrParam;
        this.primaryEntityInstance = primaryEntityInstance;
        this.secondEntityInstance = secondEntityInstance;
    }

    public String getExpressionStr(){
        return expressionStr;
    }


}





