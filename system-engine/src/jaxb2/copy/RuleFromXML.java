package jaxb2.copy;

import jaxb2.generated.*;
import system.engine.world.creation.api.ActionCreation;
import system.engine.world.creation.impl.rule.RuleCreation;
import system.engine.world.creation.impl.rule.action.ActionCreationImpl;
import system.engine.world.definition.entity.api.EntityDefinition;
import system.engine.world.definition.entity.manager.api.EntityDefinitionManager;
import system.engine.world.definition.entity.secondary.api.SecondaryEntityDefinition;
import system.engine.world.definition.entity.secondary.impl.SecondaryEntityDefinitionImpl;
import system.engine.world.rule.action.api.Action;
import system.engine.world.rule.action.impl.condition.ConditionAction;
import system.engine.world.rule.action.impl.condition.MultipleConditionAction;
import system.engine.world.rule.action.impl.condition.SingleConditionAction;
import system.engine.world.rule.activation.api.Activation;
import system.engine.world.rule.activation.impl.ActivationImpl;
import system.engine.world.rule.api.Rule;
import system.engine.world.rule.manager.api.RuleDefinitionManager;
import system.engine.world.rule.manager.impl.RuleDefinitionManagerImpl;

public class RuleFromXML {
    private RuleDefinitionManager ruleDefinitionManager=new RuleDefinitionManagerImpl();

    public RuleFromXML(PRDRules prdRules, EntityDefinitionManager entityDefinitionManager) {
        for(PRDRule prdRule:prdRules.getPRDRule()){
            RuleCreation ruleCreation=new RuleCreation(prdRule.getName());
            if(prdRule.getPRDActivation()!=null)
                setRuleActivation(prdRule.getPRDActivation(),ruleCreation.getRule());
            for(PRDAction prdAction:prdRule.getPRDActions().getPRDAction()){
                ruleCreation.getRule().addAction(createAction(entityDefinitionManager,prdAction.getEntity(),prdAction.getPRDSecondaryEntity(),prdAction));
            }
            ruleDefinitionManager.addRuleDefinition(ruleCreation.getRule());
        }
    }

    public void setRuleActivation(PRDActivation prdActivation, Rule rule){
        Activation activation=new ActivationImpl();
        if(prdActivation.getProbability()!=null&&prdActivation.getTicks()==null)
            activation.setProbability((float)prdActivation.getProbability().doubleValue());
        else if(prdActivation.getProbability()==null&&prdActivation.getTicks()!=null)
            activation.setTicks(prdActivation.getTicks());
        else{
            activation.setProbability((float)prdActivation.getProbability().doubleValue());
            activation.setTicks(prdActivation.getTicks());
        }
        rule.setActivation(activation);

    }

    public SecondaryEntityDefinition createSecondaryEntity(EntityDefinitionManager entityDefinitionManager, PRDAction.PRDSecondaryEntity prdSecondaryEntity)
    {
        EntityDefinition extendEntityDefinition= entityDefinitionManager.getEntityDefinitionByName(prdSecondaryEntity.getEntity());
        ConditionAction selectionCondition=createConditionActionFromPRDCondition(entityDefinitionManager,extendEntityDefinition,null,prdSecondaryEntity.getPRDSelection().getPRDCondition());
        SecondaryEntityDefinition secondaryEntityDefinition=new SecondaryEntityDefinitionImpl(extendEntityDefinition,prdSecondaryEntity.getPRDSelection().getCount(),selectionCondition);
        return secondaryEntityDefinition;
    }

