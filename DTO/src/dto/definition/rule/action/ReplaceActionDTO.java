package dto.definition.rule.action;

import dto.definition.rule.action.api.AbstractActionDTO;
import dto.definition.rule.action.api.ActionType;

public class ReplaceActionDTO extends AbstractActionDTO {
    private final String mode;
    private final String createEntityDefinitionName;

    public ReplaceActionDTO(String killDefinitionParam, String secondEntityDefinition,
                            String createEntityDefinitionName, String mode) {
        super(ActionType.REPLACE,killDefinitionParam,secondEntityDefinition);
        this.mode = mode;
        this.createEntityDefinitionName=createEntityDefinitionName;
    }

    public String getMode() {
        return mode;
    }

    public String getCreateEntityDefinitionName() {
        return createEntityDefinitionName;
    }
}
