package engine.per.file.dto.creation;

import dto.primary.DTONamesListForUi;
import engine.per.file.engine.world.api.WorldDefinition;
import engine.per.file.engine.world.definition.entity.api.EntityDefinition;

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
