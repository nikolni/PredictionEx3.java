package admin.component.body.management.server;

import admin.util.constants.Constants;
import dto.definition.user.request.DTOUserRequestForUi;
import dto.include.DTOIncludeSimulationDetailsForUi;
import dto.primary.DTOThreadsPoolStatusForUi;
import javafx.application.Platform;
import okhttp3.*;
import org.jetbrains.annotations.NotNull;
import admin.util.http.HttpClientUtil;

import java.io.IOException;
import java.util.List;
import java.util.function.Consumer;

import static admin.util.constants.Constants.popUpWindow;
import static admin.util.http.HttpClientUtil.HTTP_CLIENT_PUBLIC;

public class RequestsFromServer {

    private Consumer<DTOThreadsPoolStatusForUi> dtoThreadsPoolStatusConsumer;

    public void setConsumer(Consumer<DTOThreadsPoolStatusForUi> dtoThreadsPoolStatusConsumer){
        this.dtoThreadsPoolStatusConsumer = dtoThreadsPoolStatusConsumer;
    }

    public void getThreadPoolStatusFromServer() {

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
                            DTOThreadsPoolStatusForUi dtoThreadsPoolStatusForUis = Constants.GSON_INSTANCE.fromJson(json, DTOThreadsPoolStatusForUi.class);
                            dtoThreadsPoolStatusConsumer.accept(dtoThreadsPoolStatusForUis);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
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

    public DTOIncludeSimulationDetailsForUi getSimulationDetailsFromServer(String simulationName) {
        final DTOIncludeSimulationDetailsForUi[] simulationDetails = {null};
        String finalUrl = HttpUrl
                .parse(Constants.SIMULATION_DETAILS_PAGE)
                .newBuilder()
                .addQueryParameter("simulation_name", simulationName)
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
                            simulationDetails[0] = Constants.GSON_INSTANCE.fromJson(json, DTOIncludeSimulationDetailsForUi.class);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        return simulationDetails[0];
    }

}
