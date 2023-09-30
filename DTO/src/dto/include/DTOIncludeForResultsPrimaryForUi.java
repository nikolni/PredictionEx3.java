package dto.include;

import dto.primary.DTODefinitionsForUi;
import dto.primary.DTOEntitiesAfterSimulationByQuantityForUi;

public class DTOIncludeForResultsPrimaryForUi {
    private final DTOEntitiesAfterSimulationByQuantityForUi dtoEntitiesAfterSimulationByQuantity;
    private final DTODefinitionsForUi dtoDefinitions;

    public DTOIncludeForResultsPrimaryForUi(DTOEntitiesAfterSimulationByQuantityForUi dtoEntitiesAfterSimulationByQuantity,
                                            DTODefinitionsForUi dtoDefinitions) {
        this.dtoEntitiesAfterSimulationByQuantity = dtoEntitiesAfterSimulationByQuantity;
        this.dtoDefinitions = dtoDefinitions;
    }
    public DTODefinitionsForUi getDtoDefinitions() {
        return dtoDefinitions;
    }
    public DTOEntitiesAfterSimulationByQuantityForUi getEntitiesAfterSimulationByQuantity() {
        return dtoEntitiesAfterSimulationByQuantity;
    }
}
