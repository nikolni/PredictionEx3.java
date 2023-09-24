package engine.per.file.engine.world.creation.impl.expression;

import engine.per.file.engine.world.creation.api.ExpressionCreation;
import engine.per.file.engine.world.execution.instance.enitty.api.EntityInstance;
import engine.per.file.engine.world.rule.action.expression.api.Expression;
import engine.per.file.engine.world.rule.action.expression.impl.ExpFreeValue;
import engine.per.file.engine.world.rule.action.expression.impl.ExpFuncName;
import engine.per.file.engine.world.rule.action.expression.impl.ExpPropName;

public class ExpressionCreationImpl implements ExpressionCreation{

    public Expression craeteExpression(String expressionStr,EntityInstance expressionEntityInstance) {
        //String[] numAction = {"increase", "decrease","calculation", "divide", "ticks"};
        //String[] boolAction = {"environment", "random","evaluate", "percent", "ticks"};
        //String[] stringAction = {"environment", "random","evaluate", "percent", "ticks"};
        String argument = null;
        Expression expression = null;

        if((expression = createFuncExpression(expressionStr ,expressionEntityInstance)) == null){
            if((expression = createPropExpression(expressionStr,expressionEntityInstance)) == null){
                expression = new ExpFreeValue(expressionStr,expressionEntityInstance);
            }

        }
        return expression;
    }

    public Expression createFuncExpression(String expressionStr,EntityInstance expressionEntityInstance) {
        String[] allowedPrefixes = {"environment", "random","evaluate", "percent", "ticks"};
        String funcNameStr = null;
        String arguments = null;
        String[] argumentsArr = null;
        Expression expression = null;

        for (String prefix : allowedPrefixes) {
            if (expressionStr.startsWith(prefix + "(") && expressionStr.endsWith(")")) {
                funcNameStr = prefix;
                arguments = expressionStr.substring(prefix.length() + 1, expressionStr.length() - 1);
                argumentsArr = arguments.split(",");
                expression = new ExpFuncName(funcNameStr,expressionEntityInstance,argumentsArr);
                break;
            }
        }
        return expression;
    }

    public Expression createPropExpression(String expressionStr,EntityInstance expressionEntityInstance) {
        String property = expressionStr;
        Expression expression = null;

        if(expressionEntityInstance.getPropertyByName(expressionStr) != null){
            expression = new ExpPropName(property,expressionEntityInstance);
        }
        return expression;
    }
}
