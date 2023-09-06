package system.engine.world.api;

import system.engine.world.definition.entity.manager.api.EntityDefinitionManager;
import system.engine.world.definition.environment.variable.api.EnvVariablesDefinitionManager;
import system.engine.world.impl.WorldInstanceImpl;
import system.engine.world.rule.manager.api.RuleDefinitionManager;
import system.engine.world.termination.condition.manager.api.TerminationConditionsManager;

import java.util.Map;

public interface WorldDefinition {
    WorldInstance createWorldInstance(int id);

    EntityDefinitionManager getEntityDefinitionManager();

    EnvVariablesDefinitionManager getEnvVariablesDefinitionManager();
    RuleDefinitionManager getRuleDefinitionManager();

    TerminationConditionsManager getTerminationConditionsManager();
    void addPopulationToEntitiesDefinition(Map<String, Integer> entityNameDefToPopulation);
    Integer getGridRows();
    Integer getGridColumns();
}
