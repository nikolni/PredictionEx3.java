package app.body.screen1.tile.rule.action.condition;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class ConditionActionController {

        @FXML
        private Label actionTypeValLabel;

        @FXML
        private Label primaryEntityValLabel;

        @FXML
        private Label singularityValLabel;

        public void setActionTypeLabel(String actionTypeLabel) {
                this.actionTypeValLabel.setText(actionTypeLabel);
        }

        public void setPrimaryEntityLabel(String primaryEntityLabel) {
                this.primaryEntityValLabel.setText(primaryEntityLabel);
        }

        public void setSingularityLabel(String singularityLabel) {
                this.singularityValLabel.setText(singularityLabel);
        }
}
