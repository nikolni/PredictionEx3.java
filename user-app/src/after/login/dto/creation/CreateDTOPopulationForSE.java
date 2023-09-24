package after.login.dto.creation;

import after.login.component.body.execution.tile.entity.EntityController;
import dto.primary.DTOPopulationValuesForSE;

import java.util.HashMap;
import java.util.Map;

public class CreateDTOPopulationForSE {

    public DTOPopulationValuesForSE getData(Map<String, EntityController> entityNameToTileController) {
        Map<String, Integer> entityNameDefToPopulation = new HashMap<>();

        for (String key : entityNameToTileController.keySet()) {
            entityNameDefToPopulation.put(key, entityNameToTileController.get(key).getPopulationValue());
        }
        return new DTOPopulationValuesForSE(entityNameDefToPopulation);
    }
    /*public DTOPopulationValuesForSE getData(List<String> entitiesNames, List<Integer> entitiesPopulations) {
        return new DTOPopulationValuesForSEImpl(entitiesNames, entitiesPopulations);
    }*/
}
