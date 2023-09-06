package app.body.screen1.tile.rule.action.proximity;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class ProximityActionController {
        @FXML
        private Label primaryEntityLabel;

        @FXML
        private Label actionTypeLabel;

        @FXML
        private Label ofLabel;

        @FXML
        private Label actionsQuantityLabel;

        @FXML
        private Label secondEntityLabel;

        public void setActionTypeLabel(String actionTypeText) {
                this.actionTypeLabel.setText(actionTypeText);
        }

        public void setPrimaryEntityLabel(String primaryEntityText) {
                this.primaryEntityLabel.setText(primaryEntityText);
        }
        public void setSecondEntityLabel(String secondEntityText) {
                this.secondEntityLabel.setText(secondEntityText);
        }

        public void setOfLabel(String of) {
                this.ofLabel.setText(of);
        }

        public void setActionsNumLabel(String actionsQuantity) {
                this.actionsQuantityLabel.setText(actionsQuantity);
        }
}
