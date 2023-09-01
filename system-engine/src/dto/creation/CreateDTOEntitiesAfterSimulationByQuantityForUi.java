package dto.creation;

import dto.api.DTOEntitiesAfterSimulationByQuantityForUi;
import dto.impl.DTOEntitiesAfterSimulationByQuantityForUiImpl;
import system.engine.world.api.WorldDefinition;
import system.engine.world.api.WorldInstance;
import system.engine.world.definition.entity.api.EntityDefinition;

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

        return new DTOEntitiesAfterSimulationByQuantityForUiImpl(entitiesNames, entitiesPopulationBeforeSimulation,
                entitiesPopulationAfterSimulation);
    }
}
