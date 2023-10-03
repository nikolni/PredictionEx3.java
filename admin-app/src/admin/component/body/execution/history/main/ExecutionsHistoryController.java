package admin.component.body.execution.history.main;

import admin.main.AdminController;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;

public class ExecutionsHistoryController {

    @FXML
    private ListView<?> SimulationsDoneDetailsList;

    @FXML
    private ScrollPane simulationResultScrollPane;

    private AdminController mainController;




    public void setMainController(AdminController mainController) {
        this.mainController = mainController;
    }

}
