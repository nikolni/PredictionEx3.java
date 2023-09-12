package dto.impl;

import dto.api.DTOEntitiesAfterSimulationByQuantityForUi;

import java.util.List;
import java.util.Map;

public class DTOEntitiesAfterSimulationByQuantityForUiImpl implements DTOEntitiesAfterSimulationByQuantityForUi {
    //private final List<EntityDefinitionDTO> entitiesDTO;
    private final List<String> entitiesNames;
    private final List<Integer> entitiesPopulationBeforeSimulation;
    private final List<Integer> entitiesPopulationAfterSimulation;
    private Map<Integer,Integer> entitiesLeftByTicks;


    public DTOEntitiesAfterSimulationByQuantityForUiImpl(List<String> entitiesNames, List<Integer> entitiesPopulationBeforeSimulation,
                                                         List<Integer> entitiesPopulationAfterSimulation,Map<Integer,Integer> entitiesLeftByTicks){
        this.entitiesNames =entitiesNames;
        this.entitiesPopulationBeforeSimulation =entitiesPopulationBeforeSimulation;
        this.entitiesPopulationAfterSimulation =entitiesPopulationAfterSimulation;
        this.entitiesLeftByTicks=entitiesLeftByTicks;
    }



    @Override
    public List<String> getEntitiesNames() {
        return entitiesNames;
    }

    @Override
    public List<Integer> getEntitiesPopulationAfterSimulation() {
        return entitiesPopulationAfterSimulation;
    }

    @Override
    public List<Integer> getEntitiesPopulationBeforeSimulation() {
        return entitiesPopulationBeforeSimulation;
    }

    @Override
    public Map<Integer,Integer> getEntitiesLeftByTicks(){return entitiesLeftByTicks;}
}
