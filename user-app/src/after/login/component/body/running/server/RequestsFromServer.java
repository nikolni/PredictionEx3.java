package after.login.component.body.running.server;

import com.google.gson.reflect.TypeToken;
import dto.definition.termination.condition.api.TerminationConditionsDTO;
import dto.include.DTOIncludeForResultsAdditionalForUi;
import dto.include.DTOIncludeForResultsPrimaryForUi;
import dto.primary.DTOSecTicksForUi;
import dto.primary.DTOSimulationEndingForUi;
import dto.primary.DTOSimulationProgressForUi;
import javafx.application.Platform;
import okhttp3.*;
import org.jetbrains.annotations.NotNull;
import util.constants.Constants;
import util.http.HttpClientUtil;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import static util.constants.Constants.LINE_SEPARATOR;
import static util.constants.Constants.popUpWindow;
import static util.http.HttpClientUtil.HTTP_CLIENT_PUBLIC;

public class RequestsFromServer {
    private Consumer<Map<Integer, String>> simulationsStatusesConsumer;
    public void setSimulationsStatusesConsumer(Consumer<Map<Integer, String>> simulationsStatusesConsumer){
        this.simulationsStatusesConsumer = simulationsStatusesConsumer;
    }
    public void getSimulationsStatusesFromServer(String userName, List<Integer> executionsIdList) {
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
                            Map<Integer, String> simulationIdToStatuses = Constants.GSON_INSTANCE.fromJson(json, mapType);
                            simulationsStatusesConsumer.accept(simulationIdToStatuses);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private Consumer<DTOSimulationProgressForUi> executionProgressConsumer;
    public void setExecutionProgressConsumer(Consumer<DTOSimulationProgressForUi> executionProgressConsumer){
        this.executionProgressConsumer = executionProgressConsumer;
    }
    public void getExecutionProgressFromServer(String userName, Integer executionID) {
        String finalUrl = HttpUrl
                .parse(Constants.UPDATE_EXECUTION_PROGRESS_PAGE)
                .newBuilder()
                .addQueryParameter("user_name", userName)
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
                            DTOSimulationProgressForUi dtoSimulationProgressForUi = Constants.GSON_INSTANCE.fromJson(json, DTOSimulationProgressForUi.class);
                            executionProgressConsumer.accept(dtoSimulationProgressForUi);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public void postControlRequestToServer(String userName, Integer executionID, String action){
        String body = "user_name="+userName + LINE_SEPARATOR +
                "executionID="+executionID + LINE_SEPARATOR +
                "action"+action;

        Request request = new Request.Builder()
                .url(Constants.CONTROL_EXECUTION_PAGE)
                .post(RequestBody.create(body.getBytes()))
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
                    Platform.runLater(() -> popUpWindow("Your request has been received and is being processed", "Request was received"));
                }
            }
        });
    }
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private Consumer<DTOSecTicksForUi> totalSecAndTickConsumer;
    public void setTotalSecAndTickConsumer(Consumer<DTOSecTicksForUi> totalSecAndTickConsumer){
        this.totalSecAndTickConsumer = totalSecAndTickConsumer;
    }
    public void getTotalSecAndTickFromServer(String userName, Integer executionID) {
        String finalUrl = HttpUrl
                .parse(Constants.SEC_TICK_PAGE)
                .newBuilder()
                .addQueryParameter("user_name", userName)
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
                            DTOSecTicksForUi dtoSecTicksForUis = Constants.GSON_INSTANCE.fromJson(json, DTOSecTicksForUi.class);
                            totalSecAndTickConsumer.accept(dtoSecTicksForUis);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private Consumer<DTOIncludeForResultsPrimaryForUi> resultsPrimaryConsumer;
    public void setResultsPrimaryForUiConsumer(Consumer<DTOIncludeForResultsPrimaryForUi> resultsPrimaryConsumer){
        this.resultsPrimaryConsumer = resultsPrimaryConsumer;
    }
    public void getPrimaryResults (String userName, Integer executionID) {
        String finalUrl = HttpUrl
                .parse(Constants.RESULTS_PAGE)
                .newBuilder()
                .addQueryParameter("user_name", userName)
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
                            DTOIncludeForResultsPrimaryForUi dtoIncludeForResultsPrimaryForUis = Constants.GSON_INSTANCE.fromJson(json, DTOIncludeForResultsPrimaryForUi.class);
                            resultsPrimaryConsumer.accept(dtoIncludeForResultsPrimaryForUis);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private Consumer<DTOIncludeForResultsAdditionalForUi> additionalResultsConsumer;
    public void setAdditionalResultsConsumer(Consumer<DTOIncludeForResultsAdditionalForUi> additionalResultsConsumer){
        this.additionalResultsConsumer = additionalResultsConsumer;
    }
    public void getAdditionalResults(String userName, Integer executionID, String entityName, String propertyName){
        String body = "user_name="+userName + LINE_SEPARATOR +
                "executionID="+executionID + LINE_SEPARATOR +
                "entity_name="+entityName + LINE_SEPARATOR +
                "property_name="+propertyName;

        Request request = new Request.Builder()
                .url(Constants.RESULTS_PAGE)
                .post(RequestBody.create(body.getBytes()))
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
                            DTOIncludeForResultsAdditionalForUi dtoIncludeForResultsAdditionalForUi = Constants.GSON_INSTANCE.fromJson(json, DTOIncludeForResultsAdditionalForUi.class);
                            additionalResultsConsumer.accept(dtoIncludeForResultsAdditionalForUi);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }                }
            }
        });
    }
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private Consumer<List<DTOSimulationEndingForUi>> simulationEndingListConsumer;
    public void setSimulationEndingListConsumer(Consumer<List<DTOSimulationEndingForUi>> simulationEndingListConsumer){
        this.simulationEndingListConsumer = simulationEndingListConsumer;
    }
    public void getSimulationEndingListFromServer(String userName) {

        String finalUrl = HttpUrl
                .parse(Constants.EXECUTIONS_ENDING_PAGE)
                .newBuilder()
                .addQueryParameter("user_name", userName)
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
                            Type listType = new TypeToken<List<DTOSimulationEndingForUi>>() {}.getType();
                            List<DTOSimulationEndingForUi> simulationEndingList = Constants.GSON_INSTANCE.fromJson(json, listType);
                            simulationEndingListConsumer.accept(simulationEndingList);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private Consumer<List<TerminationConditionsDTO>> terminationConditionsListConsumer;
    public void setTerminationConditionsListConsumer(Consumer<List<TerminationConditionsDTO>> terminationConditionsListConsumer){
        this.terminationConditionsListConsumer = terminationConditionsListConsumer;
    }
    public void getTerminationConditionsFromServer(String userName, Integer executionID) {
        String finalUrl = HttpUrl
                .parse(Constants.TERMINATION_CONDITIONS_PAGE)
                .newBuilder()
                .addQueryParameter("user_name", userName)
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
                            Type listType = new TypeToken<List<TerminationConditionsDTO>>() {}.getType();
                            List<TerminationConditionsDTO> terminationConditionsDTOList = Constants.GSON_INSTANCE.fromJson(response.body().string(), listType);
                            terminationConditionsListConsumer.accept(terminationConditionsDTOList);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
}
