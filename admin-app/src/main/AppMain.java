package main;

import admin.main.AdminController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;

import static main.CommonResourcesPaths.ADMIN_FXML_INCLUDE_RESOURCE;


public class AppMain extends Application {

    public static void main(String[] args) {
        Thread.currentThread().setName("main");
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
            FXMLLoader fxmlLoader = new FXMLLoader();
            URL url = getClass().getResource(ADMIN_FXML_INCLUDE_RESOURCE);
            fxmlLoader.setLocation(url);
            Parent root = fxmlLoader.load(url.openStream());

            AdminController adminController = fxmlLoader.getController();

            Scene scene = new Scene(root, 800, 600);
            primaryStage.setScene(scene);
            primaryStage.show();
        }

}