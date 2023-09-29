package engine.per.file.dto.creation;

import dto.primary.DTONamesListForUi;
import engine.per.file.engine.world.definition.entity.api.EntityDefinition;
import engine.per.file.engine.world.definition.property.api.PropertyDefinition;

import java.util.ArrayList;
import java.util.List;

public class CreatePropertiesNamesListForUi {

    public DTONamesListForUi getData(EntityDefinition entityDefinition) {
        List<String> propertiesNames = new ArrayList<>();

        for(PropertyDefinition propertyDefinition : entityDefinition.getProps()) {
            propertiesNames.add(propertyDefinition.getUniqueName());
        }

        return new DTONamesListForUi(propertiesNames);

    }
}
