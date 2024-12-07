package com.example.tiptracker.controllers;

import com.example.tiptracker.Main;
import com.example.tiptracker.models.Tip;
import com.example.tiptracker.utils.DataManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.time.format.DateTimeFormatter;
import java.util.List;

public class GraphTipsController {

    @FXML
    private BarChart<String, Number> tipsChart;
    @FXML
    private CategoryAxis xAxis;
    @FXML
    private NumberAxis yAxis;

    private Stage stage;

    @FXML
    public void initialize() {
        // Set up the axes
        xAxis.setLabel("Date");
        yAxis.setLabel("Amount");

        // Set up the data series for the chart
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Tips");

        // Get all tips from the DataManager
        List<Tip> tips = DataManager.getTips();

        // Add data to the series
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        for (Tip tip : tips) {
            String dateStr = tip.getDate().format(formatter);  // Format the date as string
            series.getData().add(new XYChart.Data<>(dateStr, tip.getAmount()));
        }

        tipsChart.getData().add(series);
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    public void goBackToMainMenu() {
        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("MainMenuView.fxml"));
            Scene mainMenuScene = new Scene(loader.load());

            MainMenuController controller = loader.getController();
            controller.setStage(stage);

            stage.setScene(mainMenuScene);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
