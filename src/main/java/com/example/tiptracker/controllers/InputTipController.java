package com.example.tiptracker.controllers;

import com.example.tiptracker.Main;
import com.example.tiptracker.models.Tip;
import com.example.tiptracker.utils.DataManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;

public class InputTipController {
    @FXML
    private DatePicker datePicker;
    @FXML
    private TextField amountField;
    @FXML
    private TextArea notesField;
    private ObservableList<Tip> tips;

    private Stage stage;

    @FXML
    public void initialize() {
        tips = FXCollections.observableArrayList(DataManager.getTips());
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    public void addTip() {
        LocalDate date = datePicker.getValue();
        String amountText = amountField.getText();
        String notes = notesField.getText();

        if (date == null || amountText.isEmpty()) {
            showError("Date and amount are required.");
            return;
        }

        try {
            double amount = Double.parseDouble(amountText);

            Tip newTip = new Tip(date, amount, notes);
            DataManager.addTip(newTip);
            tips.add(newTip);

            datePicker.setValue(null);
            amountField.clear();
            notesField.clear();
        } catch (NumberFormatException e) {
            showError("Amount must be a valid number.");
        }
    }

    @FXML
    public void goBackToMainMenu() {
        try {
            // Load the MainMenuView.fxml and set the controller
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("MainMenuView.fxml"));
            Scene mainMenuScene = new Scene(loader.load());

            MainMenuController controller = loader.getController();
            controller.setStage(stage);

            stage.setScene(mainMenuScene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Input Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}

