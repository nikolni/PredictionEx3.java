package system.engine.run.simulation.impl;

import dto.api.DTOSimulationProgressForUi;
import dto.impl.DTOSimulationProgressForUiImpl;
import system.engine.run.simulation.api.RunSimulation;
import system.engine.run.simulation.manager.IsSimulationPaused;
import system.engine.world.api.WorldDefinition;
import system.engine.world.api.WorldInstance;
import system.engine.world.execution.instance.enitty.api.EntityInstance;
import system.engine.world.execution.instance.environment.api.EnvVariablesInstanceManager;
import system.engine.world.rule.action.api.Action;
import system.engine.world.rule.action.api.ActionType;
import system.engine.world.rule.api.Rule;
import system.engine.world.rule.context.Context;
import system.engine.world.rule.context.ContextImpl;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RunSimulationImpl implements RunSimulation {
    private DTOSimulationProgressForUi dtoSimulationProgressForUi= null;

    private IsSimulationPaused isSimulationPaused;
    private boolean isCanceled = false;
    //private Task<Boolean> currentTask;
    private final long SLEEP_TIME = 3;
    @Override
    public void setCanceled(boolean canceled) {
        isCanceled = canceled;
    }
    @Override
    public IsSimulationPaused getIsSimulationPaused() {
        return isSimulationPaused;
    }

    @Override
    public int[] runSimulationOnLastWorldInstance(WorldDefinition worldDefinition, WorldInstance worldInstance,
                                                 EnvVariablesInstanceManager envVariablesInstanceManager)
            throws IllegalArgumentException {

        int entitiesLeft = worldInstance.getEntityInstanceManager().getInstances().size();
        Instant startTime = Instant.now();
        Instant endTime;
        Duration duration;
        int tick = 0;
        int seconds = 0;
        int numOfTicksToRun = getNumOfTicksToRun(worldDefinition);
        int numOfSecondsToRun = getNumOfSecondsToRun(worldDefinition);
        String progressMassage = "Running!";

        List<Action> actionsList = new ArrayList<>();
        List<EntityInstance> entitiesToKill = new ArrayList<>();

        while(tick <= numOfTicksToRun && seconds <= numOfSecondsToRun) {
            /*synchronized (this){
                if(isSimulationPaused.isPaused()) {
                    try {
                        this.wait(); // Pause the thread until notified
                    } catch (InterruptedException e) {
                    }
                }
            }*/
            while (isSimulationPaused.isPaused()) {
                updateDtoSimulationProgressForUi(seconds, tick, progressMassage,
                        worldInstance.getEntityInstanceManager().getEntitiesPopulationAfterSimulationRunning());

                if(isCanceled){
                    progressMassage = "Canceled!";
                    updateDtoSimulationProgressForUi(seconds, tick, progressMassage,
                            worldInstance.getEntityInstanceManager().getEntitiesPopulationAfterSimulationRunning());

                    break;
                }
                entitiesToKill.clear();
                actionsList.clear();
                for (Rule rule : getActiveRules(tick, worldDefinition)) {
                    actionsList.addAll(rule.getActionsToPerform());
                }

                entitiesLeft -= runAllActionsOnAllEntities(worldInstance, envVariablesInstanceManager, actionsList, entitiesToKill, tick);

                tick++;
                endTime = Instant.now();
                duration = Duration.between(startTime, endTime);
                seconds = (int) duration.getSeconds();

                updateDtoSimulationProgressForUi(seconds, tick, progressMassage,
                        worldInstance.getEntityInstanceManager().getEntitiesPopulationAfterSimulationRunning());

                if(!(tick <= numOfTicksToRun && seconds <= numOfSecondsToRun)){
                    break;
                }
                sleepForAWhile(SLEEP_TIME);
            }
            if(isCanceled){
                break;
            }
        }


        int[] terminationCausePair=new int[4];
        terminationCausePair[0]=tick;
        terminationCausePair[1]=seconds;

        if(isCanceled) {terminationCausePair[3]=2;} //by user
        else{
            if(tick>numOfTicksToRun){terminationCausePair[3]=0;} //last tick
            else {terminationCausePair[3]=1;} //time ran out
            progressMassage = "Done!";
            updateDtoSimulationProgressForUi(seconds-1, tick-1, progressMassage,
                    worldInstance.getEntityInstanceManager().getEntitiesPopulationAfterSimulationRunning());

        }

        return terminationCausePair;
    }


    public  void sleepForAWhile(long sleepTime) {
        if (sleepTime != 0) {
            try {
                Thread.sleep(sleepTime);
            } catch (InterruptedException ignored) {

            }
        }
    }

    private int runAllActionsOnAllEntities(WorldInstance worldInstance, EnvVariablesInstanceManager envVariablesInstanceManager,
                                            List<Action> actionsList, List<EntityInstance> entitiesToKill, Integer tickNumber){

        List<EntityInstance> currentEntitiesToKill = new ArrayList<>();
        for(EntityInstance primaryEntityInstance : getAllEntityInstancesOfWorldInstance(worldInstance)){
            if(primaryEntityInstance!=null){
                currentEntitiesToKill.clear();
                for(Action action : actionsList){
                    //the entity instance is from the type of primary entity of the action
                    if(action.getContextPrimaryEntity().getUniqueName().equals(primaryEntityInstance.getEntityDefinition().getUniqueName())){
                        Context context  =null;
                        //there is second entity
                        if(action.getSecondaryEntityDefinition() != null){
                            List<EntityInstance> chosenSecondaryEntities=action.getSecondaryEntityDefinition().generateSecondaryEntityList(worldInstance,envVariablesInstanceManager, tickNumber);
                            if(!chosenSecondaryEntities.isEmpty()){
                                for(EntityInstance secondEntityInstance :chosenSecondaryEntities){
                                    context = new ContextImpl(primaryEntityInstance,secondEntityInstance, envVariablesInstanceManager,
                                            currentEntitiesToKill, tickNumber,worldInstance.getEntityInstanceManager());
                                    action.executeAction(context);
                                }
                            }
                            else{ //secondary Entities list is empty
                                context = new ContextImpl(primaryEntityInstance,null, envVariablesInstanceManager,
                                        currentEntitiesToKill, tickNumber,worldInstance.getEntityInstanceManager());
                                if(action.getActionType().equals(ActionType.CONDITION))
                                    action.executeAction(context);
                            }

                        }
                        //no second entity
                        else{
                            context = new ContextImpl(primaryEntityInstance,null, envVariablesInstanceManager,
                                    currentEntitiesToKill, tickNumber,worldInstance.getEntityInstanceManager());
                            action.executeAction(context);
                        }
                    }
                }

                primaryEntityInstance.moveEntityInWorld();
                entitiesToKill.addAll(currentEntitiesToKill);
            }

            }

        for(EntityInstance entityInstance : entitiesToKill){
            worldInstance.getEntityInstanceManager().killEntity(entityInstance.getId());
        }
        return entitiesToKill.size();
    }


    private void updateDtoSimulationProgressForUi(Integer seconds, Integer tick, String progressMassage,
                                                  Map<String, Integer> entitiesPopulationAfterSimulationRunning) {
        dtoSimulationProgressForUi = new DTOSimulationProgressForUiImpl(seconds, tick, progressMassage, entitiesPopulationAfterSimulationRunning);
    }
    @Override
    public DTOSimulationProgressForUi getDtoSimulationProgressForUi() {
        return dtoSimulationProgressForUi;
    }


    private int getNumOfTicksToRun(WorldDefinition worldDefinition) {
        return worldDefinition.getTerminationConditionsManager().getTerminationConditionsList().get(0).getTerminationCondition();
    }


    private int getNumOfSecondsToRun(WorldDefinition worldDefinition) {
        return worldDefinition.getTerminationConditionsManager().getTerminationConditionsList().get(1).getTerminationCondition();
    }


    private List<Rule> getActiveRules(int tickNumber, WorldDefinition worldDefinition) {
        List<Rule> activeRules = new ArrayList<>();

        for(Rule rule : worldDefinition.getRuleDefinitionManager().getDefinitions()){
            if(rule.getActivation().isActive(tickNumber)){
                activeRules.add(rule);
            }
        }
        return activeRules;
    }


    private List<EntityInstance> getAllEntityInstancesOfWorldInstance(WorldInstance worldInstance) {
        return worldInstance.getEntityInstanceManager().getInstances();
    }


}
