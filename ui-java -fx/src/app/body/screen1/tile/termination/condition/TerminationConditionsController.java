package app.body.screen1.tile.termination.condition;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class TerminationConditionsController {

    @FXML
    private Label ticksSecDefLabel;

    @FXML
    private Label ticksSecValueLabel;


    public void setTicksSecDefLabel(String ticksSecDefLabel) {
        this.ticksSecDefLabel.setText(ticksSecDefLabel);
    }

    public void setTicksSecValueLabel(String ticksSecValueLabel) {
        this.ticksSecValueLabel.setText(ticksSecValueLabel);
    }
}

