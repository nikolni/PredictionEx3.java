package system.engine.world.rule.action.expression.api;

import system.engine.world.execution.instance.enitty.api.EntityInstance;

public abstract class AbstractExpressionImpl implements Expression {
    protected String expressionStr="";
    protected EntityInstance entityInstance;

    public AbstractExpressionImpl(String expressionStrParam, EntityInstance entityInstanceParam){
        expressionStr = expressionStrParam;
        entityInstance = entityInstanceParam;
    }

    public String getExpressionStr(){
        return expressionStr;
    }


}





