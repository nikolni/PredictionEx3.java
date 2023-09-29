package engine.per.file.dto.creation;

import dto.primary.DTOEntityPropertyConsistencyForUi;
import engine.per.file.engine.world.api.WorldInstance;

public class CreateDTOEntityPropertyConsistency {

    public DTOEntityPropertyConsistencyForUi getData(WorldInstance worldInstance, String entityName, String propertyName){
        Float propertyConsistency=worldInstance.getEntityInstanceManager().getConsistencyByEntityAndPropertyName(entityName,propertyName);
        return new DTOEntityPropertyConsistencyForUi(propertyConsistency);
    }
}
