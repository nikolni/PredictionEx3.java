package jaxb.copy;

import jaxb.generated.PRDBySecond;
import jaxb.generated.PRDByTicks;
import jaxb.generated.PRDTermination;
import system.engine.world.termination.condition.api.TerminationCondition;
import system.engine.world.termination.condition.impl.TicksTerminationConditionImpl;
import system.engine.world.termination.condition.impl.TimeTerminationConditionImpl;
import system.engine.world.termination.condition.manager.api.TerminationConditionsManager;
import system.engine.world.termination.condition.manager.impl.TerminationConditionsManagerImpl;
import system.engine.world.tick.Tick;

public class TerminationFromXML {
    private TerminationConditionsManager terminationConditionsManager= new TerminationConditionsManagerImpl();

    public TerminationFromXML(PRDTermination prdTermination){
        for(Object obj:prdTermination.getPRDByTicksOrPRDBySecond()){
            TerminationCondition terminationCondition=null;
            if(obj instanceof PRDBySecond)
                terminationCondition=new TimeTerminationConditionImpl(((PRDBySecond)obj).getCount());
            if(obj instanceof PRDByTicks)
                terminationCondition=new TicksTerminationConditionImpl(new Tick(((PRDByTicks)obj).getCount()));
            terminationConditionsManager.addTerminationCondition(terminationCondition);
        }
    }

    public TerminationConditionsManager getTerminationConditionsManager() {
        return terminationConditionsManager;
    }
}
