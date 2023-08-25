package dto.impl;

import dto.api.DTODefinitionsForUi;
import dto.definition.entity.api.EntityDefinitionDTO;
import dto.definition.rule.api.RuleDTO;
import dto.definition.termination.condition.manager.api.TerminationConditionsDTOManager;

import java.util.List;

public class DTODefinitionsForUiImpl implements DTODefinitionsForUi {

        private final List<EntityDefinitionDTO> entitiesDTO;
        private final List<RuleDTO> rulesDTO;
        private final TerminationConditionsDTOManager terminationConditionsDTOManager;


        public DTODefinitionsForUiImpl(List<EntityDefinitionDTO> entitiesDTO, List<RuleDTO> rulesDTO,
                                       TerminationConditionsDTOManager terminationConditionsDTOManager){
                this.entitiesDTO =entitiesDTO;
                this.rulesDTO =rulesDTO;
                this.terminationConditionsDTOManager = terminationConditionsDTOManager;
        }


        @Override
        public List<EntityDefinitionDTO> getEntitiesDTO() {
                return entitiesDTO;
        }

        @Override
        public List<RuleDTO> getRulesDTO() {
                return rulesDTO;
        }

        @Override
        public TerminationConditionsDTOManager getTerminationConditionsDTOManager() {
                return terminationConditionsDTOManager;
        }

}
