package engine.per.file.engine.world.api;

import engine.per.file.engine.world.definition.entity.manager.api.EntityDefinitionManager;
import engine.per.file.engine.world.definition.environment.variable.api.EnvVariablesDefinitionManager;
import engine.per.file.engine.world.execution.instance.environment.api.EnvVariablesInstanceManager;
import engine.per.file.engine.world.termination.condition.manager.api.TerminationConditionsManager;
import engine.per.file.engine.world.impl.WorldInstanceImpl;
import engine.per.file.engine.world.rule.manager.api.RuleDefinitionManager;

import java.util.Map;

public interface WorldDefinition {
    WorldInstanceImpl createWorldInstance(int id, EnvVariablesInstanceManager envVariablesInstanceManager, EntityDefinitionManager entityDefinitionManager);

    EntityDefinitionManager getEntityDefinitionManager();

    EnvVariablesDefinitionManager getEnvVariablesDefinitionManager();
    RuleDefinitionManager getRuleDefinitionManager();


    EntityDefinitionManager createNewEntitiesDefinitionsManagerWithPopulations(Map<String, Integer> entityNameDefToPopulation);
    Integer getGridRows();
    Integer getGridColumns();

}
