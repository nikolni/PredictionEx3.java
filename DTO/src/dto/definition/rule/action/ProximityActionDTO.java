package dto.definition.rule.action;

import dto.definition.rule.action.api.AbstractActionDTO;
import dto.definition.rule.action.api.ActionType;

import java.util.ArrayList;

public class ProximityActionDTO extends AbstractActionDTO {

    private final String of;
    private final Integer actionsCollectionSize;

    public ProximityActionDTO(String entityDefinitionParam, String secondaryEntityDefinition,
                              String of, Integer actionsCollectionSize) {
        super(ActionType.PROXIMITY,entityDefinitionParam,secondaryEntityDefinition);
        this.of = of;
        this.actionsCollectionSize= actionsCollectionSize;
    }

    public String getOf() {
        return of.toString();
    }

    public Integer getActionsCollectionSize() {
        return actionsCollectionSize;
    }
}
