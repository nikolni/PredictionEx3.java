package dto.impl;

import dto.api.DTOSimulationProgressForUi;

import java.util.Map;

public class DTOSimulationProgressForUiImpl implements DTOSimulationProgressForUi {

    private final Integer secondsPast;
    private final Integer ticksPast;
    private final Map<String, Integer> entitiesPopulationAfterSimulationRunning;
    private final String progressMassage;

    public DTOSimulationProgressForUiImpl(Integer secondsPast, Integer ticksPast, String progressMassage,
                                          Map<String, Integer> entitiesPopulationAfterSimulationRunning) {
        this.secondsPast = secondsPast;
        this.ticksPast = ticksPast;
        this.entitiesPopulationAfterSimulationRunning = entitiesPopulationAfterSimulationRunning;
        this.progressMassage = progressMassage;
    }

    @Override
    public Integer getSecondsPast() {
        return secondsPast;
    }

    @Override
    public Integer getTicksPast() {
        return ticksPast;
    }

    @Override
    public Map<String, Integer> getEntitiesLeft() {
        return entitiesPopulationAfterSimulationRunning;
    }

    @Override
    public String getProgressMassage() {
        return progressMassage;
    }
}
