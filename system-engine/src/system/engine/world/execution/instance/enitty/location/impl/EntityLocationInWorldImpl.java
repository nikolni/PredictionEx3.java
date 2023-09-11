package system.engine.world.execution.instance.enitty.location.impl;

import system.engine.world.execution.instance.enitty.api.EntityInstance;
import system.engine.world.execution.instance.enitty.location.api.AbstractEntityLocationInWorld;
import system.engine.world.execution.instance.enitty.location.api.EntityLocationInWorld;
import system.engine.world.grid.api.WorldGrid;

public class EntityLocationInWorldImpl extends AbstractEntityLocationInWorld implements EntityLocationInWorld {
    private final WorldGrid worldGrid;
    private final EntityInstance entityInstance;


    private Integer row;
    private Integer column;


    public EntityLocationInWorldImpl(WorldGrid worldGrid, EntityInstance entityInstance) {
        this.worldGrid =worldGrid;
        this.entityInstance =entityInstance;
        lotteryPosition();
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
