package app.body.screen2.tile.environment.variable;

import dto.definition.property.definition.api.PropertyDefinitionDTO;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.util.List;

public class EnvironmentVariableController {

    @FXML
    private TextField valueTextField;

    @FXML
    private Label envVarTypeLabel;

    private Boolean isValueChanged = false;

    @FXML
    private Label envVarNameLabel;
    //private List<PropertyDefinitionDTO> envVarsList;
    //private List<Object> initValues;






    @FXML
    public void initialize() {
        valueTextField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> ov, String t, String t1) {
                checkInputValidity();
            }
        });

    }

    public Object getValueTextField() {
        Object value= null;
        if(isValueChanged){
             String input= valueTextField.getText();
             String envVarType = envVarTypeLabel.getText();

            switch (envVarType) {
                case "DECIMAL":
                    if(valueTextField.getText() != "") {
                        value = Integer.parseInt(input);
                    }
                    break;
                case "FLOAT":
                    if(valueTextField.getText() != "") {
                        value=Float.parseFloat(input);
                    }

                    break;
                case "STRING":
                    if(valueTextField.getText() != "") {
                        value= envVarType;
                    }

                    break;
                case "BOOLEAN":
                    if(valueTextField.getText() != "") {
                        value = Boolean.parseBoolean(input);
                    }

                    break;
            }
        }

        return value;
    }
    @FXML
    private void checkInputValidity(){
        isValueChanged = true;
        String input= valueTextField.getText();
        String envVarType = envVarTypeLabel.getText();
        boolean isInputValid;
        Stage primaryStage = new Stage();

            isInputValid= true;
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
            }
            catch (NumberFormatException e) {
                Label errorMessage = new Label("Error in value type!\nTry again.");
                StackPane root = new StackPane();
                root.getChildren().add(errorMessage);
                Scene scene = new Scene(root, 150, 80);
                primaryStage.setScene(scene);
                primaryStage.setTitle("Error Window");
                primaryStage.show();
                isInputValid= false;
                valueTextField.clear();
            }
            //primaryStage.close();
    }


    public void setEnvVarNameLabel(String envVarNameLabel) {
        this.envVarNameLabel.setText(envVarNameLabel);
    }
    public void setEnvVarTypeLabel(String envVarTypeLabel) {
        this.envVarTypeLabel.setText(envVarTypeLabel);
    }

    public void resetTextField(){
        valueTextField=new TextField();
        valueTextField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> ov, String t, String t1) {
                checkInputValidity();
            }
        });
    }

    /* public int getEnvVarIndexByName(String name) {
        int index = 0;

        for (PropertyDefinitionDTO propertyDefinitionDTO : envVarsList) {
            if (propertyDefinitionDTO.getUniqueName().equals(name)) {
                return index;
            }
            index++;
        }
        throw new IllegalArgumentException("Can't find entity with name " + name);
    }*/

    /*public void setEnvVarsList(List<PropertyDefinitionDTO> envVarsList) {
        this.envVarsList = envVarsList;
    }

    public void setInitValues(List<Object> initValues) {
        this.initValues = initValues;
    }*/

/*    @FXML
    void setValueFromUser() {
        int envVarIndex = getEnvVarIndexByName(envVarNameLabel.getText());
        if(checkInputValidity(valueTextField.getText())){
            initValues.set(envVarIndex,valueTextField.getText());
        }
        else{
            valueTextField.clear();
        }
    }*/

    /*@FXML
    void setValueFromUser(InputMethodEvent event) {

    }*/
}