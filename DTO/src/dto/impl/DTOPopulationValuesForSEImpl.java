package dto.impl;

import dto.api.DTOPopulationValuesForSE;

import java.util.List;

public class DTOPopulationValuesForSEImpl implements DTOPopulationValuesForSE {

    private List<String> entitiesNames;



    private List<Integer> entitiesPopulations;


    public DTOPopulationValuesForSEImpl(List<String> entitiesNames, List<Integer> entitiesPopulations) {
        this.entitiesNames = entitiesNames;
        this.entitiesPopulations = entitiesPopulations;
    }

    @Override
    public List<String> getEntitiesNames() {
        return entitiesNames;
    }
    @Override
    public List<Integer> getEntitiesPopulations() {
        return entitiesPopulations;
    }

}
