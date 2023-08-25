package dto.definition.rule.action.condition;

import dto.definition.entity.api.EntityDefinitionDTO;
import dto.definition.rule.action.api.AbstractActionDTO;
import dto.definition.rule.action.api.ActionType;

import java.util.ArrayList;
import java.util.List;

public class ConditionActionDTO extends AbstractActionDTO {
    protected String singularity;
    //protected List<AbstractActionDTO> thenActionList;
   // protected List<AbstractActionDTO> elseActionList;

    public ConditionActionDTO(String singularity, String entityDefinitionParam) {
        super(ActionType.CONDITION, entityDefinitionParam);
        this.singularity = singularity;;
       // thenActionList = new ArrayList<>();
        //elseActionList = new ArrayList<>();
    }

    public String getSingularity() {
        return singularity;
    }


}
