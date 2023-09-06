package system.engine.world.grid.impl;

import system.engine.world.execution.instance.enitty.location.api.EntityLocationInWorld;
import system.engine.world.grid.api.WorldGrid;

public final class WorldGridImpl implements WorldGrid {
    private final Integer gridRows;
    private final Integer gridColumns;
    private final Integer[][] worldGrid;


    public WorldGridImpl(Integer gridRows, Integer gridColumns) {
        this.gridRows = gridRows;
        this.gridColumns = gridColumns;

        this.worldGrid = new Integer[gridRows][gridColumns];
        for (int i = 0; i < gridRows; i++) {
            for (int j = 0; j < gridColumns; j++) {
                worldGrid[i][j] = 0;
            }
        }
    }

    @Override
    public Integer getGridRows() {
        return gridRows;
    }
    @Override
    public Integer getGridColumns() {
        return gridColumns;
    }
    @Override
    public void setPosition(Integer row, Integer column){
        worldGrid[row][column] = 1;
    }
    @Override
    public Boolean isPositionAvailable(Integer row, Integer column){
        return worldGrid[row][column] == 0;
    }
    @Override
    public Boolean isPositionAvailableForMovingEntity(EntityLocationInWorld currentEntityLocationInWorld,
                                                      EntityLocationInWorld desirableEntityLocationInWorld){
        //column change
        if(currentEntityLocationInWorld.getRow().equals(desirableEntityLocationInWorld.getRow())){
            if(desirableEntityLocationInWorld.getColumn().equals(-1) ){
                if(isPositionAvailable(desirableEntityLocationInWorld.getRow(), gridColumns-1)){
                    desirableEntityLocationInWorld.setColumn(gridColumns-1);
                    return true;
                }
                return false;
            }
            else if (desirableEntityLocationInWorld.getColumn().equals(gridColumns)){
                if(isPositionAvailable(desirableEntityLocationInWorld.getRow(), 0)){
                    desirableEntityLocationInWorld.setColumn(0);
                    return true;
                }
                return false;
            }
            else{
                return isPositionAvailable(desirableEntityLocationInWorld.getRow(), desirableEntityLocationInWorld.getColumn());
            }
        }
        //row change
        else{
            if(desirableEntityLocationInWorld.getRow().equals(-1) ){
                if(isPositionAvailable(gridRows-1, desirableEntityLocationInWorld.getColumn())){
                    desirableEntityLocationInWorld.setRow(gridRows-1);
                    return true;
                }
                return false;
            }
            else if (desirableEntityLocationInWorld.getRow().equals(gridRows)){
                if(isPositionAvailable(0, desirableEntityLocationInWorld.getColumn())){
                    desirableEntityLocationInWorld.setRow(0);
                    return true;
                }
                return false;
            }
            else{
                return isPositionAvailable(desirableEntityLocationInWorld.getRow(), desirableEntityLocationInWorld.getColumn());
            }
        }
    }
}
