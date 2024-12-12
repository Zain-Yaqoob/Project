package com.example.project;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class AddEmployeeStage {

    private Stage menuStage;  // Reference to the MenuStage

    public AddEmployeeStage(Stage menuStage) {
        this.menuStage = menuStage;  // Save reference to menuStage passed from MenuStage
    }

    public void show() {
        // Create a new stage for adding employee details
        Stage addEmployeeStage = new Stage();

        // Create a VBox layout to organize the form fields
        VBox layout = new VBox(15); // 15px spacing between fields
        layout.setStyle("-fx-background-color: linear-gradient(to bottom right, #0f0c29, #302b63, #24243e); -fx-padding: 20px;");
        layout.setAlignment(Pos.CENTER);

        // Define universal styles for the UI elements
        String labelStyle = "-fx-text-fill: white; -fx-font-size: 14px; -fx-font-weight: bold;";
        String textFieldStyle = "-fx-background-color: white; -fx-border-radius: 8px; -fx-padding: 5px; -fx-font-size: 12px;";
        String buttonStyle = "-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-size: 16px; -fx-padding: 12px 20px; -fx-border-radius: 8px;";

        // Create Labels and TextFields for employeeId, Name, Age, and CNIC
        Label employeeIdLabel = new Label("Employee ID:");
        employeeIdLabel.setStyle(labelStyle);
        TextField employeeIdField = new TextField();
        employeeIdField.setStyle(textFieldStyle);

        Label nameLabel = new Label("Name:");
        nameLabel.setStyle(labelStyle);
        TextField nameField = new TextField();
        nameField.setStyle(textFieldStyle);

        Label ageLabel = new Label("Age:");
        ageLabel.setStyle(labelStyle);
        TextField ageField = new TextField();
        ageField.setStyle(textFieldStyle);

        Label cnicLabel = new Label("CNIC:");
        cnicLabel.setStyle(labelStyle);
        TextField cnicField = new TextField();
        cnicField.setStyle(textFieldStyle);

        // Create an "Add" button to save the employee details
        Button addButton = new Button("Add");
        addButton.setStyle(buttonStyle);

        // Add hover effect for the "Add" button
        addButton.setOnMouseEntered(e -> addButton.setStyle("-fx-background-color: #45a049; -fx-text-fill: white; -fx-font-size: 16px; -fx-padding: 12px 20px; -fx-border-radius: 8px;"));
        addButton.setOnMouseExited(e -> addButton.setStyle(buttonStyle));

        // Add event handler for the "Add" button
        addButton.setOnAction(e -> {
            String employeeId = employeeIdField.getText();
            String name = nameField.getText();
            String age = ageField.getText();
            String cnic = cnicField.getText();

            if (employeeId.isEmpty() || name.isEmpty() || age.isEmpty() || cnic.isEmpty()) {
                showAlert("Error", "All fields are required", Alert.AlertType.ERROR);
            } else {
                // Write the employee's information to the Employees.txt file
                try (BufferedWriter writer = new BufferedWriter(new FileWriter("Employees.txt", true))) {
                    // Create a string with employee details
                    String employeeData = employeeId + "," + name + "," + age + "," + cnic + System.lineSeparator();

                    // Write to the file
                    writer.write(employeeData);

                    showAlert("Success", "The employee has been added successfully.", Alert.AlertType.INFORMATION);

                    // Clear the fields for new employee entry, keeping the AddEmployeeStage open
                    employeeIdField.clear();
                    nameField.clear();
                    ageField.clear();
                    cnicField.clear();
                } catch (IOException ex) {
                    ex.printStackTrace();
                    showAlert("Error", "Failed to add employee. Please try again.", Alert.AlertType.ERROR);
                }
            }
        });

        // Create a "Display" button to view employees
        Button displayButton = new Button("Display");
        displayButton.setStyle(buttonStyle);

        // Add hover effect for the "Display" button
        displayButton.setOnMouseEntered(e -> displayButton.setStyle("-fx-background-color: #45a049; -fx-text-fill: white; -fx-font-size: 16px; -fx-padding: 12px 20px; -fx-border-radius: 8px;"));
        displayButton.setOnMouseExited(e -> displayButton.setStyle(buttonStyle));

        // Add event handler for the "Display" button
        displayButton.setOnAction(e -> {
            addEmployeeStage.close();  // Close the AddEmployeeStage
            ViewLiveInEmployeesStage viewStage = new ViewLiveInEmployeesStage(addEmployeeStage);
            viewStage.show();
        });

        // Create an HBox to hold the Add and Display buttons
        HBox buttonBox = new HBox(10); // 10px spacing between buttons
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.getChildren().addAll(addButton, displayButton);

        // Back Button (Top Right Corner)
        Button backButton = new Button("Back");
        backButton.setStyle("-fx-background-color: #4569a0; -fx-text-fill: white; -fx-font-size: 16px; -fx-padding: 12px 20px; -fx-border-radius: 8px;");
        backButton.setOnAction(e -> {
            addEmployeeStage.close();  // Close the AddEmployeeStage
            menuStage.show();  // Show the MenuStage again
        });

        // Create an HBox to position the Back button at the top-right
        HBox topBar = new HBox();
        topBar.setAlignment(Pos.TOP_RIGHT); // Align the button to the top-right
        topBar.getChildren().add(backButton); // Add the Back button to the top bar

        // Add all components to the layout
        layout.getChildren().addAll(topBar, employeeIdLabel, employeeIdField, nameLabel, nameField, ageLabel, ageField, cnicLabel, cnicField, buttonBox);

        // Create and show the scene for the add employee stage
        Scene scene = new Scene(layout, 600, 500); // Adjusted scene size
        addEmployeeStage.setScene(scene);
        addEmployeeStage.setTitle("Add New Employee");
        addEmployeeStage.show();
    }

    private void showAlert(String title, String content, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
