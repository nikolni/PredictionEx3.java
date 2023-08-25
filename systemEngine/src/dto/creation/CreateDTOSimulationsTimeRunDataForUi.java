package dto.creation;

import dto.api.DTOSimulationsTimeRunDataForUi;
import dto.impl.DTOSimulationsTimeRunDataForUiImpl;
import system.engine.world.api.WorldInstance;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class CreateDTOSimulationsTimeRunDataForUi {

    public DTOSimulationsTimeRunDataForUi getData(List<WorldInstance> worldInstances ) {
        List<Integer> idList = new ArrayList<>();
        List<LocalDateTime> simulationRunTimeList = new ArrayList<>();

        for(WorldInstance worldInstance : worldInstances) {
            idList.add(worldInstance.getId());
            simulationRunTimeList.add(worldInstance.getSimulationRunTime());
        }

            return new DTOSimulationsTimeRunDataForUiImpl(idList,simulationRunTimeList );

    }
}
