package dto.creation;

import dto.api.DTOPopulationValuesForSE;
import dto.impl.DTOPopulationValuesForSEImpl;

import java.util.List;

public class CreateDTOPopulationForSE {
    public DTOPopulationValuesForSE getData(List<String> entitiesNames, List<Integer> entitiesPopulations) {
        return new DTOPopulationValuesForSEImpl(entitiesNames, entitiesPopulations);
    }
}
