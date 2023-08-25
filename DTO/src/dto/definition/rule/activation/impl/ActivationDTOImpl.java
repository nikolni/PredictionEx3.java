package dto.definition.rule.activation.impl;

import dto.definition.rule.activation.api.ActivationDTO;
import dto.definition.rule.activation.tick.Tick;


public class ActivationDTOImpl implements ActivationDTO {
    private Tick ticks = new Tick();
    private float probability = 1;

    public ActivationDTOImpl(int tickNumber, float probability){
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
