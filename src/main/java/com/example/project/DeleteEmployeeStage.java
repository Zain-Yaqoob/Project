package com.example.project;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class DeleteEmployeeStage {

    private Stage menuStage; // Reference to MenuStage

    public DeleteEmployeeStage(Stage menuStage) {
        this.menuStage = menuStage; // Save reference to menuStage passed from MenuStage
    }

    public void show() {
        // Create a new stage for deleting an employee
        Stage deleteEmployeeStage = new Stage();
        deleteEmployeeStage.setTitle("Delete Employee");

        // Create a VBox layout for the delete employee form
        VBox layout = new VBox(15); // 15px spacing between fields
        layout.setStyle("-fx-background-color: linear-gradient(to bottom right, #0f0c29, #302b63, #24243e); -fx-padding: 20px;");
        layout.setAlignment(Pos.CENTER);

        // Define style for the UI elements
        String labelStyle = "-fx-text-fill: white; -fx-font-size: 14px; -fx-font-weight: bold;";
        String textFieldStyle = "-fx-background-color: white; -fx-border-radius: 10px; -fx-padding: 10px; -fx-font-size: 14px;";
        String buttonStyle = "-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-size: 16px; -fx-padding: 12px 20px; -fx-border-radius: 8px;";

        // Create a Label and TextField for employee ID input
        Label employeeIdLabel = new Label("Enter Employee ID to Delete:");
        employeeIdLabel.setStyle(labelStyle);
        TextField employeeIdField = new TextField();
        employeeIdField.setStyle(textFieldStyle);

        // Create Labels to display employee details (initially hidden)
        Label nameLabel = new Label("Name: ");
        nameLabel.setStyle(labelStyle);
        Label ageLabel = new Label("Age: ");
        ageLabel.setStyle(labelStyle);
        Label cnicLabel = new Label("CNIC: ");
        cnicLabel.setStyle(labelStyle);

        // Initially disable the delete button
        Button deleteButton = new Button("Delete");
        deleteButton.setStyle(buttonStyle);
        deleteButton.setDisable(true);

        // Back Button (Top Right Corner)
        Button backButton = new Button("Back");
        backButton.setStyle("-fx-background-color: #4569a0; -fx-text-fill: white; -fx-font-size: 16px; -fx-padding: 12px 20px; -fx-border-radius: 8px;");
        backButton.setOnAction(e -> {
            deleteEmployeeStage.close(); // Close the DeleteEmployeeStage
            menuStage.show(); // Show the MenuStage again
        });

        // Add event handler for the "Search" button
        Button searchButton = new Button("Search");
        searchButton.setStyle(buttonStyle);
        searchButton.setOnAction(e -> handleSearchAction(employeeIdField, nameLabel, ageLabel, cnicLabel, deleteButton));

        // Add event handler for the "Delete" button
        deleteButton.setOnAction(e -> handleDeleteAction(employeeIdField, deleteButton, nameLabel, ageLabel, cnicLabel));

        // Add Display Button
        Button displayButton = new Button("Display");
        displayButton.setStyle(buttonStyle);
        displayButton.setOnAction(e -> {
            deleteEmployeeStage.close();
            new ViewLiveInEmployeesStage(deleteEmployeeStage).show();
        });

        // Create an HBox for Delete and Display buttons
        HBox buttonBox = new HBox(10); // 10px spacing
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.getChildren().addAll(deleteButton, displayButton);

        // Create an HBox to position the Back button at the top-right
        HBox topBar = new HBox();
        topBar.setAlignment(Pos.TOP_RIGHT); // Align the button to the top-right
        topBar.getChildren().add(backButton); // Add the Back button to the top bar

        // Add the input field, search button, labels, and buttons to the layout
        layout.getChildren().addAll(topBar, employeeIdLabel, employeeIdField, searchButton, nameLabel, ageLabel, cnicLabel, buttonBox);

        // Create and show the scene for the delete employee stage
        Scene scene = new Scene(layout, 500, 500);
        deleteEmployeeStage.setScene(scene);
        deleteEmployeeStage.show();
    }

    private void handleSearchAction(TextField employeeIdField, Label nameLabel, Label ageLabel, Label cnicLabel, Button deleteButton) {
        String employeeId = employeeIdField.getText().trim();
        if (employeeId.isEmpty()) {
            showAlert("Error", "Employee ID is required", "Please enter the ID of the employee to search.", Alert.AlertType.ERROR);
            return;
        }

        // Search for employee in the file
        File employeeFile = new File("Employees.txt");
        boolean employeeFound = false;

        try (BufferedReader reader = new BufferedReader(new FileReader(employeeFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] employeeData = line.split(",");
                if (employeeData.length >= 4 && employeeData[0].equals(employeeId)) {
                    // Display employee details
                    nameLabel.setText("Name: " + employeeData[1]);
                    ageLabel.setText("Age: " + employeeData[2]);
                    cnicLabel.setText("CNIC: " + employeeData[3]);

                    // Enable delete button
                    deleteButton.setDisable(false);

                    employeeFound = true;
                    break;
                }
            }

            if (!employeeFound) {
                showAlert("Employee Not Found", null, "No employee found with the ID: " + employeeId, Alert.AlertType.ERROR);
                deleteButton.setDisable(true);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
            showAlert("Error", "Failed to Search", "There was an error reading the employee data.", Alert.AlertType.ERROR);
        }
    }

    private void handleDeleteAction(TextField employeeIdField, Button deleteButton, Label nameLabel, Label ageLabel, Label cnicLabel) {
        String employeeId = employeeIdField.getText().trim();
        if (employeeId.isEmpty()) {
            showAlert("Error", "Employee ID is required", "Please enter the ID of the employee to delete.", Alert.AlertType.ERROR);
            return;
        }

        // Search for employee in the file and delete the record
        File employeeFile = new File("Employees.txt");
        List<String> updatedContent = new ArrayList<>();
        boolean employeeFound = false;

        try (BufferedReader reader = new BufferedReader(new FileReader(employeeFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] employeeData = line.split(",");
                if (employeeData.length >= 4 && employeeData[0].equals(employeeId)) {
                    employeeFound = true; // Employee found, skipping the record to delete it
                    continue;
                }
                updatedContent.add(line); // Add other employees to the updated list
            }

            if (!employeeFound) {
                showAlert("Employee Not Found", null, "No employee found with the ID: " + employeeId, Alert.AlertType.ERROR);
            } else {
                // Overwrite the file with the updated content (without the deleted employee)
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(employeeFile))) {
                    for (String lineContent : updatedContent) {
                        writer.write(lineContent);
                        writer.newLine();
                    }
                }

                showAlert("Success", "Employee Deleted", "The employee record has been successfully deleted.", Alert.AlertType.INFORMATION);

                // Reset the labels and disable the delete button
                nameLabel.setText("Name: ");
                ageLabel.setText("Age: ");
                cnicLabel.setText("CNIC: ");
                employeeIdField.clear();
                deleteButton.setDisable(true);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
            showAlert("Error", "Failed to Delete Employee", "There was an error reading or writing the employee data.", Alert.AlertType.ERROR);
        }
    }

    private void showAlert(String title, String header, String content, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
}