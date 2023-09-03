package system.engine.world.definition.entity.secondary.impl;

import system.engine.world.definition.entity.api.EntityDefinition;
import system.engine.world.definition.entity.impl.EntityDefinitionImpl;
import system.engine.world.definition.entity.secondary.api.SecondaryEntityDefinition;
import system.engine.world.rule.action.impl.condition.ConditionAction;

public class SecondaryEntityDefinitionImpl implements SecondaryEntityDefinition {
    private EntityDefinition extendsEntityDefinition;
    private String count;
    private ConditionAction selectionCondition;
    public SecondaryEntityDefinitionImpl(EntityDefinition extendsEntityDefinition,String count,ConditionAction selectionCondition) {
       this.extendsEntityDefinition=extendsEntityDefinition;
        this.count=count;
        this.selectionCondition=selectionCondition;
    }

    public EntityDefinition getExtendsEntityDefinition() {
        return extendsEntityDefinition;
    }

    public String getSecEntityCount() {
        return count;
    }

    public ConditionAction getSelectionCondition() {
        return selectionCondition;
    }
}
