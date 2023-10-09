package after.login.component.body.running.server;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import dto.definition.rule.action.KillActionDTO;
import dto.definition.rule.action.ProximityActionDTO;
import dto.definition.rule.action.ReplaceActionDTO;
import dto.definition.rule.action.SetActionDTO;
import dto.definition.rule.action.api.AbstractActionDTO;
import dto.definition.rule.action.condition.MultipleConditionActionDTO;
import dto.definition.rule.action.condition.SingleConditionActionDTO;
import dto.definition.rule.action.numeric.DecreaseActionDTO;
import dto.definition.rule.action.numeric.IncreaseActionDTO;
import dto.definition.rule.action.numeric.calculation.DivideActionDTO;
import dto.definition.rule.action.numeric.calculation.MultiplyActionDTO;
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
import java.util.HashMap;
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
                Platform.runLater(() -> popUpWindow(e.getMessage(), "Simulations status failure!"));
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if (response.code() != 200) {
                    String responseBody = response.body().string();
                    Platform.runLater(() -> popUpWindow(responseBody, "Simulations status error!"));
                } else {
                    // Read and process the response content
                    try (ResponseBody responseBody = response.body()) {
                        if (responseBody != null) {
                            String json = response.body().string();
                            Type mapType = new TypeToken<Map<Integer, String>>() {
                            }.getType();
                            Map<Integer, String> simulationIdToStatuses = new HashMap<>();
                            simulationIdToStatuses.putAll(Constants.GSON_INSTANCE.fromJson(json, mapType));
                            //Map<Integer, String> simulationIdToStatuses = Constants.GSON_INSTANCE.fromJson(json, mapType);
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
                Platform.runLater(() -> popUpWindow(e.getMessage(), "Execution progress failure!"));
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if (response.code() != 200) {
                    String responseBody = response.body().string();
                    Platform.runLater(() -> popUpWindow(responseBody, "Execution progress error!"));
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
                "action="+action;

        Request request = new Request.Builder()
                .url(Constants.CONTROL_EXECUTION_PAGE)
                .post(RequestBody.create(body.getBytes()))
                .build();

        Call call = HTTP_CLIENT_PUBLIC.newCall(request);
        call.enqueue( new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Platform.runLater(() -> popUpWindow(e.getMessage(), "Control request failure!"));
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if (response.code() != 200) {
                    String responseBody = response.body().string();
                    Platform.runLater(() -> popUpWindow(responseBody, "Control request error!"));
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
                Platform.runLater(() -> popUpWindow(e.getMessage(), "TotalSecAndTick failure!"));
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if (response.code() != 200) {
                    String responseBody = response.body().string();
                    Platform.runLater(() -> popUpWindow(responseBody, "TotalSecAndTick error!"));
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
                Platform.runLater(() -> popUpWindow(e.getMessage(), "Primary results failure!"));
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if (response.code() != 200) {
                    String responseBody = response.body().string();
                    Platform.runLater(() -> popUpWindow(responseBody, "Primary results error!"));
                } else {
                    // Read and process the response content
                    try (ResponseBody responseBody = response.body()) {
                        if (responseBody != null) {
                            String json = response.body().string();

                            GsonBuilder gsonBuilder = new GsonBuilder();
                            Gson gson = gsonBuilder.create();
                            gsonBuilder.registerTypeAdapter(AbstractActionDTO.class, new AbstractActionDTOTypeAdapter(gson));
                            gson = gsonBuilder.create();

                            DTOIncludeForResultsPrimaryForUi dtoIncludeForResultsPrimaryForUis = gson.fromJson(json, DTOIncludeForResultsPrimaryForUi.class);
                            resultsPrimaryConsumer.accept(dtoIncludeForResultsPrimaryForUis);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
    public class AbstractActionDTOTypeAdapter extends TypeAdapter<AbstractActionDTO> {
        private final Gson gson;
        public AbstractActionDTOTypeAdapter(Gson gson) {
            this.gson = gson;
        }
        @Override
        public void write(JsonWriter out, AbstractActionDTO value) throws IOException {
            // Implement serialization logic here if needed
        }

        @Override
        public AbstractActionDTO read(JsonReader in) throws IOException {
            JsonElement jsonElement = gson.getAdapter(JsonElement.class).read(in);

            if (jsonElement.isJsonObject()) {
                JsonObject jsonObject = jsonElement.getAsJsonObject();
                JsonElement actionTypeElement = jsonObject.get("actionType");

                if (actionTypeElement != null && actionTypeElement.isJsonPrimitive()) {
                    String actionType = actionTypeElement.getAsString();

                    switch (actionType) {
                        case "INCREASE":
                            return gson.fromJson(jsonElement, IncreaseActionDTO.class);
                        case "DECREASE":
                            return gson.fromJson(jsonElement, DecreaseActionDTO.class);
                        case "SET":
                            return gson.fromJson(jsonElement, SetActionDTO.class);
                        case "DIVIDE":
                            return gson.fromJson(jsonElement, DivideActionDTO.class);
                        case "MULTIPLY":
                            return gson.fromJson(jsonElement, MultiplyActionDTO.class);
                        case "SINGLE":
                            return gson.fromJson(jsonElement, SingleConditionActionDTO.class);
                        case "MULTIPLE":
                            return gson.fromJson(jsonElement, MultipleConditionActionDTO.class);
                        case "KILL":
                            return gson.fromJson(jsonElement, KillActionDTO.class);
                        case "PROXIMITY":
                            return gson.fromJson(jsonElement, ProximityActionDTO.class);
                        case "REPLACE":
                            return gson.fromJson(jsonElement, ReplaceActionDTO.class);
                    }

                }
            }

            throw new JsonParseException("Invalid AbstractActionDTO JSON: " + jsonElement);
        }
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
                Platform.runLater(() -> popUpWindow(e.getMessage(), "Additional results failure!"));
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if (response.code() != 200) {
                    String responseBody = response.body().string();
                    Platform.runLater(() -> popUpWindow(responseBody, "Additional results error!"));
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
                Platform.runLater(() -> popUpWindow(e.getMessage(), "Simulation ending failure!"));
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if (response.code() != 200) {
                    String responseBody = response.body().string();
                    Platform.runLater(() -> popUpWindow(responseBody, "Simulation ending error!"));
                } else {
                    // Read and process the response content
                    try (ResponseBody responseBody = response.body()) {
                        if (responseBody != null) {
                            String json = response.body().string();
                            Type listType = new TypeToken<List<DTOSimulationEndingForUi>>() {}.getType();
                            //List<DTOSimulationEndingForUi> simulationEndingList = new ArrayList<>();
                            List<DTOSimulationEndingForUi> simulationEndingList = (Constants.GSON_INSTANCE.fromJson(json, listType));
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
                Platform.runLater(() -> popUpWindow(e.getMessage(), "Termination conditions failure!"));
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if (response.code() != 200) {
                    String responseBody = response.body().string();
                    Platform.runLater(() -> popUpWindow(responseBody, "Termination conditions error!"));
                } else {
                    // Read and process the response content
                    try (ResponseBody responseBody = response.body()) {
                        if (responseBody != null) {
                            String json = response.body().string();
                            GsonBuilder gsonBuilder = new GsonBuilder();
                            Gson gson = gsonBuilder.create();
                            gsonBuilder.registerTypeAdapter(TerminationConditionsDTO.class, new TerminationConditionsDTOTypeAdapter(gson));
                            gson = gsonBuilder.create();


                            Type listType = new TypeToken<List<TerminationConditionsDTO>>() {}.getType();
                            List<TerminationConditionsDTO> terminationConditionsDTOList = gson.fromJson(json, listType);
                            terminationConditionsListConsumer.accept(terminationConditionsDTOList);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    public class TerminationConditionsDTOTypeAdapter extends TypeAdapter<TerminationConditionsDTO> {
        private final Gson gson;
        public TerminationConditionsDTOTypeAdapter(Gson gson) {
            this.gson = gson;
        }
        @Override
        public void write(JsonWriter out, TerminationConditionsDTO value) throws IOException {
            // Implement serialization logic here if needed
        }

        @Override
        public TerminationConditionsDTO read(JsonReader in) throws IOException {
            JsonElement jsonElement = gson.getAdapter(JsonElement.class).read(in);

            if (jsonElement.isJsonObject()) {
                JsonObject jsonObject = jsonElement.getAsJsonObject();
                JsonElement actionTypeElement = jsonObject.get("type");

                if (actionTypeElement != null && actionTypeElement.isJsonPrimitive()) {
                    String actionType = actionTypeElement.getAsString();

                    switch (actionType) {
                        case "Seconds":
                            return gson.fromJson(jsonElement, TimeTerminationConditionsDTOImpl.class);
                        case "Ticks":
                            return gson.fromJson(jsonElement, TicksTerminationConditionsDTOImpl.class);
                        case "ByUser":
                            return gson.fromJson(jsonElement, ByUserTerminationConditionDTOImpl.class);
                    }

                }
            }

            throw new JsonParseException("Invalid AbstractActionDTO JSON: " + jsonElement);
        }
    }
}
