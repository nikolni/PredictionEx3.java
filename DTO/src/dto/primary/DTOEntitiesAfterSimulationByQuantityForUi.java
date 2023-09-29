package dto.primary;

import java.util.List;
import java.util.Map;

public class DTOEntitiesAfterSimulationByQuantityForUi  {
    private final List<String> entitiesNames;
    private final List<Integer> entitiesPopulationBeforeSimulation;
    private final List<Integer> entitiesPopulationAfterSimulation;
    private Map<Integer,Integer> entitiesLeftByTicks;



    public DTOEntitiesAfterSimulationByQuantityForUi(List<String> entitiesNames, List<Integer> entitiesPopulationBeforeSimulation,
                                                     List<Integer> entitiesPopulationAfterSimulation, Map<Integer,Integer> entitiesLeftByTicks){
        this.entitiesNames =entitiesNames;
        this.entitiesPopulationBeforeSimulation =entitiesPopulationBeforeSimulation;
        this.entitiesPopulationAfterSimulation =entitiesPopulationAfterSimulation;
        this.entitiesLeftByTicks=entitiesLeftByTicks;
    }


    public List<String> getEntitiesNames() {
        return entitiesNames;
    }

    public List<Integer> getEntitiesPopulationAfterSimulation() {
        return entitiesPopulationAfterSimulation;
    }

    public List<Integer> getEntitiesPopulationBeforeSimulation() {
        return entitiesPopulationBeforeSimulation;
    }

    public Map<Integer,Integer> getEntitiesLeftByTicks(){return entitiesLeftByTicks;}
}
