package engine.per.file.engine.world.rule.action.impl;

import engine.per.file.engine.world.creation.impl.expression.ExpressionCreationImpl;
import engine.per.file.engine.world.definition.entity.api.EntityDefinition;
import engine.per.file.engine.world.definition.entity.secondary.api.SecondaryEntityDefinition;
import engine.per.file.engine.world.execution.instance.enitty.api.EntityInstance;
import engine.per.file.engine.world.grid.api.WorldGrid;
import engine.per.file.engine.world.rule.action.api.AbstractAction;
import engine.per.file.engine.world.rule.action.api.Action;
import engine.per.file.engine.world.rule.action.api.ActionType;
import engine.per.file.engine.world.creation.api.ExpressionCreation;
import engine.per.file.engine.world.rule.action.expression.api.Expression;
import engine.per.file.engine.world.rule.context.Context;

import java.util.ArrayList;
import java.util.List;

import static engine.per.file.engine.world.rule.action.impl.numeric.api.NumericVerify.verifyNumericExpressionValue;

public class ProximityAction extends AbstractAction {

    private final String ofExp;
    private final List<Action> actionsCollection;

    private final EntityDefinition targetEntityDefinition;

    public ProximityAction(EntityDefinition entityDefinitionParam, SecondaryEntityDefinition secondaryEntityDefinition,
                           String of ,EntityDefinition targetEntityDefinition) {
        super(ActionType.PROXIMITY,entityDefinitionParam,secondaryEntityDefinition);
        this.ofExp = of;
        this.actionsCollection= new ArrayList<>();
        this.targetEntityDefinition=targetEntityDefinition;
    }



    @Override
    public void executeAction(Context context) {
        WorldGrid worldGrid= context.getEntityInstanceManager().getWorldGrid();
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
        targetEntityInstance = isThereSecondEntityThatCloseEnough(primaryEntityInstance, targetEntityName, of, worldGrid);
        if(targetEntityInstance != null){
            for(Action action : actionsCollection){
                if(action instanceof ReplaceAction  || action instanceof ProximityAction){
                    context.setSecondEntityInstance(null);
                    action.executeAction(context);
                }
                else{
                    context.setSecondEntityInstance(targetEntityInstance);
                    action.executeAction(context);
                }
            }
        }
    }

    private EntityInstance isThereSecondEntityThatCloseEnough(EntityInstance primaryEntityInstance, String targetEntityName, Float of, WorldGrid worldGrid){
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
