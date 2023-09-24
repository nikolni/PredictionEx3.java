package engine.per.file.engine.world.termination.condition.impl;

import engine.per.file.engine.world.termination.condition.api.TerminationCondition;
import engine.per.file.engine.world.tick.Tick;

public class TicksTerminationConditionImpl implements TerminationCondition {
    private Tick ticks = new Tick();

    public TicksTerminationConditionImpl(Tick ticks) {
        this.ticks = ticks;
    }

    @Override
    public int getTerminationCondition() {
        return ticks.getTick();
    }

    @Override
    public void setTerminationCondition(int terminationConditions) {

        ticks.setTick(terminationConditions);
    }
}
