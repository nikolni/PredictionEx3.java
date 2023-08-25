package dto.definition.rule.action.numeric;

import dto.definition.entity.api.EntityDefinitionDTO;
import dto.definition.rule.action.api.AbstractActionDTO;
import dto.definition.rule.action.api.ActionType;

public class DecreaseActionDTO extends AbstractActionDTO {

    private final String propertyName;
    private final String expressionStr;

    public DecreaseActionDTO(String entityDefinitionParam, String propertyNameParam, String expressionStrParam) {
        super(ActionType.DECREASE, entityDefinitionParam);
        propertyName = propertyNameParam;
        expressionStr = expressionStrParam;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public String getExpressionStr() {
        return expressionStr;
    }
}