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

import java.util.ArrayList;
import java.util.List;

import static system.engine.world.rule.action.impl.numeric.api.NumericVerify.verifyNumericExpressionValue;

public class ProximityAction extends AbstractAction {

    private final String ofExp;
    private final List<Action> actionsCollection;
    private final WorldGrid worldGrid;

    public ProximityAction(EntityDefinition entityDefinitionParam, SecondaryEntityDefinition secondaryEntityDefinition,
                           String of, WorldGrid worldGrid) {
        super(ActionType.PROXIMITY,entityDefinitionParam,secondaryEntityDefinition);
        this.ofExp = of;
        this.actionsCollection= new ArrayList<>();
        this.worldGrid = worldGrid;
    }

    @Override
    public void executeAction(Context context) {
        ExpressionCreation expressionCreation = new ExpressionCreationImpl();
        Expression expression = expressionCreation.craeteExpression(ofExp, context.getPrimaryEntityInstance(),
                context.getSecondEntityInstance());
        if (!verifyNumericExpressionValue(expression, context)) {
            throw new IllegalArgumentException("proximity action can't operate with non numeric expression type");
        }
        Integer of=  (Integer) expression.evaluateExpression(context);

        EntityInstance primaryEntityInstance = context.getPrimaryEntityInstance();
        EntityInstance secondEntityInstance = null;
        if(isThereSecondEntityThatCloseEnough(primaryEntityInstance, secondEntityInstance, of)){
            context.setSecondEntityInstance(secondEntityInstance);
            for(Action action : actionsCollection){
                action.executeAction(context);
            }
        }
    }

    private Boolean isThereSecondEntityThatCloseEnough(EntityInstance primaryEntityInstance, EntityInstance secondEntityInstance,
                                                       Integer of){
        int currentOf=1;

        while(currentOf <= of){
            if(worldGrid.isThereSecondEntityCloseEnough(primaryEntityInstance, secondEntityInstance, currentOf)){
                break;
            }
            currentOf++;
        }
        return secondEntityInstance != null;
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
}
