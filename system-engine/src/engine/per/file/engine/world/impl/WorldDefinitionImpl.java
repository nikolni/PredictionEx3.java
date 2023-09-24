package engine.per.file.engine.world.impl;

import engine.per.file.engine.world.api.WorldDefinition;
import engine.per.file.engine.world.definition.entity.manager.api.EntityDefinitionManager;
import engine.per.file.engine.world.definition.environment.variable.api.EnvVariablesDefinitionManager;
import engine.per.file.engine.world.execution.instance.environment.api.EnvVariablesInstanceManager;
import engine.per.file.engine.world.grid.api.WorldGrid;
import engine.per.file.engine.world.grid.impl.WorldGridImpl;
import engine.per.file.engine.world.termination.condition.manager.api.TerminationConditionsManager;
import engine.per.file.engine.world.rule.manager.api.RuleDefinitionManager;

import java.util.Map;

public class WorldDefinitionImpl implements WorldDefinition {
    private final EntityDefinitionManager entityDefinitionManager;
    private final EnvVariablesDefinitionManager envVariablesDefinitionManager;
    private final RuleDefinitionManager ruleDefinitionManager;
    private final TerminationConditionsManager terminationConditionsManager;
    private final WorldGrid worldGrid;


    private final int threadPoolSize;

    public WorldDefinitionImpl(EntityDefinitionManager entityDefinitionManager,
                               EnvVariablesDefinitionManager envVariablesDefinitionManager,
                               RuleDefinitionManager ruleDefinitionManager,
                               TerminationConditionsManager terminationConditionsManager,
                               WorldGrid worldGrid,int threadPoolSize){
        this.entityDefinitionManager = entityDefinitionManager;
        this.envVariablesDefinitionManager = envVariablesDefinitionManager;
        this.ruleDefinitionManager = ruleDefinitionManager;
        this.terminationConditionsManager = terminationConditionsManager;
        this.worldGrid = worldGrid;
        this.threadPoolSize=threadPoolSize;
    }

    public EntityDefinitionManager getEntityDefinitionManager() {
        return entityDefinitionManager;
    }

    public EnvVariablesDefinitionManager getEnvVariablesDefinitionManager() {
        return envVariablesDefinitionManager;
    }

    public RuleDefinitionManager getRuleDefinitionManager(){return ruleDefinitionManager;}

    public TerminationConditionsManager getTerminationConditionsManager(){return terminationConditionsManager;}

    @Override
    public WorldInstanceImpl createWorldInstance(int id, EnvVariablesInstanceManager envVariablesInstanceManager, EntityDefinitionManager entityDefinitionManager) {
        return new WorldInstanceImpl(this, id, new WorldGridImpl((worldGrid.getGridRows()), worldGrid.getGridColumns()), envVariablesInstanceManager, entityDefinitionManager);
    }

    @Override
    public EntityDefinitionManager createNewEntitiesDefinitionsManagerWithPopulations(Map<String, Integer> entityNameDefToPopulation){
        EntityDefinitionManager NewEntityDefinitionManager = entityDefinitionManager.copyFromMe();
        for (String key : entityNameDefToPopulation.keySet()) {
            if(entityNameDefToPopulation.get(key) != null){
                NewEntityDefinitionManager.getEntityDefinitionByName(key).setPopulation(entityNameDefToPopulation.get(key));
            }
        }
        return NewEntityDefinitionManager;
    }

    @Override
    public Integer getGridRows() {
        return worldGrid.getGridRows();
    }

    @Override
    public Integer getGridColumns() {
        return worldGrid.getGridColumns();
    }
    @Override
    public int getThreadPoolSize() {
        return threadPoolSize;
    }


}
