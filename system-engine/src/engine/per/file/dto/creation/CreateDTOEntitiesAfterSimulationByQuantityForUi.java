package engine.per.file.dto.creation;

import dto.primary.DTOEntitiesAfterSimulationByQuantityForUi;
import engine.per.file.engine.world.api.WorldDefinition;
import engine.per.file.engine.world.api.WorldInstance;
import engine.per.file.engine.world.definition.entity.api.EntityDefinition;

import java.util.ArrayList;
import java.util.List;

public class CreateDTOEntitiesAfterSimulationByQuantityForUi {

    public DTOEntitiesAfterSimulationByQuantityForUi getData(WorldDefinition worldDefinition, WorldInstance worldInstance ){
        List<String> entitiesNames = new ArrayList<>();
        List<Integer> entitiesPopulationBeforeSimulation = new ArrayList<>();
        List<Integer> entitiesPopulationAfterSimulation = new ArrayList<>();

        for(EntityDefinition entityDefinition : worldDefinition.getEntityDefinitionManager().getDefinitions()){
            entitiesNames.add(entityDefinition.getUniqueName());
            entitiesPopulationBeforeSimulation.add(entityDefinition.getPopulation());
            entitiesPopulationAfterSimulation.add(worldInstance.getEntityInstanceManager().
                    getEntityPopulationAfterRunning(entityDefinition.getUniqueName()));
        }

        return new DTOEntitiesAfterSimulationByQuantityForUi(entitiesNames, entitiesPopulationBeforeSimulation,
                entitiesPopulationAfterSimulation,worldInstance.getEntityInstanceManager().getNumOfEntitiesLeftByTicks());
    }
}
