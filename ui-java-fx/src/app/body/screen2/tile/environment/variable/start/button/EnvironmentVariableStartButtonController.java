package app.body.screen2.tile.environment.variable.start.button;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class EnvironmentVariableStartButtonController {
    @FXML
    private Label envVarTypeLabel;
    @FXML
    private Label valueLabel;

    private Boolean isValueChanged = false;

    @FXML
    private Label envVarNameLabel;


    public void setEnvVarNameLabel(String envVarNameLabel) {
        this.envVarNameLabel.setText(envVarNameLabel);
    }
    public void setValueLabel(String envValueLabel) {
        this.valueLabel.setText(envValueLabel);
    }


}