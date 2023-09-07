package app.body.screen3.simulation.progress;

import dto.api.DTOSimulationEndingForUi;
import dto.api.DTOSimulationProgressForUi;

public interface SimulationCallback {
    void onUpdateWhileSimulationRunning(DTOSimulationProgressForUi dtoSimulationProgressForUi);
    void onUpdateWhileSimulationIsPaused();
}
