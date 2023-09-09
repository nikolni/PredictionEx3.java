package system.engine.run.simulation.manager;

import dto.api.DTOSimulationProgressForUi;
import dto.impl.DTOSimulationProgressForUiImpl;
import system.engine.run.simulation.api.RunSimulation;
import system.engine.world.api.WorldInstance;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RunSimulationManager {
    private final ExecutorService threadPool;
    private final Map<Integer, RunSimulation>  simulationIdToRunSimulation;
    private final Map<Integer, WorldInstance> simulationIdToWorldInstance;

    public RunSimulationManager(int threadPoolSize, Map<Integer, WorldInstance> simulationIdToWorldInstance) {
        threadPool = Executors.newFixedThreadPool(threadPoolSize);
        simulationIdToRunSimulation = new HashMap<>();
        this.simulationIdToWorldInstance = simulationIdToWorldInstance;
    }
    public void addSimulationIdToRunSimulation(Integer simulationID, RunSimulation runSimulation){
        simulationIdToRunSimulation.put(simulationID, runSimulation);
    }
    public void addTaskToQueue(Runnable runSimulationRunnable){
        threadPool.submit(runSimulationRunnable);
    }

    public DTOSimulationProgressForUi getDtoSimulationProgressForUi(Integer simulationID){
        if(simulationIdToRunSimulation.get(simulationID) != null){
            return simulationIdToRunSimulation.get(simulationID).getDtoSimulationProgressForUi();
        }
        return new DTOSimulationProgressForUiImpl(0, 0 ,"Getting ready...",
                simulationIdToWorldInstance.get(simulationID).getEntityInstanceManager().getEntitiesPopulationAfterSimulationRunning());
    }
    public void pauseSimulation(int simulationID){
        if(simulationIdToRunSimulation.get(simulationID) != null) {
            simulationIdToRunSimulation.get(simulationID).setPaused(true);
        }
    }
    public void resumeSimulation(int simulationID){
        if(simulationIdToRunSimulation.get(simulationID) != null) {
            simulationIdToRunSimulation.get(simulationID).setPaused(false);
            simulationIdToRunSimulation.get(simulationID).getIsSimulationPaused().resume();
        }
    }
    public void cancelSimulation(int simulationID){
        if(simulationIdToRunSimulation.get(simulationID) != null) {
            simulationIdToRunSimulation.get(simulationID).setCanceled(true);
            if(simulationIdToRunSimulation.get(simulationID).getPaused()){
                simulationIdToRunSimulation.get(simulationID).getIsSimulationPaused().resume();
            }
        }
    }
}
