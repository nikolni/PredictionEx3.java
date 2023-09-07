package dto.api;

import dto.definition.entity.api.EntityDefinitionDTO;
import dto.definition.property.definition.api.PropertyDefinitionDTO;
import dto.definition.rule.api.RuleDTO;
import dto.definition.termination.condition.manager.api.TerminationConditionsDTOManager;

import java.util.List;

public interface DTODefinitionsForUi {

     List<EntityDefinitionDTO> getEntitiesDTO();
     List<RuleDTO> getRulesDTO();
     TerminationConditionsDTOManager getTerminationConditionsDTOManager();
     PropertyDefinitionDTO getPropertyDefinitionByName(String EntityName, String propertyName);

}
