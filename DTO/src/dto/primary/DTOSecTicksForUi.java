package dto.primary;

public class DTOSecTicksForUi {
    private final Integer seconds;
    private final Integer ticks;

    public DTOSecTicksForUi(Integer seconds, Integer ticks) {
        this.seconds = seconds;
        this.ticks = ticks;
    }


    public Integer getSeconds() {
        return seconds;
    }

    public Integer getTicks() {
        return ticks;
    }
}
