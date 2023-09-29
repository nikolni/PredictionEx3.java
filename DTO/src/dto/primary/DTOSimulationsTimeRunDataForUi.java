package dto.primary;

import java.time.LocalDateTime;
import java.util.List;

public class DTOSimulationsTimeRunDataForUi{
    private final List<Integer> idList;
    private final List<LocalDateTime> simulationRunTimeList;


    public DTOSimulationsTimeRunDataForUi(List<Integer> idList, List<LocalDateTime> simulationRunTimeList){
        this.idList = idList;
        this.simulationRunTimeList = simulationRunTimeList;
    }


    public List<Integer> getIdList() {
        return idList;
    }

    public List<LocalDateTime> getSimulationRunTimeList() {
        return simulationRunTimeList;
    }
}
