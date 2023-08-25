package dto.impl;

import dto.api.DTOSimulationsTimeRunDataForUi;

import java.time.LocalDateTime;
import java.util.List;

public class DTOSimulationsTimeRunDataForUiImpl implements DTOSimulationsTimeRunDataForUi {
    private final List<Integer> idList;
    private final List<LocalDateTime> simulationRunTimeList;


    public DTOSimulationsTimeRunDataForUiImpl(List<Integer> idList, List<LocalDateTime> simulationRunTimeList){
        this.idList = idList;
        this.simulationRunTimeList = simulationRunTimeList;
    }


    @Override
    public List<Integer> getIdList() {
        return idList;
    }

    @Override
    public List<LocalDateTime> getSimulationRunTimeList() {
        return simulationRunTimeList;
    }
}
