package app.body.screen2.task.context;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import system.engine.api.SystemEngineAccess;

import java.util.function.Consumer;

public interface Context {
     SimpleIntegerProperty getSecondsPast() ;
     SimpleIntegerProperty getTicksPast() ;
     SimpleIntegerProperty getEntitiesLeft() ;
     SimpleBooleanProperty getIsPausedProperty();
     SystemEngineAccess getSystemEngineAccess() ;
     Consumer<Runnable> getOnCancel();
     Integer getTotalTicksNumber();
}
