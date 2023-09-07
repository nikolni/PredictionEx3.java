package dto.definition.rule.action;

import dto.definition.rule.action.api.AbstractActionDTO;
import dto.definition.rule.action.api.ActionType;


public class ProximityActionDTO extends AbstractActionDTO {

    private final String of;
    private final Integer actionsCollectionSize;
    private final String targetEntityDefinitionName;

    public ProximityActionDTO(String entityDefinitionParam, String secondaryEntityDefinition,
                              String targetEntityDefinition, String of, Integer actionsCollectionSize) {
        super(ActionType.PROXIMITY,entityDefinitionParam,secondaryEntityDefinition);
        this.of = of;
        this.actionsCollectionSize= actionsCollectionSize;
        this.targetEntityDefinitionName=targetEntityDefinition;
    }

    public String getOf() {
        return of;
    }

    public Integer getActionsCollectionSize() {
        return actionsCollectionSize;
    }

    public String getTargetEntityDefinitionName() {
        return targetEntityDefinitionName;
    }
}
