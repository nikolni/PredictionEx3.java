package app.body.screen1.tile.rule.action.replace;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class ReplaceActionController {

        @FXML
        private Label killEntityLabel;

        @FXML
        private Label actionTypeLabel;

        @FXML
        private Label modeLabel;

        @FXML
        private Label createEntityLabel;

        public void setActionTypeLabel(String actionTypeText) {
                this.actionTypeLabel.setText(actionTypeText);
        }

        public void setKillEntityLabel(String killEntityLabel) {
                this.killEntityLabel.setText(killEntityLabel);
        }
        public void setCreateEntityLabel(String createEntityLabel) {
                this.createEntityLabel.setText(createEntityLabel);
        }

        public void setModeLabel(String modeLabel) {
                this.modeLabel.setText(modeLabel);
        }

}
