package dto.definition.rule.action.condition;

import dto.definition.entity.api.EntityDefinitionDTO;
import dto.definition.rule.action.api.AbstractActionDTO;
import dto.definition.rule.action.api.ActionType;

import java.util.ArrayList;
import java.util.List;

public class ConditionActionDTO extends AbstractActionDTO {
    protected String singularity;



    protected Integer thenActionNumber;
    protected Integer elseActionNumber;

    public ConditionActionDTO(String singularity, String entityDefinitionParam, String secondEntityDefinitionDTO,
                              Integer thenActionNumber,Integer elseActionNumber ) {
        super(ActionType.CONDITION, entityDefinitionParam, secondEntityDefinitionDTO);
        this.singularity = singularity;;
       this.thenActionNumber = thenActionNumber;
       this.elseActionNumber = elseActionNumber;
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
