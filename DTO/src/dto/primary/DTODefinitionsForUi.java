package dto.primary;

import dto.definition.entity.EntityDefinitionDTO;
import dto.definition.property.definition.PropertyDefinitionDTO;
import dto.definition.rule.RuleDTO;
import dto.definition.termination.condition.manager.TerminationConditionsDTOManager;

import java.util.List;

public class DTODefinitionsForUi{

        private final List<EntityDefinitionDTO> entitiesDTO;
        private final List<RuleDTO> rulesDTO;
        //private final TerminationConditionsDTOManager terminationConditionsDTOManager;


        public DTODefinitionsForUi(List<EntityDefinitionDTO> entitiesDTO, List<RuleDTO> rulesDTO){
                this.entitiesDTO =entitiesDTO;
                this.rulesDTO =rulesDTO;
                //this.terminationConditionsDTOManager = terminationConditionsDTOManager;
        }

        public List<EntityDefinitionDTO> getEntitiesDTO() {
                return entitiesDTO;
        }

        public List<RuleDTO> getRulesDTO() {
                return rulesDTO;
        }

       /* public TerminationConditionsDTOManager getTerminationConditionsDTOManager() {
                return terminationConditionsDTOManager;
        }*/

        public PropertyDefinitionDTO getPropertyDefinitionByName(String EntityName,String propertyName){
                for(EntityDefinitionDTO entityDefinitionDTO:entitiesDTO){
                        if(entityDefinitionDTO.getUniqueName().equals(EntityName)){
                                for(PropertyDefinitionDTO propertyDefinitionDTO:entityDefinitionDTO.getProps()){
                                        if(propertyDefinitionDTO.getUniqueName().equals(propertyName))
                                                return propertyDefinitionDTO;
                                }
                        }
                }
              return null;
        }

}
