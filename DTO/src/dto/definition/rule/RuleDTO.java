package dto.definition.rule;

import dto.definition.rule.action.api.AbstractActionDTO;
import dto.definition.rule.activation.ActivationDTO;

import java.util.List;

public class RuleDTO{
    private final String name;
    private final List<AbstractActionDTO> actionDTOS;
    private ActivationDTO activation;
    private final List<String> actionNames;

    public RuleDTO(String name, List<String> actionNames, List<AbstractActionDTO> actionDTOS, ActivationDTO activation) {
        this.name = name;
        this.actionDTOS = actionDTOS;
        this.actionNames =actionNames;
        this.activation = activation;
    }


    public String getName() {
        return name;
    }

    public ActivationDTO getActivation() {
        return activation;
    }

    public int getNumOfActions() {
        return actionDTOS.size();
    }

    public List<AbstractActionDTO> getActions() {
        return actionDTOS;
    }

    public List<String> getActionsNames() {
        return actionNames;
    }
}
