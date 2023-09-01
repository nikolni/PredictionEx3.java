package system.engine.world.rule.activation.impl;

import system.engine.world.definition.value.generator.api.ValueGenerator;
import system.engine.world.definition.value.generator.impl.random.impl.numeric.RandomFloatGenerator;
import system.engine.world.rule.activation.api.Activation;
import system.engine.world.tick.Tick;

public class ActivationImpl implements Activation {
    private Tick ticks = new Tick();
    private float probability = 1;

    public int getTicks() {
        return ticks.getTick();
    }

    public void setTicks(int ticks) {
        this.ticks.setTick(ticks);
    }

    public float getProbability() {
        return probability;
    }

    public void setProbability(float probability) {
        this.probability = probability;
    }

    @Override
    public boolean isActive(int tickNumber) {
        ValueGenerator<Float> randomProbability = new RandomFloatGenerator(0f, 1f);
        float randomProbabilityValue = randomProbability.generateValue();
        if(randomProbabilityValue <= probability){   //active by probability
            int myTick = 0;
            if(myTick == tickNumber){
                return true;
            }
            while(myTick < tickNumber) {
                myTick+= ticks.getTick();
                if(myTick == tickNumber){
                    return true;
                }
            }
        }

        return false;
    }
}
