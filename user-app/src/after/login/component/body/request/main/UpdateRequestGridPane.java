package after.login.component.body.request.main;

import com.google.gson.reflect.TypeToken;
import dto.definition.user.request.DTOUserRequestForUi;
import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import okhttp3.*;
import org.jetbrains.annotations.NotNull;
import util.constants.Constants;
import util.http.HttpClientUtil;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

import static java.lang.Thread.sleep;
import static util.constants.Constants.popUpWindow;

public class UpdateRequestGridPane implements Runnable{
    private final GridPane requestGridPane;

    public UpdateRequestGridPane(GridPane requestGridPane) {
        this.requestGridPane = requestGridPane;
    }

    @Override
    public void run() {
        while(Thread.currentThread().isAlive()) {
            String finalUrl = HttpUrl
                    .parse(Constants.USER_REQUEST_PAGE)
                    .newBuilder()
                    .build()
                    .toString();

            HttpClientUtil.runAsync(finalUrl, new Callback() {
                @Override
                public void onFailure(@NotNull Call call, @NotNull IOException e) {
                    popUpWindow(e.getMessage(), "Error!");
                }

                @Override
                public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                    if (response.code() != 200) {
                        String responseBody = response.body().string();
                        popUpWindow(responseBody, "Error!");
                    } else {
                        // Read and process the response content
                        try (ResponseBody responseBody = response.body()) {
                            if (responseBody != null) {
                                String json = response.body().string();
                                Type listType = new TypeToken<List<DTOUserRequestForUi>>() {}.getType();
                                List<DTOUserRequestForUi> userRequestList = Constants.GSON_INSTANCE.fromJson(json, listType);
                                updateRequestGridPane(userRequestList);
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
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
            Label requestStatus= new Label(userRequest.getRequestStatus());
            Label numOfSimulationsRunning= new Label(userRequest.getNumOfSimulationsRunning().toString());
            Label numOfSimulationsDone= new Label(userRequest.getNumOfSimulationsDone().toString());
            int finalRow = row;
            Platform.runLater(() -> {
                requestGridPane.add(requestStatus, 1, finalRow);
                requestGridPane.add(numOfSimulationsRunning, 2, finalRow);
                requestGridPane.add(numOfSimulationsDone, 3, finalRow);
            });
            row++;
        }
    }

}
