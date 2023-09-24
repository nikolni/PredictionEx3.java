package after.login.component.body.execution.tile.entity.start.button;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

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

