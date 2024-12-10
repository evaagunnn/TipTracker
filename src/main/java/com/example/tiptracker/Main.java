package com.example.tiptracker;

import com.example.tiptracker.controllers.MainMenuController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class  Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("MainMenuView.fxml"));
        Scene mainScene = new Scene(loader.load(), 800,600);


        MainMenuController controller = loader.getController();
        controller.setStage(primaryStage);

        primaryStage.setScene(mainScene);
        primaryStage.setTitle("Tip Tracker");
        primaryStage.setFullScreen(true);

        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
