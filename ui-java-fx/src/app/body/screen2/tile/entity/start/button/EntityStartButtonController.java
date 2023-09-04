package app.body.screen2.tile.entity.start.button;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.util.List;

public class EntityStartButtonController {


    @FXML
    private Label entityNameLabel;
    @FXML
    private Label populationValueLabel;


    public void setEntityNameLabel(String entityNameLabel) {
        this.entityNameLabel.setText(entityNameLabel);
    }
    public void setPopulationValueLabel(String PopulationValueLabel) {
        this.populationValueLabel.setText(PopulationValueLabel);
    }

}

