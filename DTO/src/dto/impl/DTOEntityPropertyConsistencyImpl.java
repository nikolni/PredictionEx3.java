package dto.impl;

import dto.api.DTOEntityPropertyConsistency;

public class DTOEntityPropertyConsistencyImpl implements DTOEntityPropertyConsistency {
private Float consistency;

    public DTOEntityPropertyConsistencyImpl(Float consistency) {
        this.consistency = consistency;
    }

    @Override
    public Float getConsistency() {
        return consistency;
    }
}
