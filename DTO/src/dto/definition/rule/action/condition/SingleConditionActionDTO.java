package dto.definition.rule.action.condition;


import dto.definition.rule.action.api.AbstractActionDTO;
import dto.definition.rule.action.api.ActionType;

public class SingleConditionActionDTO extends AbstractActionDTO {

    private final String innerEntityDefinitionName;
    private final String propertyName;
    private final String expressionStr;
    private final String operator;
    private final String singularity;
    private final Integer thenActionNumber;
    private final Integer elseActionNumber;

    public SingleConditionActionDTO(String singularity, String entityDefinitionParam, String secondEntityDefinitionDTO,
                                    String innerEntityDefinitionName, String propertyNameParam, String operatorParam, String expressionParam,
                                    Integer thenActionNumber,Integer elseActionNumber) {
        super(ActionType.SINGLE, entityDefinitionParam, secondEntityDefinitionDTO);
        this.singularity = singularity;;
        this.thenActionNumber = thenActionNumber;
        this.elseActionNumber = elseActionNumber;
        this.innerEntityDefinitionName = innerEntityDefinitionName;
        propertyName = propertyNameParam;
        operator = operatorParam;
        expressionStr =expressionParam;
    }

    public String getInnerEntityDefinition() {
        return innerEntityDefinitionName;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public String getExpressionStr() {
        return expressionStr;
    }

    public String getOperator() {
        return operator;
    }

    public String getSingularity() {
        return singularity;
    }
    public Integer getThenActionNumber() {
        return thenActionNumber;
    }

    public Integer getElseActionNumber() {
        return elseActionNumber;
    }

}
