package dto.definition.rule.action.condition;


public class SingleConditionActionDTO extends ConditionActionDTO {

    private final String innerEntityDefinitionName;
    private final String propertyName;
    private final String expressionStr;
    private final String operator;

    public SingleConditionActionDTO(String singularity, String primaryEntityDefinition, String secondaryEntityDefinition,
                                    String innerEntityDefinitionName, String propertyNameParam, String operatorParam, String expressionParam,
                                    Integer thenActionNumber,Integer elseActionNumber) {
        super(singularity,primaryEntityDefinition,secondaryEntityDefinition, thenActionNumber, elseActionNumber);
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

}
