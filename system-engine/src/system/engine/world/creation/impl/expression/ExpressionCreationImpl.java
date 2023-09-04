package system.engine.world.creation.impl.expression;

import system.engine.world.creation.api.ExpressionCreation;
import system.engine.world.execution.instance.enitty.api.EntityInstance;
import system.engine.world.rule.action.expression.api.Expression;
import system.engine.world.rule.action.expression.impl.ExpFreeValue;
import system.engine.world.rule.action.expression.impl.ExpFuncName;
import system.engine.world.rule.action.expression.impl.ExpPropName;

public class ExpressionCreationImpl implements ExpressionCreation{

    public Expression craeteExpression(String expressionStr, EntityInstance primaryEntityInstance,
                                       EntityInstance secondEntityInstance, String propertyName) {
        //String[] numAction = {"increase", "decrease","calculation", "divide", "ticks"};
        //String[] boolAction = {"environment", "random","evaluate", "percent", "ticks"};
        //String[] stringAction = {"environment", "random","evaluate", "percent", "ticks"};
        String argument = null;
        Expression expression = null;

        if((expression = createFuncExpression(expressionStr , primaryEntityInstance,secondEntityInstance, propertyName)) == null){
            if((expression = createPropExpression(expressionStr, primaryEntityInstance,secondEntityInstance, propertyName)) == null){
                expression = new ExpFreeValue(expressionStr, primaryEntityInstance, secondEntityInstance);
            }

        }
        return expression;
    }

    public Expression createFuncExpression(String expressionStr, EntityInstance primaryEntityInstance,
                                           EntityInstance secondEntityInstance, String propertyName) {
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
                expression = new ExpFuncName(funcNameStr, primaryEntityInstance,secondEntityInstance,
                        propertyName, argumentsArr);
                break;
            }
        }
        return expression;
    }

    public Expression createPropExpression(String expressionStr, EntityInstance entityInstance,
                                           EntityInstance secondEntityInstance, String propertyName) {
        String property = expressionStr;
        Expression expression = null;

        if(entityInstance.getPropertyByName(expressionStr) != null){
            expression = new ExpPropName(property, entityInstance, secondEntityInstance, propertyName);
        }
        return expression;
    }
}
