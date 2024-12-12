package com.example.project;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class UpdateEmployeeStage {

    private Stage menuStage;  // Reference to the MenuStage

    public UpdateEmployeeStage(Stage menuStage) {
        this.menuStage = menuStage;  // Save reference to menuStage passed from MenuStage
    }

    public void show() {
        // Create a new stage for updating employee details
        Stage updateEmployeeStage = new Stage();

        // Create a VBox layout to organize the form fields
        VBox layout = new VBox(15); // 15px spacing between fields
        layout.setStyle("-fx-background-color: linear-gradient(to bottom right, #0f0c29, #302b63, #24243e); -fx-padding: 20px;");
        layout.setAlignment(Pos.CENTER);

        // Define a universal label style
        String labelStyle = "-fx-text-fill: white; -fx-font-size: 14px; -fx-font-weight: bold;";

        // Label and TextField to search by employee's ID
        Label searchLabel = new Label("Enter Employee ID to Update:");
        searchLabel.setStyle(labelStyle);
        TextField searchField = new TextField();
        searchField.setPromptText("Employee ID");

        // Button to search the employee
        Button searchButton = new Button("Search");
        searchButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-size: 16px; -fx-padding: 12px 20px; -fx-border-radius: 8px;");

        // Create fields for displaying and updating employee details (now editable)
        Label nameLabel = new Label("Name:");
        nameLabel.setStyle(labelStyle);
        TextField nameField = new TextField();

        Label ageLabel = new Label("Age:");
        ageLabel.setStyle(labelStyle);
        TextField ageField = new TextField();

        Label cnicLabel = new Label("CNIC:");
        cnicLabel.setStyle(labelStyle);
        TextField cnicField = new TextField();

        // Create an "Update" button to save the updated employee details
        Button updateButton = new Button("Update");
        updateButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-size: 16px; -fx-padding: 12px 20px; -fx-border-radius: 8px;");
        updateButton.setDisable(true); // Initially disabled until the employee is found

        // Create a "Display" button to view employee data
        Button displayButton = new Button("Display");
        displayButton.setStyle("-fx-background-color: #FFA500; -fx-text-fill: white; -fx-font-size: 16px; -fx-padding: 12px 20px; -fx-border-radius: 8px;");
        displayButton.setOnAction(e -> {
            updateEmployeeStage.close();
            new ViewLiveInEmployeesStage(updateEmployeeStage).show();
        });

        // Event handler for search button
        searchButton.setOnAction(e -> handleSearchAction(searchField, nameField, ageField, cnicField, updateButton));

        // Event handler for update button
        updateButton.setOnAction(e -> handleUpdateAction(searchField, nameField, ageField, cnicField, updateButton));

        // Back Button (Top Right Corner)
        Button backButton = new Button("Back");
        backButton.setStyle("-fx-background-color: #4569a0; -fx-text-fill: white; -fx-font-size: 16px; -fx-padding: 12px 20px; -fx-border-radius: 8px;");
        backButton.setOnAction(e -> {
            updateEmployeeStage.close();  // Close the UpdateEmployeeStage
            menuStage.show();  // Show the MenuStage again
        });

        // Create an HBox to position the Back button at the top-right
        HBox topBar = new HBox();
        topBar.setAlignment(Pos.TOP_RIGHT); // Align the button to the top-right
        topBar.getChildren().add(backButton); // Add the Back button to the top bar

        // Create an HBox for the Update and Display buttons
        HBox actionButtons = new HBox(10); // 10px spacing between buttons
        actionButtons.setAlignment(Pos.CENTER);
        actionButtons.getChildren().addAll(updateButton, displayButton);

        // Add all components to the layout
        layout.getChildren().addAll(topBar, searchLabel, searchField, searchButton,
                nameLabel, nameField,
                ageLabel, ageField,
                cnicLabel, cnicField,
                actionButtons);

        // Create and show the scene for the update employee stage
        Scene scene = new Scene(layout, 600, 600);
        updateEmployeeStage.setScene(scene);
        updateEmployeeStage.setTitle("Update Employee Record");
        updateEmployeeStage.show();
    }

    private void handleSearchAction(TextField searchField, TextField nameField, TextField ageField, TextField cnicField, Button updateButton) {
        String searchID = searchField.getText().trim();
        if (searchID.isEmpty()) {
            showAlert("Error", "Employee ID is required", "Please enter an employee ID to search.", Alert.AlertType.ERROR);
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader("Employees.txt"))) {
            String line;
            boolean found = false;

            while ((line = reader.readLine()) != null) {
                String[] employeeData = line.split(",");
                if (employeeData[0].equals(searchID)) {
                    // Populate the fields with existing data
                    nameField.setText(employeeData[1]);
                    ageField.setText(employeeData[2]);
                    cnicField.setText(employeeData[3]);
                    updateButton.setDisable(false); // Enable the update button
                    found = true;
                    break;
                }
            }

            if (!found) {
                showAlert("Employee Not Found", "Employee Not Found", "No employee found with the ID: " + searchID, Alert.AlertType.ERROR);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
            showAlert("Error", "Failed to Search", "There was an error searching for the employee.", Alert.AlertType.ERROR);
        }
    }

    private void handleUpdateAction(TextField searchField, TextField nameField, TextField ageField, TextField cnicField, Button updateButton) {
        String searchID = searchField.getText().trim();
        String newName = nameField.getText().trim();
        String age = ageField.getText().trim();
        String cnic = cnicField.getText().trim();

        if (newName.isEmpty() || age.isEmpty() || cnic.isEmpty()) {
            showAlert("Error", "All fields are required", "Please fill in all the fields.", Alert.AlertType.ERROR);
            return;
        }

        try {
            List<String> lines = Files.readAllLines(Paths.get("Employees.txt"));
            boolean updated = false;

            for (int i = 0; i < lines.size(); i++) {
                String[] employeeData = lines.get(i).split(",");
                if (employeeData[0].equals(searchID)) {
                    lines.set(i, searchID + "," + newName + "," + age + "," + cnic);
                    updated = true;
                    break;
                }
            }

            if (updated) {
                Files.write(Paths.get("Employees.txt"), lines);
                showAlert("Success", "Employee Updated", "The employee's record has been updated successfully.", Alert.AlertType.INFORMATION);

                // Clear the fields and keep the stage open for further updates
                nameField.clear();
                ageField.clear();
                cnicField.clear();

                // Optionally, disable the update button again after update
                updateButton.setDisable(true);
            } else {
                showAlert("Error", "Update Failed", "No matching employee found to update.", Alert.AlertType.ERROR);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
            showAlert("Error", "Failed to Update", "There was an error updating the employee record.", Alert.AlertType.ERROR);
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