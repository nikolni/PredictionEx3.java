package after.login.component.body.simulation.details.server;

import dto.include.DTOIncludeSimulationDetailsForUi;
import javafx.application.Platform;
import okhttp3.*;
import org.jetbrains.annotations.NotNull;
import util.constants.Constants;
import util.http.HttpClientUtil;

import java.io.IOException;

import static util.constants.Constants.popUpWindow;
import static util.http.HttpClientUtil.HTTP_CLIENT_PUBLIC;

public class RequestsFromServer {
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

    public String[] getNewSimulationsNames(String[] simulationsArray){
        final String[][] newSimulationsArray = new String[1][1];
        String json = Constants.GSON_INSTANCE.toJson(simulationsArray);

        Request request = new Request.Builder()
                .url(Constants.EXECUTION_PAGE)
                .post(RequestBody.create(json.getBytes()))
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
                    try (ResponseBody responseBody = response.body()) {
                        if (responseBody != null) {
                            String json = response.body().string();
                            newSimulationsArray[0] = Constants.GSON_INSTANCE.fromJson(json, String[].class);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }                }
            }
        });
        return newSimulationsArray[0];
    }
}
