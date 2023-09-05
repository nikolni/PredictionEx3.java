package system.engine.world.definition.entity.secondary.impl;

import system.engine.world.api.WorldInstance;
import system.engine.world.definition.entity.api.EntityDefinition;
import system.engine.world.definition.entity.impl.EntityDefinitionImpl;
import system.engine.world.definition.entity.secondary.api.SecondaryEntityDefinition;
import system.engine.world.execution.instance.enitty.api.EntityInstance;
import system.engine.world.execution.instance.environment.api.EnvVariablesInstanceManager;
import system.engine.world.rule.action.impl.condition.ConditionAction;
import system.engine.world.rule.context.Context;
import system.engine.world.rule.context.ContextImpl;


import java.util.Random;
import java.util.*;

public class SecondaryEntityDefinitionImpl implements SecondaryEntityDefinition {
    private EntityDefinition extendsEntityDefinition;
    private String count;
    private ConditionAction selectionCondition;
    public SecondaryEntityDefinitionImpl(EntityDefinition extendsEntityDefinition,String count,ConditionAction selectionCondition) {
       this.extendsEntityDefinition=extendsEntityDefinition;
        this.count=count;
        this.selectionCondition=selectionCondition;
    }
    @Override
    public EntityDefinition getExtendsEntityDefinition() {
        return extendsEntityDefinition;
    }
    @Override
    public String getSecEntityCount() {
        return count;
    }
    @Override
    public ConditionAction getSelectionCondition() {
        return selectionCondition;
    }

    @Override
    public List<EntityInstance> generateSecondaryEntityList(WorldInstance worldInstance, EnvVariablesInstanceManager envVariablesInstanceManager) {

        List<EntityInstance> allEntityInstancesOfDefinition = worldInstance.getEntityInstanceManager().getEntityInstanceByEntityDef().get(extendsEntityDefinition.getUniqueName());
        if (count.equals("ALL"))
            return allEntityInstancesOfDefinition;
        else {
            List<EntityInstance> chosenEntities = new ArrayList<>();
            int countIndex = Integer.parseInt(count);
            if (selectionCondition == null) {
                if (countIndex > allEntityInstancesOfDefinition.size())
                    countIndex = allEntityInstancesOfDefinition.size();
                Random random = new Random();
                for (int i = 0; i < countIndex; i++) {
                    int randomIndex = random.nextInt(allEntityInstancesOfDefinition.size());
                    chosenEntities.add(allEntityInstancesOfDefinition.get(randomIndex));
                }
            }
            else {
                for(EntityInstance checkedEntityInstance: allEntityInstancesOfDefinition){
                    Context context = new ContextImpl(checkedEntityInstance,null, envVariablesInstanceManager, null);
                    if(selectionCondition.isConditionFulfilled(context))
                        chosenEntities.add(checkedEntityInstance);
                }
                if(countIndex<chosenEntities.size()){
                    List<EntityInstance> randomChosenEntities = new ArrayList<>();
                    Random random = new Random();
                    for (int i = 0; i < countIndex; i++) {
                        int randomIndex = random.nextInt(chosenEntities.size());
                        randomChosenEntities.add(chosenEntities.get(randomIndex));
                }
                    return randomChosenEntities;
            }
        }
            return chosenEntities;
    }
    }

}
