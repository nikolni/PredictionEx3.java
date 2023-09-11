package app.body.screen1.tile.world.grid;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class WorldGridSizesController {

    @FXML
    private Label columnsLabel;

    @FXML
    private Label rowsLabel;
    @FXML
    private Label populationLabel;

    public void setColumnsLabel(String columnsLabel) {
        this.columnsLabel.setText(columnsLabel);
    }
    public void setRowsLabel(String rowsLabel) {
        this.rowsLabel.setText(rowsLabel);
    }
    public void setPopulationLabel(String populationLabel) {
        this.populationLabel.setText(populationLabel);
    }

}
