package engine.per.file.engine.world.execution.instance.enitty.location.impl;

import engine.per.file.engine.world.execution.instance.enitty.api.EntityInstance;
import engine.per.file.engine.world.execution.instance.enitty.location.api.AbstractEntityLocationInWorld;
import engine.per.file.engine.world.execution.instance.enitty.location.api.EntityLocationInWorld;
import engine.per.file.engine.world.grid.api.WorldGrid;

public class EntityLocationInWorldImpl extends AbstractEntityLocationInWorld implements EntityLocationInWorld {
    private final WorldGrid worldGrid;
    private final EntityInstance entityInstance;


    private Integer row = null;
    private Integer column= null;


    public EntityLocationInWorldImpl(WorldGrid worldGrid, EntityInstance entityInstance) {
        this.worldGrid =worldGrid;
        this.entityInstance =entityInstance;
        if(worldGrid.getNumOfLocationsLeft() > 0){
            lotteryPosition();
        }
    }

    private void lotteryPosition() {
        row= random.nextInt(worldGrid.getGridRows()-1);
        column= random.nextInt(worldGrid.getGridColumns()-1);

        while (!worldGrid.isPositionAvailable(row, column)){
            row= random.nextInt(worldGrid.getGridRows());
            column= random.nextInt(worldGrid.getGridColumns());
        }
        worldGrid.setPosition(row, column, entityInstance);
    }
    @Override
    public Integer getRow() {
        return row;
    }
    @Override
    public Integer getColumn() {
        return column;
    }
    @Override
    public void setRow(Integer row) {
        this.row = row;
    }
    @Override
    public void setColumn(Integer column) {
        this.column = column;
    }
}
