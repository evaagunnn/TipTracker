package com.example.tiptracker.controllers;

import com.example.tiptracker.Main;
import com.example.tiptracker.models.Tip;
import com.example.tiptracker.utils.DataManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

public class GraphTipsController {

    @FXML
    private VBox topChartContainer; // Top chart pane
    @FXML
    private VBox bottomChartContainer; // Bottom chart pane
    @FXML
    private DatePicker topStartPicker, topEndPicker;
    @FXML
    private DatePicker bottomStartPicker, bottomEndPicker;
    @FXML
    private ToggleButton topToggleButton, bottomToggleButton; // Toggles for charts

    private BarChart<String, Number> topBarChart;
    private LineChart<String, Number> topLineChart;
    private BarChart<String, Number> bottomBarChart;
    private LineChart<String, Number> bottomLineChart;
    private CategoryAxis topXAxis, bottomXAxis;
    private NumberAxis topYAxis, bottomYAxis;

    private Stage stage;

    @FXML
    public void initialize() {
        setupCharts();
    }

    private void setupCharts() {
        topXAxis = new CategoryAxis();
        topYAxis = new NumberAxis();
        topBarChart = new BarChart<>(topXAxis, topYAxis);
        topLineChart = new LineChart<>(topXAxis, topYAxis);

        bottomXAxis = new CategoryAxis();
        bottomYAxis = new NumberAxis();
        bottomBarChart = new BarChart<>(bottomXAxis, bottomYAxis);
        bottomLineChart = new LineChart<>(bottomXAxis, bottomYAxis);

        topChartContainer.getChildren().add(topBarChart);
        bottomChartContainer.getChildren().add(bottomBarChart);

        topXAxis.setLabel("Date");
        topYAxis.setLabel("Amount");
        bottomXAxis.setLabel("Date");
        bottomYAxis.setLabel("Amount");
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
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void updateTopChart() {
        LocalDate start = topStartPicker.getValue();
        LocalDate end = topEndPicker.getValue();

        if (!validateRange(start, end)) {
            showError("Please select a valid date range.");
            return;
        }

        List<Tip> tips = DataManager.getTips().stream()
                .filter(tip -> !tip.getDate().isBefore(start) && !tip.getDate().isAfter(end))
                .collect(Collectors.toList());

        updateChart(topToggleButton, topChartContainer, tips, "Top Range");
    }

    @FXML
    public void updateBottomChart() {
        LocalDate start = bottomStartPicker.getValue();
        LocalDate end = bottomEndPicker.getValue();

        if (!validateRange(start, end)) {
            showError("Please select a valid date range.");
            return;
        }

        List<Tip> tips = DataManager.getTips().stream()
                .filter(tip -> !tip.getDate().isBefore(start) && !tip.getDate().isAfter(end))
                .collect(Collectors.toList());

        updateChart(bottomToggleButton, bottomChartContainer, tips, "Bottom Range");
    }

    private void updateChart(ToggleButton toggleButton, VBox container, List<Tip> tips, String seriesName) {
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName(seriesName);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        for (Tip tip : tips) {
            String dateStr = tip.getDate().format(formatter);
            series.getData().add(new XYChart.Data<>(dateStr, tip.getAmount()));
        }

        if (toggleButton.isSelected()) { // Line chart
            LineChart<String, Number> lineChart = (container == topChartContainer) ? topLineChart : bottomLineChart;
            switchToChart(container, lineChart);
            lineChart.getData().clear();
            lineChart.getData().add(series);
        } else { // Bar chart
            BarChart<String, Number> barChart = (container == topChartContainer) ? topBarChart : bottomBarChart;
            switchToChart(container, barChart);
            barChart.getData().clear();
            barChart.getData().add(series);
        }
    }

    private void switchToChart(VBox container, XYChart<String, Number> chart) {
        container.getChildren().setAll(chart);
    }

    private boolean validateRange(LocalDate start, LocalDate end) {
        return start != null && end != null && !start.isAfter(end);
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
