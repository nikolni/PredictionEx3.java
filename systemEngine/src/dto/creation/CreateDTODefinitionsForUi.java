package dto.creation;

import dto.api.DTODefinitionsForUi;
import dto.definition.entity.api.EntityDefinitionDTO;
import dto.definition.entity.impl.EntityDefinitionDTOImpl;
import dto.definition.property.definition.api.PropertyDefinitionDTO;
import dto.definition.property.definition.impl.PropertyDefinitionDTOImpl;
import dto.definition.rule.action.KillActionDTO;
import dto.definition.rule.action.SetActionDTO;
import dto.definition.rule.action.api.AbstractActionDTO;
import dto.definition.rule.action.condition.ConditionActionDTO;
import dto.definition.rule.action.numeric.DecreaseActionDTO;
import dto.definition.rule.action.numeric.IncreaseActionDTO;
import dto.definition.rule.action.numeric.calculation.DivideActionDTO;
import dto.definition.rule.action.numeric.calculation.MultiplyActionDTO;
import dto.definition.rule.activation.impl.ActivationDTOImpl;
import dto.definition.rule.api.RuleDTO;
import dto.definition.rule.impl.RuleDTOImpl;
import dto.definition.termination.condition.api.TerminationConditionsDTO;
import dto.definition.termination.condition.impl.ByUserTerminationConditionDTOImpl;
import dto.definition.termination.condition.impl.TicksTerminationConditionsDTOImpl;
import dto.definition.termination.condition.impl.TimeTerminationConditionsDTOImpl;
import dto.definition.termination.condition.manager.api.TerminationConditionsDTOManager;
import dto.definition.termination.condition.manager.impl.TerminationConditionsDTOManagerImpl;
import dto.impl.DTODefinitionsForUiImpl;
import system.engine.world.api.WorldDefinition;
import system.engine.world.definition.entity.api.EntityDefinition;
import system.engine.world.definition.property.api.PropertyDefinition;
import system.engine.world.rule.action.api.*;
import system.engine.world.rule.action.impl.KillAction;
import system.engine.world.rule.action.impl.SetAction;
import system.engine.world.rule.action.impl.condition.ConditionAction;
import system.engine.world.rule.action.impl.numeric.impl.DecreaseAction;
import system.engine.world.rule.action.impl.numeric.impl.IncreaseAction;
import system.engine.world.rule.action.impl.numeric.impl.calculation.DivideAction;
import system.engine.world.rule.action.impl.numeric.impl.calculation.MultiplyAction;
import system.engine.world.rule.api.Rule;
import system.engine.world.termination.condition.api.TerminationCondition;
import system.engine.world.termination.condition.impl.TicksTerminationConditionImpl;
import system.engine.world.termination.condition.impl.TimeTerminationConditionImpl;
import system.engine.world.termination.condition.manager.api.TerminationConditionsManager;

import java.util.ArrayList;
import java.util.List;

public class CreateDTODefinitionsForUi {

    public DTODefinitionsForUi getData(WorldDefinition worldDefinition) {
        List<EntityDefinitionDTO> entitiesDTO = new ArrayList<>();
        List<RuleDTO> rulesDTO = new ArrayList<>();
        List<TerminationConditionsDTO> terminationConditionsDTO= new ArrayList<>();

        List<EntityDefinition> entities = worldDefinition.getEntityDefinitionManager().getDefinitions();
        for(EntityDefinition entityDefinition: entities){
            entitiesDTO.add(createEntityDefinitionDTO(entityDefinition));
        }

        List<Rule> rules = worldDefinition.getRuleDefinitionManager().getDefinitions();
        for(Rule rule: rules){
            rulesDTO.add(new RuleDTOImpl(rule.getName(), rule.getActionsNames(), createActionsDTOs(rule.getActionsToPerform()),
                    new ActivationDTOImpl(rule.getActivation().getTicks(), rule.getActivation().getProbability())));
        }

        TerminationConditionsManager terminationConditionsManager = worldDefinition.getTerminationConditionsManager();
        for(TerminationCondition terminationCondition : terminationConditionsManager.getTerminationConditionsList()){
            terminationConditionsDTO.add(createTerminationConditionsDTO(terminationCondition));
        }
        TerminationConditionsDTOManager terminationConditionsDTOManager = new TerminationConditionsDTOManagerImpl(terminationConditionsDTO);

        return new DTODefinitionsForUiImpl(entitiesDTO, rulesDTO, terminationConditionsDTOManager);
    }

