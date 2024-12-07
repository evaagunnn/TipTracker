package com.example.tiptracker.controllers;

import com.example.tiptracker.models.Tip;
import com.example.tiptracker.utils.DataManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.time.LocalDate;

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

    private ObservableList<Tip> tips;

    @FXML
    public void initialize() {
        System.out.println("Initializing TipController...");
        System.out.println("Tip Table: " + tipTable);
        System.out.println("Date Picker: " + datePicker);

        tips = FXCollections.observableArrayList(DataManager.getTips());
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        amountColumn.setCellValueFactory(new PropertyValueFactory<>("amount"));
        notesColumn.setCellValueFactory(new PropertyValueFactory<>("notes"));
        tipTable.setItems(tips);
    }
    @FXML
    public void test(){
    }

    @FXML
    public void addTip() {
        // Validate inputs
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
            System.out.println(newTip);
            tips.add(newTip);

            datePicker.setValue(null);
            amountField.clear();
            notesField.clear();
        } catch (NumberFormatException e) {
            showError("Amount must be a valid number.");
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
