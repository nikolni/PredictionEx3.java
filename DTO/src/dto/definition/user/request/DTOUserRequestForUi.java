package dto.definition.user.request;

public class DTOUserRequestForUi {
    private String requestStatus = "in process";

    private Integer numOfSimulationsRunning = 0;
    private Integer numOfSimulationsDone = 0;
    private Integer requestID = 0;

    public DTOUserRequestForUi(String requestStatus, Integer numOfSimulationsRunning,
                               Integer getNumOfSimulationsDone, Integer requestID) {
        this.requestStatus = requestStatus;
        this.numOfSimulationsRunning = numOfSimulationsRunning;
        this.numOfSimulationsDone = numOfSimulationsDone;
        this.requestID = requestID;
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
}
