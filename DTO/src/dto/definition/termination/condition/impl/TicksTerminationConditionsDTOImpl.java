package dto.definition.termination.condition.impl;

import dto.definition.termination.condition.api.TerminationConditionsDTO;
import dto.definition.termination.condition.tick.Tick;

public class TicksTerminationConditionsDTOImpl implements TerminationConditionsDTO {
    private Tick ticks = new Tick();

    public TicksTerminationConditionsDTOImpl(int tickNumber){
        ticks.setTick(tickNumber);
    }

    @Override
    public int getTerminationCondition() {
        return ticks.getTick();
    }

}
