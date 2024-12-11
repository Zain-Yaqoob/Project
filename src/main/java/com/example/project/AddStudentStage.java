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

public class AddStudentStage {

    private Stage menuStage;  // Reference to the MenuStage

    public AddStudentStage(Stage menuStage) {
        this.menuStage = menuStage;  // Save reference to menuStage passed from MenuStage
    }

    public void show() {
        // Create a new stage for adding student details
        Stage addStudentStage = new Stage();

        // Create a VBox layout to organize the form fields
        VBox layout = new VBox(15); // 15px spacing between fields
        layout.setStyle("-fx-background-color: linear-gradient(to bottom right, #0f0c29, #302b63, #24243e); -fx-padding: 20px;");
        layout.setAlignment(Pos.CENTER);

        // Define universal styles for the UI elements
        String labelStyle = "-fx-text-fill: white; -fx-font-size: 14px; -fx-font-weight: bold;";
        String textFieldStyle = "-fx-background-color: white; -fx-border-radius: 8px; -fx-padding: 5px; -fx-font-size: 12px;";
        String buttonStyle = "-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-size: 16px; -fx-padding: 12px 20px; -fx-border-radius: 8px;";

        // Create Labels and TextFields for Name, Guardian Name, Age, CNIC
        Label nameLabel = new Label("Name:");
        nameLabel.setStyle(labelStyle);
        TextField nameField = new TextField();
        nameField.setStyle(textFieldStyle);

        Label guardianNameLabel = new Label("Guardian Name:");
        guardianNameLabel.setStyle(labelStyle);
        TextField guardianNameField = new TextField();
        guardianNameField.setStyle(textFieldStyle);

        Label ageLabel = new Label("Age:");
        ageLabel.setStyle(labelStyle);
        TextField ageField = new TextField();
        ageField.setStyle(textFieldStyle);

        Label cnicLabel = new Label("CNIC:");
        cnicLabel.setStyle(labelStyle);
        TextField cnicField = new TextField();
        cnicField.setStyle(textFieldStyle);

        // Create an "Add" button to save the student details
        Button addButton = new Button("Add");
        addButton.setStyle(buttonStyle);

        // Add hover effect for the "Add" button
        addButton.setOnMouseEntered(e -> addButton.setStyle("-fx-background-color: #45a049; -fx-text-fill: white; -fx-font-size: 16px; -fx-padding: 12px 20px; -fx-border-radius: 8px;"));
        addButton.setOnMouseExited(e -> addButton.setStyle(buttonStyle));

        // Add event handler for the "Add" button
        addButton.setOnAction(e -> {
            String name = nameField.getText();
            String guardianName = guardianNameField.getText();
            String age = ageField.getText();
            String cnic = cnicField.getText();

            if (name.isEmpty() || guardianName.isEmpty() || age.isEmpty() || cnic.isEmpty()) {
                showAlert("Error", "All fields are required", Alert.AlertType.ERROR);
            } else {
                // Write the student's information to the Students.txt file
                try (BufferedWriter writer = new BufferedWriter(new FileWriter("Students.txt", true))) {
                    // Create a string with student details
                    String studentData = name + "," + guardianName + "," + age + "," + cnic + System.lineSeparator();

                    // Write to the file
                    writer.write(studentData);

                    showAlert("Success", "The student has been added successfully.", Alert.AlertType.INFORMATION);

                    // Close the add student stage after successful addition
                    addStudentStage.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                    showAlert("Error", "Failed to add student. Please try again.", Alert.AlertType.ERROR);
                }
            }
        });

        // Back Button (Top Right Corner)
        Button backButton = new Button("Back");
        backButton.setStyle("-fx-background-color: #4569a0; -fx-text-fill: white; -fx-font-size: 16px; -fx-padding: 12px 20px; -fx-border-radius: 8px;");
        backButton.setOnAction(e -> {
            addStudentStage.close();  // Close the AddStudentStage
            menuStage.show();  // Show the MenuStage again
        });

        // Create an HBox to position the Back button at the top-right
        HBox topBar = new HBox();
        topBar.setAlignment(Pos.TOP_RIGHT); // Align the button to the top-right
        topBar.getChildren().add(backButton); // Add the Back button to the top bar

        // Add all components to the layout
        layout.getChildren().addAll(topBar, nameLabel, nameField, guardianNameLabel, guardianNameField, ageLabel, ageField, cnicLabel, cnicField, addButton);

        // Create and show the scene for the add student stage
        Scene scene = new Scene(layout, 600, 500); // Adjusted scene size
        addStudentStage.setScene(scene);
        addStudentStage.setTitle("Add New Student");
        addStudentStage.show();
    }

    private void showAlert(String title, String content, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
