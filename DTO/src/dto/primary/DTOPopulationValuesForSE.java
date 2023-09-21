package dto.primary;

import java.util.Map;

public class DTOPopulationValuesForSE  {

   private final Map<String, Integer> entityNameDefToPopulation;


    public DTOPopulationValuesForSE(Map<String, Integer> entityNameDefToPopulation) {
        this.entityNameDefToPopulation = entityNameDefToPopulation;
    }

    public Map<String, Integer> getEntityNameDefToPopulation() {
        return entityNameDefToPopulation;
    }
}
