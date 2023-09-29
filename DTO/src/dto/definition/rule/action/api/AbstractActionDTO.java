package dto.definition.rule.action.api;


public abstract class AbstractActionDTO {

    private final ActionType actionType;
    private final String primaryEntityDefinitionDTO;
    private final String secondEntityDefinitionDTO;

    protected AbstractActionDTO(ActionType actionType, String primaryEntityDefinitionDTO,
                                String secondEntityDefinitionDTO) {
        this.actionType = actionType;
        this.primaryEntityDefinitionDTO = primaryEntityDefinitionDTO;
        this.secondEntityDefinitionDTO = secondEntityDefinitionDTO;
    }


    public ActionType getActionType() {
        return actionType;
    }


    public String getEntityDefinitionName() {
        return primaryEntityDefinitionDTO;
    }

    public String getSecondEntityDefinitionDTO() {
        return secondEntityDefinitionDTO;
    }
}
