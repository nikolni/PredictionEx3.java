package system.engine.world.impl;

import system.engine.world.api.WorldDefinition;
import system.engine.world.definition.entity.manager.api.EntityDefinitionManager;
import system.engine.world.definition.environment.variable.api.EnvVariablesDefinitionManager;
import system.engine.world.rule.manager.api.RuleDefinitionManager;
import system.engine.world.termination.condition.manager.api.TerminationConditionsManager;

public class WorldDefinitionImpl implements WorldDefinition {
    private EntityDefinitionManager entityDefinitionManager;
    private EnvVariablesDefinitionManager envVariablesDefinitionManager;
    private RuleDefinitionManager ruleDefinitionManager;
    private TerminationConditionsManager terminationConditionsManager;

    public WorldDefinitionImpl(EntityDefinitionManager entityDefinitionManager,
                               EnvVariablesDefinitionManager envVariablesDefinitionManager,
                               RuleDefinitionManager ruleDefinitionManager,
                               TerminationConditionsManager terminationConditionsManager){
        this.entityDefinitionManager = entityDefinitionManager;
        this.envVariablesDefinitionManager = envVariablesDefinitionManager;
        this.ruleDefinitionManager = ruleDefinitionManager;
        this.terminationConditionsManager = terminationConditionsManager;
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
    public WorldInstanceImpl createWorldInstance(int id) {
        return new WorldInstanceImpl(this, id);
    }
}
