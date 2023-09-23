package component.body.execution.tile.environment.variable.start.button;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

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