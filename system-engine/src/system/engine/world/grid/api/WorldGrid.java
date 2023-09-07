package system.engine.world.grid.api;

import system.engine.world.execution.instance.enitty.api.EntityInstance;
import system.engine.world.execution.instance.enitty.location.api.EntityLocationInWorld;

public interface WorldGrid {
     Integer getGridRows();
   Integer getGridColumns();
   void setPosition(Integer row, Integer column, EntityInstance entityInstance);
   Boolean isPositionAvailable(Integer row, Integer column);
    Boolean isPositionAvailableForMovingEntity(EntityLocationInWorld currentEntityLocationInWorld,
                                               EntityLocationInWorld desirableEntityLocationInWorld);
    Boolean isThereSecondEntityCloseEnough(EntityInstance primaryEntityInstance, EntityInstance secondEntityInstance,
                                           String targetEntityName, Integer of);
}
