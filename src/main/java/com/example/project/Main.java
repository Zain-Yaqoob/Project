package com.example.project;

import com.example.project.MenuStage;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Root Pane
        Pane root = new Pane();
        root.setStyle("-fx-background-color: linear-gradient(to right, #0a0f2e, #a75ebc);");

        // Title Label
        Label title = new Label("HOSTEL MANAGEMENT SYSTEM");
        title.setFont(Font.font("Arial", 36));
        title.setTextFill(Color.WHITE);
        title.setLayoutX(150);
        title.setLayoutY(50);

        // Login Form Container
        VBox loginBox = new VBox(15);
        loginBox.setAlignment(Pos.CENTER);
        loginBox.setPrefWidth(300);
        loginBox.setLayoutX(250);
        loginBox.setLayoutY(150);

        // Username Field
        Label usernameLabel = new Label("Username");
        usernameLabel.setFont(Font.font("Arial", 16));
        usernameLabel.setTextFill(Color.WHITE);
        TextField userField = new TextField();
        userField.setPromptText("Enter Username");

        // Password Field
        Label passwordLabel = new Label("Password");
        passwordLabel.setFont(Font.font("Arial", 16));
        passwordLabel.setTextFill(Color.WHITE);
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Enter Password");

        // Login Button
        Button loginButton = new Button("Log In");
        loginButton.setStyle("-fx-background-color: #3b7ef8; -fx-text-fill: white; -fx-font-size: 14px;");

        // Sign Up Button
        Button signUpButton = new Button("Sign Up");
        signUpButton.setStyle("-fx-background-color: #45c96b; -fx-text-fill: white; -fx-font-size: 14px;");

        // Add elements to loginBox
        loginBox.getChildren().addAll(usernameLabel, userField, passwordLabel, passwordField, loginButton, signUpButton);
        root.getChildren().addAll(title, loginBox);

        loginButton.setOnAction(e -> {
            String username = userField.getText();
            String password = passwordField.getText();

            // Check if username or password is empty
            if (username.isEmpty() || password.isEmpty()) {
                showAlert("Input Error", "Username and password fields cannot be empty", Alert.AlertType.WARNING);
                return;
            }

            try {
                java.io.File file = new java.io.File("user.txt");

                // Check if file exists
                if (!file.exists()) {
                    showAlert("File Error", "User data file does not exist", Alert.AlertType.ERROR);
                    return;
                }

                // Check if file is empty
                if (file.length() == 0) {
                    showAlert("File Error", "User data file is empty", Alert.AlertType.ERROR);
                    return;
                }

                // Read and validate credentials
                try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                    String line;
                    boolean userFound = false;
                    while ((line = reader.readLine()) != null) {
                        String[] credentials = line.split(",");
                        if (credentials.length >= 2 && credentials[0].equalsIgnoreCase(username) && credentials[1].equals(password)) {
                            userFound = true;

                            // Open MenuStage and close the login stage
                            Manage manageStage = new Manage();
                            manageStage.show(primaryStage);
                            return;
                        }
                    }

                    if (!userFound) {
                        showAlert("Login Failed", "Invalid username or password", Alert.AlertType.ERROR);
                    }
                }
            } catch (IOException ex) {
                ex.printStackTrace();
                showAlert("Error", "An unexpected error occurred while accessing the user data file", Alert.AlertType.ERROR);
            }
        });

        signUpButton.setOnAction(e -> {
            String username = userField.getText();
            String password = passwordField.getText();

            // Check if username or password is empty
            if (username.isEmpty() || password.isEmpty()) {
                showAlert("Input Error", "Username and password fields cannot be empty", Alert.AlertType.WARNING);
                return;
            }

            try (BufferedWriter writer = new BufferedWriter(new FileWriter("user.txt", true))) {
                String data = username + "," + password + System.lineSeparator();
                writer.write(data);
                showAlert("Sign Up Successful", "You can now log in with your credentials.", Alert.AlertType.INFORMATION);
            } catch (IOException ex) {
                ex.printStackTrace();
                showAlert("Error", "Unable to save user data", Alert.AlertType.ERROR);
            }
        });

        // Scene
        Scene scene = new Scene(root, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Hostel Management System");
        primaryStage.show();
    }

    private void showAlert(String title, String content, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
