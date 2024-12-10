package com.example.tiptracker.controllers;

import com.example.tiptracker.Main;
import com.example.tiptracker.models.Tip;
import com.example.tiptracker.utils.DataManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Comparator;

public class TipController {
    @FXML
    private TableView<Tip> tipTable;
    @FXML
    private TableColumn<Tip, LocalDate> dateColumn;
    @FXML
    private TableColumn<Tip, Double> amountColumn;
    @FXML
    private TableColumn<Tip, String> notesColumn;
    @FXML
    private DatePicker datePicker;
    @FXML
    private TextField amountField;
    @FXML
    private TextArea notesField;
    @FXML
    private Button addButton;
    @FXML
    private Button deleteButton;
    @FXML
    private Button sortByDateButton;
    @FXML
    private Button sortByAmountButton;

    private ObservableList<Tip> tips;
    private Stage stage;

    @FXML
    public void initialize() {
        tips = FXCollections.observableArrayList(DataManager.getTips());
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        amountColumn.setCellValueFactory(new PropertyValueFactory<>("amount"));
        notesColumn.setCellValueFactory(new PropertyValueFactory<>("notes"));
        tipTable.setItems(tips);
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
            controller.setStage(this.stage);

            this.stage.setScene(mainMenuScene);
        } catch (IOException e) {
            e.printStackTrace();
        }
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
    public void deleteTip() {
        Tip selectedTip = tipTable.getSelectionModel().getSelectedItem();
        if (selectedTip != null) {
            DataManager.removeTip(selectedTip);
            tips.remove(selectedTip);
        } else {
            showError("Please select a tip to delete.");
        }
    }

    @FXML
    public void sortByDate() {
        FXCollections.sort(tips, Comparator.comparing(Tip::getDate));
    }

    @FXML
    public void sortByAmount() {
        FXCollections.sort(tips, Comparator.comparing(Tip::getAmount));
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}


