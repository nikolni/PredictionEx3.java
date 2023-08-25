package dto.definition.rule.action.api;


import dto.definition.entity.api.EntityDefinitionDTO;

public abstract class AbstractActionDTO {

    private final ActionType actionType;
    private final String primaryEntityDefinitionDTO;

    protected AbstractActionDTO(ActionType actionType, String primaryEntityDefinitionDTO) {
        this.actionType = actionType;
        this.primaryEntityDefinitionDTO = primaryEntityDefinitionDTO;
    }


    public ActionType getActionType() {
        return actionType;
    }


    public String getEntityDefinitionName() {
        return primaryEntityDefinitionDTO;
    }
}
