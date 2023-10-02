package user.request;

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
    private Integer numOfSimulations=0;
    private final List<TerminationCondition> terminationConditionList;
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

        String[] sentencesPrimary = terminationConditions.split(",");
        for (String sentence : sentencesPrimary) {
            splitSentence(sentence);
        }
        return terminationConditionList;
    }

    private void splitSentence(String sentence) {
        String[] sentencesSecondery = sentence.split("=");
        if (sentencesSecondery[0].equals("1")) {
            terminationConditionList.add(new ByUserTerminationConditionImpl());
        }else if (sentencesSecondery[0].equals("2")) {
            terminationConditionList.add(new TicksTerminationConditionImpl(new Tick(Integer.parseInt(sentencesSecondery[1]))));
        } else if (sentence.equals("3")) {
            terminationConditionList.add(new TimeTerminationConditionImpl(Integer.parseInt(sentencesSecondery[1])));
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
