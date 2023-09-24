package app.body.screen3.simulation.progress;
import app.body.screen3.main.Body3Controller;
import app.body.screen3.simulation.progress.task.UpdateUiTask;
import dto.definition.termination.condition.impl.ByUserTerminationConditionDTOImpl;
import dto.definition.termination.condition.impl.TicksTerminationConditionsDTOImpl;
import dto.definition.termination.condition.impl.TimeTerminationConditionsDTOImpl;
import javafx.beans.binding.Bindings;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import engine.per.file.engine.api.SystemEngineAccess;

import java.util.Map;


public class SimulationProgressController {

    @FXML
    private Label ticksPercentLabel;
    @FXML
    private Label secondsPercentLabel;
    @FXML
    private Label simulationIdLabel;
    @FXML
    private Label progressMassageLabel;
    @FXML
    private ProgressBar ticksProgressBar;
    @FXML
    private ProgressBar secondsProgressBar;
    @FXML
    private Label totalSecondsLabel;

    @FXML
    private Label totalTicksLabel;
    @FXML
    private Label secondsLabel;
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
    private int totalSeconds;

    public void setSimulationID(int simulationID) {
        this.simulationID = simulationID;
    }
    public void setTotalSeconds(int totalSeconds) {
            this.totalSeconds = totalSeconds;
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

    public void clearMyLabels(){
        onTaskFinished();
        simulationIdLabel.setText("");
        progressMassageLabel.setText("");
        totalSecondsLabel.setText("");
        totalTicksLabel.setText("");
        secondsLabel.setText("");
        ticksLabel.setText("");
        ticksPercentLabel.setText("");
        secondsPercentLabel.setText("");
        entitiesLeftGridPane.getChildren().clear();
        ticksProgressBar.setProgress(0);
        secondsProgressBar.setProgress(0);
    }
    public void bindUiTaskToUiDownLevelComponents(UpdateUiTask uiTask) {
        secondsLabel.textProperty().bind(Bindings.format("%,d", uiTask.getSecondsPastProperty()));
        //entitiesLeftLabel.textProperty().bind(Bindings.format("%,d", uiTask.getEntitiesLeftProperty()));
        ticksLabel.textProperty().bind(Bindings.format("%,d", uiTask.getTicksPastProperty()));
        if(systemEngine.getTerminationConditions().get(0) instanceof TimeTerminationConditionsDTOImpl  ||
                ( systemEngine.getTerminationConditions().size() == 2 && systemEngine.getTerminationConditions().get(1) instanceof TimeTerminationConditionsDTOImpl)){
            secondsProgressBar.progressProperty().bind(uiTask.secondsPast.divide((double) totalSeconds));
            secondsPercentLabel.textProperty().bind(
                    Bindings.concat(
                            Bindings.format(
                                    "%.0f",
                                    Bindings.multiply(
                                            secondsProgressBar.progressProperty(),
                                            100)),
                            " %"));
            secondsProgressBar.setDisable(false);
            secondsPercentLabel.setDisable(false);
        }
        else{
            secondsProgressBar.setDisable(true);
            secondsPercentLabel.setDisable(true);
        }

    }

    public void bindUiTaskToUiUpLevelComponents(Task<Boolean> uiTask) {
        // task message
        progressMassageLabel.textProperty().bind(uiTask.messageProperty());

        if (systemEngine.getTerminationConditions().get(0) instanceof ByUserTerminationConditionDTOImpl) {
            ticksProgressBar.setDisable(true);
            secondsProgressBar.setDisable(true);
            ticksPercentLabel.setDisable(true);
            secondsPercentLabel.setDisable(true);
        } else if(systemEngine.getTerminationConditions().get(0) instanceof TicksTerminationConditionsDTOImpl) {
            // task progress bar
            ticksProgressBar.progressProperty().bind(uiTask.progressProperty());

            // task percent label
            ticksPercentLabel.textProperty().bind(
                    Bindings.concat(
                            Bindings.format(
                                    "%.0f",
                                    Bindings.multiply(
                                            uiTask.progressProperty(),
                                            100)),
                            " %"));

            ticksProgressBar.setDisable(false);
            ticksPercentLabel.setDisable(false);
        }
        else{
            ticksProgressBar.setDisable(true);
            ticksPercentLabel.setDisable(true);
        }
    }

    public void onTaskFinished() {
        this.progressMassageLabel.textProperty().unbind();
        this.ticksPercentLabel.textProperty().unbind();
        this.secondsPercentLabel.textProperty().unbind();
        this.ticksProgressBar.progressProperty().unbind();
        this.secondsProgressBar.progressProperty().unbind();
        this.secondsLabel.textProperty().unbind();
        this.ticksLabel.textProperty().unbind();
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
       systemEngine.cancelSimulation(simulationID);

        toggleTaskButtons(false);
        onTaskFinished();
    }

    @FXML
    void onRerunClick(MouseEvent event) {
        body3ComponentController.onRerunClick(simulationID);
    }

    public void updateEntitiesLeftGridPane(Map<String, Integer> entitiesPopulationAfterSimulationRunning) {
        if(! simulationIdLabel.getText().equals("")) {
            int row = 0;
            entitiesLeftGridPane.getChildren().clear();
            for (String key : entitiesPopulationAfterSimulationRunning.keySet()) {
                Label entityName = new Label(key);
                entitiesLeftGridPane.add(entityName, 0, row);
                Label population = new Label(entitiesPopulationAfterSimulationRunning.get(key).toString());
                entitiesLeftGridPane.add(population, 1, row);
                row++;
            }
        }
    }

    public void updateQueueManagementInAppMain(){
        body3ComponentController.updateQueueManagementInAppMain();
    }
    public boolean isSimulationWasChosen(){
        return simulationID == 0;
    }


    public void setTotalSecondsLabel(String totalSecondsLabel) {
        if(! simulationIdLabel.getText().equals("")) {
            this.totalSecondsLabel.setText(totalSecondsLabel);
        }
    }

    public void setTotalTicksLabel(String totalTicksLabel) {
        if(! simulationIdLabel.getText().equals("")) {
            this.totalTicksLabel.setText(totalTicksLabel);
        }
    }
}
