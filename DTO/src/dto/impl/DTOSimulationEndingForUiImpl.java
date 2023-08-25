package dto.impl;

import dto.api.DTOSimulationEndingForUi;

public class DTOSimulationEndingForUiImpl implements DTOSimulationEndingForUi {
    private int simulationID;
    private String terminationReason;


    public DTOSimulationEndingForUiImpl(int simulationID, String terminationReason){
        this.simulationID = simulationID;
        this.terminationReason = terminationReason;
    }

    @Override
    public int getSimulationID() {
        return simulationID;
    }

    @Override
    public String getTerminationReason() {
        return terminationReason;
    }
}
