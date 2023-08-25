package dto.api;

import java.util.List;

public interface DTOEntitiesAfterSimulationByQuantityForUi {

    List<String> getEntitiesNames();
    List<Integer> getEntitiesPopulationBeforeSimulation();
    List<Integer> getEntitiesPopulationAfterSimulation();
}
