package system.engine.run.simulation.manager;

import dto.primary.DTOSimulationProgressForUi;
import dto.primary.DTOThreadsPoolStatusForUi;
import system.engine.run.simulation.api.RunSimulation;
import system.engine.world.api.WorldInstance;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RunSimulationManager {
    private final ExecutorService threadPool;
    /*private final Map<String, Map<Integer, WorldInstance>>  userNameToSimulationsMap;
    private final Map<String, Map<Integer, WorldInstance>> userNameToWorldInstancesMap;*/
    private final Map<Integer, RunSimulation>  simulationIdToRunSimulation;
    private final Map<Integer, WorldInstance> simulationIdToWorldInstance;
    private int completedTaskCount =0;
    private int taskCount=0;
    private int activeThreadCount = 0;

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
        taskCount++;
    }

    public dto.primary.DTOSimulationProgressForUi getDtoSimulationProgressForUi(Integer simulationID){
        if(simulationIdToRunSimulation.get(simulationID) != null){
            return simulationIdToRunSimulation.get(simulationID).getDtoSimulationProgressForUi();
        }
        return new DTOSimulationProgressForUi(0, 0 ,"Getting ready...",
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
            //simulationIdToRunSimulation.get(simulationID).setCanceled(true);
            if(simulationIdToRunSimulation.get(simulationID).getPaused()){
                simulationIdToRunSimulation.get(simulationID).getIsSimulationPaused().resume();
            }
            simulationIdToRunSimulation.get(simulationID).setCanceled(true);
        }
    }
    public dto.primary.DTOThreadsPoolStatusForUi getThreadsPoolStatus(){
        //int activeThreadCount = ((ThreadPoolExecutor) threadPool).getActiveCount();
        int queueSize = taskCount - activeThreadCount - completedTaskCount;

        return new DTOThreadsPoolStatusForUi(queueSize, activeThreadCount, completedTaskCount);
    }

    public void increaseCompletedTaskCount(){
        completedTaskCount++;
    }
    public void increaseActiveCount(){
        activeThreadCount++;
    }
    public void decreaseActiveCount(){
        activeThreadCount--;
    }

    public List<String> getAllSimulationsStatus(){
        List<String> simulationsStatuses = new ArrayList<>();
        int numOfSimulations = simulationIdToWorldInstance.size();
        int i = 0;

        while(i<numOfSimulations){
            if(simulationIdToRunSimulation.get(i + 1) != null) {
                simulationsStatuses.add(simulationIdToRunSimulation.get(i + 1).getDtoSimulationProgressForUi().getProgressMassage());
            }
            else{
                simulationsStatuses.add("Getting ready...");
            }
            i++;
        }
        return simulationsStatuses;
    }
}
