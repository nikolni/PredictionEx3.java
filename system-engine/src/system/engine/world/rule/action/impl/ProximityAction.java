package system.engine.world.rule.action.impl;

import system.engine.world.creation.api.ExpressionCreation;
import system.engine.world.creation.impl.expression.ExpressionCreationImpl;
import system.engine.world.definition.entity.api.EntityDefinition;
import system.engine.world.definition.entity.secondary.api.SecondaryEntityDefinition;
import system.engine.world.execution.instance.enitty.api.EntityInstance;
import system.engine.world.grid.api.WorldGrid;
import system.engine.world.rule.action.api.AbstractAction;
import system.engine.world.rule.action.api.Action;
import system.engine.world.rule.action.api.ActionType;
import system.engine.world.rule.action.expression.api.Expression;
import system.engine.world.rule.context.Context;
import system.engine.world.rule.context.ContextImpl;

import java.util.ArrayList;
import java.util.List;

import static system.engine.world.rule.action.impl.numeric.api.NumericVerify.verifyNumericExpressionValue;

public class ProximityAction extends AbstractAction {

    private final String ofExp;
    private final List<Action> actionsCollection;

    private final EntityDefinition targetEntityDefinition;
    private final WorldGrid worldGrid;

    public ProximityAction(EntityDefinition entityDefinitionParam, SecondaryEntityDefinition secondaryEntityDefinition,
                           String of, WorldGrid worldGrid,EntityDefinition targetEntityDefinition) {
        super(ActionType.PROXIMITY,entityDefinitionParam,secondaryEntityDefinition);
        this.ofExp = of;
        this.actionsCollection= new ArrayList<>();
        this.worldGrid = worldGrid;
        this.targetEntityDefinition=targetEntityDefinition;
    }



    @Override
    public void executeAction(Context context) {
        ExpressionCreation expressionCreation = new ExpressionCreationImpl();
        EntityInstance EntityInstanceSource=checkByDefinitionIfPrimaryOrSecondary(context);
        if(EntityInstanceSource==null) //cant execute the action
            return;

        Expression expression = expressionCreation.craeteExpression(ofExp, EntityInstanceSource);
        if (!verifyNumericExpressionValue(expression, context)) {
            throw new IllegalArgumentException("proximity action can't operate with non numeric expression type");
        }
        Float of =  Float.parseFloat(expression.evaluateExpression(context).toString());

        EntityInstance primaryEntityInstance = context.getPrimaryEntityInstance();
        EntityInstance targetEntityInstance;
        String targetEntityName = targetEntityDefinition.getUniqueName();
        targetEntityInstance = isThereSecondEntityThatCloseEnough(primaryEntityInstance, targetEntityName, of);
        if(targetEntityInstance != null){
            for(Action action : actionsCollection){
                if(action instanceof ReplaceAction  || action instanceof ProximityAction){
                    action.executeAction(context);
                }
                else{
                    Context newContext = new ContextImpl(context.getPrimaryEntityInstance(),targetEntityInstance,
                            context.getEnvVariablesInstanceManager(),context.getEntitiesToKill(),
                            context.getTickNumber(), context.getEntityInstanceManager());
                    action.executeAction(newContext);
                }
            }
        }
    }

    private EntityInstance isThereSecondEntityThatCloseEnough(EntityInstance primaryEntityInstance, String targetEntityName, Float of){
        Float currentOf=1f;
        EntityInstance entityInstance =null;

        while(currentOf <= of){
            entityInstance = worldGrid.isThereSecondEntityCloseEnough(primaryEntityInstance, targetEntityName, currentOf);
            if(entityInstance != null){
                break;
            }
            currentOf++;
        }
        return entityInstance;
    }

    public void addActionToActionsCollection(Action action){
        actionsCollection.add(action);
    }

    public String getOf() {
        return ofExp;
    }

    public Integer getActionsCollectionSize() {
        return actionsCollection.size();
    }
    public String getTargetEntityDefinitionName() {
        return targetEntityDefinition.getUniqueName();
    }
}
