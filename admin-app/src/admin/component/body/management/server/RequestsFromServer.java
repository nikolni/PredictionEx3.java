package admin.component.body.management.server;

import admin.util.constants.Constants;
import com.google.gson.*;
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
import dto.definition.user.request.DTOUserRequestForUi;
import dto.include.DTOIncludeSimulationDetailsForUi;
import dto.primary.DTOThreadsPoolStatusForUi;
import javafx.application.Platform;
import okhttp3.*;
import org.jetbrains.annotations.NotNull;
import admin.util.http.HttpClientUtil;

import java.io.IOException;
import java.util.Arrays;
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
                Platform.runLater(() -> popUpWindow(e.getMessage(), "Thread pool failure!"));
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if (response.code() != 200) {
                    String responseBody = response.body().string();
                    Platform.runLater(() -> popUpWindow(responseBody, "Thread pool error!"));
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
                Platform.runLater(() -> popUpWindow(e.getMessage(), "Thread pool size failure!"));
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if (response.code() != 200) {
                    String responseBody = response.body().string();
                    Platform.runLater(() -> popUpWindow(responseBody, "Thread pool size error!"));
                } else {
                    Platform.runLater(() -> popUpWindow("Your request has been received and is being processed", "Request was accepted"));
                }
            }
        });
    }

    private Consumer<DTOIncludeSimulationDetailsForUi> dtoIncludeSimulationDetailsForUiConsumer;

    public void setDTOIncludeSimulationDetailsForUi(Consumer<DTOIncludeSimulationDetailsForUi> dtoIncludeSimulationDetailsForUiConsumer){
        this.dtoIncludeSimulationDetailsForUiConsumer = dtoIncludeSimulationDetailsForUiConsumer;
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
                Platform.runLater(() -> Constants.popUpWindow(e.getMessage(), "Simulation details failure!"));
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if (response.code() != 200) {
                    String responseBody = response.body().string();
                    Platform.runLater(() -> Constants.popUpWindow(responseBody, "Simulation details error!"));
                } else {
                    // Read and process the response content
                    try (ResponseBody responseBody = response.body()) {
                        if (responseBody != null) {
                            String json = response.body().string();

                            GsonBuilder gsonBuilder = new GsonBuilder();
                            Gson gson = gsonBuilder.create();
                            gsonBuilder.registerTypeAdapter(AbstractActionDTO.class, new AbstractActionDTOTypeAdapter(gson));
                            gson = gsonBuilder.create();

                            DTOIncludeSimulationDetailsForUi simulationDetails = gson.fromJson(json, DTOIncludeSimulationDetailsForUi.class);
                            dtoIncludeSimulationDetailsForUiConsumer.accept(simulationDetails);
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
    private Consumer<List<String>> newSimulationsNamesConsumer;

    public void setNewSimulationsNamesConsumer(Consumer<List<String>> newSimulationsNamesConsumer){
        this.newSimulationsNamesConsumer = newSimulationsNamesConsumer;
    }
    public void getNewSimulationsNames(String[] simulationsArray){
        String json = Constants.GSON_INSTANCE.toJson(simulationsArray);

        Request request = new Request.Builder()
                .url(Constants.SIMULATION_NAMES_LIST_PAGE)
                .post(RequestBody.create(json.getBytes()))
                .build();

        Call call = HttpClientUtil.HTTP_CLIENT_PUBLIC.newCall(request);
        call.enqueue( new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Platform.runLater(() -> Constants.popUpWindow(e.getMessage(), "New simulations names failure!"));
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if (response.code() != 200) {
                    String responseBody = response.body().string();
                    Platform.runLater(() -> Constants.popUpWindow(responseBody, "New simulations names error!"));
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