    public Action createAction(EntityDefinitionManager entityDefinitionManager, String primaryEntityDefinitionName, PRDAction.PRDSecondaryEntity prdSecondaryEntity, PRDAction prdAction){
        ActionCreation actionCreation=new ActionCreationImpl();
        EntityDefinition primaryEntityDefinition= entityDefinitionManager.getEntityDefinitionByName(primaryEntityDefinitionName);
        SecondaryEntityDefinition secondaryEntityDefinition=null;
        if(prdSecondaryEntity!=null)
            secondaryEntityDefinition=createSecondaryEntity(entityDefinitionManager,prdSecondaryEntity);


        switch (prdAction.getType()){
            case "increase":
                String propertyName1 = prdAction.getProperty();
                String expressionStr1 = prdAction.getBy();
                return actionCreation.createActionIncrease(primaryEntityDefinition,secondaryEntityDefinition, propertyName1, expressionStr1);
            case "decrease":
                String propertyName2 = prdAction.getProperty();
                String expressionStr2 = prdAction.getBy();
                return actionCreation.createActionDecrease(primaryEntityDefinition,secondaryEntityDefinition, propertyName2, expressionStr2);
            case "calculation":
                if(prdAction.getPRDMultiply()!=null)
                    return actionCreation.createActionCalculationMultiply(primaryEntityDefinition,secondaryEntityDefinition,prdAction.getResultProp(),prdAction.getPRDMultiply().getArg1(),prdAction.getPRDMultiply().getArg2());
                if(prdAction.getPRDDivide()!=null)
                    return actionCreation.createActionCalculationDivide(primaryEntityDefinition,secondaryEntityDefinition,prdAction.getResultProp(),prdAction.getPRDDivide().getArg1(),prdAction.getPRDDivide().getArg2());
            case "condition":
                ConditionAction conditionAction=createConditionActionFromPRDCondition(entityDefinitionManager,primaryEntityDefinition,secondaryEntityDefinition,prdAction.getPRDCondition());
                for(PRDAction thenPrdAction:prdAction.getPRDThen().getPRDAction())
                    conditionAction.addActionToThenList(createAction(entityDefinitionManager,thenPrdAction.getEntity(),thenPrdAction.getPRDSecondaryEntity(),thenPrdAction));
                if(prdAction.getPRDElse()!=null){
                    for(PRDAction elsePrdAction:prdAction.getPRDElse().getPRDAction())
                        conditionAction.addActionToElseList(createAction(entityDefinitionManager,elsePrdAction.getEntity(),elsePrdAction.getPRDSecondaryEntity(),elsePrdAction));
                }
                return conditionAction;
            case "set":
                String propertyName3 = prdAction.getProperty();
                String expressionStr3 = prdAction.getValue();
                return actionCreation.createActionSet(primaryEntityDefinition,secondaryEntityDefinition, propertyName3, expressionStr3);
            case "kill":
                return actionCreation.createActionKill(primaryEntityDefinition,secondaryEntityDefinition);

        }
        return null;
    }

    public ConditionAction createConditionActionFromPRDCondition(EntityDefinitionManager entityDefinitionManager, EntityDefinition mainEntityDefinition,SecondaryEntityDefinition secondaryEntityDefinition, PRDCondition prdCondition) {
        switch (prdCondition.getSingularity()) {
            case "single":
                return createActionConditionSingle(entityDefinitionManager, mainEntityDefinition,secondaryEntityDefinition,prdCondition.getEntity(),prdCondition.getProperty(),prdCondition.getOperator(),prdCondition.getValue());
            case "multiple":
                MultipleConditionAction multipleConditionAction=createActionConditionMultiple(mainEntityDefinition,secondaryEntityDefinition,prdCondition.getLogical());
                for(PRDCondition prdCondition1:prdCondition.getPRDCondition()){
                    multipleConditionAction.addConditionToConditionsCollection(createConditionActionFromPRDCondition(entityDefinitionManager,mainEntityDefinition,secondaryEntityDefinition,prdCondition1));
                }
                return multipleConditionAction;
        }
        return null;
    }

    public SingleConditionAction createActionConditionSingle(EntityDefinitionManager entityDefinitionManager, EntityDefinition mainEntityDefinition,SecondaryEntityDefinition secondaryEntityDefinition, String entityDefinition2, String propertyName,
                                                             String operator, String expressionStr){
        EntityDefinition innerEntityDefinition = entityDefinitionManager.getEntityDefinitionByName(entityDefinition2);
        return new SingleConditionAction("single",mainEntityDefinition,secondaryEntityDefinition, innerEntityDefinition, propertyName, operator, expressionStr);
    }

    public MultipleConditionAction createActionConditionMultiple(EntityDefinition mainEntityDefinition,SecondaryEntityDefinition secondaryEntityDefinition, String logical){
        return new MultipleConditionAction("multiple",mainEntityDefinition,secondaryEntityDefinition, logical);
    }

    public RuleDefinitionManager getRuleDefinitionManager() {
        return ruleDefinitionManager;
    }
}
