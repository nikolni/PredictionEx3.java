package engine.per.file.engine.world.termination.condition.manager.impl;

import engine.per.file.engine.world.termination.condition.api.TerminationCondition;
import engine.per.file.engine.world.termination.condition.manager.api.TerminationConditionsManager;

import java.util.ArrayList;
import java.util.List;

public class TerminationConditionsManagerImpl implements TerminationConditionsManager {
    private List<TerminationCondition> terminationConditionList;

    public TerminationConditionsManagerImpl() {
        this.terminationConditionList = new ArrayList<>();
    }

    public List<TerminationCondition> getTerminationConditionsList(){
        return terminationConditionList;
    }

    public void addTerminationCondition (TerminationCondition terminationCondition){
        terminationConditionList.add(terminationCondition);
    }
}
