package ui.impl;


import dto.api.DTODefinitionsForUi;
import dto.definition.entity.api.EntityDefinitionDTO;
import dto.definition.property.definition.api.PropertyDefinitionDTO;
import dto.definition.rule.api.RuleDTO;
import dto.definition.termination.condition.api.TerminationConditionsDTO;
import dto.definition.termination.condition.impl.TicksTerminationConditionsDTOImpl;
import dto.definition.termination.condition.manager.api.TerminationConditionsDTOManager;
import system.engine.api.SystemEngineAccess;
import ui.api.MenuExecution;

import java.util.List;

public class Menu2 implements MenuExecution {
    @Override
    public void executeUserChoice(SystemEngineAccess systemEngine) {
        showSimulationDetails(systemEngine);
    }

    public void showSimulationDetails(SystemEngineAccess systemEngineAccess){
        DTODefinitionsForUi dtoDefinitionsForUi = systemEngineAccess.getDefinitionsDataFromSE();
        List<EntityDefinitionDTO> entities = dtoDefinitionsForUi.getEntitiesDTO();
        List<RuleDTO> rules = dtoDefinitionsForUi.getRulesDTO();
        TerminationConditionsDTOManager terminationConditionsDTOManager = dtoDefinitionsForUi.getTerminationConditionsDTOManager();

        printEntitiesData(entities);
        printRulesData(rules);
        printTerminationConditionsData(terminationConditionsDTOManager);
    }

    private void printEntitiesData(List<EntityDefinitionDTO> entities){
        int countEntities = 0;
        int countProperties = 0;

        System.out.println("\nEntities:");
        for(EntityDefinitionDTO entityDefinitionDTO : entities){
            countEntities++;
            System.out.println("#" + countEntities + " name: " + entityDefinitionDTO.getUniqueName());
            System.out.println("  " + " population: " + entityDefinitionDTO.getPopulation());
            System.out.println("  " + " properties:");
            List<PropertyDefinitionDTO> properties = entityDefinitionDTO.getProps();
            printPropertiesData(properties);
        }
    }

    private void printPropertiesData(List<PropertyDefinitionDTO> properties){
        int countProperties = 0;

        for(PropertyDefinitionDTO propertyDefinitionDTO : properties){
            countProperties++;
            System.out.println("   #" + countProperties + " name: " + propertyDefinitionDTO.getUniqueName());
            System.out.println("     " + " type: " + propertyDefinitionDTO.getType());
            System.out.println("     " + " random initialize: " + propertyDefinitionDTO.isRandomInitialized());
            System.out.println("     " + (propertyDefinitionDTO.doesHaveRange() ? " range: from " +
                    propertyDefinitionDTO.getRange().get(0) + " to " + propertyDefinitionDTO.getRange().get(1) : " no range"));
        }
    }

    private void printRulesData(List<RuleDTO> rules){
        int countRules = 0;
        int countActions = 0;

        System.out.println("Rules:");
        for(RuleDTO ruleDTO : rules){
            countRules++;
            System.out.println("#" + countRules + " name: " + ruleDTO.getName());
            System.out.println("  " + " active every " + ruleDTO.getActivation().getTicks() +
                    "ticks with probability of: " + ruleDTO.getActivation().getProbability());
            System.out.println("  " + " number of actions: " + ruleDTO.getNumOfActions());
            System.out.println("  " + " actions names:");
            countActions = 0;
            for(String actionName : ruleDTO.getActionsNames()){
                countActions++;
                System.out.println("   #" + countActions + " " + actionName);
            }
        }
    }

    private void printTerminationConditionsData(TerminationConditionsDTOManager terminationConditionsDTOManager){
        int countTerminationConditions = 0;

        System.out.println("Termination conditions:");
        for(TerminationConditionsDTO terminationConditionsDTO : terminationConditionsDTOManager.getTerminationConditionsDTOList()){
            countTerminationConditions++;
            System.out.println("#" + countTerminationConditions + (terminationConditionsDTO instanceof TicksTerminationConditionsDTOImpl?
                    " after " + terminationConditionsDTO.getTerminationCondition() + " ticks" : " after " +
                    terminationConditionsDTO.getTerminationCondition() + " seconds"));
        }
    }


}
