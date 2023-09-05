package dto.impl;

import dto.api.DTOPopulationValuesForSE;

import java.util.List;
import java.util.Map;

public class DTOPopulationValuesForSEImpl implements DTOPopulationValuesForSE {

   private final Map<String, Integer> entityNameDefToPopulation;


    public DTOPopulationValuesForSEImpl( Map<String, Integer> entityNameDefToPopulation) {
        this.entityNameDefToPopulation = entityNameDefToPopulation;
    }

    @Override
    public Map<String, Integer> getEntityNameDefToPopulation() {
        return entityNameDefToPopulation;
    }
}
