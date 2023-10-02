package dto.definition.termination.condition.manager;

import dto.definition.termination.condition.api.TerminationConditionsDTO;

import java.util.List;

public class TerminationConditionsDTOManager {
    private List<TerminationConditionsDTO> terminationConditionsDTOList;

    public TerminationConditionsDTOManager(List<TerminationConditionsDTO> terminationConditionsDTOList){
        this.terminationConditionsDTOList = terminationConditionsDTOList;
    }

    public List<TerminationConditionsDTO> getTerminationConditionsDTOList(){
        return terminationConditionsDTOList;
    }


}
