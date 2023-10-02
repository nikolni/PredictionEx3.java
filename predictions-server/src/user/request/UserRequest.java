package user.request;

import engine.per.file.engine.world.termination.condition.api.TerminationCondition;
import engine.per.file.engine.world.termination.condition.impl.ByUserTerminationConditionImpl;

import java.util.ArrayList;
import java.util.List;

public class UserRequest {
    private Integer requestID = 0;
    private String simulationName;
    private String userName;
    private Integer numOfSimulations=0;
    private List<TerminationCondition> terminationConditionList;
    private List<String> terminationConditionListString;
    private String requestStatus = "in process";

     private Integer numOfSimulationsRunning = 0;
    private Integer numOfSimulationsDone = 0;



    public UserRequest(String simulationName, Integer numOfSimulations,
                       String terminationConditions,String userName) {
        this.simulationName = simulationName;
        this.numOfSimulations = numOfSimulations;
        this.terminationConditionList = buildTerminationConditionList(terminationConditions);
        this.userName=userName;
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
        return terminationConditionList;
    }

    public List<String> getTerminationConditionListString() {
        return terminationConditionListString;
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

    public String getUserName() {
        return userName;
    }

    public void setNumOfSimulationsDone(Integer numOfSimulationsDone) {
        this.numOfSimulationsDone = numOfSimulationsDone;
    }

    public void setRequestID(Integer requestID) {
        this.requestID = requestID;
    }
    public Integer getRequestID() {
        return requestID;
    }

}
