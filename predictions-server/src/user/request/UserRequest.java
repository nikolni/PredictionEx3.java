package user.request;

import engine.per.file.engine.world.termination.condition.api.TerminationCondition;

import java.util.List;

public class UserRequest {
    private String simulationName;
    private Integer numOfSimulations=0;
    private List<TerminationCondition> terminationConditionList;


    public UserRequest(String simulationName, Integer numOfSimulations,
                       String terminationConditions) {
        this.simulationName = simulationName;
        this.numOfSimulations = numOfSimulations;
        this.terminationConditionList = buildTerminationConditionList(terminationConditions);
    }

    private List<TerminationCondition> buildTerminationConditionList(String terminationConditions){

    }
}
