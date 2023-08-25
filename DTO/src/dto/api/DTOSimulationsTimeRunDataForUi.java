package dto.api;

import java.time.LocalDateTime;
import java.util.List;

public interface DTOSimulationsTimeRunDataForUi {

    List<Integer> getIdList();
 List<LocalDateTime> getSimulationRunTimeList();
}
