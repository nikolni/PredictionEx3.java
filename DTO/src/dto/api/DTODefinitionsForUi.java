package dto.api;

import dto.definition.entity.api.EntityDefinitionDTO;
import dto.definition.rule.api.RuleDTO;
import dto.definition.termination.condition.manager.api.TerminationConditionsDTOManager;

import java.util.List;

public interface DTODefinitionsForUi {

     List<EntityDefinitionDTO> getEntitiesDTO();
     List<RuleDTO> getRulesDTO();
     TerminationConditionsDTOManager getTerminationConditionsDTOManager();

}
