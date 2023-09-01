package app.body.screen2.task.context;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import system.engine.api.SystemEngineAccess;

import java.util.function.Consumer;

public class ContextImpl implements Context{

    private SimpleIntegerProperty secondsPast;
    private SimpleIntegerProperty ticksPast;
    private SimpleIntegerProperty entitiesLeft;
    private SimpleBooleanProperty isPaused;
    private SystemEngineAccess systemEngineAccess;
    private Consumer<Runnable> onCancel;
    private Integer totalTicksNumber;

    public ContextImpl(SimpleIntegerProperty secondsPast, SimpleIntegerProperty ticksPast,
                       SimpleIntegerProperty entitiesLeft, SimpleBooleanProperty isPaused,
                        SystemEngineAccess systemEngineAccess,
                       Consumer<Runnable> onCancel, Integer totalTicksNumber) {
        this.secondsPast = secondsPast;
        this.ticksPast = ticksPast;
        this.entitiesLeft = entitiesLeft;
        this.isPaused = isPaused;
        this.systemEngineAccess = systemEngineAccess;
        this.onCancel = onCancel;
        this.totalTicksNumber = totalTicksNumber;
    }

    @Override
    public SimpleIntegerProperty getSecondsPast() {
        return secondsPast;
    }
    @Override
    public SimpleIntegerProperty getTicksPast() {
        return ticksPast;
    }
    @Override
    public SimpleIntegerProperty getEntitiesLeft() {
        return entitiesLeft;
    }
    @Override
    public SimpleBooleanProperty getIsPausedProperty() {
        return isPaused;
    }

    @Override
    public SystemEngineAccess getSystemEngineAccess() {
        return systemEngineAccess;
    }
    @Override
    public Consumer<Runnable> getOnCancel() {
        return onCancel;
    }
    @Override
    public Integer getTotalTicksNumber() {
        return totalTicksNumber;
    }
}
