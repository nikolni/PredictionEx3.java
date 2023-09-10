package system.engine.world.grid.impl;

import system.engine.world.execution.instance.enitty.api.EntityInstance;
import system.engine.world.execution.instance.enitty.location.api.EntityLocationInWorld;
import system.engine.world.grid.api.WorldGrid;

public final class WorldGridImpl implements WorldGrid {
    private final Integer gridRows;
    private final Integer gridColumns;
    private final EntityInstance[][] worldGrid;


    public WorldGridImpl(Integer gridRows, Integer gridColumns) {
        this.gridRows = gridRows;
        this.gridColumns = gridColumns;

        this.worldGrid = new EntityInstance[gridRows][gridColumns];
        for (int i = 0; i < gridRows; i++) {
            for (int j = 0; j < gridColumns; j++) {
                worldGrid[i][j] = null;
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
    public void setPosition(Integer row, Integer column, EntityInstance entityInstance){
        worldGrid[row][column] = entityInstance;
    }
    @Override
    public Boolean isPositionAvailable(Integer row, Integer column){
        return worldGrid[row][column] == null;
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

    @Override
    public EntityInstance isThereSecondEntityCloseEnough(EntityInstance primaryEntityInstance, String targetEntityName, Float of){
        int centerX = primaryEntityInstance.getColumns();
        int centerY = primaryEntityInstance.getRow();
        Float radius = of;
        EntityInstance entityInstance =null;

        for (int row = 0; row < gridRows; row++) {
            for (int col = 0; col < gridColumns; col++) {
                if(row == centerY & col == centerX){
                    continue;
                }
                // Calculate the distance from the current square to the center point.
                int distance = (int) Math.sqrt((row - centerY) * (row - centerY) + (col - centerX) * (col - centerX));

                // Check if the square is at the specified distance from the center point.
                if (Math.abs(distance - radius) < 0.5 || isAtEdge(centerY, centerX, gridRows, gridColumns)) {
                    if(worldGrid[row][col] != null && worldGrid[row][col].getEntityDefinition().getUniqueName().equals(targetEntityName)){
                        entityInstance = worldGrid[row][col];
                        return entityInstance;
                    }
                }
            }
        }
        return entityInstance;
    }

        private boolean isAtEdge(int row, int col, int numRows, int numCols) {
            return row == 0 || row == numRows - 1 || col == 0 || col == numCols - 1;
        }
}
