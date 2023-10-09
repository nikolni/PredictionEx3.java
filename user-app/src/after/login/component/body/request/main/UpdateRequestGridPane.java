package after.login.component.body.request.main;

import after.login.component.body.request.server.RequestsFromServer;
import dto.definition.user.request.DTOUserRequestForUi;
import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static java.lang.Thread.sleep;

public class UpdateRequestGridPane implements Runnable{
    private final GridPane requestGridPane;
    private final RequestsFromServer requestsFromServer;
    private final String userName;
    private Integer lastRequestID = 0;
    private final List<Label> helperList;
    private Integer numOfRequests = 0;

    public UpdateRequestGridPane(GridPane requestGridPane, RequestsFromServer requestsFromServer, String userName, List<Label> helperList) {
        this.requestGridPane = requestGridPane;
        this.userName = userName;
        this.helperList = helperList;

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
        int row = 1;
        if(userRequestList != null) {
            Collections.sort(userRequestList, Comparator.comparingInt(DTOUserRequestForUi::getRequestID));
            for (DTOUserRequestForUi userRequest : userRequestList) {
                if (userRequest.getRequestID() > lastRequestID) {
                    lastRequestID = userRequest.getRequestID();

                    Label requestID = new Label(userRequest.getRequestID().toString());
                    Label requestStatus = new Label(userRequest.getRequestStatus());
                    Label numOfSimulationsRunning = new Label(userRequest.getNumOfSimulationsRunning().toString());
                    Label numOfSimulationsDone = new Label(userRequest.getNumOfSimulationsDone().toString());
                    helperList.set((row-1)*3, requestID);
                    int finalRow = row;
                    Platform.runLater(() -> {
                        requestGridPane.add(requestID, 0, finalRow);
                        requestGridPane.add(requestStatus, 1, finalRow);
                        requestGridPane.add(numOfSimulationsRunning, 2, finalRow);
                        requestGridPane.add(numOfSimulationsDone, 3, finalRow);
                    });
                    numOfRequests++;
                }
                row++;
            }
        }
    }

}
