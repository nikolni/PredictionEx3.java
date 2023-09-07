package system.engine.run.simulation.manager;

import dto.api.DTOSimulationProgressForUi;
import system.engine.run.simulation.api.RunSimulation;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RunSimulationManager {
    private final ExecutorService threadPool;
    private final Map<Integer, RunSimulation>  simulationIdToRunSimulation;

    public RunSimulationManager(int threadPoolSize) {
        threadPool = Executors.newFixedThreadPool(threadPoolSize);
        simulationIdToRunSimulation = new HashMap<>();
    }
    public void addSimulationIdToRunSimulation(Integer simulationID, RunSimulation runSimulation){
        simulationIdToRunSimulation.put(simulationID, runSimulation);
    }
    public void addTaskToQueue(Runnable runSimulationRunnable){
        threadPool.submit(runSimulationRunnable);
    }

    public DTOSimulationProgressForUi getDtoSimulationProgressForUi(Integer simulationID){
        return simulationIdToRunSimulation.get(simulationID).getDtoSimulationProgressForUi();
    }
    public void pauseSimulation(int simulationID){
        simulationIdToRunSimulation.get(simulationID).getIsSimulationPaused().pause();
    }
    public void resumeSimulation(int simulationID){
        simulationIdToRunSimulation.get(simulationID).getIsSimulationPaused().resume();
    }
    public void cancelSimulation(int simulationID){
        simulationIdToRunSimulation.get(simulationID).setCanceled(true);
    }
}
