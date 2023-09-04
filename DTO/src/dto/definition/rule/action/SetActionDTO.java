package dto.definition.rule.action;

import dto.definition.rule.action.api.AbstractActionDTO;
import dto.definition.rule.action.api.ActionType;

public class SetActionDTO extends AbstractActionDTO {
        private final String propertyName;
        private final String expressionStr;

    public SetActionDTO(String entityDefinitionParam,String secondEntityDefinitionDTO,
                        String propertyNameParam, String expressionStrParam) {
        super(ActionType.INCREASE, entityDefinitionParam, secondEntityDefinitionDTO);
        propertyName = propertyNameParam;
        expressionStr =expressionStrParam;
    }


    public String getPropertyName() {
        return propertyName;
    }

    public String getExpressionStr() {
        return expressionStr;
    }
}
