package system.engine.world.rule.action.expression.impl;

import system.engine.world.creation.api.ExpressionCreation;
import system.engine.world.creation.impl.expression.ExpressionCreationImpl;
import system.engine.world.rule.action.expression.api.Expression;
import system.engine.world.rule.action.impl.numeric.api.NumericVerify;
import system.engine.world.rule.context.Context;
import system.engine.world.rule.enums.Type;
import system.engine.world.definition.value.generator.impl.random.impl.numeric.RandomFloatGenerator;
import system.engine.world.definition.value.generator.impl.random.impl.numeric.RandomIntegerGenerator;
import system.engine.world.execution.instance.enitty.api.EntityInstance;
import system.engine.world.execution.instance.property.api.PropertyInstance;
import system.engine.world.rule.action.expression.api.AbstractExpressionImpl;

import java.util.Arrays;
import java.util.List;


public class ExpFuncName extends AbstractExpressionImpl {

    private final List<String> functionArgs;


    public ExpFuncName(String expressionStrParam, EntityInstance expressionEntityInstance,String... strings) {
        super(expressionStrParam,expressionEntityInstance);
        functionArgs = Arrays.asList(strings);

    }

    @Override
    public Object evaluateExpression(Context context) {
        Object value = null;

        switch (expressionStr) {
            case "environment":
                value= environment(context.getEnvironmentVariable(functionArgs.get(0)));
                break;
            case "random":
                value= random(functionArgs.get(0));
                break;
            case "evaluate":
                value= evaluate(functionArgs.get(0),context);
                break;
            case "percent":
                value= percent(functionArgs.get(0),functionArgs.get(1), context);
                break;
            case "ticks":
                value= ticks(functionArgs.get(0), context);
                break;
        }
        return value;
    }

    private Object environment(PropertyInstance environmentVariable) {
        return environmentVariable.getValue();
    }

    private Object random(String num) {
    int number = Integer.parseInt(num);   //throws

    return (new RandomIntegerGenerator(0, number)).generateValue();

    }

    private Object evaluate(String propertyByEntity,Context context) {   //using only arguments
        String entityName;
        String propertyName;

        String[] parts = propertyByEntity.split("\\.");
        if (parts.length == 2) {
             entityName = parts[0];
             propertyName = parts[1];
        } else {
            throw new IllegalArgumentException("the argument for evaluate function is invalid!");
        }

        EntityInstance entityInstance= null;
        if(entityName.equals(context.getPrimaryEntityInstance().getEntityDefinition().getUniqueName())){
            entityInstance = context.getPrimaryEntityInstance();
        }
        else if(context.getSecondEntityInstance()!=null){
            if(entityName.equals(context.getSecondEntityInstance().getEntityDefinition().getUniqueName()))
                entityInstance = context.getSecondEntityInstance();
        }
        if(entityInstance== null) //entityName is name of instance that not exist
            throw new IllegalArgumentException("in evaluate function"+entityName+"instance is not exist");

        PropertyInstance propertyInstance =entityInstance.getPropertyByName(propertyName);
        return propertyInstance.getValue();
    }

    private float percent(String wholeNum, String percentNum, Context context) {
        ExpressionCreation expressionCreation = new ExpressionCreationImpl();

        Expression wholeNumExp = expressionCreation.craeteExpression(wholeNum, getExpressionEntityInstance());
        Expression percentNumExp = expressionCreation.craeteExpression(percentNum, getExpressionEntityInstance());

        if (!NumericVerify.verifyNumericExpressionValue(wholeNumExp, context) |
                !NumericVerify.verifyNumericExpressionValue(percentNumExp, context)) {
            throw new IllegalArgumentException("Percent function can't operate on non-numeric expressions!");
        }

        Object wholeNumValue = wholeNumExp.evaluateExpression(context);
        Object percentNumValue = percentNumExp.evaluateExpression(context);
        return (Float.parseFloat(percentNumValue.toString()) / 100) * Float.parseFloat(wholeNumValue.toString());
    }

    private Float ticks(String propertyByEntity, Context context) {
        String entityName;
        String propertyName;

        String[] parts = propertyByEntity.split("\\.");
        if (parts.length == 2) {
            entityName = parts[0];
            propertyName = parts[1];
        } else {
            throw new IllegalArgumentException("the argument for evaluate function is invalid!");
        }

        EntityInstance entityInstance= null;
        if(entityName.equals(context.getPrimaryEntityInstance().getEntityDefinition().getUniqueName())){
            entityInstance = context.getPrimaryEntityInstance();
        }
        else if(context.getSecondEntityInstance()!=null){
            if(entityName.equals(context.getSecondEntityInstance().getEntityDefinition().getUniqueName()))
                entityInstance = context.getSecondEntityInstance();
        }
        if(entityInstance== null) //entityName is name of instance that not exist
            throw new IllegalArgumentException("in ticks function"+entityName+"instance is not exist");

        PropertyInstance propertyInstance =entityInstance.getPropertyByName(propertyName);
        Integer result=context.getTickNumber() - propertyInstance.getLastTickNumberOfValueUpdate();
        Float resultFloat = Float.parseFloat(result.toString());
        return resultFloat;
    }
}