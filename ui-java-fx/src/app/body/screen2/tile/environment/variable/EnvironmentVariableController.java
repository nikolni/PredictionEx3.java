package app.body.screen2.tile.environment.variable;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class EnvironmentVariableController {
    @FXML
    private TextField valueTextField;
    @FXML
    private Label envVarTypeLabel;
    private Boolean isValueChanged = false;

    @FXML
    private Label envVarNameLabel;

    @FXML
    public void initialize() {
        valueTextField.setText("");
    }

    public Object getValueTextField() {
        Object value= null;
             String input= valueTextField.getText();
             String envVarType = envVarTypeLabel.getText();
            if(! valueTextField.getText().isEmpty()){
                switch (envVarType) {
                    case "DECIMAL":
                        value = Integer.parseInt(input);
                        break;
                    case "FLOAT":
                        value=Float.parseFloat(input);
                        break;
                    case "STRING":
                        value= envVarType;
                        break;
                    case "BOOLEAN":
                        value = Boolean.parseBoolean(input);
                        break;
                }
            }
        return value;
    }
    public boolean isInputValid(){
        if(! valueTextField.getText().isEmpty()) {
            String input = valueTextField.getText();
            String envVarType = envVarTypeLabel.getText();
            boolean inputValid = true;

            try {
                switch (envVarType) {
                    case "DECIMAL":
                        Integer.parseInt(input);
                        break;
                    case "FLOAT":
                        Float.parseFloat(input);
                        break;
                    case "STRING":
                        break;
                    case "BOOLEAN":
                        Boolean.parseBoolean(input);
                        break;
                }
            } catch (NumberFormatException e) {
                inputValid = false;
            }

            return inputValid;
        }
        return true;
    }


    public void setEnvVarNameLabel(String envVarNameLabel) {
        this.envVarNameLabel.setText(envVarNameLabel);
    }
    public void setEnvVarTypeLabel(String envVarTypeLabel) {
        this.envVarTypeLabel.setText(envVarTypeLabel);
    }


}