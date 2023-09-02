package system.engine.run.simulation;

import dto.api.DTOSimulationEndingForUi;
import dto.api.DTOSimulationProgressForUi;

public interface SimulationCallback {
    void onUpdateWhileSimulationRunning(DTOSimulationProgressForUi dtoSimulationProgressForUi);
    void onUpdateWhileSimulationIsPaused();
}