    private EntityDefinitionDTO createEntityDefinitionDTO(EntityDefinition entityDefinition){
        List<PropertyDefinitionDTO> propertiesDTO = new ArrayList<>();
        for(PropertyDefinition propertyDefinition: entityDefinition.getProps()){
            propertiesDTO.add(createPropertyDefinitionDTO(propertyDefinition));
        }
        return new EntityDefinitionDTOImpl(entityDefinition.getUniqueName(), entityDefinition.getPopulation(), propertiesDTO);
    }

    private PropertyDefinitionDTO createPropertyDefinitionDTO(PropertyDefinition propertyDefinition){
        return  new PropertyDefinitionDTOImpl(propertyDefinition.getUniqueName(), propertyDefinition.getType().toString(),
                propertyDefinition.isRandomInitialized(), propertyDefinition.doesHaveRange(), propertyDefinition.getRange());
    }

    private List<AbstractActionDTO> createActionsDTOs (List<Action> actionList){
        List<AbstractActionDTO> actionsDTOs = new ArrayList<>();
        for(Action action: actionList){

            switch (action.getActionType()){
                case INCREASE:
                    IncreaseAction increaseAction = (IncreaseAction)action;
                    actionsDTOs.add(new IncreaseActionDTO(increaseAction.getContextPrimaryEntity().getUniqueName(),
                            increaseAction.getPropertyName(), increaseAction.getExpressionStr()));
                    break;
                case DECREASE:
                    DecreaseAction decreaseAction = (DecreaseAction)action;
                    actionsDTOs.add(new DecreaseActionDTO(decreaseAction.getContextPrimaryEntity().getUniqueName(),
                            decreaseAction.getPropertyName(), decreaseAction.getExpressionStr()));
                    break;
                case CALCULATION:
                    if(action instanceof DivideAction) {
                        DivideAction divideAction = (DivideAction) action;
                        actionsDTOs.add(new DivideActionDTO(divideAction.getContextPrimaryEntity().getUniqueName(),
                                divideAction.getResultPropName(), divideAction.getExpressionStrArg1(), divideAction.getExpressionStrArg2()));
                    }
                    else{
                        MultiplyAction multiplyAction = (MultiplyAction) action;
                        actionsDTOs.add(new MultiplyActionDTO(multiplyAction.getContextPrimaryEntity().getUniqueName(),
                                multiplyAction.getResultPropName(), multiplyAction.getExpressionStrArg1(), multiplyAction.getExpressionStrArg2()));
                    }
                    break;
                case CONDITION:
                    ConditionAction conditionAction = (ConditionAction) action;
                    actionsDTOs.add(new ConditionActionDTO(conditionAction.getSingularity(),
                            conditionAction.getContextPrimaryEntity().getUniqueName()));
                    break;
                case SET:
                    SetAction setAction= (SetAction) action;
                    actionsDTOs.add(new SetActionDTO(setAction.getContextPrimaryEntity().getUniqueName(),
                            setAction.getPropertyName(), setAction.getExpressionStr()));
                    break;
                case KILL:
                    KillAction killAction = (KillAction)action;
                    actionsDTOs.add(new KillActionDTO(killAction.getContextPrimaryEntity().getUniqueName()));
                    break;
            }
        }
        return actionsDTOs;
    }

    private TerminationConditionsDTO createTerminationConditionsDTO (TerminationCondition terminationCondition){
        if(terminationCondition instanceof TicksTerminationConditionImpl){
            return new TicksTerminationConditionsDTOImpl(terminationCondition.getTerminationCondition());
        }
        else if(terminationCondition instanceof TimeTerminationConditionImpl){
            return new TimeTerminationConditionsDTOImpl(terminationCondition.getTerminationCondition());
        }
        else{
            return new ByUserTerminationConditionDTOImpl();
        }
    }

}
