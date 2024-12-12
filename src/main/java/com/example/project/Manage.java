package com.example.project;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Manage {

    public void show(Stage stage) {
        // Root Pane
        Pane root = new Pane();
        root.setStyle("-fx-background-color: linear-gradient(to right, #0a0f2e, #a75ebc);");

        // Title Label
        Text title = new Text("HOSTEL MANAGEMENT SYSTEM");
        title.setFont(Font.font("Arial", 30));
        title.setFill(Color.WHITE);
        title.setX(150);
        title.setY(50);

        // VBox for Buttons
        VBox buttonBox = new VBox(20);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setPrefWidth(300);
        buttonBox.setLayoutX(250);
        buttonBox.setLayoutY(150);

        // Manage Students Button
        Button manageStudentsButton = new Button("Manage Students");
        manageStudentsButton.setStyle("-fx-background-color: #3b7ef8; -fx-text-fill: white; -fx-font-size: 16px; -fx-pref-width: 200px; -fx-pref-height: 50px;");

        // Manage Employees Button
        Button manageEmployeesButton = new Button("Manage Employees");
        manageEmployeesButton.setStyle("-fx-background-color: #3b7ef8; -fx-text-fill: white; -fx-font-size: 16px; -fx-pref-width: 200px; -fx-pref-height: 50px;");

        // Logout Button
        Button logoutButton = new Button("Log Out");
        logoutButton.setStyle("-fx-background-color: #e84545; -fx-text-fill: white; -fx-font-size: 14px; -fx-pref-width: 100px; -fx-pref-height: 30px;");
        logoutButton.setLayoutX(650);
        logoutButton.setLayoutY(20);


        manageStudentsButton.setOnAction(e -> {
            stage.close();
            new MenuStage(stage).show(); // Pass the current stage to MenuStage
        });

        manageEmployeesButton.setOnAction(e -> {
            stage.close();
            new EmployeeStage(stage).show(); // Pass the current stage to MenuStage
        });

        // Logout Button Action
        logoutButton.setOnAction(e -> {
            new Main().start(stage); // Redirect to Main Login Screen
            showAlert("Logged Out", "You have logged out successfully.", Alert.AlertType.INFORMATION);

        });

        // Add Buttons to VBox
        buttonBox.getChildren().addAll(manageStudentsButton, manageEmployeesButton);
        root.getChildren().addAll(title, buttonBox, logoutButton);

        // Scene
        Scene scene = new Scene(root, 800, 600);
        stage.setScene(scene);
        stage.setTitle("Manage");
        stage.show();
    }

    private void showAlert(String title, String content, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
