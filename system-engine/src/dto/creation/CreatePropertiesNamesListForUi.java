package dto.creation;

import dto.api.DTONamesListForUi;
import dto.impl.DTONamesListForUiImpl;
import system.engine.world.api.WorldDefinition;
import system.engine.world.definition.entity.api.EntityDefinition;
import system.engine.world.definition.property.api.PropertyDefinition;

import java.util.ArrayList;
import java.util.List;

public class CreatePropertiesNamesListForUi {

    public DTONamesListForUi getData(EntityDefinition entityDefinition) {
        List<String> propertiesNames = new ArrayList<>();

        for(PropertyDefinition propertyDefinition : entityDefinition.getProps()) {
            propertiesNames.add(propertyDefinition.getUniqueName());
        }

        return new DTONamesListForUiImpl(propertiesNames);

    }
}
