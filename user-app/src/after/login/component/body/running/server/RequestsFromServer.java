package after.login.component.body.running.server;

import com.google.gson.reflect.TypeToken;
import javafx.application.Platform;
import okhttp3.*;
import org.jetbrains.annotations.NotNull;
import util.constants.Constants;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

import static util.constants.Constants.popUpWindow;
import static util.http.HttpClientUtil.HTTP_CLIENT_PUBLIC;

public class RequestsFromServer {
    public Map<Integer, String> getSimulationsStatusesFromServer(String userName, List<Integer> executionsIdList) {
        final Map<Integer, String>[] simulationIdToStatuses = new Map[]{null};

        Integer[] list = executionsIdList.toArray(new Integer[0]);
        String json = Constants.GSON_INSTANCE.toJson(list);

        Request request = new Request.Builder()
                .url(Constants.EXECUTIONS_STATUS_PAGE)
                .post(RequestBody.create(json.getBytes()))
                .addHeader("user_name", userName)
                .build();

        Call call = HTTP_CLIENT_PUBLIC.newCall(request);
        call.enqueue( new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Platform.runLater(() -> popUpWindow(e.getMessage(), "Error!"));
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if (response.code() != 200) {
                    String responseBody = response.body().string();
                    Platform.runLater(() -> popUpWindow(responseBody, "Error!"));
                } else {
                    // Read and process the response content
                    try (ResponseBody responseBody = response.body()) {
                        if (responseBody != null) {
                            String json = response.body().string();
                            Type mapType = new TypeToken<Map<Integer, String>>() {
                            }.getType();
                            simulationIdToStatuses[0] = Constants.GSON_INSTANCE.fromJson(json, mapType);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        return simulationIdToStatuses[0];
    }
}
