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

public class UpdateStudentStage {

    private Stage menuStage;  // Reference to the MenuStage

    public UpdateStudentStage(Stage menuStage) {
        this.menuStage = menuStage;  // Save reference to menuStage passed from MenuStage
    }

    public void show() {
        // Create a new stage for updating student details
        Stage updateStudentStage = new Stage();

        // Create a VBox layout to organize the form fields
        VBox layout = new VBox(15); // 15px spacing between fields
        layout.setStyle("-fx-background-color: linear-gradient(to bottom right, #0f0c29, #302b63, #24243e); -fx-padding: 20px;");
        layout.setAlignment(Pos.CENTER);

        // Define a universal label style
        String labelStyle = "-fx-text-fill: white; -fx-font-size: 14px; -fx-font-weight: bold;";

        // Label and TextField to search by student's ID
        Label searchLabel = new Label("Enter Student ID to Update:");
        searchLabel.setStyle(labelStyle);
        TextField searchField = new TextField();
        searchField.setPromptText("Student ID");

        // Button to search the student
        Button searchButton = new Button("Search");
        searchButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-size: 16px; -fx-padding: 12px 20px; -fx-border-radius: 8px;");

        // Create fields for displaying and updating student details (now editable)
        Label nameLabel = new Label("Name:");
        nameLabel.setStyle(labelStyle);
        TextField nameField = new TextField();

        Label guardianNameLabel = new Label("Guardian Name:");
        guardianNameLabel.setStyle(labelStyle);
        TextField guardianNameField = new TextField();

        Label ageLabel = new Label("Age:");
        ageLabel.setStyle(labelStyle);
        TextField ageField = new TextField();

        Label cnicLabel = new Label("CNIC:");
        cnicLabel.setStyle(labelStyle);
        TextField cnicField = new TextField();

        // Create an "Update" button to save the updated student details
        Button updateButton = new Button("Update");
        updateButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-size: 16px; -fx-padding: 12px 20px; -fx-border-radius: 8px;");
        updateButton.setDisable(true); // Initially disabled until the student is found

        // Create a "Display" button to view live information in a new stage
        Button displayButton = new Button("Display");
        displayButton.setStyle("-fx-background-color: #2196F3; -fx-text-fill: white; -fx-font-size: 16px; -fx-padding: 12px 20px; -fx-border-radius: 8px;");

        // Event handler for search button
        searchButton.setOnAction(e -> handleSearchAction(searchField, nameField, guardianNameField, ageField, cnicField, updateButton));

        // Event handler for update button
        updateButton.setOnAction(e -> handleUpdateAction(searchField, nameField, guardianNameField, ageField, cnicField, updateButton));

        // Event handler for display button
        displayButton.setOnAction(e -> {
            updateStudentStage.close();
            ViewLiveInStudentsStage viewLiveInStudentStage = new ViewLiveInStudentsStage(updateStudentStage);
            viewLiveInStudentStage.show();
        });

        // Create an HBox for the Update and Display buttons
        HBox buttonBox = new HBox(10); // 10px spacing between buttons
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.getChildren().addAll(updateButton, displayButton);

        // Back Button (Top Right Corner)
        Button backButton = new Button("Back");
        backButton.setStyle("-fx-background-color: #4569a0; -fx-text-fill: white; -fx-font-size: 16px; -fx-padding: 12px 20px; -fx-border-radius: 8px;");
        backButton.setOnAction(e -> {
            updateStudentStage.close();  // Close the UpdateStudentStage
            menuStage.show();  // Show the MenuStage again
        });

        // Create an HBox to position the Back button at the top-right
        HBox topBar = new HBox();
        topBar.setAlignment(Pos.TOP_RIGHT); // Align the button to the top-right
        topBar.getChildren().add(backButton); // Add the Back button to the top bar

        // Add all components to the layout
        layout.getChildren().addAll(topBar, searchLabel, searchField, searchButton,
                nameLabel, nameField,
                guardianNameLabel, guardianNameField,
                ageLabel, ageField,
                cnicLabel, cnicField,
                buttonBox);

        // Create and show the scene for the update student stage
        Scene scene = new Scene(layout, 600, 600);
        updateStudentStage.setScene(scene);
        updateStudentStage.setTitle("Update Student Record");
        updateStudentStage.show();
    }

    private void handleSearchAction(TextField searchField, TextField nameField, TextField guardianNameField, TextField ageField, TextField cnicField, Button updateButton) {
        String searchID = searchField.getText().trim();
        if (searchID.isEmpty()) {
            showAlert("Error", "Student ID is required", "Please enter a student ID to search.", Alert.AlertType.ERROR);
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader("Students.txt"))) {
            String line;
            boolean found = false;

            while ((line = reader.readLine()) != null) {
                String[] studentData = line.split(",");
                if (studentData[0].equals(searchID)) {
                    // Populate the fields with existing data
                    nameField.setText(studentData[1]);
                    guardianNameField.setText(studentData[2]);
                    ageField.setText(studentData[3]);
                    cnicField.setText(studentData[4]);
                    updateButton.setDisable(false); // Enable the update button
                    found = true;
                    break;
                }
            }

            if (!found) {
                showAlert("Student Not Found", "Student Not Found", "No student found with the ID: " + searchID, Alert.AlertType.ERROR);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
            showAlert("Error", "Failed to Search", "There was an error searching for the student.", Alert.AlertType.ERROR);
        }
    }

    private void handleUpdateAction(TextField searchField, TextField nameField, TextField guardianNameField, TextField ageField, TextField cnicField, Button updateButton) {
        String searchID = searchField.getText().trim();
        String newName = nameField.getText().trim();
        String guardianName = guardianNameField.getText().trim();
        String age = ageField.getText().trim();
        String cnic = cnicField.getText().trim();

        if (newName.isEmpty() || guardianName.isEmpty() || age.isEmpty() || cnic.isEmpty()) {
            showAlert("Error", "All fields are required", "Please fill in all the fields.", Alert.AlertType.ERROR);
            return;
        }

        try {
            List<String> lines = Files.readAllLines(Paths.get("Students.txt"));
            boolean updated = false;

            for (int i = 0; i < lines.size(); i++) {
                String[] studentData = lines.get(i).split(",");
                if (studentData[0].equals(searchID)) {
                    lines.set(i, searchID + "," + newName + "," + guardianName + "," + age + "," + cnic);
                    updated = true;
                    break;
                }
            }

            if (updated) {
                Files.write(Paths.get("Students.txt"), lines);
                showAlert("Success", "Student Updated", "The student's record has been updated successfully.", Alert.AlertType.INFORMATION);

                // Clear the fields and keep the stage open for further updates
                nameField.clear();
                guardianNameField.clear();
                ageField.clear();
                cnicField.clear();

                // Optionally, disable the update button again after update
                updateButton.setDisable(true);
            } else {
                showAlert("Error", "Update Failed", "No matching student found to update.", Alert.AlertType.ERROR);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
            showAlert("Error", "Failed to Update", "There was an error updating the student record.", Alert.AlertType.ERROR);
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
