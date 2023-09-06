package system.engine.world.execution.instance.enitty.location.impl;

import system.engine.world.execution.instance.enitty.location.api.AbstractEntityLocationInWorld;
import system.engine.world.execution.instance.enitty.location.api.EntityLocationInWorld;
import system.engine.world.grid.api.WorldGrid;

public class EntityLocationInWorldImpl extends AbstractEntityLocationInWorld implements EntityLocationInWorld {
    private final WorldGrid worldGrid;



    private Integer row;
    private Integer column;

    public EntityLocationInWorldImpl(WorldGrid worldGrid) {
        this.worldGrid =worldGrid;
        lotteryPosition();
    }

    private void lotteryPosition() {
        row= random.nextInt(worldGrid.getGridRows()-1);
        column= random.nextInt(worldGrid.getGridColumns()-1);

        while (!worldGrid.isPositionAvailable(row, column)){
            row= random.nextInt(worldGrid.getGridRows()-1);
            column= random.nextInt(worldGrid.getGridColumns()-1);
        }
        worldGrid.setPosition(row, column);
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
