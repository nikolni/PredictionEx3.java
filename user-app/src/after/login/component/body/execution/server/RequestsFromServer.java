package after.login.component.body.execution.server;

import dto.include.DTOIncludeForExecutionForServer;
import dto.include.DTOIncludeForExecutionForUi;
import dto.primary.*;
import javafx.application.Platform;
import okhttp3.*;
import org.jetbrains.annotations.NotNull;
import util.constants.Constants;
import util.http.HttpClientUtil;

import java.io.IOException;

import static util.constants.Constants.popUpWindow;
import static util.http.HttpClientUtil.HTTP_CLIENT_PUBLIC;

public class RequestsFromServer {
    public DTOIncludeForExecutionForUi getDataForExecutionFromServer(String simulationName) {
        final DTOIncludeForExecutionForUi[] dtoIncludeForExecutionForUi = {null};
        String finalUrl = HttpUrl
                .parse(Constants.DATA_EXECUTION_PAGE)
                .newBuilder()
                .addQueryParameter("simulation_name", simulationName)
                .build()
                .toString();

        HttpClientUtil.runAsync(finalUrl, new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Platform.runLater(() -> {
                    popUpWindow(e.getMessage(), "Error!");
                });
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if (response.code() != 200) {
                    String responseBody = response.body().string();
                    Platform.runLater(() -> {
                        popUpWindow(responseBody, "Error!");
                    });
                } else {
                    // Read and process the response content
                    try (ResponseBody responseBody = response.body()) {
                        if (responseBody != null) {
                            String json = response.body().string();
                            dtoIncludeForExecutionForUi[0] = Constants.GSON_INSTANCE.fromJson(json, DTOIncludeForExecutionForUi.class);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        return dtoIncludeForExecutionForUi[0];
    }

    public void postRequestExecutionToServer(DTOEnvVarDefValuesForSE dtoEnvVarDefValuesForSE ,
                                             DTOPopulationValuesForSE dtoPopulationValuesForSE ,String simulationName){
        DTOIncludeForExecutionForServer dtoIncludeForExecutionForServer = new DTOIncludeForExecutionForServer(
                dtoEnvVarDefValuesForSE, dtoPopulationValuesForSE);
        String json = Constants.GSON_INSTANCE.toJson(dtoIncludeForExecutionForServer);

        Request request = new Request.Builder()
                .url(Constants.EXECUTION_PAGE)
                .post(RequestBody.create(json.getBytes()))
                .addHeader("simulation_name", simulationName)
                .build();

        Call call = HTTP_CLIENT_PUBLIC.newCall(request);
        call.enqueue( new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Platform.runLater(() -> {
                    popUpWindow(e.getMessage(), "Error!");
                });
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if (response.code() != 200) {
                    String responseBody = response.body().string();
                    Platform.runLater(() -> {
                        popUpWindow(responseBody, "Error!");
                    });
                } else {
                    Platform.runLater(() -> {
                        popUpWindow("Your simulation execution is being processed", "Simulation Executed");
                    });
                }
            }
        });
    }
}
