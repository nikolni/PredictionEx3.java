package dto.definition.user.request;

public class DTOUserRequestForUi {
    private String requestStatus = "in process";

    private Integer numOfSimulationsRunning = 0;
    private Integer numOfSimulationsDone = 0;

    public DTOUserRequestForUi(String requestStatus, Integer numOfSimulationsRunning,
                               Integer getNumOfSimulationsDone) {
        this.requestStatus = requestStatus;
        this.numOfSimulationsRunning = numOfSimulationsRunning;
        this.numOfSimulationsDone = numOfSimulationsDone;
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
}
