package app.body.screen2.tile.entity;

import dto.definition.property.definition.api.PropertyDefinitionDTO;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.awt.event.InputMethodEvent;
import java.awt.event.MouseEvent;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class EntityController {


    @FXML
    private Label entityNameLabel;

    @FXML
    private TextField populationTextField;


    private List<String> entitiesNames;
    private List<Integer> entitiesPopulations;

    public void setEntitiesNames(List<String> entitiesNames) {
        this.entitiesNames = entitiesNames;
    }

    public void setEntitiesPopulations(List<Integer> entitiesPopulations) {
        this.entitiesPopulations = entitiesPopulations;
    }
    @FXML
    void setPopulation(InputMethodEvent event) {
        int entityIndex = getEntityIndexByName(entityNameLabel.getText());
        entitiesPopulations.set(entityIndex, Integer.parseInt(populationTextField.getText()));
    }

    public int getEntityIndexByName(String name) {
        int index = 0;

        for (String entityName : entitiesNames) {
            if (entityName.equals(name)) {
                return index;
            }
            index++;
        }
        throw new IllegalArgumentException("Can't find entity with name " + name);
    }

    public void setEntityNameLabel(String entityNameLabel) {
        this.entityNameLabel.setText(entityNameLabel);
    }
}

