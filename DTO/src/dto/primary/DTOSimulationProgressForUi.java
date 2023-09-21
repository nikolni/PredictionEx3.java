package dto.primary;

import java.util.Map;

public class DTOSimulationProgressForUi {

    private final Integer secondsPast;
    private final Integer ticksPast;
    private final Map<String, Integer> entitiesPopulationAfterSimulationRunning;
    private final String progressMassage;

    public DTOSimulationProgressForUi(Integer secondsPast, Integer ticksPast, String progressMassage,
                                      Map<String, Integer> entitiesPopulationAfterSimulationRunning) {
        this.secondsPast = secondsPast;
        this.ticksPast = ticksPast;
        this.entitiesPopulationAfterSimulationRunning = entitiesPopulationAfterSimulationRunning;
        this.progressMassage = progressMassage;
    }

    public Integer getSecondsPast() {
        return secondsPast;
    }

    public Integer getTicksPast() {
        return ticksPast;
    }

    public Map<String, Integer> getEntitiesLeft() {
        return entitiesPopulationAfterSimulationRunning;
    }

    public String getProgressMassage() {
        return progressMassage;
    }
}
