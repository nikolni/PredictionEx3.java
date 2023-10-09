package dto.definition.termination.condition.impl;

import dto.definition.termination.condition.api.TerminationConditionsDTO;

public class ByUserTerminationConditionDTOImpl implements TerminationConditionsDTO {
    private String type = "ByUser";


    @Override
    public int getTerminationCondition() {
        return 0;
    }
}
