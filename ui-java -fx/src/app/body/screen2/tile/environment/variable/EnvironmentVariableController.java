package app.body.screen2.tile.environment.variable;

import dto.definition.property.definition.api.PropertyDefinitionDTO;
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
    private Label envVarNameLabel;

    @FXML
    private TextField valueTextField;

    @FXML
    private Label envVarTypeLabel;


    private List<PropertyDefinitionDTO> envVarsList;
    private List<Object> initValues;

    private String envVarType;


    @FXML
    public void initialize() {
        //initValues = new ArrayList<>(Collections.nCopies(envVarsList.size(), null));
    }

    public void setEnvVarsList(List<PropertyDefinitionDTO> envVarsList) {
        this.envVarsList = envVarsList;
    }

    public void setInitValues(List<Object> initValues) {
        this.initValues = initValues;
    }

    @FXML
    void setValueFromUser(InputMethodEvent event) {
        int envVarIndex = getEnvVarIndexByName(envVarNameLabel.getText());
        if(checkInputValidity(valueTextField.getText())){
            initValues.set(envVarIndex,valueTextField.getText());
        }
        else{
            valueTextField.clear();
        }
    }

    private Boolean checkInputValidity(String input){
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
                Scene scene = new Scene(root, 300, 200);
                primaryStage.setScene(scene);
                primaryStage.setTitle("Error Window");
                primaryStage.show();
                isInputValid= false;
            }

        //primaryStage.close();

        return isInputValid;
    }


    public void setEnvVarNameLabel(String envVarNameLabel) {
        this.envVarNameLabel.setText(envVarNameLabel);
    }
    public void setEnvVarTypeLabel(String envVarTypeLabel) {
        this.envVarTypeLabel.setText(envVarTypeLabel);
    }

    public int getEnvVarIndexByName(String name) {
        int index = 0;

        for (PropertyDefinitionDTO propertyDefinitionDTO : envVarsList) {
            if (propertyDefinitionDTO.getUniqueName().equals(name)) {
                return index;
            }
            index++;
        }
        throw new IllegalArgumentException("Can't find entity with name " + name);
    }

    public void setEnvVarType(String envVarType) {
        this.envVarType = envVarType;
    }

    public void resetTextField(){
        valueTextField.clear();
    }
}