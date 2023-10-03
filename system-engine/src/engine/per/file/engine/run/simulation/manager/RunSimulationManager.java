package engine.per.file.engine.run.simulation.manager;

import dto.primary.DTOSimulationEndingForUi;
import dto.primary.DTOSimulationProgressForUi;
import dto.primary.DTOThreadsPoolStatusForUi;
import engine.per.file.engine.run.simulation.api.RunSimulation;
import engine.per.file.engine.world.api.WorldInstance;
import engine.per.file.engine.world.termination.condition.api.TerminationCondition;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RunSimulationManager {
    //private final ExecutorService threadPool;
    private final Map<Integer, RunSimulation>  simulationIdToRunSimulation;
    private final Map<Integer, WorldInstance> simulationIdToWorldInstance;


    private final Map<Integer, DTOSimulationEndingForUi> simulationIdToSimulationEnding;
    private final Map<Integer, List<TerminationCondition>> simulationIdToTerminationConditions;
    /*private int completedTaskCount =0;
    private int taskCount=0;
    private int activeThreadCount = 0;*/

    public RunSimulationManager( Map<Integer, WorldInstance> simulationIdToWorldInstance) {
        simulationIdToRunSimulation = new HashMap<>();
        simulationIdToSimulationEnding = new HashMap<>();
        simulationIdToTerminationConditions = new HashMap<>();
        this.simulationIdToWorldInstance = simulationIdToWorldInstance;
    }
    public void addSimulationIdToRunSimulation(Integer simulationID, RunSimulation runSimulation){
        simulationIdToRunSimulation.put(simulationID, runSimulation);
    }
    public void addSimulationEndingDto(Integer simulationID, DTOSimulationEndingForUi dtoSimulationEndingForUi){
        simulationIdToSimulationEnding.put(simulationID, dtoSimulationEndingForUi);
    }
    /*public void addTaskToQueue(Runnable runSimulationRunnable){
        threadPool.submit(runSimulationRunnable);
        taskCount++;
    }*/

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
    /*public dto.primary.DTOThreadsPoolStatusForUi getThreadsPoolStatus(){
        //int activeThreadCount = ((ThreadPoolExecutor) threadPool).getActiveCount();
        int queueSize = taskCount - activeThreadCount - completedTaskCount;

        return new DTOThreadsPoolStatusForUi(queueSize, activeThreadCount, completedTaskCount);
    }*/

 /*   public void increaseCompletedTaskCount(){
        completedTaskCount++;
    }
    public void increaseActiveCount(){
        activeThreadCount++;
    }
    public void decreaseActiveCount(){
        activeThreadCount--;
    }*/

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

    public String getSimulationStatusByID(Integer simulationID){
        String simulationStatus ;

        if(simulationIdToRunSimulation.get(simulationID) != null) {
            simulationStatus = simulationIdToRunSimulation.get(simulationID).getDtoSimulationProgressForUi().getProgressMassage();
        }
        else{
            simulationStatus = "Getting ready...";
        }

        return simulationStatus;
    }

    public void addTerminationConditionsList(Integer simulationID, List<TerminationCondition>  terminationConditionsList){
        simulationIdToTerminationConditions.put(simulationID, terminationConditionsList);
    }


    public List<TerminationCondition> getTerminationConditionsListByID(Integer executionID) {
        return simulationIdToTerminationConditions.get(executionID);
    }

    public Map<Integer, DTOSimulationEndingForUi> getSimulationIdToSimulationEnding() {
        return simulationIdToSimulationEnding;
    }

}
