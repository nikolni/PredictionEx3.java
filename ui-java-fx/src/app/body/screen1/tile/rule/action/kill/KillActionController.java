package app.body.screen1.tile.rule.action.kill;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class KillActionController {
        @FXML
        private Label actionTypeLabel;

        @FXML
        private Label primaryEntityLabel;


        public void setActionTypeLabel(String actionTypeLabel) {
                this.actionTypeLabel.setText(actionTypeLabel);
        }

        public void setPrimaryEntityLabel(String primaryEntityLabel) {
                this.primaryEntityLabel.setText(primaryEntityLabel);
        }
}