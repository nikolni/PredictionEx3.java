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
import java.util.function.Consumer;

import static util.constants.Constants.popUpWindow;
import static util.http.HttpClientUtil.HTTP_CLIENT_PUBLIC;

public class RequestsFromServer {
    private Consumer<Integer> executionIDConsumer;
    public void setExecutionIDConsumer(Consumer<Integer> executionIDConsumer){
        this.executionIDConsumer = executionIDConsumer;
    }
    private Consumer<DTOIncludeForExecutionForUi> dtoIncludeForExecutionConsumer;
    public void setDtoIncludeForExecutionConsumer(Consumer<DTOIncludeForExecutionForUi> dtoIncludeForExecutionConsumer){
        this.dtoIncludeForExecutionConsumer = dtoIncludeForExecutionConsumer;
    }
    private Consumer<DTORerunValuesForUi> dtoRerunValuesForUiConsumer;
    public void setDTORerunValuesConsumer(Consumer<DTORerunValuesForUi> dtoRerunValuesForUiConsumer){
        this.dtoRerunValuesForUiConsumer = dtoRerunValuesForUiConsumer;
    }

    public void getExecutionIDFromServer() {
        String finalUrl = HttpUrl
                .parse(Constants.RERUN_PAGE)
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
                            Integer executionID = Constants.GSON_INSTANCE.fromJson(json, Integer.class);
                            executionIDConsumer.accept(executionID);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
    public void getDataForExecutionFromServer(String simulationName) {
        String finalUrl = HttpUrl
                .parse(Constants.DATA_EXECUTION_PAGE)
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
                            DTOIncludeForExecutionForUi dtoIncludeForExecutionForUi = Constants.GSON_INSTANCE.fromJson(json, DTOIncludeForExecutionForUi.class);
                            dtoIncludeForExecutionConsumer.accept(dtoIncludeForExecutionForUi);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    public void postRequestExecutionToServer(DTOEnvVarDefValuesForSE dtoEnvVarDefValuesForSE ,
                                             DTOPopulationValuesForSE dtoPopulationValuesForSE ,String simulationName,
                                             String userName, String requestID){
        DTOIncludeForExecutionForServer dtoIncludeForExecutionForServer = new DTOIncludeForExecutionForServer(
                dtoEnvVarDefValuesForSE, dtoPopulationValuesForSE);
        String json = Constants.GSON_INSTANCE.toJson(dtoIncludeForExecutionForServer);

        Request request = new Request.Builder()
                .url(Constants.EXECUTION_PAGE)
                .post(RequestBody.create(json.getBytes()))
                .addHeader("simulation_name", simulationName)
                .addHeader("user_name", userName)
                .addHeader("requestID", requestID)
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
                    Platform.runLater(() -> popUpWindow("Your simulation execution is being processed", "Simulation Executed"));
                }
            }
        });
    }

    public void getRerunValuesFromServer(String simulationName, Integer executionID) {
        String finalUrl = HttpUrl
                .parse(Constants.RERUN_PAGE)
                .newBuilder()
                .addQueryParameter("simulation_name", simulationName)
                .addQueryParameter("executionID", executionID.toString())
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
                            DTORerunValuesForUi dtoRerunValuesForUi = Constants.GSON_INSTANCE.fromJson(json, DTORerunValuesForUi.class);
                            dtoRerunValuesForUiConsumer.accept(dtoRerunValuesForUi);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
}
