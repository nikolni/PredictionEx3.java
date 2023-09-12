package dto.api;

import java.util.List;
import java.util.Map;

public interface DTOEntitiesAfterSimulationByQuantityForUi {

    List<String> getEntitiesNames();
    List<Integer> getEntitiesPopulationBeforeSimulation();
    List<Integer> getEntitiesPopulationAfterSimulation();

    Map<Integer,Integer> getEntitiesLeftByTicks();
}
