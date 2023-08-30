package app.body.screen2.tile.environment.variable;

import dto.definition.property.definition.api.PropertyDefinitionDTO;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.awt.event.InputMethodEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class EnvironmentVariableController {


    @FXML
    private Label envVarNameLabel;

    @FXML
    private TextField valueTextField;


    private List<PropertyDefinitionDTO> envVarsList;
    private List<Object> initValues;


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
        initValues.set(envVarIndex,valueTextField.getText());
    }

    public void setEnvVarNameLabel(String envVarNameLabel) {
        this.envVarNameLabel.setText(envVarNameLabel);
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
}