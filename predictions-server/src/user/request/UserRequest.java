package user.request;

import engine.per.file.engine.world.termination.condition.api.TerminationCondition;
import engine.per.file.engine.world.termination.condition.impl.ByUserTerminationConditionImpl;

import java.util.ArrayList;
import java.util.List;

public class UserRequest {
    private String simulationName;
    private Integer numOfSimulations=0;
    private List<TerminationCondition> terminationConditionList;
    private String requestStatus = "in process";

     private Integer numOfSimulationsRunning = 0;
    private Integer numOfSimulationsDone = 0;



    public UserRequest(String simulationName, Integer numOfSimulations,
                       String terminationConditions) {
        this.simulationName = simulationName;
        this.numOfSimulations = numOfSimulations;
        this.terminationConditionList = buildTerminationConditionList(terminationConditions);
    }

    private List<TerminationCondition> buildTerminationConditionList(String terminationConditions) {
        List<TerminationCondition> terminationConditionList = new ArrayList<>();

        String[] sentences = terminationConditions.split(",");
        // Process the sentences
        for (String sentence : sentences) {
            if (sentence.equals("1")) {
                terminationConditionList.add(new ByUserTerminationConditionImpl());
            } else if (sentence.equals("2")) {

            } else {

            }
        }
    }

    public String getRequestStatus() {
        return requestStatus;
    }

    public String getSimulationName() {
        return simulationName;
    }

    public Integer getNumOfSimulations() {
        return numOfSimulations;
    }

    public List<TerminationCondition> getTerminationConditionList() {
        return terminationConditionList;
    }
    public Integer getNumOfSimulationsRunning() {
        return numOfSimulationsRunning;
    }

    public Integer getNumOfSimulationsDone() {
        return numOfSimulationsDone;
    }

    public void setRequestStatus(String requestStatus) {
        this.requestStatus = requestStatus;
    }

    public void setNumOfSimulationsRunning(Integer numOfSimulationsRunning) {
        this.numOfSimulationsRunning = numOfSimulationsRunning;
    }

    public void setNumOfSimulationsDone(Integer numOfSimulationsDone) {
        this.numOfSimulationsDone = numOfSimulationsDone;
    }
}
