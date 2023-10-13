package admin.component.body.allocation.main;

import admin.main.AdminController;
import admin.util.constants.Constants;
import dto.definition.user.request.DTOUserRequestForUi;
import javafx.application.Platform;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class AllocationsController {
    private Timer timer;
    private TimerTask requestGridRefresher;
    private final IntegerProperty totalRequests;
    @FXML
    private GridPane executionRequestGridPane;

    private AdminController mainController;
    private int numOfRequest = 0;

    public void setMainController(AdminController mainController) {
        this.mainController = mainController;
    }

    public AllocationsController() {
        totalRequests=new SimpleIntegerProperty();
    }

    private void updateUsersList(List<DTOUserRequestForUi> userRequests) {
        if(numOfRequest < userRequests.size()) {
            Platform.runLater(() -> {
                totalRequests.set(userRequests.size());
                for (int i = numOfRequest; i < userRequests.size(); i++) {
                    DTOUserRequestForUi request = userRequests.get(i);

                    Label requestIDLabel = new Label(String.valueOf(request.getRequestID()));
                    Label requestStatusLabel = new Label(String.valueOf(request.getRequestStatus()));
                    Label exeRunningNowLabel = new Label(String.valueOf(request.getNumOfSimulationsRunning()));
                    Label exeDoneLabel = new Label(String.valueOf(request.getNumOfSimulationsDone()));
                    Label simulationNameLabel = new Label(String.valueOf(request.getSimulationName()));
                    Label userNameLabel = new Label(String.valueOf(request.getUserName()));
                    Label exeTotalLabel = new Label(String.valueOf(request.getNumOfCycles()));
                    Label terminationLabel = new Label(String.valueOf(request.getTerminationCause()));

                    GridPane.setHalignment(requestIDLabel, javafx.geometry.HPos.CENTER);
                    GridPane.setHalignment(requestStatusLabel, javafx.geometry.HPos.CENTER);
                    GridPane.setHalignment(exeRunningNowLabel, javafx.geometry.HPos.CENTER);
                    GridPane.setHalignment(exeDoneLabel, javafx.geometry.HPos.CENTER);
                    GridPane.setHalignment(simulationNameLabel, javafx.geometry.HPos.CENTER);
                    GridPane.setHalignment(userNameLabel, javafx.geometry.HPos.CENTER);
                    GridPane.setHalignment(exeTotalLabel, javafx.geometry.HPos.CENTER);
                    GridPane.setHalignment(terminationLabel, javafx.geometry.HPos.CENTER);

                    executionRequestGridPane.addRow(i + 1, requestIDLabel, requestStatusLabel, exeRunningNowLabel, exeDoneLabel,
                            simulationNameLabel, userNameLabel, exeTotalLabel, terminationLabel);
                }
                numOfRequest = userRequests.size();
            });
        }
    }

    public void startListRefresher() {
        requestGridRefresher = new RequestsGridRefresher(this::updateUsersList);

        timer = new Timer();
        timer.schedule(requestGridRefresher, Constants.REFRESH_RATE, Constants.REFRESH_RATE);
    }

}
