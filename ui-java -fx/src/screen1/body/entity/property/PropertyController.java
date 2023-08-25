package screen1.body.entity.property;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class PropertyController {

    @FXML
    private Label propertyNameLabel;

    @FXML
    private Label propertyTypeLabel;

    @FXML
    private Label propertyRangeLabel;

    @FXML
    private Label propertyIsRandomLabel;

    public void setPropertyNameLabel(String propertyNameLabel) {
        this.propertyNameLabel.setText(propertyNameLabel);
    }

    public void setPropertyTypeLabel(String propertyTypeLabel) {
        this.propertyTypeLabel.setText(propertyTypeLabel);
    }

    public void setPropertyRangeLabel(String propertyRangeLabel) {
        this.propertyRangeLabel.setText(propertyRangeLabel);
    }

    public void setPropertyIsRandomLabel(String propertyIsRandomLabel) {
        this.propertyIsRandomLabel.setText(propertyIsRandomLabel);
    }
}

