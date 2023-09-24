package engine.per.file.engine.world.rule.impl;

import engine.per.file.engine.world.rule.api.Rule;
import engine.per.file.engine.world.rule.action.api.Action;
import engine.per.file.engine.world.rule.activation.api.Activation;
import engine.per.file.engine.world.rule.activation.impl.ActivationImpl;

import java.util.ArrayList;
import java.util.List;

public class RuleImpl implements Rule {
    private final String name;
    private final List<Action> actions;
    private Activation activation;

    public RuleImpl(String name) {
        this.name = name;
        actions = new ArrayList<>();
        activation = new ActivationImpl();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Activation getActivation() {
        return activation;
    }

    public void setActivation(Activation activation) {
        this.activation = activation;
    }

    @Override
    public List<Action> getActionsToPerform() {
        return actions;
    }

    @Override
    public void addAction(Action action) {
        actions.add(action);
    }

    @Override
    public int getNumOfActions() {
        return actions.size();
    }

    @Override
    public List<String> getActionsNames() {
        List<String> names = new ArrayList<>();
        for(Action action: actions){
            names.add((action.getActionType()).toString());
        }
        return names;
    }

}
