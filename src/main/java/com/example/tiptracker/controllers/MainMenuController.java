package com.example.tiptracker.controllers;

import com.example.tiptracker.Main;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainMenuController {
    private Stage stage;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    public void goToInputTip() {
        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("InputTipsView.fxml"));
            Scene inputTipScene = new Scene(loader.load());

            InputTipController controller = loader.getController();
            controller.setStage(stage);

            stage.setScene(inputTipScene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void goToGetTips() {
        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("TipTrackerView.fxml"));
            Scene tipScene = new Scene(loader.load());

            TipController controller = loader.getController();
            controller.setStage(this.stage);

            this.stage.setScene(tipScene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void goToGraphTips() {
        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("GraphTips.fxml"));
            Scene graphTipsScene = new Scene(loader.load());

            GraphTipsController controller = loader.getController();
            controller.setStage(stage);

            stage.setScene(graphTipsScene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

