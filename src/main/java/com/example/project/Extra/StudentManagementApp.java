package com.example.project.Extra;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.*;

public class StudentManagementApp extends Application {

    private Stage primaryStage;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        openMenuStage();
    }

    private void openMenuStage() {
        VBox root = new VBox(20);
        root.setStyle("-fx-background-color: linear-gradient(to bottom right, #0f0c29, #302b63, #24243e);");
        root.setPadding(new Insets(20));
        root.setAlignment(Pos.TOP_LEFT);

        String buttonStyle = "-fx-background-color: linear-gradient(to bottom right, #000428, #004e92);"
                + "-fx-text-fill: white;"
                + "-fx-font-size: 16px;"
                + "-fx-font-weight: bold;"
                + "-fx-padding: 15px 30px;"
                + "-fx-background-radius: 10;"
                + "-fx-pref-width: 300;";

        Button addButton = new Button("ADD A NEW STUDENT RECORD");
        addButton.setStyle(buttonStyle);
        addButton.setFont(Font.font("Arial", 14));
        addButton.setOnAction(e -> openAddStudentScreen());

        Button updateButton = new Button("UPDATE A STUDENT RECORD");
        updateButton.setStyle(buttonStyle);
        updateButton.setFont(Font.font("Arial", 14));
        updateButton.setOnAction(e -> handleUpdateStudentAction());

        Button deleteButton = new Button("DELETE A STUDENT RECORD");
        deleteButton.setStyle(buttonStyle);
        deleteButton.setFont(Font.font("Arial", 14));
        deleteButton.setOnAction(e -> handleDeleteStudentAction());

        Button viewLiveInButton = new Button("VIEW THE LIVE-IN STUDENT DETAILS");
        viewLiveInButton.setStyle(buttonStyle);
        viewLiveInButton.setFont(Font.font("Arial", 14));
        viewLiveInButton.setOnAction(e -> handleViewLiveInStudents());

        root.getChildren().addAll(addButton, updateButton, deleteButton, viewLiveInButton);

        Scene scene = new Scene(root, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Menu");
        primaryStage.show();
    }

    private void openAddStudentScreen() {
        VBox layout = new VBox(15);
        layout.setStyle("-fx-background-color: linear-gradient(to bottom right, #0f0c29, #302b63, #24243e); -fx-padding: 20px;");
        layout.setAlignment(Pos.CENTER);

        String labelStyle = "-fx-text-fill: white; -fx-font-size: 14px; -fx-font-weight: bold;";
        String textFieldStyle = "-fx-background-color: white; -fx-border-radius: 8px; -fx-padding: 5px; -fx-font-size: 12px;";
        String buttonStyle = "-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-size: 16px; -fx-padding: 12px 20px; -fx-border-radius: 8px;";

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

        Button addButton = new Button("Add");
        addButton.setStyle(buttonStyle);

        addButton.setOnAction(e -> {
            String name = nameField.getText();
            String guardianName = guardianNameField.getText();
            String age = ageField.getText();
            String cnic = cnicField.getText();

            if (name.isEmpty() || guardianName.isEmpty() || age.isEmpty() || cnic.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("All fields are required");
                alert.setContentText("Please fill in all the fields.");
                alert.show();
            } else {
                try (BufferedWriter writer = new BufferedWriter(new FileWriter("Students.txt", true))) {
                    String studentData = name + "," + guardianName + "," + age + "," + cnic + System.lineSeparator();
                    writer.write(studentData);

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Success");
                    alert.setHeaderText("Student Added");
                    alert.setContentText("The student has been added successfully.");
                    alert.show();

                    openMenuStage();
                } catch (IOException ex) {
                    ex.printStackTrace();
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("Failed to add student");
                    alert.setContentText("There was an error saving the student data.");
                    alert.show();
                }
            }
        });

        Button backButton = new Button("Back");
        backButton.setStyle("-fx-background-color: #d9534f; -fx-text-fill: white; -fx-font-size: 16px; -fx-padding: 12px 20px; -fx-border-radius: 8px;");
        backButton.setOnAction(e -> openMenuStage());

        layout.getChildren().addAll(nameLabel, nameField, guardianNameLabel, guardianNameField, ageLabel, ageField, cnicLabel, cnicField, addButton, backButton);

        Scene scene = new Scene(layout, 500, 400);
        primaryStage.setScene(scene);
    }

    private void handleUpdateStudentAction() {
        // Placeholder for update student action
        System.out.println("Update student action");
    }

    private void handleDeleteStudentAction() {
        // Placeholder for delete student action
        System.out.println("Delete student action");
    }

    private void handleViewLiveInStudents() {
        // Placeholder for view live-in students action
        System.out.println("View live-in students action");
    }

    public static void main(String[] args) {
        launch(args);
    }
}
