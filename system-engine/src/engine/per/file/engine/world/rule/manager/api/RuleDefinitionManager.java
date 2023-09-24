package engine.per.file.engine.world.rule.manager.api;


import engine.per.file.engine.world.rule.api.Rule;

import java.util.List;

public interface RuleDefinitionManager {
    void addRuleDefinition(Rule rule);
    List<Rule> getDefinitions();
}
