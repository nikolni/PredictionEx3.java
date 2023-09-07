package dto.api;

import java.util.Map;

public interface DTOSimulationProgressForUi {
     Integer getSecondsPast();

     Integer getTicksPast() ;

     Map<String, Integer> getEntitiesLeft();
     String getProgressMassage();
}
