package after.login.component.body.running.server;

import com.google.gson.reflect.TypeToken;
import dto.definition.termination.condition.api.TerminationConditionsDTO;
import dto.definition.termination.condition.impl.ByUserTerminationConditionDTOImpl;
import dto.definition.termination.condition.impl.TicksTerminationConditionsDTOImpl;
import dto.definition.termination.condition.impl.TimeTerminationConditionsDTOImpl;
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

import static util.constants.Constants.LINE_SEPARATOR;
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

    public DTOSimulationProgressForUi getExecutionProgressFromServer(String userName, Integer executionID) {
        final DTOSimulationProgressForUi[] dtoSimulationProgressForUi = {null};
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
                            dtoSimulationProgressForUi[0] = Constants.GSON_INSTANCE.fromJson(json, DTOSimulationProgressForUi.class);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        return dtoSimulationProgressForUi[0];
    }

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
    public DTOSecTicksForUi getTotalSecAndTickFromServer(String userName, Integer executionID) {
        final DTOSecTicksForUi[] dtoSecTicksForUis = {null};
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
                            dtoSecTicksForUis[0] = Constants.GSON_INSTANCE.fromJson(json, DTOSecTicksForUi.class);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        return dtoSecTicksForUis[0];
    }

    public DTOIncludeForResultsPrimaryForUi getPrimaryResults (String userName, Integer executionID) {
        final DTOIncludeForResultsPrimaryForUi[] dtoIncludeForResultsPrimaryForUis = {null};
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
                            dtoIncludeForResultsPrimaryForUis[0] = Constants.GSON_INSTANCE.fromJson(json, DTOIncludeForResultsPrimaryForUi.class);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        return dtoIncludeForResultsPrimaryForUis[0];
    }

    public DTOIncludeForResultsAdditionalForUi getAdditionalResults(String userName, Integer executionID, String entityName, String propertyName){
        final DTOIncludeForResultsAdditionalForUi[] dtoIncludeForResultsAdditionalForUi = {null};

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
                            dtoIncludeForResultsAdditionalForUi[0] = Constants.GSON_INSTANCE.fromJson(json, DTOIncludeForResultsAdditionalForUi.class);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }                }
            }
        });
        return dtoIncludeForResultsAdditionalForUi[0];
    }
    public List<DTOSimulationEndingForUi> getSimulationEndingListFromServer(String userName) {
        final List<DTOSimulationEndingForUi>[] simulationEndingList = new List[]{null};

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
                            simulationEndingList[0] = Constants.GSON_INSTANCE.fromJson(response.body().string(), listType);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        return simulationEndingList[0];
    }
}
