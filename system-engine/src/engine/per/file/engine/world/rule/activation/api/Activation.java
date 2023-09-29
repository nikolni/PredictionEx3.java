package engine.per.file.engine.world.rule.activation.api;

public interface Activation {
    boolean isActive(int tickNumber);
    int getTicks();
    void setTicks(int ticks);
    float getProbability();
    public void setProbability(float probability);
}
