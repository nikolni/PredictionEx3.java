package system.engine.run.simulation.impl;

import dto.api.DTOSimulationProgressForUi;
import dto.impl.DTOSimulationProgressForUiImpl;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import system.engine.run.simulation.SimulationCallback;
import system.engine.run.simulation.api.RunSimulation;
import system.engine.world.api.WorldDefinition;
import system.engine.world.api.WorldInstance;
import system.engine.world.execution.instance.enitty.api.EntityInstance;
import system.engine.world.execution.instance.enitty.manager.api.EntityInstanceManager;
import system.engine.world.execution.instance.environment.api.EnvVariablesInstanceManager;
import system.engine.world.rule.action.api.Action;
import system.engine.world.rule.api.Rule;
import system.engine.world.rule.context.Context;
import system.engine.world.rule.context.ContextImpl;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class RunSimulationImpl implements RunSimulation {

    private SimulationCallback callback;
    @Override
    public void registerCallback(SimulationCallback callback) {
        this.callback = callback;
    }


    @Override
    public String runSimulationOnLastWorldInstance(WorldDefinition worldDefinition, WorldInstance worldInstance,
                                                 EnvVariablesInstanceManager envVariablesInstanceManager,
                                                   SimpleBooleanProperty isPaused)
                                                    throws IllegalArgumentException{
        int entitiesLeft = worldInstance.getEntityInstanceManager().getInstances().size();
        Instant startTime = Instant.now();
        Instant endTime;
        Duration duration;
        int tick = 0;
        int seconds = 0;
        int numOfTicksToRun = getNumOfTicksToRun(worldDefinition);
        int numOfSecondsToRun = getNumOfSecondsToRun(worldDefinition);

        List<Action> actionsList = new ArrayList<>();
        List<EntityInstance> entitiesToKill = new ArrayList<>();


        while(tick <= numOfTicksToRun && seconds <= numOfSecondsToRun) {
                //while (tick<= numOfTicksToRun && seconds<=numOfSecondsToRun){
                while (!isPaused.get()) {
                    entitiesToKill.clear();
                    actionsList.clear();
                    for (Rule rule : getActiveRules(tick, worldDefinition)) {
                        actionsList.addAll(rule.getActionsToPerform());
                    }

                    entitiesLeft -= runAllActionsOnAllEntities(worldInstance, envVariablesInstanceManager, actionsList, entitiesToKill);

                    tick++;
                    endTime = Instant.now();
                    duration = Duration.between(startTime, endTime);
                    seconds = (int) duration.getSeconds();

                    // Notify the UI about progress and status via the callback
                    if (callback != null) {
                        DTOSimulationProgressForUi dtoSimulationProgressForUi = new DTOSimulationProgressForUiImpl(seconds, tick, entitiesLeft);
                        callback.onUpdate(dtoSimulationProgressForUi);
                    }
                }
        }

        if(tick>numOfTicksToRun){return "last tick";}
        else {return "time run out";}
    }

    private int runAllActionsOnAllEntities(WorldInstance worldInstance, EnvVariablesInstanceManager envVariablesInstanceManager,
                                            List<Action> actionsList, List<EntityInstance> entitiesToKill){

        List<EntityInstance> currentEntitiesToKill = new ArrayList<>();
        for(EntityInstance entityInstance : getAllEntityInstancesOfWorldInstance(worldInstance)){
            currentEntitiesToKill.clear();
            if(entityInstance != null){
                Context context = new ContextImpl(entityInstance, envVariablesInstanceManager, currentEntitiesToKill);
                actionsList.forEach(action -> action.executeAction(context));
                entitiesToKill.addAll(currentEntitiesToKill);
            }
        }
        for(EntityInstance entityInstance : entitiesToKill){
            worldInstance.getEntityInstanceManager().killEntity(entityInstance.getId());
        }
        return entitiesToKill.size();
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


    private EntityInstanceManager getEntityInstanceManagerOfWorldInstance(WorldInstance worldInstance) {
        return worldInstance.getEntityInstanceManager();
    }
}
