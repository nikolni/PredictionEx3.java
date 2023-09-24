package engine.per.file.engine.world.rule.manager.impl;

import engine.per.file.engine.world.rule.api.Rule;
import engine.per.file.engine.world.rule.manager.api.RuleDefinitionManager;

import java.util.ArrayList;
import java.util.List;

public class RuleDefinitionManagerImpl implements RuleDefinitionManager {
    private List<Rule> ruleDefinitions;

    public RuleDefinitionManagerImpl() {
        ruleDefinitions = new ArrayList<>();
    }

    @Override
    public void addRuleDefinition(Rule rule) {
        ruleDefinitions.add(rule);
    }

    @Override
    public List<Rule> getDefinitions() {
        return ruleDefinitions;
    }

}

