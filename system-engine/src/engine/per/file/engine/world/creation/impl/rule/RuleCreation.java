package engine.per.file.engine.world.creation.impl.rule;

import engine.per.file.engine.world.rule.api.Rule;
import engine.per.file.engine.world.rule.impl.RuleImpl;

public class RuleCreation {
    private Rule rule;

    public RuleCreation(String ruleName) {
        this.rule = new RuleImpl(ruleName);
    }

    public Rule getRule() {
        return rule;
    }
}
