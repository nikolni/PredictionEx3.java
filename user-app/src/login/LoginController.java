package login;

import after.login.main.UserController;
import util.constants.Constants;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import okhttp3.*;
import org.jetbrains.annotations.NotNull;
import util.http.HttpClientUtil;

import java.io.IOException;
import java.net.URL;


public class LoginController {

    @FXML
    public TextField userNameTextField;

    @FXML
    public Label errorMessageLabel;

    private final StringProperty errorMessageProperty = new SimpleStringProperty();
    private Stage primaryStage;

    @FXML
    public void initialize() {
        errorMessageLabel.textProperty().bind(errorMessageProperty);
        /*HttpClientUtil.setCookieManagerLoggingFacility(line ->
                Platform.runLater(() ->
                        updateHttpStatusLine(line)));*/
    }

    @FXML
    private void loginButtonClicked(ActionEvent event) {

        String userName = userNameTextField.getText();
        if (userName.isEmpty()) {
            errorMessageProperty.set("User name is empty. You can't login with empty user name");
            return;
        }

        //noinspection ConstantConditions
        String finalUrl = HttpUrl
                .parse(Constants.LOGIN_PAGE)
                .newBuilder()
                .addQueryParameter("username", userName)
                .build()
                .toString();


        HttpClientUtil.runAsync(finalUrl, new Callback() {

            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Platform.runLater(() ->
                        errorMessageProperty.set("Something went wrong: " + e.getMessage())
                );
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if (response.code() != 200) {
                    String responseBody = response.body().string();
                    Platform.runLater(() ->
                            errorMessageProperty.set("Something went wrong: " + responseBody)
                    );
                } else {
                    Platform.runLater(() ->
                            openUserAppAfterLogin()
                    );

                }
            }
        });
    }

    @FXML
    private void userNameKeyTyped(KeyEvent event) {
        errorMessageProperty.set("");
    }

    @FXML
    private void quitButtonClicked(ActionEvent e) {
        Platform.exit();
    }
    public void setPrimaryStage(Stage primaryStage){
        this.primaryStage = primaryStage;
    }
    private void openUserAppAfterLogin(){
        primaryStage.close();
        popUpForSuccessfulLogin();
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            URL url = getClass().getResource("/after/login/main/user.fxml");
            fxmlLoader.setLocation(url);
            Parent root = fxmlLoader.load(url.openStream());
            UserController userController = fxmlLoader.getController();
            userController.setUserName(userNameTextField.getText());
            userController.initialPrimaryForTabPaneComponenets();

            Scene scene = new Scene(root, 850, 680);
            primaryStage.setScene(scene);
            primaryStage.show();
        }catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void popUpForSuccessfulLogin(){
        Stage SuccessfulLoginStage = new Stage();
        String message = "You have successfully registered to the system!";

        Label label = new Label(message);
        Font font = new Font(16);
        label.setFont(font);
        VBox vbox = new VBox(label);
        vbox.setAlignment(Pos.CENTER);
        StackPane root = new StackPane(vbox);
        Scene scene = new Scene(root, 350, 250);

        SuccessfulLoginStage.setScene(scene);
        SuccessfulLoginStage.setTitle("Welcome!");
        SuccessfulLoginStage.show();
    }
}
