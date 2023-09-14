package app.header;

import app.main.AppController;
import javafx.animation.FadeTransition;
import javafx.animation.FillTransition;
import javafx.animation.PathTransition;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Shape;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import system.engine.api.SystemEngineAccess;

import javax.xml.bind.JAXBException;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import javafx.scene.shape.Rectangle;
import javafx.geometry.Insets;

public class HeaderController {


    @FXML
    private GridPane headerComponent;

    @FXML
    private Button loadFileButton;


    @FXML
    private Label waitingLabel;
    @FXML
    private Label overLabel;
    @FXML
    private Label currentlyExecutingLabel;
    @FXML
    private Label valueCurrentlyExecutingLabel;
    @FXML
    private Label valueWaitingLabel;
    @FXML
    private GridPane queueManagementGridPane;
    @FXML
    private Label valueOverLabel;
    @FXML
    private Button detailsButton;
    @FXML
    private Button newExecutionButton;
    @FXML
    private Button resultButtonClick;
    @FXML
    private Label selectedFileNameLabel;
    @FXML
    private Rectangle detailsRec;
    @FXML
    private Rectangle executionRec;
    @FXML
    private Rectangle resultsRec;
    @FXML
    private Circle animationCircle;

    private SystemEngineAccess systemEngine;
    private SimpleStringProperty selectedFileProperty;
    private SimpleBooleanProperty isFileSelected;
    private AppController mainController;
    private Stage primaryStage;
    private boolean activeAnimations = true;


    public void setSystemEngine(SystemEngineAccess systemEngineAccess){
        this.systemEngine = systemEngineAccess;
    }
    public void setMainController(AppController mainController) {
        this.mainController = mainController;
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }


    public HeaderController() {
        selectedFileProperty = new SimpleStringProperty();
        isFileSelected = new SimpleBooleanProperty(false);
    }

    @FXML
    private void initialize() {
        selectedFileNameLabel.textProperty().bind(selectedFileProperty);
        detailsButton.disableProperty().bind(isFileSelected.not());
        newExecutionButton.disableProperty().bind(isFileSelected.not());
        resultButtonClick.disableProperty().bind(isFileSelected.not());
    }

    @FXML
    void onLoadFileButtonClick(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select XML file");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("XML files", "*.xml"));
        File selectedFile = fileChooser.showOpenDialog(primaryStage);
        if (selectedFile == null) {
            return;
        }

        String absolutePath = selectedFile.getAbsolutePath();
        try {
            systemEngine.getXMLFromUser(absolutePath);
            startFadeTransition(animationCircle, true);
        } catch(RuntimeException | JAXBException | FileNotFoundException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setHeaderText("This is an error message.");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
        selectedFileProperty.set(absolutePath);
        isFileSelected.set(true);

        mainController.onLoadFileButtonClick();

    }

    @FXML
    void onDetailsButtonClick(ActionEvent event) {
        mainController.onDetailsButtonClick();
        if(activeAnimations){
            startFillTransition();
        }

    }
    private void startFillTransition(){
        FillTransition ft = new FillTransition(Duration.millis(2000), detailsRec, Color.GREY, Color.PINK);

        ft.setCycleCount(4);
        ft.setAutoReverse(true);
        ft.play();
    }

    @FXML
    void onNewExecutionButtonClick(ActionEvent event) {
        mainController.onNewExecutionButtonClick();
        if(activeAnimations){
            startFadeTransition(executionRec, false);
        }
    }

    private void startFadeTransition(Shape shape, boolean longer){
        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(2), shape);
        fadeTransition.setFromValue(1.0); // Fully visible
        fadeTransition.setToValue(0.2);   // 20% opacity

        if(longer){
            fadeTransition.setCycleCount(6);
        }
        else{
            fadeTransition.setCycleCount(4);
        }
        fadeTransition.setAutoReverse(true); // Reverse the animation
        fadeTransition.play();
    }




    public void setCurrentlyExecutingLabel(String currentlyExecutingLabel) {
        this.valueCurrentlyExecutingLabel.setText(currentlyExecutingLabel);
    }

    public void setWaitingLabel(String waitingLabel) {
        this.valueWaitingLabel.setText(waitingLabel);
    }

    public void setOverLabel(String overLabel) {
        this.valueOverLabel.setText(overLabel);
    }

    private void startPathTransition(){
        Line path = new Line();
        path.setStartX(0);
        path.setStartY(0);
        path.setEndX(0);
        path.setEndY(60); // Adjust the Y coordinate to control the vertical movement

        // Create PathTransition animations for label pairs
        PathTransition transition01 = createPathTransition(waitingLabel, valueWaitingLabel, path);
        PathTransition transition02 = createPathTransition(currentlyExecutingLabel, valueCurrentlyExecutingLabel, path);
        PathTransition transition03 = createPathTransition(overLabel, valueOverLabel, path);

        // Set the cycle count and auto-reverse for animations
        transition01.setCycleCount(2);
        transition02.setCycleCount(2);
        transition03.setCycleCount(2);

        transition01.setAutoReverse(true);
        transition02.setAutoReverse(true);
        transition03.setAutoReverse(true);

        transition01.play();
        transition02.setDelay(Duration.seconds(1.0)); // Delay by 1 second
        transition02.play();
        transition03.setDelay(Duration.seconds(2)); // Delay by 2 seconds
        transition03.play();
    }

    private PathTransition createPathTransition(Label label1, Label label2, Line path) {
        PathTransition transition = new PathTransition();
        transition.setDuration(Duration.seconds(2.0)); // Set the duration
        transition.setPath(path);
        transition.setNode(label1);
        transition.setNode(label2);
        return transition;
    }

    @FXML
    void onResultButtonClick(ActionEvent event) {
        mainController.onResultButtonClick();
        if(activeAnimations){
            startPathTransition();
            Insets labelMargins = new Insets(20, 0, 0, 0);
            GridPane.setMargin(valueWaitingLabel, labelMargins);
            GridPane.setMargin(valueCurrentlyExecutingLabel, labelMargins);
            GridPane.setMargin(valueOverLabel, labelMargins);
        }
    }

    @FXML
    void onCircleClick(MouseEvent event) {
        activeAnimations = !activeAnimations;
        startFadeTransition(animationCircle, false);
    }
}
