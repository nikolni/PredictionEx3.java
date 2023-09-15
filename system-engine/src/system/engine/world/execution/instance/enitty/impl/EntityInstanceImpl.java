package system.engine.world.execution.instance.enitty.impl;

import system.engine.world.definition.entity.api.EntityDefinition;
import system.engine.world.execution.instance.enitty.api.EntityInstance;
import system.engine.world.execution.instance.enitty.location.api.AbstractEntityLocationInWorld;
import system.engine.world.execution.instance.enitty.location.api.EntityLocationInWorld;
import system.engine.world.execution.instance.enitty.location.impl.EntityLocationInWorldImpl;
import system.engine.world.execution.instance.property.api.PropertyInstance;
import system.engine.world.grid.api.WorldDirections;
import system.engine.world.grid.api.WorldGrid;


import java.util.HashMap;
import java.util.Map;

public class EntityInstanceImpl extends AbstractEntityLocationInWorld implements EntityInstance {

    private final EntityDefinition entityDefinition;
    private final int id;
    private final Map<String, PropertyInstance> properties;
    private final Map<String, Float> propertiesConsistency;

    private final EntityLocationInWorld entityLocationInWorld;
    private final WorldGrid worldGrid;

    public EntityInstanceImpl(EntityDefinition entityDefinition, int id, WorldGrid worldGrid) {
        this.entityDefinition = entityDefinition;
        this.id = id;
        properties = new HashMap<>();
        propertiesConsistency=new HashMap<>();
        entityLocationInWorld  = new EntityLocationInWorldImpl(worldGrid, this);
        this.worldGrid = worldGrid;
    }

    @Override
    public EntityDefinition getEntityDefinition(){
        return entityDefinition;
    }
    @Override
    public int getId() {
        return id;
    }

    @Override
    public PropertyInstance getPropertyByName(String name) {
        if (!properties.containsKey(name)) {
            return null;
        }

        return properties.get(name);
    }
    @Override
    public void createConsistencyMapInSingleEntityInstance(){
        for (Map.Entry<String, PropertyInstance> entry : properties.entrySet())
           propertiesConsistency.put(entry.getKey(),entry.getValue().calculatePropertyAverage());
    }

    @Override
    public Float getPropertyConsistencyByName(String PropertyName){
       return propertiesConsistency.get(PropertyName);
    }

    @Override
    public void addPropertyInstance(PropertyInstance propertyInstance) {
        properties.put(propertyInstance.getPropertyDefinition().getUniqueName(), propertyInstance);
    }

    @Override
    public Integer getRow() {
        return entityLocationInWorld.getRow();
    }
    @Override
    public Integer getColumns() {
        return entityLocationInWorld.getColumn();
    }

    @Override
    public void setRow(Integer newRow) {
        entityLocationInWorld.setRow(newRow);
    }

    @Override
    public void setColumns(Integer newColumns) {
        entityLocationInWorld.setColumn(newColumns);
    }

    @Override
    public void moveEntityInWorld() {
        int[] directionArr = {0,0,0,0};

        EntityLocationInWorld desirableEntityLocationInWorld = new EntityLocationInWorldImpl(worldGrid, this);
        if(desirableEntityLocationInWorld.getRow() != null){
            worldGrid.setPosition(desirableEntityLocationInWorld.getRow(), desirableEntityLocationInWorld.getColumn(), null);
        }

        Boolean stillLooking = true;

        while (stillLooking & isDirectionsLeft(directionArr)) {

            WorldDirections worldDirections = lotteryDirection(directionArr);

            switch (worldDirections) {
                case FORWARD:
                    directionArr[0] = 1;
                    desirableEntityLocationInWorld.setRow(entityLocationInWorld.getRow() - 1);
                    desirableEntityLocationInWorld.setColumn(entityLocationInWorld.getColumn());
                    break;
                case RIGHT:
                    directionArr[1] = 1;
                    desirableEntityLocationInWorld.setRow(entityLocationInWorld.getRow());
                    desirableEntityLocationInWorld.setColumn(entityLocationInWorld.getColumn() + 1);
                    break;
                case BACK:
                    directionArr[2] = 1;
                    desirableEntityLocationInWorld.setRow(entityLocationInWorld.getRow() + 1);
                    desirableEntityLocationInWorld.setColumn(entityLocationInWorld.getColumn());
                    break;
                case LEFT:
                    directionArr[3] = 1;
                    desirableEntityLocationInWorld.setRow(entityLocationInWorld.getRow());
                    desirableEntityLocationInWorld.setColumn(entityLocationInWorld.getColumn() - 1);
                    break;
            }

            if (worldGrid.isPositionAvailableForMovingEntity(entityLocationInWorld, desirableEntityLocationInWorld)) {
                worldGrid.setPosition(entityLocationInWorld.getRow(), entityLocationInWorld.getColumn(), null);
                entityLocationInWorld.setRow(desirableEntityLocationInWorld.getRow());
                entityLocationInWorld.setColumn(desirableEntityLocationInWorld.getColumn());
                worldGrid.setPosition(entityLocationInWorld.getRow(), entityLocationInWorld.getColumn(), this);

                stillLooking = false;
            }
        }

    }

    private Boolean isDirectionsLeft(int[] directionArr){
        for(int i = 0; i < directionArr.length; i++){
            if(directionArr[i] == 0){
                return true;
            }
        }
        return false;
    }

    private WorldDirections lotteryDirection(int[] directionArr){
        WorldDirections worldDirections = null;
        Boolean found = false;
        int direction;

        while(!found){
            direction =random.nextInt(4);
            if(directionArr[direction] == 0){
                worldDirections = WorldDirections.intToEnum(direction);
                found= true;
            }
        }
        return worldDirections;
    }
}
