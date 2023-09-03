package system.engine.world.creation.api;

import system.engine.world.definition.entity.api.EntityDefinition;
import system.engine.world.definition.entity.manager.api.EntityDefinitionManager;
import system.engine.world.definition.entity.secondary.api.SecondaryEntityDefinition;
import system.engine.world.rule.action.api.Action;

import java.util.List;

public interface ActionCreation {

    Action createActionIncrease(EntityDefinition entityDefinition, SecondaryEntityDefinition secondaryEntityDefinition, String propertyName, String expressionStr);
    Action createActionDecrease(EntityDefinition entityDefinition,SecondaryEntityDefinition secondaryEntityDefinition, String propertyName, String expressionStr);

    Action createActionCalculationDivide(EntityDefinition entityDefinition,SecondaryEntityDefinition secondaryEntityDefinition, String resultPropertyName,
                                         String expressionStr1,String expressionStr2 );
    Action createActionCalculationMultiply(EntityDefinition entityDefinition,SecondaryEntityDefinition secondaryEntityDefinition, String resultPropertyName,
                                           String expressionStr1,String expressionStr2);

    Action createActionSet(EntityDefinition entityDefinition,SecondaryEntityDefinition secondaryEntityDefinition, String propertyName, String expressionStr);
    Action createActionKill(EntityDefinition entityDefinition,SecondaryEntityDefinition secondaryEntityDefinition);
}
