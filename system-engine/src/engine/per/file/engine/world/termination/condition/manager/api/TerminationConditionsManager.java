package engine.per.file.engine.world.termination.condition.manager.api;

import engine.per.file.engine.world.termination.condition.api.TerminationCondition;

import java.util.List;

public interface TerminationConditionsManager {
     List<TerminationCondition> getTerminationConditionsList();
     void addTerminationCondition (TerminationCondition terminationCondition);
}
