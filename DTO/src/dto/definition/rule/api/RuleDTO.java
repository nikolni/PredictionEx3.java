package dto.definition.rule.api;

import dto.definition.rule.action.api.AbstractActionDTO;
import dto.definition.rule.activation.api.ActivationDTO;

import java.util.List;

public interface RuleDTO {
    String getName();
    ActivationDTO getActivation();
    int getNumOfActions();
    List<AbstractActionDTO> getActions();

    public List<String> getActionsNames();
}
