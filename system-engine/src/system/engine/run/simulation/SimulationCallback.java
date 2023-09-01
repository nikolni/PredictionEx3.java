package system.engine.run.simulation;

import dto.api.DTOSimulationProgressForUi;

public interface SimulationCallback {
    void onUpdate(DTOSimulationProgressForUi dtoSimulationProgressForUi);
}
