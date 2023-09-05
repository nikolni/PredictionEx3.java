package dto.creation;

import app.body.screen2.tile.entity.EntityController;
import dto.api.DTOPopulationValuesForSE;
import dto.impl.DTOPopulationValuesForSEImpl;

import java.util.HashMap;
import java.util.Map;

public class CreateDTOPopulationForSE {

    public DTOPopulationValuesForSE getData(Map<String, EntityController> entityNameToTileController) {
        Map<String, Integer> entityNameDefToPopulation = new HashMap<>();

        for (String key : entityNameToTileController.keySet()) {
            entityNameDefToPopulation.put(key, entityNameToTileController.get(key).getPopulationValue());
        }
        return new DTOPopulationValuesForSEImpl(entityNameDefToPopulation);
    }
    /*public DTOPopulationValuesForSE getData(List<String> entitiesNames, List<Integer> entitiesPopulations) {
        return new DTOPopulationValuesForSEImpl(entitiesNames, entitiesPopulations);
    }*/
}
