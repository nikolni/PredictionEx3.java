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
    private Animations animations;


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
        this.animations = new Animations();
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
        boolean exceptionOccurred = false;
        String absolutePath = selectedFile.getAbsolutePath();
        try {
            systemEngine.getXMLFromUser(absolutePath);
            animations.startFadeTransition(animationCircle, true);
        } catch(RuntimeException | JAXBException | FileNotFoundException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setHeaderText("Invalid XML file - Please load other file");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
            exceptionOccurred=true;
        }
        if(!exceptionOccurred){
            selectedFileProperty.set(absolutePath);
            isFileSelected.set(true);
            mainController.onLoadFileButtonClick();
        }


    }

    @FXML
    void onDetailsButtonClick(ActionEvent event) {
        mainController.onDetailsButtonClick();
        if(activeAnimations){
            animations.startFillTransition(detailsRec);
        }

    }


    @FXML
    void onNewExecutionButtonClick(ActionEvent event) {
        mainController.onNewExecutionButtonClick();
        if(activeAnimations){
            animations.startFadeTransition(executionRec, false);
        }
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

    @FXML
    void onResultButtonClick(ActionEvent event) {
        mainController.onResultButtonClick();
        if(activeAnimations){
            animations.startPathTransition(valueWaitingLabel, valueCurrentlyExecutingLabel, valueOverLabel);
            Insets labelMargins = new Insets(20, 0, 0, 0);
            GridPane.setMargin(valueWaitingLabel, labelMargins);
            GridPane.setMargin(valueCurrentlyExecutingLabel, labelMargins);
            GridPane.setMargin(valueOverLabel, labelMargins);
        }
    }

    @FXML
    void onCircleClick(MouseEvent event) {
        activeAnimations = !activeAnimations;
        animations.startFadeTransition(animationCircle, false);
    }
}
