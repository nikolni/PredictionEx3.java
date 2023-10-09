package dto.definition.termination.condition.impl;

import dto.definition.termination.condition.api.TerminationConditionsDTO;

public class TimeTerminationConditionsDTOImpl implements TerminationConditionsDTO {
    private int seconds;
    private String type = "Seconds";
    public TimeTerminationConditionsDTOImpl(int seconds){
        this.seconds = seconds;
    }


    @Override
    public int getTerminationCondition() {
        return seconds;
    }

}
