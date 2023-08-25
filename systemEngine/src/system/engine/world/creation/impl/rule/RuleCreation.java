package system.engine.world.creation.impl.rule;

import system.engine.world.rule.api.Rule;
import system.engine.world.rule.impl.RuleImpl;

public class RuleCreation {
    private Rule rule;

    public RuleCreation(String ruleName) {
        this.rule = new RuleImpl(ruleName);
    }

    public Rule getRule() {
        return rule;
    }
}
