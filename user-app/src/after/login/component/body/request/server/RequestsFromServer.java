package after.login.component.body.request.server;

import com.google.gson.reflect.TypeToken;
import dto.definition.user.request.DTOUserRequestForUi;
import javafx.application.Platform;
import okhttp3.*;
import org.jetbrains.annotations.NotNull;
import util.constants.Constants;
import util.http.HttpClientUtil;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;

import static util.constants.Constants.LINE_SEPARATOR;
import static util.constants.Constants.popUpWindow;
import static util.http.HttpClientUtil.HTTP_CLIENT_PUBLIC;

public class RequestsFromServer {
    public void postRequestToServer(String simulationName, String numberOfExecutions, String terminationConditions,
                                    String userName){
        String body = "simulation_name="+simulationName + LINE_SEPARATOR +
                "number_of_executions="+numberOfExecutions + LINE_SEPARATOR +
                "termination_conditions="+terminationConditions;

        Request request = new Request.Builder()
                .url(Constants.USER_REQUEST_PAGE)
                .post(RequestBody.create(body.getBytes()))
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
                    Platform.runLater(() -> popUpWindow("Your request has been received and is being processed", "Request was accepted"));
                }
            }
        });
    }
    public List<String> getSimulationNamesFromServer() {
        final List<String>[] simulationNames = new List[]{null};

        String finalUrl = HttpUrl
                .parse(Constants.SIMULATION_NAMES_LIST_PAGE)
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
                            simulationNames[0] = Arrays.asList(Constants.GSON_INSTANCE.fromJson(json, String[].class));
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        return simulationNames[0];
    }

    public List<DTOUserRequestForUi> getUserRequestListFromServer(String userName) {
        final List<DTOUserRequestForUi>[] userRequestList = new List[]{null};
        String finalUrl = HttpUrl
                .parse(Constants.USER_REQUEST_PAGE)
                .newBuilder()
                .addQueryParameter("user_name", userName)
                .build()
                .toString();

        HttpClientUtil.runAsync(finalUrl, new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                popUpWindow(e.getMessage(), "Error!");
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if (response.code() != 200) {
                    String responseBody = response.body().string();
                    popUpWindow(responseBody, "Error!");
                } else {
                    // Read and process the response content
                    try (ResponseBody responseBody = response.body()) {
                        if (responseBody != null) {
                            String json = response.body().string();
                            Type listType = new TypeToken<List<DTOUserRequestForUi>>() {
                            }.getType();
                            userRequestList[0] = Constants.GSON_INSTANCE.fromJson(json, listType);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        return userRequestList[0];
    }
}
