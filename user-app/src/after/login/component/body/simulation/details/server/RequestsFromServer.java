package after.login.component.body.simulation.details.server;

import dto.include.DTOIncludeSimulationDetailsForUi;
import dto.primary.DTOThreadsPoolStatusForUi;
import javafx.application.Platform;
import okhttp3.*;
import org.jetbrains.annotations.NotNull;
import util.constants.Constants;
import util.http.HttpClientUtil;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

import static util.constants.Constants.popUpWindow;
import static util.http.HttpClientUtil.HTTP_CLIENT_PUBLIC;

public class RequestsFromServer {
    private Consumer<DTOIncludeSimulationDetailsForUi> dtoIncludeSimulationDetailsForUiConsumer;

    public void setDTOIncludeSimulationDetailsForUi(Consumer<DTOIncludeSimulationDetailsForUi> dtoIncludeSimulationDetailsForUiConsumer){
        this.dtoIncludeSimulationDetailsForUiConsumer = dtoIncludeSimulationDetailsForUiConsumer;
    }
    private Consumer<List<String>> newSimulationsNamesConsumer;

    public void setNewSimulationsNamesConsumer(Consumer<List<String>> newSimulationsNamesConsumer){
        this.newSimulationsNamesConsumer = newSimulationsNamesConsumer;
    }
    public void getSimulationDetailsFromServer(String simulationName) {
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
                            DTOIncludeSimulationDetailsForUi simulationDetails = Constants.GSON_INSTANCE.fromJson(json, DTOIncludeSimulationDetailsForUi.class);
                            dtoIncludeSimulationDetailsForUiConsumer.accept(simulationDetails);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    public void getNewSimulationsNames(String[] simulationsArray){
        String json = Constants.GSON_INSTANCE.toJson(simulationsArray);

        Request request = new Request.Builder()
                .url(Constants.SIMULATION_NAMES_LIST_PAGE)
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
                            String[] newSimulationsArray = Constants.GSON_INSTANCE.fromJson(json, String[].class);
                            newSimulationsNamesConsumer.accept(Arrays.asList(newSimulationsArray));
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }                }
            }
        });
    }
}
