package system.engine.world.api;

import system.engine.world.definition.entity.manager.api.EntityDefinitionManager;
import system.engine.world.definition.environment.variable.api.EnvVariablesDefinitionManager;
import system.engine.world.execution.instance.environment.api.EnvVariablesInstanceManager;
import system.engine.world.impl.WorldInstanceImpl;
import system.engine.world.rule.manager.api.RuleDefinitionManager;
import system.engine.world.termination.condition.manager.api.TerminationConditionsManager;

import java.util.Map;

public interface WorldDefinition {
    WorldInstanceImpl createWorldInstance(int id, EnvVariablesInstanceManager envVariablesInstanceManager, EntityDefinitionManager entityDefinitionManager);

    EntityDefinitionManager getEntityDefinitionManager();

    EnvVariablesDefinitionManager getEnvVariablesDefinitionManager();
    RuleDefinitionManager getRuleDefinitionManager();

    TerminationConditionsManager getTerminationConditionsManager();
    EntityDefinitionManager createNewEntitiesDefinitionsManagerWithPopulations(Map<String, Integer> entityNameDefToPopulation);
    Integer getGridRows();
    Integer getGridColumns();
    int getThreadPoolSize();
}
