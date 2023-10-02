package dto.definition.user.request;

import java.util.List;

public class DTOUserRequestForUi {
    private String requestStatus = "in process";

    private Integer numOfSimulationsRunning = 0;
    private Integer numOfSimulationsDone = 0;
    private Integer requestID = 0;
    private String simulationName;
    private String userName;
    private List<String> terminationCause;
    private Integer numOfCycles;

    public DTOUserRequestForUi(String requestStatus, Integer numOfSimulationsRunning, Integer numOfSimulationsDone, Integer requestID,
                               String simulationName, String userName, List<String> terminationCause, Integer numOfCycles) {
        this.requestStatus = requestStatus;
        this.numOfSimulationsRunning = numOfSimulationsRunning;
        this.numOfSimulationsDone = numOfSimulationsDone;
        this.requestID = requestID;
        this.simulationName = simulationName;
        this.userName = userName;
        this.terminationCause = terminationCause;
        this.numOfCycles = numOfCycles;
    }

    public String getRequestStatus() {
        return requestStatus;
    }
    public Integer getNumOfSimulationsRunning() {
        return numOfSimulationsRunning;
    }
    public Integer getNumOfSimulationsDone() {
        return numOfSimulationsDone;
    }
    public Integer getRequestID() {
        return requestID;
    }

    public String getSimulationName() {
        return simulationName;
    }

    public String getUserName() {
        return userName;
    }

    public List<String> getTerminationCause() {
        return terminationCause;
    }

    public Integer getNumOfCycles() {
        return numOfCycles;
    }
}
