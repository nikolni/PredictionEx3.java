package component.body.simulation.details.single.simulation.tile.rule.action.kill;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class KillActionController {
        @FXML
        private Label actionTypeLabel;
        @FXML
        private Label primaryEntityLabel;
        @FXML
        private Label secondEntityLabel;


        public void setActionTypeLabel(String actionTypeLabel) {
                this.actionTypeLabel.setText(actionTypeLabel);
        }

        public void setPrimaryEntityLabel(String primaryEntityLabel) {
                this.primaryEntityLabel.setText(primaryEntityLabel);
        }
        public void setSecondEntityLabel(String secondEntityText) {
                this.secondEntityLabel.setText(secondEntityText);
        }
}
