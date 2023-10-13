package admin.util.constants;

import com.google.gson.Gson;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import okhttp3.OkHttpClient;

public class Constants {

    // global constants
    public final static String LINE_SEPARATOR = System.getProperty("line.separator");
    public final static String JHON_DOE = "<Anonymous>";
    public final static int REFRESH_RATE = 2000;

    // Server resources locations
    public final static String BASE_DOMAIN = "localhost";
    public final static String BASE_URL = "http://" + BASE_DOMAIN + ":8080";
    public final static String CONTEXT_PATH = "/predictionsApp";
    public final static String FULL_SERVER_PATH = BASE_URL + CONTEXT_PATH;

    public final static String SIMULATION_NAMES_LIST_PAGE = FULL_SERVER_PATH + "/simulation/names";
    public final static String ALL_REQUESTS_PAGE = FULL_SERVER_PATH + "/adminallrequests";
    public final static String SIMULATION_DETAILS_PAGE = FULL_SERVER_PATH + "/simulation/details";
    public final static String THREAD_POOL_STATUS_PAGE = FULL_SERVER_PATH + "/thread/pool";
    public final static String FILE_PAGE = FULL_SERVER_PATH + "/upload/file";
    public final static String ALL_EXECUTIONS_RESULTS_PAGE = FULL_SERVER_PATH + "/all/executions/results";
    public final static String ALL_EXECUTIONS_STATUSES_PAGE = FULL_SERVER_PATH + "/all/executions/statuses";
    public final static String RESULTS_PAGE = FULL_SERVER_PATH + "/execution/results";

    public final static OkHttpClient HTTP_CLIENT = new OkHttpClient();
    // GSON instance
    public final static Gson GSON_INSTANCE = new Gson();

    public static void popUpWindow(String massage, String title){
        Stage errorStage = new Stage();

        Label label = new Label(massage);
        label.setWrapText(true);
        Font font = new Font(16);
        label.setFont(font);
        VBox vbox = new VBox(label);
        vbox.setAlignment(Pos.CENTER);
        StackPane root = new StackPane(vbox);
        Scene scene = new Scene(root, 450, 150);

        errorStage.setScene(scene);
        errorStage.setTitle(title);
        errorStage.show();
    }
}
