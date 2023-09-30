package dto.include;

import dto.primary.*;

public class DTOIncludeForResultsAdditionalForUi {

    private final DTOEntityPropertyConsistencyForUi dtoEntityPropertyConsistency;
    private final DTOSimulationProgressForUi dtoSimulationProgress;
    private final DTOPropertyHistogramForUi dtoPropertyHistogram;
    public DTOIncludeForResultsAdditionalForUi(DTOEntityPropertyConsistencyForUi dtoEntityPropertyConsistencyForUi,
                                               DTOSimulationProgressForUi dtoSimulationProgressForUi, DTOPropertyHistogramForUi dtoPropertyHistogramForUi) {
        this.dtoEntityPropertyConsistency = dtoEntityPropertyConsistencyForUi;
        this.dtoSimulationProgress = dtoSimulationProgressForUi;
        this.dtoPropertyHistogram = dtoPropertyHistogramForUi;
    }

    public DTOEntityPropertyConsistencyForUi getDtoEntityPropertyConsistency() {
        return dtoEntityPropertyConsistency;
    }

    public DTOSimulationProgressForUi getDtoSimulationProgress() {
        return dtoSimulationProgress;
    }
    public DTOPropertyHistogramForUi getDtoPropertyHistogram() {
        return dtoPropertyHistogram;
    }

}
