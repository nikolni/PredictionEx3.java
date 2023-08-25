package dto.definition.rule.impl;

import dto.definition.rule.action.api.AbstractActionDTO;
import dto.definition.rule.activation.api.ActivationDTO;
import dto.definition.rule.api.RuleDTO;
import java.util.List;

public class RuleDTOImpl implements RuleDTO {
    private final String name;
    private final List<AbstractActionDTO> actionDTOS;
    private ActivationDTO activation;
    private final List<String> actionNames;

    public RuleDTOImpl(String name,List<String> actionNames, List<AbstractActionDTO> actionDTOS, ActivationDTO activation) {
        this.name = name;
        this.actionDTOS = actionDTOS;
        this.actionNames =actionNames;
        this.activation = activation;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public ActivationDTO getActivation() {
        return activation;
    }

    @Override
    public int getNumOfActions() {
        return actionDTOS.size();
    }

    @Override
    public List<AbstractActionDTO> getActions() {
        return actionDTOS;
    }

    @Override
    public List<String> getActionsNames() {
        return actionNames;
    }


}
