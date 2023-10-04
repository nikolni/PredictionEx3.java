package admin.component.body.management.server;

import admin.util.constants.Constants;
import dto.primary.DTOThreadsPoolStatusForUi;
import javafx.application.Platform;
import okhttp3.*;
import org.jetbrains.annotations.NotNull;
import admin.util.http.HttpClientUtil;

import java.io.IOException;

import static admin.util.constants.Constants.popUpWindow;
import static admin.util.http.HttpClientUtil.HTTP_CLIENT_PUBLIC;

public class RequestsFromServer {

    public DTOThreadsPoolStatusForUi getThreadPoolStatusFromServer() {
        final DTOThreadsPoolStatusForUi[] dtoThreadsPoolStatusForUis = {null};
        String finalUrl = HttpUrl
                .parse(Constants.THREAD_POOL_STATUS_PAGE)
                .newBuilder()
                .build()
                .toString();

        HttpClientUtil.runAsync(finalUrl, new Callback() {
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
                            dtoThreadsPoolStatusForUis[0] = Constants.GSON_INSTANCE.fromJson(json, DTOThreadsPoolStatusForUi.class);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        return dtoThreadsPoolStatusForUis[0];
    }

    public void setThreadsPoolSizeToServer(Integer size){
        String body = "";

        Request request = new Request.Builder()
                .url(Constants.THREAD_POOL_STATUS_PAGE)
                .post(RequestBody.create(body.getBytes()))
                .addHeader("size", size.toString())
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
                    Platform.runLater(() -> popUpWindow("Your request has been received and is being processed", "Request was accepted"));
                }
            }
        });
    }
}
