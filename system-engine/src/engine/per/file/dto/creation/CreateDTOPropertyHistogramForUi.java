package engine.per.file.dto.creation;

import dto.primary.DTOPropertyHistogramForUi;
import engine.per.file.engine.world.api.WorldInstance;

import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class CreateDTOPropertyHistogramForUi {

    public DTOPropertyHistogramForUi getData(WorldInstance worldInstance, String entityName, String propertyName){

       /* Map<Object, Long> propertyHistogram = worldInstance.getEntityInstanceManager().getInstancesBeforeKill().stream().
               filter(e -> e.getEntityDefinition().getUniqueName().equals(entityName)).
                collect(Collectors.groupingBy(e -> e.getPropertyByName(propertyName).getValue(), Collectors.counting()));*/

        Map<Object, Long> propertyHistogram = worldInstance.getEntityInstanceManager().getInstances().stream().
                filter(Objects::nonNull).
                filter(e -> e.getEntityDefinition().getUniqueName().equals(entityName)).
                collect(Collectors.groupingBy(e -> e.getPropertyByName(propertyName).getValue(), Collectors.counting()));

        return new DTOPropertyHistogramForUi(propertyHistogram, propertyName);

    }
}
