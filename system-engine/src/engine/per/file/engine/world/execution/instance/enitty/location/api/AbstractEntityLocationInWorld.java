package engine.per.file.engine.world.execution.instance.enitty.location.api;

import java.util.Random;

public abstract class AbstractEntityLocationInWorld {
    protected final Random random;
    protected AbstractEntityLocationInWorld() {
        random = new Random();
    }
}
