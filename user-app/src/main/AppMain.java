package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import login.LoginController;

import java.net.URL;

import static main.CommonResourcesPaths.LOGIN_FXML_INCLUDE_RESOURCE;

public class AppMain extends Application {



    public static void main(String[] args) {
        Thread.currentThread().setName("main");
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
            FXMLLoader fxmlLoader = new FXMLLoader();
            URL url = getClass().getResource(LOGIN_FXML_INCLUDE_RESOURCE);
            fxmlLoader.setLocation(url);
            Parent root = fxmlLoader.load(url.openStream());

            LoginController loginController = fxmlLoader.getController();
            loginController.setPrimaryStage(primaryStage);
            Scene scene = new Scene(root, 300, 190);
            primaryStage.setScene(scene);
            primaryStage.show();


        }

}