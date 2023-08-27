package system.engine.world.termination.condition.impl;

import system.engine.world.termination.condition.api.TerminationCondition;

public class ByUserTerminationConditionImpl implements TerminationCondition {
    @Override
    public int getTerminationCondition() {
        return 0;
    }

    @Override
    public void setTerminationCondition(int terminationConditions) {
    }
}
