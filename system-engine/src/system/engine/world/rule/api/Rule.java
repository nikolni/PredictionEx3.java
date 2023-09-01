package system.engine.world.rule.api;

import system.engine.world.rule.action.api.Action;
import system.engine.world.rule.activation.api.Activation;

import java.util.List;

public interface Rule {
    String getName();
    Activation getActivation();
    List<Action> getActionsToPerform();
    void addAction(Action action);
    int getNumOfActions();
    void setActivation(Activation activation);
    List<String> getActionsNames();
}
