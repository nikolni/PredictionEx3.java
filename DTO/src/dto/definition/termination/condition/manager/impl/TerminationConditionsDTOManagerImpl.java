package dto.definition.termination.condition.manager.impl;

import dto.definition.termination.condition.api.TerminationConditionsDTO;
import dto.definition.termination.condition.manager.api.TerminationConditionsDTOManager;

import java.util.List;

public class TerminationConditionsDTOManagerImpl implements TerminationConditionsDTOManager {
    private List<TerminationConditionsDTO> terminationConditionsDTOList;

    public TerminationConditionsDTOManagerImpl (List<TerminationConditionsDTO> terminationConditionsDTOList){
        this.terminationConditionsDTOList = terminationConditionsDTOList;
    }
    @Override
    public List<TerminationConditionsDTO> getTerminationConditionsDTOList(){
        return terminationConditionsDTOList;
    }


}
