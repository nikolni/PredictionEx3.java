package component.body.allocation.main;

import dto.definition.user.request.DTOUserRequestForUi;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import main.AdminController;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import static util.constants.Constants.REFRESH_RATE;

public class AllocationsController {
    private Timer timer;
    private TimerTask requestGridRefresher;
    private final IntegerProperty totalRequests;
    @FXML
    private GridPane executionRequesGridPane;

    private AdminController mainController;




    public void setMainController(AdminController mainController) {
        this.mainController = mainController;
    }

    public AllocationsController() {
        totalRequests=new SimpleIntegerProperty();
    }

    private void updateUsersList(List<DTOUserRequestForUi> userRequests) {

        Platform.runLater(() -> {
            totalRequests.set(userRequests.size());
            executionRequesGridPane.getChildren().clear();
            for (int i = 0; i < userRequests.size(); i++) {
                DTOUserRequestForUi request = userRequests.get(i);

                Label requestIDLabel = new Label(String.valueOf(request.getRequestID()));
                Label requestStatusLabel=new Label(String.valueOf(request.getRequestStatus()));
                Label exeRunningNowLabel=new Label(String.valueOf(request.getNumOfSimulationsRunning()));
                Label exeDoneLabel=new Label(String.valueOf(request.getNumOfSimulationsDone()));
                Label simulationNameLabel=new Label(String.valueOf(request.getSimulationName()));
                Label userNameLabel=new Label(String.valueOf(request.getUserName()));
                Label exeTotalLabel=new Label(String.valueOf(request.getNumOfCycles()));
                Label terminationLabel=new Label(String.valueOf(request.getTerminationCause()));

                executionRequesGridPane.addRow(i + 1, requestIDLabel,requestStatusLabel,exeRunningNowLabel,exeDoneLabel,
                        simulationNameLabel,userNameLabel,exeTotalLabel,terminationLabel);
            }
        });

    }

    public void startListRefresher() {
        requestGridRefresher = new RequestsGridRefresher(this::updateUsersList);

        timer = new Timer();
        timer.schedule(requestGridRefresher, REFRESH_RATE, REFRESH_RATE);
    }

}
