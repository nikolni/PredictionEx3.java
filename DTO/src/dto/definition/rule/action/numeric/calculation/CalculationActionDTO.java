package dto.definition.rule.action.numeric.calculation;

import dto.definition.entity.api.EntityDefinitionDTO;
import dto.definition.rule.action.api.AbstractActionDTO;
import dto.definition.rule.action.api.ActionType;

public abstract class CalculationActionDTO extends AbstractActionDTO {

    protected String resultPropName;
    protected String expressionStrArg1;
    protected String expressionStrArg2;

    public CalculationActionDTO(String entityDefinitionParam,String secondEntityDefinitionDTO,
                                String propertyNameParam, String expressionStrParam1, String expressionStrParam2){
        super(ActionType.CALCULATION, entityDefinitionParam, secondEntityDefinitionDTO);
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
