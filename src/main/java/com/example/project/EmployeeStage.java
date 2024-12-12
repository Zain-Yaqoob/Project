package com.example.project;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class EmployeeStage {

    private final Stage previousStage; // Store reference to the previous stage

    // Constructor to accept the previous stage (Manage stage)
    public EmployeeStage(Stage previousStage) {
        this.previousStage = previousStage;
    }

    public void show() {
        Stage employeeStage = new Stage();

        // Root layout
        VBox root = new VBox(20);
        root.setStyle("-fx-background-color: linear-gradient(to bottom right, #0f0c29, #302b63, #24243e);");
        root.setPadding(new Insets(20));

        // Top Bar Layout for Back Button
        HBox topBar = new HBox();
        topBar.setAlignment(Pos.TOP_RIGHT);
        topBar.setPadding(new Insets(10));

        // Back Button
        Button backButton = new Button("Back");
        backButton.setStyle("-fx-background-color: #e84545; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 10px 20px; -fx-background-radius: 8px;");
        backButton.setOnAction(e -> {
            employeeStage.close(); // Close the current stage
            new Manage().show(previousStage); // Show the Manage stage
        });

        // Add Back Button to the Top Bar
        topBar.getChildren().add(backButton);

        // Button style (similar to MenuStage)
        String buttonStyle = "-fx-background-color: linear-gradient(to bottom right, #000428, #004e92);"
                + "-fx-text-fill: white;"
                + "-fx-font-size: 16px;"
                + "-fx-font-weight: bold;"
                + "-fx-padding: 15px 30px;"
                + "-fx-background-radius: 10;"
                + "-fx-pref-width: 300;";

        // Buttons
        Button addButton = new Button("ADD A NEW EMPLOYEE RECORD");
        addButton.setStyle(buttonStyle);
        addButton.setFont(Font.font("Arial", 14));
        addButton.setOnAction(e -> handleAddEmployeeAction(employeeStage));

        Button updateButton = new Button("UPDATE AN EMPLOYEE RECORD");
        updateButton.setStyle(buttonStyle);
        updateButton.setFont(Font.font("Arial", 14));
        updateButton.setOnAction(e -> handleUpdateEmployeeAction(employeeStage));

        Button deleteButton = new Button("DELETE AN EMPLOYEE RECORD");
        deleteButton.setStyle(buttonStyle);
        deleteButton.setFont(Font.font("Arial", 14));
        deleteButton.setOnAction(e -> handleDeleteEmployeeAction(employeeStage));

        Button viewLiveInButton = new Button("VIEW THE LIVE-IN EMPLOYEE DETAILS");
        viewLiveInButton.setStyle(buttonStyle);
        viewLiveInButton.setFont(Font.font("Arial", 14));
        viewLiveInButton.setOnAction(e -> handleViewLiveInEmployees(employeeStage));

        // Add components to the root layout
        root.getChildren().addAll(topBar, addButton, updateButton, deleteButton, viewLiveInButton);

        // Scene and stage setup
        Scene scene = new Scene(root, 800, 600);
        employeeStage.setScene(scene);
        employeeStage.setTitle("Employee Menu");
        employeeStage.show();
    }

    private void handleAddEmployeeAction(Stage employeeStage) {
        employeeStage.close();
        new AddEmployeeStage(employeeStage).show();
    }

    private void handleUpdateEmployeeAction(Stage employeeStage) {
        employeeStage.close();
        new UpdateEmployeeStage(employeeStage).show();
    }

    private void handleDeleteEmployeeAction(Stage employeeStage) {
        employeeStage.close();
        new DeleteEmployeeStage(employeeStage).show();
    }

    private void handleViewLiveInEmployees(Stage employeeStage) {
        employeeStage.close();
        new ViewLiveInEmployeesStage(employeeStage).show();
    }
}
