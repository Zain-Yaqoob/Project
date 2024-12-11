package com.example.project;

import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class DeleteStudentStage {

    public void show() {
        // Create a new stage for deleting a student
        Stage deleteStudentStage = new Stage();
        deleteStudentStage.setTitle("Delete Student");

        // Create a VBox layout for the delete student form
        VBox layout = new VBox(15); // 15px spacing between fields
        layout.setStyle("-fx-background-color: linear-gradient(to bottom right, #0f0c29, #302b63, #24243e); -fx-padding: 20px;");
        layout.setAlignment(javafx.geometry.Pos.CENTER);

        // Define style for the UI elements
        String labelStyle = "-fx-text-fill: white; -fx-font-size: 14px; -fx-font-weight: bold;";
        String textFieldStyle = "-fx-background-color: white; -fx-border-radius: 10px; -fx-padding: 10px; -fx-font-size: 14px;";
        String buttonStyle = "-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-size: 16px; -fx-padding: 12px 20px; -fx-border-radius: 8px;";

        // Create a Label and TextField for student name input
        Label nameLabel = new Label("Enter Student Name to Delete:");
        nameLabel.setStyle(labelStyle);
        TextField nameField = new TextField();
        nameField.setStyle(textFieldStyle);

        // Create a "Delete" button to delete the student record
        Button deleteButton = new Button("Delete");
        deleteButton.setStyle(buttonStyle);

        // Add event handler for the "Delete" button
        deleteButton.setOnAction(e -> handleDeleteAction(nameField.getText(), deleteStudentStage));

        // Add the input field and button to the layout
        layout.getChildren().addAll(nameLabel, nameField, deleteButton);

        // Create and show the scene for the delete student stage
        Scene scene = new Scene(layout, 400, 300);
        deleteStudentStage.setScene(scene);
        deleteStudentStage.show();
    }

    private void handleDeleteAction(String studentName, Stage stage) {
        if (studentName.isEmpty()) {
            showAlert("Error", "Name is required", "Please enter the name of the student to delete.", Alert.AlertType.ERROR);
            return;
        }

        // File and data handling
        File studentFile = new File("Students.txt");
        List<String> updatedContent = new ArrayList<>();
        boolean studentFound = false;

        try (BufferedReader reader = new BufferedReader(new FileReader(studentFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] studentData = line.split(",");
                if (studentData[0].equalsIgnoreCase(studentName)) {
                    studentFound = true; // Found the student
                    continue; // Skip adding this student to the updated list (delete the record)
                }
                updatedContent.add(line); // Add other students to the updated list
            }

            if (!studentFound) {
                showAlert("Student Not Found", null, "No student found with the name: " + studentName, Alert.AlertType.ERROR);
            } else {
                // Overwrite the file with the updated content (without the deleted student)
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(studentFile))) {
                    for (String lineContent : updatedContent) {
                        writer.write(lineContent);
                        writer.newLine();
                    }
                }

                showAlert("Success", "Student Deleted", "The student record has been successfully deleted.", Alert.AlertType.INFORMATION);
                stage.close();
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

