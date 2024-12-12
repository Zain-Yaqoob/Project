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

public class DeleteStudentStage {

    private Stage menuStage;  // Reference to MenuStage

    public DeleteStudentStage(Stage menuStage) {
        this.menuStage = menuStage;  // Save reference to menuStage passed from MenuStage
    }

    public void show() {
        // Create a new stage for deleting a student
        Stage deleteStudentStage = new Stage();
        deleteStudentStage.setTitle("Delete Student");

        // Create a VBox layout for the delete student form
        VBox layout = new VBox(15); // 15px spacing between fields
        layout.setStyle("-fx-background-color: linear-gradient(to bottom right, #0f0c29, #302b63, #24243e); -fx-padding: 20px;");
        layout.setAlignment(Pos.CENTER);

        // Define style for the UI elements
        String labelStyle = "-fx-text-fill: white; -fx-font-size: 14px; -fx-font-weight: bold;";
        String textFieldStyle = "-fx-background-color: white; -fx-border-radius: 10px; -fx-padding: 10px; -fx-font-size: 14px;";
        String buttonStyle = "-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-size: 16px; -fx-padding: 12px 20px; -fx-border-radius: 8px;";

        // Create a Label and TextField for student ID input
        Label studentIdLabel = new Label("Enter Student ID to Delete:");
        studentIdLabel.setStyle(labelStyle);
        TextField studentIdField = new TextField();
        studentIdField.setStyle(textFieldStyle);

        // Create Labels to display student details (initially hidden)
        Label nameLabel = new Label("Name: ");
        nameLabel.setStyle(labelStyle);
        Label guardianNameLabel = new Label("Guardian Name: ");
        guardianNameLabel.setStyle(labelStyle);
        Label ageLabel = new Label("Age: ");
        ageLabel.setStyle(labelStyle);
        Label cnicLabel = new Label("CNIC: ");
        cnicLabel.setStyle(labelStyle);

        // Initially disable the delete button (the display button will always be enabled)
        Button deleteButton = new Button("Delete");
        deleteButton.setStyle(buttonStyle);
        deleteButton.setDisable(true); // Disable delete initially

        Button displayButton = new Button("Display");
        displayButton.setStyle(buttonStyle);
        // Display button is always enabled
        displayButton.setOnAction(e -> {deleteStudentStage.close();
        ViewLiveInStudentsStage viewStage = new ViewLiveInStudentsStage(deleteStudentStage); // Passing the current stage as the previous stage
        viewStage.show();}
        );

        // Back Button (Top Right Corner)
        Button backButton = new Button("Back");
        backButton.setStyle("-fx-background-color: #4569a0; -fx-text-fill: white; -fx-font-size: 16px; -fx-padding: 12px 20px; -fx-border-radius: 8px;");
        backButton.setOnAction(e -> {
            deleteStudentStage.close();  // Close the DeleteStudentStage
            menuStage.show();  // Show the MenuStage again
        });

        // Create an HBox to position the Back button at the top-right
        HBox topBar = new HBox();
        topBar.setAlignment(Pos.TOP_RIGHT); // Align the button to the top-right
        topBar.getChildren().add(backButton); // Add the Back button to the top bar

        // Add event handler for the "Search" button
        Button searchButton = new Button("Search");
        searchButton.setStyle(buttonStyle);
        searchButton.setOnAction(e -> handleSearchAction(studentIdField, nameLabel, guardianNameLabel, ageLabel, cnicLabel, deleteButton, displayButton));

        // Add event handler for the "Delete" button
        deleteButton.setOnAction(e -> handleDeleteAction(studentIdField, deleteButton, displayButton, nameLabel, guardianNameLabel, ageLabel, cnicLabel));

        // Create an HBox for the delete and display buttons
        HBox buttonBox = new HBox(10); // 10px spacing between buttons
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.getChildren().addAll(displayButton, deleteButton);

        // Add the input field, search button, labels, and buttons to the layout
        layout.getChildren().addAll(topBar, studentIdLabel, studentIdField, searchButton, nameLabel, guardianNameLabel, ageLabel, cnicLabel, buttonBox);

        // Create and show the scene for the delete student stage
        Scene scene = new Scene(layout, 500, 500);
        deleteStudentStage.setScene(scene);
        deleteStudentStage.show();
    }

    private void handleSearchAction(TextField studentIdField, Label nameLabel, Label guardianNameLabel, Label ageLabel, Label cnicLabel, Button deleteButton, Button displayButton) {
        String studentId = studentIdField.getText().trim();
        if (studentId.isEmpty()) {
            showAlert("Error", "Student ID is required", "Please enter the ID of the student to search.", Alert.AlertType.ERROR);
            return;
        }

        // Search for student in the file
        File studentFile = new File("Students.txt");
        boolean studentFound = false;

        try (BufferedReader reader = new BufferedReader(new FileReader(studentFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] studentData = line.split(",");
                if (studentData.length >= 5 && studentData[0].equals(studentId)) {
                    // Display student details
                    nameLabel.setText("Name: " + studentData[1]);
                    guardianNameLabel.setText("Guardian Name: " + studentData[2]);
                    ageLabel.setText("Age: " + studentData[3]);
                    cnicLabel.setText("CNIC: " + studentData[4]);

                    // Enable delete button
                    deleteButton.setDisable(false);

                    studentFound = true;
                    break;
                }
            }

            if (!studentFound) {
                showAlert("Student Not Found", null, "No student found with the ID: " + studentId, Alert.AlertType.ERROR);
                deleteButton.setDisable(true);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
            showAlert("Error", "Failed to Search", "There was an error reading the student data.", Alert.AlertType.ERROR);
        }
    }

    // Handle the deletion process when "Delete" is pressed
    private void handleDeleteAction(TextField studentIdField, Button deleteButton, Button displayButton, Label nameLabel, Label guardianNameLabel, Label ageLabel, Label cnicLabel) {
        String studentId = studentIdField.getText().trim();
        if (studentId.isEmpty()) {
            showAlert("Error", "Student ID is required", "Please enter the ID of the student to delete.", Alert.AlertType.ERROR);
            return;
        }

        // Search for student in the file and delete the record
        File studentFile = new File("Students.txt");
        List<String> updatedContent = new ArrayList<>();
        boolean studentFound = false;

        try (BufferedReader reader = new BufferedReader(new FileReader(studentFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] studentData = line.split(",");
                if (studentData.length >= 5 && studentData[0].equals(studentId)) {
                    studentFound = true;  // Student found, skipping the record to delete it
                    continue;
                }
                updatedContent.add(line);  // Add other students to the updated list
            }

            if (!studentFound) {
                showAlert("Student Not Found", null, "No student found with the ID: " + studentId, Alert.AlertType.ERROR);
            } else {
                // Overwrite the file with the updated content (without the deleted student)
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(studentFile))) {
                    for (String lineContent : updatedContent) {
                        writer.write(lineContent);
                        writer.newLine();
                    }
                }

                showAlert("Success", "Student Deleted", "The student record has been successfully deleted.", Alert.AlertType.INFORMATION);

                // Reset the labels and disable the buttons
                nameLabel.setText("Name: ");
                guardianNameLabel.setText("Guardian Name: ");
                ageLabel.setText("Age: ");
                cnicLabel.setText("CNIC: ");
                studentIdField.clear();
                deleteButton.setDisable(true);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
            showAlert("Error", "Failed to Delete Student", "There was an error reading or writing the student data.", Alert.AlertType.ERROR);
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
