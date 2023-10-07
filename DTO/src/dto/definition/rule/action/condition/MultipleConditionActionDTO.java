package dto.definition.rule.action.condition;

import dto.definition.rule.action.api.AbstractActionDTO;
import dto.definition.rule.action.api.ActionType;

public class MultipleConditionActionDTO extends AbstractActionDTO {
    private final String logical;
    private final Integer conditionsNumber;
    private final String singularity;
    private final Integer thenActionNumber;
    private final Integer elseActionNumber;
    public MultipleConditionActionDTO(String singularity, String entityDefinitionParam,
                                      String secondEntityDefinitionDTO, String logicalParam,
                                      Integer conditionsNumber, Integer thenActionNumber,Integer elseActionNumber) {
        super(ActionType.MULTIPLE, entityDefinitionParam, secondEntityDefinitionDTO);
        this.singularity = singularity;
        this.thenActionNumber = thenActionNumber;
        this.elseActionNumber = elseActionNumber;
        logical = logicalParam;
        this.conditionsNumber = conditionsNumber;
    }

    public String getLogical() {
        return logical;
    }

    public Integer getConditionsNumber() {
        return conditionsNumber;
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
