package system.engine.world.rule.action.impl.numeric.impl.calculation;

import system.engine.world.definition.entity.api.EntityDefinition;
import system.engine.world.rule.action.impl.numeric.api.NumericVerify;
import system.engine.world.rule.action.api.AbstractAction;
import system.engine.world.rule.action.api.ActionType;

public abstract class CalculationAction extends AbstractAction implements NumericVerify {

    protected String resultPropName;
    protected String expressionStrArg1;
    protected String expressionStrArg2;

    public CalculationAction(EntityDefinition entityDefinitionParam, String propertyNameParam, String expressionStrParam1, String expressionStrParam2){
        super(ActionType.CALCULATION, entityDefinitionParam);
        resultPropName = propertyNameParam;
        expressionStrArg1= expressionStrParam1;
        expressionStrArg2= expressionStrParam2;
    }

    public String getResultPropName() {
        return resultPropName;
    }

    public String getExpressionStrArg1() {
        return expressionStrArg1;
    }

    public String getExpressionStrArg2() {
        return expressionStrArg2;
    }
}
