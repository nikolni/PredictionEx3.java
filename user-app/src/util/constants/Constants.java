package util.constants;

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
    private final static String BASE_URL = "http://" + BASE_DOMAIN + ":8080";
    private final static String CONTEXT_PATH = "/predictionsApp";
    private final static String FULL_SERVER_PATH = BASE_URL + CONTEXT_PATH;

    public final static String LOGIN_PAGE = FULL_SERVER_PATH + "/login";
    public final static String USER_REQUEST_PAGE = FULL_SERVER_PATH + "/userrequest";
    public final static String SIMULATION_NAMES_LIST_PAGE = FULL_SERVER_PATH + "/simulationnames";
    public final static String DATA_EXECUTION_PAGE = FULL_SERVER_PATH + "/dataexecution";
    public final static String EXECUTION_PAGE = FULL_SERVER_PATH + "/execution";
    public final static String RERUN_PAGE = FULL_SERVER_PATH + "/rerun";
    public final static String EXECUTIONS_STATUS_PAGE = FULL_SERVER_PATH + "/executions/update";



    // GSON instance
    public final static Gson GSON_INSTANCE = new Gson();

    public final static void popUpWindow(String massage, String title){
        Stage emptyFieldsErrorStage = new Stage();

        Label label = new Label(massage);
        Font font = new Font(16);
        label.setFont(font);
        VBox vbox = new VBox(label);
        vbox.setAlignment(Pos.CENTER);
        StackPane root = new StackPane(vbox);
        Scene scene = new Scene(root, 350, 250);

        emptyFieldsErrorStage.setScene(scene);
        emptyFieldsErrorStage.setTitle(title);
        emptyFieldsErrorStage.show();
    }
}
