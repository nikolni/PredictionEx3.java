package jaxb2.copy;

import jaxb2.generated.PRDBySecond;
import jaxb2.generated.PRDByTicks;
import jaxb2.generated.PRDTermination;
import system.engine.world.termination.condition.api.TerminationCondition;
import system.engine.world.termination.condition.impl.ByUserTerminationConditionImpl;
import system.engine.world.termination.condition.impl.TicksTerminationConditionImpl;
import system.engine.world.termination.condition.impl.TimeTerminationConditionImpl;
import system.engine.world.termination.condition.manager.api.TerminationConditionsManager;
import system.engine.world.termination.condition.manager.impl.TerminationConditionsManagerImpl;
import system.engine.world.tick.Tick;

public class TerminationFromXML {
    private TerminationConditionsManager terminationConditionsManager= new TerminationConditionsManagerImpl();

    public TerminationFromXML(PRDTermination prdTermination){
        TerminationCondition terminationCondition=null;
        if(prdTermination.getPRDByUser()!=null){
            terminationCondition=new ByUserTerminationConditionImpl();
            terminationConditionsManager.addTerminationCondition(terminationCondition);
        }
        else{
            for(Object obj:prdTermination.getPRDBySecondOrPRDByTicks()){
                if(obj instanceof PRDBySecond)
                    terminationCondition=new TimeTerminationConditionImpl(((PRDBySecond)obj).getCount());
                if(obj instanceof PRDByTicks)
                    terminationCondition=new TicksTerminationConditionImpl(new Tick(((PRDByTicks)obj).getCount()));
                terminationConditionsManager.addTerminationCondition(terminationCondition);
            }
        }

    }

    public TerminationConditionsManager getTerminationConditionsManager() {
        return terminationConditionsManager;
    }
}
