package app.body.screen3.simulation.progress;
import app.body.screen2.task.RunSimulationTask;
import app.body.screen2.task.context.api.SimulationRunTaskContext;
import app.body.screen2.task.context.impl.SimulationRunTaskContextImpl;
import app.body.screen3.main.Body3Controller;
import dto.api.DTOSimulationEndingForUi;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.input.MouseEvent;
import system.engine.api.SystemEngineAccess;

import java.util.Optional;
import java.util.concurrent.ExecutorService;

public class SimulationProgressController {

    @FXML
    private ProgressBar simulationProgressBar;
    @FXML
    private Label PercentLabel;
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

    private final SimpleIntegerProperty secondsPast;
    private final SimpleIntegerProperty ticksPast;
    private final SimpleIntegerProperty entitiesLeft;

    private final SimpleBooleanProperty isPaused;
    private Task<Boolean>  runSimulationTask;



    private Body3Controller body3ComponentController;

    public SimulationProgressController() {
        this.secondsPast = new SimpleIntegerProperty(0);
        this.ticksPast = new SimpleIntegerProperty(0);
        this.entitiesLeft = new SimpleIntegerProperty(0);
        this.isPaused =  new SimpleBooleanProperty(false);
    }

    @FXML
    private void initialize() {
        secondsLabel.textProperty().bind(Bindings.format("%,d", secondsPast));
        entitiesLeftLabel.textProperty().bind(Bindings.format("%,d", entitiesLeft));
        ticksLabel.textProperty().bind(Bindings.format("%,d", ticksPast));
    }

    public void setBody3ComponentController(Body3Controller body3ComponentController) {
        this.body3ComponentController = body3ComponentController;
    }

    public void runSimulation(SystemEngineAccess systemEngineAccess, ExecutorService threadPool){
        toggleTaskButtons(true);
        SimulationRunTaskContext simulationRunTaskContext = new SimulationRunTaskContextImpl(secondsPast, ticksPast, entitiesLeft, isPaused,
                systemEngineAccess, (q) -> onTaskFinished(Optional.ofNullable(() -> toggleTaskButtons(false))),
                systemEngineAccess.getTotalTicksNumber());
        runSimulationTask = new RunSimulationTask(simulationRunTaskContext, this);
        System.out.println("controller address " + this + " task address " + runSimulationTask);
        bindTaskToUIComponents(runSimulationTask, () -> toggleTaskButtons(false));

        threadPool.submit(runSimulationTask);
        /*Thread simulationThread = new Thread(runSimulationTask);
        simulationThread.start();*/

        /*while(simulationThread.isAlive()){
            try {
                Thread.sleep(250);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }*/
    }


    public void bindTaskToUIComponents(Task<Boolean> aTask, Runnable onFinish) {
        // task message
        progressMassageLabel.textProperty().bind(aTask.messageProperty());

        // task progress bar
        simulationProgressBar.progressProperty().bind(aTask.progressProperty());

        // task percent label
        PercentLabel.textProperty().bind(
                Bindings.concat(
                        Bindings.format(
                                "%.0f",
                                Bindings.multiply(
                                        aTask.progressProperty(),
                                        100)),
                        " %"));

        // task cleanup upon finish
        aTask.valueProperty().addListener((observable, oldValue, newValue) -> {
            onTaskFinished(Optional.ofNullable(onFinish));
        });

    }

    public void onTaskFinished(Optional<Runnable> onFinish) {
        this.progressMassageLabel.textProperty().unbind();
        this.PercentLabel.textProperty().unbind();
        this.simulationProgressBar.progressProperty().unbind();
        onFinish.ifPresent(Runnable::run);
    }

    private void toggleTaskButtons(boolean isActive) {
        stopButton.setDisable(!isActive);
        pauseButton.setDisable(!isActive);
        resumeButton.setDisable(!isActive);
    }

    public void addNewSimulationResultToBody3Controller(DTOSimulationEndingForUi dtoSimulationEndingForUi){
        body3ComponentController.createAndAddNewSimulationResultToList(dtoSimulationEndingForUi);
    }


    @FXML
    void onPauseClick(MouseEvent event) {
        isPaused.set(true);
    }

    @FXML
    void onResumeClick(MouseEvent event) {
        isPaused.set(false);
    }

    @FXML
    void onStopClick(MouseEvent event) {
        System.out.println(":canceld by task address " + runSimulationTask);
        runSimulationTask.cancel();
    }

}
