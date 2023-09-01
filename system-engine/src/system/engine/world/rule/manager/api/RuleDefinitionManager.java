package system.engine.world.rule.manager.api;


import system.engine.world.rule.api.Rule;

import java.util.List;

public interface RuleDefinitionManager {
    void addRuleDefinition(Rule rule);
    List<Rule> getDefinitions();
}
