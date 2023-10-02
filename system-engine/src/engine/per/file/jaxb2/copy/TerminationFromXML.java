package engine.per.file.jaxb2.copy;

import engine.per.file.engine.world.termination.condition.api.TerminationCondition;
import engine.per.file.engine.world.termination.condition.impl.ByUserTerminationConditionImpl;
import engine.per.file.engine.world.termination.condition.impl.TicksTerminationConditionImpl;
import engine.per.file.engine.world.termination.condition.impl.TimeTerminationConditionImpl;
import engine.per.file.engine.world.termination.condition.manager.api.TerminationConditionsManager;
import engine.per.file.engine.world.termination.condition.manager.impl.TerminationConditionsManagerImpl;
import engine.per.file.engine.world.tick.Tick;
import engine.per.file.jaxb2.generated.PRDBySecond;
import engine.per.file.jaxb2.generated.PRDByTicks;
import engine.per.file.jaxb2.generated.PRDTermination;

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
