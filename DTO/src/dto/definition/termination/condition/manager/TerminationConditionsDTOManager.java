package dto.definition.termination.condition.manager;

import dto.definition.termination.condition.api.TerminationConditionsDTO;

import java.util.List;

public class TerminationConditionsDTOManager implements TerminationConditionsDTOManager {
    private List<TerminationConditionsDTO> terminationConditionsDTOList;

    public TerminationConditionsDTOManager(List<TerminationConditionsDTO> terminationConditionsDTOList){
        this.terminationConditionsDTOList = terminationConditionsDTOList;
    }
    @Override
    public List<TerminationConditionsDTO> getTerminationConditionsDTOList(){
        return terminationConditionsDTOList;
    }


}
