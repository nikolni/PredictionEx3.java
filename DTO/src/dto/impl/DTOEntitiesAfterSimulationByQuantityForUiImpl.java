package dto.impl;

import dto.api.DTOEntitiesAfterSimulationByQuantityForUi;

import java.util.List;

public class DTOEntitiesAfterSimulationByQuantityForUiImpl implements DTOEntitiesAfterSimulationByQuantityForUi {
    //private final List<EntityDefinitionDTO> entitiesDTO;
    private final List<String> entitiesNames;
    private final List<Integer> entitiesPopulationBeforeSimulation;
    private final List<Integer> entitiesPopulationAfterSimulation;


    public DTOEntitiesAfterSimulationByQuantityForUiImpl(List<String> entitiesNames, List<Integer> entitiesPopulationBeforeSimulation,
                                                         List<Integer> entitiesPopulationAfterSimulation){
        this.entitiesNames =entitiesNames;
        this.entitiesPopulationBeforeSimulation =entitiesPopulationBeforeSimulation;
        this.entitiesPopulationAfterSimulation =entitiesPopulationAfterSimulation;
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
}
