package admin.component.body.allocation.main;


import admin.util.constants.Constants;
import dto.definition.user.request.DTOUserRequestForUi;
import javafx.application.Platform;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;

import admin.util.http.HttpClientUtil;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.TimerTask;
import java.util.function.Consumer;


public class RequestsGridRefresher extends TimerTask {
    private final Consumer<List<DTOUserRequestForUi>> usersListConsumer;
    private int requestNumber;

    public RequestsGridRefresher(Consumer<List<DTOUserRequestForUi>> usersListConsumer) {
        this.usersListConsumer = usersListConsumer;
        this.requestNumber = 0;
    }

    @Override
    public void run() {

        final int finalRequestNumber = ++requestNumber;
        HttpClientUtil.runAsync(Constants.ALL_REQUESTS_PAGE, new Callback() {

            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Platform.runLater(() -> {
                    Constants.popUpWindow(e.getMessage(), "Requests update failure!");
                });
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String jsonArrayOfUsersNames = response.body().string();
                DTOUserRequestForUi[] allRequests = Constants.GSON_INSTANCE.fromJson(jsonArrayOfUsersNames, DTOUserRequestForUi[].class);
                usersListConsumer.accept(Arrays.asList(allRequests));
            }
        });
    }
}
