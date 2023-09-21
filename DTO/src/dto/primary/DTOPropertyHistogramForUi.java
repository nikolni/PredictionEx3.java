package dto.primary;

import java.util.Map;

public class DTOPropertyHistogramForUi {

    private final Map< Object, Long> propertyHistogram;
    private final String propertyName;

    public DTOPropertyHistogramForUi(Map< Object, Long> propertyHistogram, String propertyName){
        this.propertyHistogram = propertyHistogram;
        this.propertyName = propertyName;
    }

    public Map< Object, Long> getPropertyHistogram() {
        return propertyHistogram;
    }

    public String getPropertyName() {
        return propertyName;
    }
}
