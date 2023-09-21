package dto.creation;

import dto.primary.DTONamesListForUi;
import system.engine.world.api.WorldDefinition;
import system.engine.world.definition.entity.api.EntityDefinition;

import java.util.ArrayList;
import java.util.List;

public class CreateEntitiesNamesListForUi {
    public DTONamesListForUi getData(WorldDefinition worldDefinition) {
        List<String> entitiesNames = new ArrayList<>();

        for(EntityDefinition entityDefinition : worldDefinition.getEntityDefinitionManager().getDefinitions()) {
            entitiesNames.add(entityDefinition.getUniqueName());
        }

        return new DTONamesListForUi(entitiesNames);

    }
}
