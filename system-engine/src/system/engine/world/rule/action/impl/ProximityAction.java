package system.engine.world.rule.action.impl;

import system.engine.world.definition.entity.api.EntityDefinition;
import system.engine.world.definition.entity.secondary.api.SecondaryEntityDefinition;
import system.engine.world.execution.instance.enitty.api.EntityInstance;
import system.engine.world.grid.api.WorldGrid;
import system.engine.world.rule.action.api.AbstractAction;
import system.engine.world.rule.action.api.Action;
import system.engine.world.rule.action.api.ActionType;
import system.engine.world.rule.context.Context;

import java.util.ArrayList;
import java.util.List;

public class ProximityAction extends AbstractAction {

    private final Integer of;
    private final List<Action> actionsCollection;
    private final WorldGrid worldGrid;

    public ProximityAction(EntityDefinition entityDefinitionParam, SecondaryEntityDefinition secondaryEntityDefinition,
                           String of, WorldGrid worldGrid) {
        super(ActionType.PROXIMITY,entityDefinitionParam,secondaryEntityDefinition);
        this.of = Integer.parseInt(of);
        this.actionsCollection= new ArrayList<>();
        this.worldGrid = worldGrid;
    }

    @Override
    public void executeAction(Context context) {
        EntityInstance primaryEntityInstance = context.getPrimaryEntityInstance();
        EntityInstance secondEntityInstance = null;
        if(isThereSecondEntityThatCloseEnough(primaryEntityInstance, secondEntityInstance)){
            context.setSecondEntityInstance(secondEntityInstance);
            for(Action action : actionsCollection){
                action.executeAction(context);
            }
        }
    }

    private Boolean isThereSecondEntityThatCloseEnough(EntityInstance primaryEntityInstance, EntityInstance secondEntityInstance){
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
}
