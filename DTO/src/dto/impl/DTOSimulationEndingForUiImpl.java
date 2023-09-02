package dto.impl;

import dto.api.DTOSimulationEndingForUi;

public class DTOSimulationEndingForUiImpl implements DTOSimulationEndingForUi {
    private int simulationID;
    private int[] terminationReasonArr;


    public DTOSimulationEndingForUiImpl(int simulationID, int[] terminationReasonArr){
        this.simulationID = simulationID;
        this.terminationReasonArr = terminationReasonArr;
    }

    @Override
    public int getSimulationID() {
        return simulationID;
    }

    @Override
    public int[] getTerminationReason() {
        return terminationReasonArr;
    }
}
