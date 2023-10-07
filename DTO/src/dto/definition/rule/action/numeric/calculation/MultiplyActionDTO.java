package dto.definition.rule.action.numeric.calculation;

import dto.definition.rule.action.api.AbstractActionDTO;
import dto.definition.rule.action.api.ActionType;

public class MultiplyActionDTO extends AbstractActionDTO {
    private final String resultPropName;
    private final String expressionStrArg1;
    private final String expressionStrArg2;

    public MultiplyActionDTO(String entityDefinitionParam,String secondEntityDefinitionDTO,
                           String propertyNameParam, String expressionStrParam1, String expressionStrParam2){
        super(ActionType.MULTIPLY, entityDefinitionParam, secondEntityDefinitionDTO);
        this.resultPropName = propertyNameParam;
        this.expressionStrArg1= expressionStrParam1;
        this.expressionStrArg2= expressionStrParam2;
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