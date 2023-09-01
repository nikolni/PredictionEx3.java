package dto.impl;

import dto.api.DTOSimulationProgressForUi;

public class DTOSimulationProgressForUiImpl implements DTOSimulationProgressForUi {

    private Integer secondsPast;
    private Integer ticksPast;
    private Integer entitiesLeft;

    public DTOSimulationProgressForUiImpl(Integer secondsPast, Integer ticksPast, Integer entitiesLeft) {
        this.secondsPast = secondsPast;
        this.ticksPast = ticksPast;
        this.entitiesLeft = entitiesLeft;
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
    public Integer getEntitiesLeft() {
        return entitiesLeft;
    }
}
