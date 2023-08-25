package dto.api;

import java.util.Map;

public interface DTOPropertyHistogramForUi {
    Map< Object, Long> getPropertyHistogram();
    String getPropertyName();
}
