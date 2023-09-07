package dto.creation;

import dto.api.DTOSimulationsTimeRunDataForUi;
import dto.impl.DTOSimulationsTimeRunDataForUiImpl;
import system.engine.world.api.WorldInstance;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CreateDTOSimulationsTimeRunDataForUi {

    public DTOSimulationsTimeRunDataForUi getData(Map<Integer, WorldInstance> simulationIdToWorldInstance ) {
        List<Integer> idList = new ArrayList<>();
        List<LocalDateTime> simulationRunTimeList = new ArrayList<>();

        for(Integer simulationID : simulationIdToWorldInstance.keySet()) {
            idList.add(simulationID);
            simulationRunTimeList.add(simulationIdToWorldInstance.get(simulationIdToWorldInstance).getSimulationRunTime());
        }

            return new DTOSimulationsTimeRunDataForUiImpl(idList,simulationRunTimeList );

    }
}
