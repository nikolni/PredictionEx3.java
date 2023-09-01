package dto.creation;

import app.body.screen2.tile.entity.EntityController;
import dto.api.DTOPopulationValuesForSE;
import dto.impl.DTOPopulationValuesForSEImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CreateDTOPopulationForSE {

    public DTOPopulationValuesForSE getData(Map<String, EntityController> entityNameToTileController) {
         List<String> entitiesNames = new ArrayList<>();
         List<Integer> entitiesPopulations= new ArrayList<>();

        for (String key : entityNameToTileController.keySet()) {
            entitiesNames.add(key);
            entitiesPopulations.add(entityNameToTileController.get(key).getPopulationValue()) ;
        }
        return new DTOPopulationValuesForSEImpl(entitiesNames, entitiesPopulations);
    }
    /*public DTOPopulationValuesForSE getData(List<String> entitiesNames, List<Integer> entitiesPopulations) {
        return new DTOPopulationValuesForSEImpl(entitiesNames, entitiesPopulations);
    }*/
}
