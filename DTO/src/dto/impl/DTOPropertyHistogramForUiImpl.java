package dto.impl;

import dto.api.DTOPropertyHistogramForUi;

import java.util.Map;

public class DTOPropertyHistogramForUiImpl implements DTOPropertyHistogramForUi {

    private final Map< Object, Long> propertyHistogram;
    private final String propertyName;

    public DTOPropertyHistogramForUiImpl( Map< Object, Long> propertyHistogram, String propertyName){
        this.propertyHistogram = propertyHistogram;
        this.propertyName = propertyName;
    }

    @Override
    public Map< Object, Long> getPropertyHistogram() {
        return propertyHistogram;
    }

    @Override
    public String getPropertyName() {
        return propertyName;
    }
}
