package after.login.component.body.request.main;

import after.login.component.body.request.server.RequestsFromServer;
import dto.definition.user.request.DTOUserRequestForUi;
import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static java.lang.Thread.sleep;

public class UpdateRequestGridPane implements Runnable{
    private final GridPane requestGridPane;
    private final RequestsFromServer requestsFromServer;
    private final String userName;
    private Integer lastRequestID = 0;
    private final List<Label> helperListFromCallerController;

    private final List<Label> myHelperList;

    public UpdateRequestGridPane(GridPane requestGridPane, RequestsFromServer requestsFromServer, String userName, List<Label> helperList) {
        this.requestGridPane = requestGridPane;
        this.userName = userName;
        this.helperListFromCallerController = helperList;
        myHelperList = new ArrayList<>();

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
            userRequestList.sort(Comparator.comparingInt(DTOUserRequestForUi::getRequestID));
            for (DTOUserRequestForUi userRequest : userRequestList) {
                if (userRequest.getRequestID() > lastRequestID) {   //first update
                    lastRequestID = userRequest.getRequestID();

                    Label requestID = new Label(userRequest.getRequestID().toString());
                    Label requestStatus = new Label(userRequest.getRequestStatus());
                    Label numOfSimulationsRunning = new Label(userRequest.getNumOfSimulationsRunning().toString());
                    Label numOfSimulationsDone = new Label(userRequest.getNumOfSimulationsDone().toString());
                    Label terminationConditions= new Label(String.valueOf(userRequest.getTerminationCause()));

                    myHelperList.add(requestID);
                    myHelperList.add(requestStatus);
                    myHelperList.add(numOfSimulationsRunning);
                    myHelperList.add(numOfSimulationsDone);

                    helperListFromCallerController.set((row-1)*4, requestID);
                    helperListFromCallerController.set((row-1)*4 +1, requestStatus);
                    int finalRow = row;
                    Platform.runLater(() -> {
                        requestGridPane.add(requestID, 0, finalRow);
                        requestGridPane.add(requestStatus, 1, finalRow);
                        requestGridPane.add(numOfSimulationsRunning, 2, finalRow);
                        requestGridPane.add(numOfSimulationsDone, 3, finalRow);
                        requestGridPane.add(terminationConditions, 7, finalRow);

                        GridPane.setHalignment(requestID, javafx.geometry.HPos.CENTER);
                        GridPane.setHalignment(requestStatus, javafx.geometry.HPos.CENTER);
                        GridPane.setHalignment(numOfSimulationsRunning, javafx.geometry.HPos.CENTER);
                        GridPane.setHalignment(numOfSimulationsDone, javafx.geometry.HPos.CENTER);
                        GridPane.setHalignment(terminationConditions, javafx.geometry.HPos.CENTER);
                    });
                }
                else{ //already exist
                    int finalRow = row;
                    while((finalRow-1)*4 == myHelperList.size()){}
                    Platform.runLater(() -> {
                    myHelperList.get((finalRow-1)*4).setText(userRequest.getRequestID().toString());
                    myHelperList.get((finalRow-1)*4 + 1).setText(userRequest.getRequestStatus());
                    myHelperList.get((finalRow-1)*4 + 2).setText(userRequest.getNumOfSimulationsRunning().toString());
                    myHelperList.get((finalRow-1)*4 + 3).setText(userRequest.getNumOfSimulationsDone().toString());
                    });
                }
                row++;
            }
        }
    }
}
