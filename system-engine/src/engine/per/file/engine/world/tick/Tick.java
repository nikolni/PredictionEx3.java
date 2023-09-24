package engine.per.file.engine.world.tick;

public class Tick {
    private int tick = 1;

    public Tick(){}

    public Tick(int tick){this.tick = tick;}

    public int getTick() {
        return tick;
    }

    public void setTick(int tick) {
        this.tick = tick;
    }
}
