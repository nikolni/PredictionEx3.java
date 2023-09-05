package app.body.screen2.task.context.api;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import system.engine.api.SystemEngineAccess;

import java.util.function.Consumer;

public interface SimulationRunTaskContext {
     SimpleIntegerProperty getSecondsPast() ;
     SimpleIntegerProperty getTicksPast() ;
     SimpleIntegerProperty getEntitiesLeft() ;
     SimpleBooleanProperty getIsPausedProperty();
     SystemEngineAccess getSystemEngineAccess() ;
     Consumer<Runnable> getOnCancel();
     Integer getTotalTicksNumber();
     Integer getSimulationID();
}
