package engine.per.file.engine.run.simulation.impl;

import dto.primary.DTOSimulationProgressForUi;
import engine.per.file.engine.run.simulation.api.RunSimulation;
import engine.per.file.engine.world.execution.instance.enitty.api.EntityInstance;
import engine.per.file.engine.world.execution.instance.environment.api.EnvVariablesInstanceManager;
import engine.per.file.engine.world.termination.condition.api.TerminationCondition;
import engine.per.file.engine.world.termination.condition.impl.ByUserTerminationConditionImpl;
import engine.per.file.engine.world.termination.condition.impl.TicksTerminationConditionImpl;
import engine.per.file.engine.world.termination.condition.impl.TimeTerminationConditionImpl;
import engine.per.file.engine.run.simulation.manager.IsSimulationPaused;
import engine.per.file.engine.world.api.WorldDefinition;
import engine.per.file.engine.world.api.WorldInstance;
import engine.per.file.engine.world.rule.action.api.Action;
import engine.per.file.engine.world.rule.action.api.ActionType;
import engine.per.file.engine.world.rule.api.Rule;
import engine.per.file.engine.world.rule.context.Context;
import engine.per.file.engine.world.rule.context.ContextImpl;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RunSimulationImpl implements RunSimulation {
    private dto.primary.DTOSimulationProgressForUi dtoSimulationProgressForUi;

    private final IsSimulationPaused isSimulationPaused;
    private final List<TerminationCondition> terminationConditionList;

   private boolean isPaused = false;
    private boolean isResumed = true;
    private boolean isCanceled = false;

    public RunSimulationImpl(WorldInstance worldInstance, List<TerminationCondition> terminationConditionList){
        dtoSimulationProgressForUi = new DTOSimulationProgressForUi(0, 0 ,"Running!",
                worldInstance.getEntityInstanceManager().getEntitiesPopulationAfterSimulationRunning());
        isSimulationPaused = new IsSimulationPaused();
        this.terminationConditionList = terminationConditionList;
    }
    @Override
    public void setCanceled(boolean canceled) {
        isCanceled = canceled;
    }
    @Override
    public void setPaused(boolean paused) {
        isPaused = paused;
    }
    @Override
    public boolean getPaused() {
        return isPaused ;
    }


    @Override
    public IsSimulationPaused getIsSimulationPaused() {
        return isSimulationPaused;
    }

    @Override
    public int[] runSimulationOnLastWorldInstance(WorldDefinition worldDefinition, WorldInstance worldInstance)
            throws IllegalArgumentException {

        EnvVariablesInstanceManager envVariablesInstanceManager = worldInstance.getEnvVariablesInstanceManager();

        int entitiesLeft = worldInstance.getEntityInstanceManager().getInstances().size();
        Instant startTime = Instant.now();
        Instant endTime;
        //Instant previousEndTime = endTime;
        Duration duration;
        int tick = 0;
        int secondsRun = 0;
        int secondsWait = 0;
        Instant startTimeWait = Instant.now();
        Instant endTimeWait;
        int numOfTicksToRun = 0;
        int numOfSecondsToRun = 0;
        boolean errorHappened = false;

        /*if(!isTerminationConditionByUser(worldDefinition)) {
            numOfTicksToRun = getNumOfTicksToRun(worldDefinition);
            numOfSecondsToRun = getNumOfSecondsToRun(worldDefinition);
        }*/
        if(!isTerminationConditionByUser(worldDefinition)){
            if(terminationConditionList.size()==2){
                numOfTicksToRun = getNumOfTicksToRun(worldDefinition);
                numOfSecondsToRun = getNumOfSecondsToRun(worldDefinition);
            }
            else{
                if(terminationConditionList.get(0) instanceof TimeTerminationConditionImpl){
                    numOfSecondsToRun = terminationConditionList.get(0).getTerminationCondition();
                    numOfTicksToRun=Integer.MAX_VALUE;
                }
                else if(terminationConditionList.get(0) instanceof TicksTerminationConditionImpl){
                    numOfTicksToRun= terminationConditionList.get(0).getTerminationCondition();
                    numOfSecondsToRun=Integer.MAX_VALUE;
                }
            }
        }

        String progressMassage ;

        List<Action> actionsList = new ArrayList<>();
        List<EntityInstance> entitiesToKill = new ArrayList<>();

        try{
            while(isTerminationConditionByUser(worldDefinition) || (tick <= numOfTicksToRun && secondsRun <= numOfSecondsToRun) ) {

                if(isPaused) {
                    startTimeWait = Instant.now();
                    isResumed  =false;
                    progressMassage = "Paused!";
                    updateDtoSimulationProgressForUi(secondsRun, tick, progressMassage,
                            worldInstance.getEntityInstanceManager().getEntitiesPopulationAfterSimulationRunning());
                    isSimulationPaused.pause();
                }

                while (!isPaused) {
                    if(!isResumed) {
                        endTimeWait = Instant.now();
                        duration = Duration.between(startTimeWait, endTimeWait);
                        secondsWait += (int) duration.getSeconds();
                        isResumed = true;
                    }

                    progressMassage = "Running!";
                    updateDtoSimulationProgressForUi(secondsRun, tick, progressMassage,
                            worldInstance.getEntityInstanceManager().getEntitiesPopulationAfterSimulationRunning());

                    if(isCanceled){
                        if(isTerminationConditionByUser(worldDefinition)){
                            progressMassage = "Done!";
                        }
                        else{
                            progressMassage = "Canceled!";
                        }
                        updateDtoSimulationProgressForUi(secondsRun, tick, progressMassage,
                                worldInstance.getEntityInstanceManager().getEntitiesPopulationAfterSimulationRunning());
                        break;
                    }
                    entitiesToKill.clear();
                    actionsList.clear();
                    for (Rule rule : getActiveRules(tick, worldDefinition)) {
                        actionsList.addAll(rule.getActionsToPerform());
                    }

                    entitiesLeft = runAllActionsOnAllEntities(worldInstance, envVariablesInstanceManager, actionsList, entitiesToKill, tick);
                    updateDtoSimulationProgressForUi(secondsRun, tick, progressMassage,
                            worldInstance.getEntityInstanceManager().getEntitiesPopulationAfterSimulationRunning());

                    tick++;
                    worldInstance.getEntityInstanceManager().setNumOfEntitiesLestByTicks(tick,entitiesLeft);
                    endTime = Instant.now();
                    duration = Duration.between(startTime, endTime);
                    secondsRun = (int) duration.getSeconds() - secondsWait;

                    if(!isTerminationConditionByUser(worldDefinition)  && !(tick <= numOfTicksToRun && secondsRun <= numOfSecondsToRun)){
                        break;
                    }
                }
                if(isCanceled){
                    if(isTerminationConditionByUser(worldDefinition)){
                        progressMassage = "Done!";
                    }
                    else{
                        progressMassage = "Canceled!";
                    }
                    updateDtoSimulationProgressForUi(secondsRun, tick, progressMassage,
                            worldInstance.getEntityInstanceManager().getEntitiesPopulationAfterSimulationRunning());
                    break;
                }
            }
        }
        catch (Exception e) {
            progressMassage = "terminated because of an error!";
            updateDtoSimulationProgressForUi(secondsRun, tick, progressMassage,
                    worldInstance.getEntityInstanceManager().getEntitiesPopulationAfterSimulationRunning());
            errorHappened  = true;
        }

        int[] terminationCausePair=new int[3];
        terminationCausePair[0]=tick;
        terminationCausePair[1]=secondsRun;

        if(isCanceled) {terminationCausePair[2]=2;} //by user
        else if(errorHappened){terminationCausePair[2]=3;} //error
        else{
            if(tick>numOfTicksToRun){terminationCausePair[2]=0;} //last tick
            else {terminationCausePair[2]=1;} //time ran out
            progressMassage = "Done!";
            if(secondsRun > 0 && tick >0){
                updateDtoSimulationProgressForUi(secondsRun-1, tick-1, progressMassage,
                        worldInstance.getEntityInstanceManager().getEntitiesPopulationAfterSimulationRunning());
            }
            else{
                if(secondsRun == 0  && tick >0){
                    updateDtoSimulationProgressForUi(secondsRun, tick-1, progressMassage,
                            worldInstance.getEntityInstanceManager().getEntitiesPopulationAfterSimulationRunning());
                }
                else{
                    updateDtoSimulationProgressForUi(secondsRun-1, tick, progressMassage,
                            worldInstance.getEntityInstanceManager().getEntitiesPopulationAfterSimulationRunning());
                }
            }
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
        List<EntityInstance> primaryEntityInstanceList = new ArrayList<>();
        primaryEntityInstanceList.addAll(getAllEntityInstancesOfWorldInstance(worldInstance));

        for(EntityInstance primaryEntityInstance : primaryEntityInstanceList){
            if(primaryEntityInstance!=null){
                currentEntitiesToKill.clear();
                for(Action action : actionsList){
                    //the entity instance is from the type of primary entity of the action
                    if(action.getContextPrimaryEntity().getUniqueName().equals(primaryEntityInstance.getEntityDefinition().getUniqueName())){
                        Context context ;
                        //there is second entity
                        if(action.getSecondaryEntityDefinition() != null){
                            List<EntityInstance> chosenSecondaryEntities=action.getSecondaryEntityDefinition().generateSecondaryEntityList(worldInstance,envVariablesInstanceManager, tickNumber,entitiesToKill);
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
                    addNewEntitiesToKillList(entitiesToKill, currentEntitiesToKill);
                }
                if (primaryEntityInstance.getRow() != null) {
                    primaryEntityInstance.moveEntityInWorld();
                }

                //entitiesToKill.addAll(currentEntitiesToKill);
                primaryEntityInstance.createConsistencyMapInSingleEntityInstance();
            }

        }

        for(EntityInstance entityInstance : entitiesToKill){
            worldInstance.getEntityInstanceManager().killEntity(entityInstance.getId());
        }
        return worldInstance.getEntityInstanceManager().getWorldPopulation();
    }

    private void addNewEntitiesToKillList(List<EntityInstance> entitiesToKill, List<EntityInstance> currentEntitiesToKill){

        for(EntityInstance currentEntityInstance : currentEntitiesToKill){//for every new entity to kill
            boolean alreadyInList = false;
            List<EntityInstance> entitiesToKillCopy = new ArrayList<>();
            entitiesToKillCopy.addAll(entitiesToKill);
            for(EntityInstance entityInstance : entitiesToKillCopy){  //for all entities that already need to kill
                if(entityInstance.getId() == currentEntityInstance.getId()){
                    alreadyInList = true;
                    break;
                }
            }
            if(!alreadyInList){
                entitiesToKill.add(currentEntityInstance);
            }
        }
    }

    private void updateDtoSimulationProgressForUi(Integer seconds, Integer tick, String progressMassage,
                                                  Map<String, Integer> entitiesPopulationAfterSimulationRunning) {
        dtoSimulationProgressForUi = new DTOSimulationProgressForUi(seconds, tick, progressMassage, entitiesPopulationAfterSimulationRunning);
    }
    @Override
    public dto.primary.DTOSimulationProgressForUi getDtoSimulationProgressForUi() {
        return dtoSimulationProgressForUi;
    }


    private int getNumOfTicksToRun(WorldDefinition worldDefinition) {
        if(terminationConditionList.get(0) instanceof TicksTerminationConditionImpl)
            return terminationConditionList.get(0).getTerminationCondition();

        return 0;
    }


    private int getNumOfSecondsToRun(WorldDefinition worldDefinition) {
        if(terminationConditionList.get(0) instanceof TicksTerminationConditionImpl){
            if(terminationConditionList.size() == 2){
                return terminationConditionList.get(1).getTerminationCondition();
            }
        }
        else{
            return terminationConditionList.get(0).getTerminationCondition();
        }
        return 0;
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

    private boolean isTerminationConditionByUser(WorldDefinition worldDefinition){
        return (terminationConditionList.get(0)
                instanceof ByUserTerminationConditionImpl);
    }


}
