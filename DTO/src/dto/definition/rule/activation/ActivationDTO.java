package dto.definition.rule.activation;

import dto.definition.rule.activation.tick.Tick;


public class ActivationDTO {
    private Tick ticks = new Tick();
    private float probability = 1;

    public ActivationDTO(int tickNumber, float probability){
        ticks.setTick(tickNumber);
        this.probability = probability;
    }

    public int getTicks() {
        return ticks.getTick();
    }

    public float getProbability() {
        return probability;
    }

}
