package dto.definition.rule.action.numeric;

import dto.definition.rule.action.api.AbstractActionDTO;
import dto.definition.rule.action.api.ActionType;

public class ReplaceActionDTO extends AbstractActionDTO {
    private final String mode;

    public ReplaceActionDTO(String killDefinitionParam, String createEntityDefinition, String mode) {
        super(ActionType.REPLACE,killDefinitionParam,createEntityDefinition);
        this.mode = mode;
    }

    public String getMode() {
        return mode;
    }
}
