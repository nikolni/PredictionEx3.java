package system.engine.world.rule.activation.api;

import system.engine.world.tick.Tick;

public interface Activation {
    boolean isActive(int tickNumber);
    int getTicks();
    void setTicks(int ticks);
    float getProbability();
    public void setProbability(float probability);
}
