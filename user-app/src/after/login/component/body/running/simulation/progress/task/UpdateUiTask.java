package after.login.component.body.running.simulation.progress.task;

import after.login.component.body.running.server.RequestsFromServer;
import after.login.component.body.running.simulation.progress.SimulationProgressController;
import dto.definition.termination.condition.api.TerminationConditionsDTO;
import dto.definition.termination.condition.impl.ByUserTerminationConditionDTOImpl;
import dto.primary.DTOSecTicksForUi;
import dto.primary.DTOSimulationProgressForUi;
import javafx.application.Platform;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.concurrent.Task;

import java.util.List;

import static java.lang.Thread.sleep;

public class UpdateUiTask extends Task<Boolean> {

    private final SimulationProgressController currentSimulationController;
    private final Integer executionID;
    public final SimpleIntegerProperty secondsPast;
    private final SimpleIntegerProperty ticksPast;
    private final SimpleIntegerProperty entitiesLeft;
    private final Integer totalTicksNumber;
    private final Integer totalSecondsNumber;
    private final RequestsFromServer requestsFromServer;
    private final String userName;
    private DTOSimulationProgressForUi dtoSimulationProgressForUi;
    private DTOSecTicksForUi dtoSecTicksForUis;
    private List<TerminationConditionsDTO> terminationConditionsDTOList;

    public UpdateUiTask(SimulationProgressController currentSimulationController, Integer executionID,
                        RequestsFromServer requestsFromServer, String userName) {
        this.currentSimulationController = currentSimulationController;
        this.executionID = executionID;
        this.userName = userName;

        this.requestsFromServer = requestsFromServer;
        requestsFromServer.setTotalSecAndTickConsumer(this::useDTOSecTicksForUi);
        requestsFromServer.setExecutionProgressConsumer(this::useExecutionProgress);
        requestsFromServer.setTerminationConditionsListConsumer(this::useTerminationConditions);

        this.secondsPast = new SimpleIntegerProperty(0);
        this.ticksPast = new SimpleIntegerProperty(0);
        this.entitiesLeft = new SimpleIntegerProperty(0);
        requestsFromServer.getTotalSecAndTickFromServer(userName, executionID);

        this.totalTicksNumber = dtoSecTicksForUis.getTicks();
        this.totalSecondsNumber = dtoSecTicksForUis.getSeconds();
    }
    private void useDTOSecTicksForUi(DTOSecTicksForUi secTicksConsumer){
        dtoSecTicksForUis = secTicksConsumer;
    }
    @Override
    protected Boolean call()  {
        while(Thread.currentThread().isAlive()){
            requestsFromServer.getExecutionProgressFromServer( userName, executionID);


            requestsFromServer.getTerminationConditionsFromServer(userName, executionID);


            updateSimulationProgress(dtoSimulationProgressForUi, terminationConditionsDTOList);
            try {
                sleep(200);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        }
        return null;
    }

    private void useExecutionProgress(DTOSimulationProgressForUi executionProgressConsumer){
        dtoSimulationProgressForUi = executionProgressConsumer;
    }
    private void useTerminationConditions(List<TerminationConditionsDTO> terminationConditionsConsumer){
        terminationConditionsDTOList = terminationConditionsConsumer;
    }

    public SimpleIntegerProperty getSecondsPastProperty() {
        return secondsPast;
    }
    public SimpleIntegerProperty getTicksPastProperty() {
        return ticksPast;
    }

    public void updateSimulationProgress(DTOSimulationProgressForUi dtoSimulationProgressForUi,
                                         List<TerminationConditionsDTO> terminationConditionsDTOList) {
        if (!(terminationConditionsDTOList.get(0) instanceof ByUserTerminationConditionDTOImpl)) {
            updateProgress(dtoSimulationProgressForUi.getTicksPast(), totalTicksNumber);
        }
        Platform.runLater(() -> {
            updateMessage(dtoSimulationProgressForUi.getProgressMassage());
            currentSimulationController.setTotalTicksLabel(totalTicksNumber.toString());
            currentSimulationController.setTotalSecondsLabel(totalSecondsNumber.toString());
            currentSimulationController.setRerunButtonDisable(true);
            ticksPast.set(dtoSimulationProgressForUi.getTicksPast());
            secondsPast.set(dtoSimulationProgressForUi.getSecondsPast());

            currentSimulationController.updateEntitiesLeftGridPane(dtoSimulationProgressForUi.getEntitiesLeft());
            switch (dtoSimulationProgressForUi.getProgressMassage()) {
                case "Getting ready...":
                case "Done!":
                case "Canceled!":
                    currentSimulationController.toggleTaskButtons(false);
                    break;
                case "Running!":
                    currentSimulationController.toggleTaskButtons(true);
                    currentSimulationController.setResumeButtonDisable(false);

                    break;
                case "Paused!":
                    currentSimulationController.setPauseButtonDisable(false);
                    currentSimulationController.setResumeButtonDisable(true);
                    currentSimulationController.setStopButtonDisable(true);
                    break;
            }
        });
    }
}
