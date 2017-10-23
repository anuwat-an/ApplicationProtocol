package sample.mo_sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;


public class StartApp extends Application {


    @Override
    public void start(Stage primaryStage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("mainPage.fxml"));
        primaryStage.setTitle("4 in a row game");
        primaryStage.setScene(new Scene(root, 243, 128));
        primaryStage.show();

    }


    public static void main(String[] args) {
        launch(args);
    }
}

