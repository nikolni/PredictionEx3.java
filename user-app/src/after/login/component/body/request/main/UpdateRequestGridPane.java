package after.login.component.body.request.main;

import after.login.component.body.request.server.RequestsFromServer;
import dto.definition.user.request.DTOUserRequestForUi;
import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

import java.util.List;

import static java.lang.Thread.sleep;

public class UpdateRequestGridPane implements Runnable{
    private final GridPane requestGridPane;
    private final RequestsFromServer requestsFromServer;
    private final String userName;

    public UpdateRequestGridPane(GridPane requestGridPane, RequestsFromServer requestsFromServer, String userName) {
        this.requestGridPane = requestGridPane;
        this.userName = userName;

        this.requestsFromServer = requestsFromServer;
        requestsFromServer.setUserRequestsConsumer(this::updateRequestGridPane);
    }

    @Override
    public void run() {
        while(Thread.currentThread().isAlive()) {
            requestsFromServer.getUserRequestListFromServer(userName);

            }
            try {
                sleep(300);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

    public void updateRequestGridPane(List<DTOUserRequestForUi> userRequestList) {
        int row = 0;
        for (DTOUserRequestForUi userRequest : userRequestList) {
            Label requestID= new Label(userRequest.getRequestID().toString());
            Label requestStatus= new Label(userRequest.getRequestStatus());
            Label numOfSimulationsRunning= new Label(userRequest.getNumOfSimulationsRunning().toString());
            Label numOfSimulationsDone= new Label(userRequest.getNumOfSimulationsDone().toString());
            int finalRow = row;
            Platform.runLater(() -> {
                requestGridPane.add(requestID, 0, finalRow);
                requestGridPane.add(requestStatus, 1, finalRow);
                requestGridPane.add(numOfSimulationsRunning, 2, finalRow);
                requestGridPane.add(numOfSimulationsDone, 3, finalRow);
            });
            row++;
        }
    }

}
