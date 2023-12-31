package allocation.request;

import engine.per.file.engine.world.termination.condition.api.TerminationCondition;
import engine.per.file.engine.world.termination.condition.impl.ByUserTerminationConditionImpl;
import engine.per.file.engine.world.termination.condition.impl.TicksTerminationConditionImpl;
import engine.per.file.engine.world.termination.condition.impl.TimeTerminationConditionImpl;
import engine.per.file.engine.world.tick.Tick;

import java.util.ArrayList;
import java.util.List;

public class UserRequest {
    private Integer requestID = 0;
    private final String simulationName;
    private final String userName;
    private final Integer numOfSimulations;
    private final List<TerminationCondition> terminationConditionList;
    private final List<String> terminationConditionListString;
    private String requestStatus = "in process";

    private Integer numOfSimulationsRunning = 0;
    private Integer numOfSimulationsDone = 0;

    public UserRequest(String simulationName, Integer numOfSimulations,
                       String terminationConditions, String userName) {
        this.simulationName = simulationName;
        this.numOfSimulations = numOfSimulations;
        this.terminationConditionList = new ArrayList<>();
        buildTerminationConditionList(terminationConditions);
        this.userName=userName;
        terminationConditionListString = new ArrayList<>();
        buildTerminationConditionStringsList();
    }

    private void buildTerminationConditionList(String terminationConditions) {
        String[] sentencesPrimary = terminationConditions.split(",");
        for (String sentence : sentencesPrimary) {
            splitSentence(sentence);
        }
    }

    private void splitSentence(String sentence) {
        String[] sentencesSecondery = sentence.split("=");
        switch (sentencesSecondery[0]) {
            case "1":
                terminationConditionList.add(new ByUserTerminationConditionImpl());
                break;
            case "2":
                terminationConditionList.add(new TicksTerminationConditionImpl(new Tick(Integer.parseInt(sentencesSecondery[1]))));
                break;
            case "3":
                terminationConditionList.add(new TimeTerminationConditionImpl(Integer.parseInt(sentencesSecondery[1])));
                break;
        }
    }
    private void buildTerminationConditionStringsList() {
        int i = 0;
        for (TerminationCondition terminationCondition : terminationConditionList) {
            if(terminationCondition instanceof ByUserTerminationConditionImpl){
                terminationConditionListString.add("By User");
            }
            else if(terminationCondition instanceof TicksTerminationConditionImpl){
                terminationConditionListString.add("Ticks");
            }
            else if(terminationCondition instanceof TimeTerminationConditionImpl){
                terminationConditionListString.add("Seconds");
            }

            i++;
        }
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
    public String getUserName() {
        return userName;
    }
    public void setRequestID(Integer requestID) {
        this.requestID = requestID;
    }
    public Integer getRequestID() {
        return requestID;
    }
    public synchronized void increaseNumOfSimulationsDone() {
        this.numOfSimulationsDone++;
    }
    public synchronized void increaseNumOfSimulationsRunning() {
        this.numOfSimulationsRunning++;
    }
    public synchronized void decreaseNumOfSimulationsRunning() {
        this.numOfSimulationsRunning--;
    }
    public void setRequestStatus(String requestStatus) {
        this.requestStatus = requestStatus;
    }
}
