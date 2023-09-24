package engine.per.file.engine.world.creation.api;

import engine.per.file.engine.world.definition.entity.api.EntityDefinition;
import engine.per.file.engine.world.definition.entity.secondary.api.SecondaryEntityDefinition;
import engine.per.file.engine.world.rule.action.api.Action;
import engine.per.file.engine.world.rule.action.impl.ProximityAction;

public interface ActionCreation {

    Action createActionIncrease(EntityDefinition entityDefinition, SecondaryEntityDefinition secondaryEntityDefinition, String propertyName, String expressionStr);
    Action createActionDecrease(EntityDefinition entityDefinition,SecondaryEntityDefinition secondaryEntityDefinition, String propertyName, String expressionStr);

    Action createActionCalculationDivide(EntityDefinition entityDefinition,SecondaryEntityDefinition secondaryEntityDefinition, String resultPropertyName,
                                         String expressionStr1,String expressionStr2 );
    Action createActionCalculationMultiply(EntityDefinition entityDefinition,SecondaryEntityDefinition secondaryEntityDefinition, String resultPropertyName,
                                           String expressionStr1,String expressionStr2);

    Action createActionSet(EntityDefinition entityDefinition,SecondaryEntityDefinition secondaryEntityDefinition, String propertyName, String expressionStr);
    Action createActionKill(EntityDefinition entityDefinition,SecondaryEntityDefinition secondaryEntityDefinition);
    Action createActionReplace(EntityDefinition primaryEntityDefinition, SecondaryEntityDefinition secondaryEntityDefinition,
                               EntityDefinition createEntityDefinition,String mode);
    ProximityAction createActionProximity(EntityDefinition entityDefinitionParam, SecondaryEntityDefinition secondaryEntityDefinition,
                                          String of, EntityDefinition targetEntityDefinition);

}
