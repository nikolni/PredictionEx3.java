package dto.primary;

public class DTOSimulationEndingForUi {
    private int simulationID;
    private int[] terminationReasonArr;


    public DTOSimulationEndingForUi(int simulationID, int[] terminationReasonArr){
        this.simulationID = simulationID;
        this.terminationReasonArr = terminationReasonArr;
    }

    public int getSimulationID() {
        return simulationID;
    }

    public int[] getTerminationReason() {
        return terminationReasonArr;
    }
}
