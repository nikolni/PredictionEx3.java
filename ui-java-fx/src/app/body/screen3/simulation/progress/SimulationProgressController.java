package app.body.screen3.simulation.progress;
import app.body.screen3.main.Body3Controller;
import app.body.screen3.simulation.progress.task.UpdateUiTask;
import dto.api.DTOEntitiesAfterSimulationByQuantityForUi;
import dto.api.DTOSimulationEndingForUi;
import dto.definition.termination.condition.impl.ByUserTerminationConditionDTOImpl;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import system.engine.api.SystemEngineAccess;

import java.util.List;
import java.util.Map;


public class SimulationProgressController {

    @FXML
    private ProgressBar simulationProgressBar;
    @FXML
    private Label PercentLabel;
    @FXML
    private Label simulationIdLabel;
    @FXML
    private Label progressMassageLabel;
    @FXML
    private Label secondsLabel;
    @FXML
    private Label entitiesLeftLabel;
    @FXML
    private Label ticksLabel;
    @FXML
    private Button pauseButton;
    @FXML
    private Button resumeButton;
    @FXML
    private Button stopButton;
    @FXML
    private Button rerunButton;
    @FXML
    private GridPane entitiesLeftGridPane;

    private int simulationID=0;
    private Body3Controller body3ComponentController;
    private SystemEngineAccess systemEngine;

    public void setSimulationID(int simulationID) {
        this.simulationID = simulationID;
    }

    public void setSystemEngine(SystemEngineAccess systemEngine) {
        this.systemEngine = systemEngine;
    }

    public void setBody3ComponentController(Body3Controller body3ComponentController) {
        this.body3ComponentController = body3ComponentController;
    }
    public void setSimulationIdLabel(String simulationIdLabel) {
        this.simulationIdLabel.setText(simulationIdLabel);
    }

    public void bindUiTaskToUiDownLevelComponents(UpdateUiTask uiTask) {
        secondsLabel.textProperty().bind(Bindings.format("%,d", uiTask.getSecondsPastProperty()));
        //entitiesLeftLabel.textProperty().bind(Bindings.format("%,d", uiTask.getEntitiesLeftProperty()));
        ticksLabel.textProperty().bind(Bindings.format("%,d", uiTask.getTicksPastProperty()));
    }
    public void bindUiTaskToUiUpLevelComponents(Task<Boolean> uiTask) {
        // task message
        progressMassageLabel.textProperty().bind(uiTask.messageProperty());

        if (systemEngine.getTerminationConditions() instanceof ByUserTerminationConditionDTOImpl) {
            simulationProgressBar.setDisable(true);
            PercentLabel.setDisable(true);
        } else {
            // task progress bar
            simulationProgressBar.progressProperty().bind(uiTask.progressProperty());

            // task percent label
            PercentLabel.textProperty().bind(
                    Bindings.concat(
                            Bindings.format(
                                    "%.0f",
                                    Bindings.multiply(
                                            uiTask.progressProperty(),
                                            100)),
                            " %"));

            // task cleanup upon finish
        /*aTask.valueProperty().addListener((observable, oldValue, newValue) -> {
            onTaskFinished(Optional.ofNullable(onFinish));
        });*/
        }
    }

    public void onTaskFinished() {
        this.progressMassageLabel.textProperty().unbind();
        this.PercentLabel.textProperty().unbind();
        this.simulationProgressBar.progressProperty().unbind();
    }

    public void toggleTaskButtons(boolean isActive) {
        stopButton.setDisable(!isActive);
        pauseButton.setDisable(!isActive);
        resumeButton.setDisable(!isActive);
    }
    public void setButtonsDisableBeforeSimulationWasChosen(){
        stopButton.setDisable(true);
        pauseButton.setDisable(true);
        resumeButton.setDisable(true);
        rerunButton.setDisable(true);
    }


    public void setRerunButtonDisable(boolean isActive){
        rerunButton.setDisable(!isActive);
    }
    public void setPauseButtonDisable(boolean isActive){
        pauseButton.setDisable(!isActive);
    }
    public void setResumeButtonDisable(boolean isActive){
        resumeButton.setDisable(!isActive);
    }
    public void setStopButtonDisable(boolean isActive){
        stopButton.setDisable(!isActive);
    }
    @FXML
    synchronized void onPauseClick(MouseEvent event) {
        systemEngine.pauseSimulation(simulationID);
        pauseButton.setDisable(true);
        resumeButton.setDisable(false);
    }

    @FXML
    synchronized void onResumeClick(MouseEvent event) {
        systemEngine.resumeSimulation(simulationID);
        pauseButton.setDisable(false);
        resumeButton.setDisable(true);
    }

    @FXML
    void onStopClick(MouseEvent event) {
        System.out.println( "canceled!!  thread address  " + Thread.currentThread().getName() );
       systemEngine.cancelSimulation(simulationID);

        toggleTaskButtons(false);
        onTaskFinished();
    }

    @FXML
    void onRerunClick(MouseEvent event) {
        body3ComponentController.onRerunClick(simulationID);
    }

    public void updateEntitiesLeftGridPane(Map<String, Integer> entitiesPopulationAfterSimulationRunning) {
        int row = 0;
        entitiesLeftGridPane.getChildren().clear();
        for(String key: entitiesPopulationAfterSimulationRunning.keySet()){
            Label entityName = new Label(key);
            entitiesLeftGridPane.add(entityName, 0, row);
            Label population = new Label(entitiesPopulationAfterSimulationRunning.get(key).toString());
            entitiesLeftGridPane.add(population, 1, row);
            row++;
        }
    }

    public void updateQueueManagementInAppMain(){
        body3ComponentController.updateQueueManagementInAppMain();
    }
    public boolean isSimulationWasChosen(){
        return simulationID == 0;
    }
}
