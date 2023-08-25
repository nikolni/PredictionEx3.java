package dto.creation;

import dto.api.DTONamesListForUi;
import dto.api.DTOSimulationsTimeRunDataForUi;
import dto.impl.DTONamesListForUiImpl;
import dto.impl.DTOSimulationsTimeRunDataForUiImpl;
import system.engine.world.api.WorldDefinition;
import system.engine.world.api.WorldInstance;
import system.engine.world.definition.entity.api.EntityDefinition;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class CreateEntitiesNamesListForUi {
    public DTONamesListForUi getData(WorldDefinition worldDefinition) {
        List<String> entitiesNames = new ArrayList<>();

        for(EntityDefinition entityDefinition : worldDefinition.getEntityDefinitionManager().getDefinitions()) {
            entitiesNames.add(entityDefinition.getUniqueName());
        }

        return new DTONamesListForUiImpl(entitiesNames);

    }
}
