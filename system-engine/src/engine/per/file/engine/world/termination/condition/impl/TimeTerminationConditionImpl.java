package engine.per.file.engine.world.termination.condition.impl;

import engine.per.file.engine.world.termination.condition.api.TerminationCondition;

public class TimeTerminationConditionImpl implements TerminationCondition {
    private int seconds;

    public TimeTerminationConditionImpl(int seconds) {
        this.seconds = seconds;
    }

    @Override
    public int getTerminationCondition() {
        return seconds;
    }

    @Override
    public void setTerminationCondition(int terminationConditions) {
        this.seconds = terminationConditions;
    }
}
