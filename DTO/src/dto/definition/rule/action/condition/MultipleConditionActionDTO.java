package dto.definition.rule.action.condition;

public class MultipleConditionActionDTO extends ConditionActionDTO {
    private final String logical;
    private final Integer conditionsNumber;

    public MultipleConditionActionDTO(String singularity, String entityDefinitionParam,
                                      String secondaryEntityDefinition, String logicalParam,
                                      Integer conditionsNumber, Integer thenActionNumber,Integer elseActionNumber) {
        super(singularity,entityDefinitionParam,secondaryEntityDefinition, thenActionNumber, elseActionNumber);
        logical = logicalParam;
        this.conditionsNumber = conditionsNumber;
    }

    public String getLogical() {
        return logical;
    }

    public Integer getConditionsNumber() {
        return conditionsNumber;
    }
}
