package dto.definition.rule.action;

import dto.definition.rule.action.api.AbstractActionDTO;
import dto.definition.rule.action.api.ActionType;

public class KillActionDTO extends AbstractActionDTO {

    public KillActionDTO(String entityDefinition) {
        super(ActionType.KILL, entityDefinition);
    }


}
