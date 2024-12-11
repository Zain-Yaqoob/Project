package com.example.project.Extra;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.*;

public class Hostel extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Root Pane
        Pane root = new Pane();
        root.setStyle("-fx-background-color: linear-gradient(to right, #0a0f2e, #a75ebc);");

        // Title Label
        Label title = new Label("HOSTEL MANAGEMENT SYSTEM");
        title.setFont(Font.font("Arial", 36));
        title.setTextFill(Color.WHITE);
        title.setLayoutX(40);
        title.setLayoutY(30);

        // Login Form Container
        VBox loginBox = new VBox(15);
        loginBox.setAlignment(Pos.CENTER_LEFT);
        loginBox.setLayoutX(50);
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
        loginButton.setPrefWidth(200);

        // Sign Up Button
        Button signUpButton = new Button("Sign Up");
        signUpButton.setStyle("-fx-background-color: #45c96b; -fx-text-fill: white; -fx-font-size: 14px;");
        signUpButton.setPrefWidth(200);

        // Add elements to loginBox
        loginBox.getChildren().addAll(usernameLabel, userField, passwordLabel, passwordField, loginButton, signUpButton);

        // Add elements to root
        root.getChildren().addAll(title, loginBox);

        // Login Button Action
        loginButton.setOnAction(e -> {
            String username = userField.getText();
            String password = passwordField.getText();

            try (BufferedReader reader = new BufferedReader(new FileReader("user.txt"))) {
                String line;
                boolean userFound = false;
                while ((line = reader.readLine()) != null) {
                    String[] credentials = line.split(",");
                    if (credentials[0].equalsIgnoreCase(username) && credentials[1].equals(password)) {
                        userFound = true;
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Sign In Update");
                        alert.setHeaderText(null);
                        alert.setContentText("Signed In Successfully");
                        alert.show();
                        break;
                    }
                }
                if (!userFound) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("User Not Found");
                    alert.setHeaderText(null);
                    alert.setContentText("No user found with the username: " + username);
                    alert.show();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

        // Sign Up Button Action
        signUpButton.setOnAction(e -> {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter("user.txt", true))) {
                String data = userField.getText() + "," + passwordField.getText() + System.lineSeparator();
                writer.write(data);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Sign Up Update");
                alert.setHeaderText(null);
                alert.setContentText("Signed Up Successfully");
                alert.showAndWait();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

        // Scene
        Scene scene = new Scene(root, 800, 600);
        primaryStage.setScene(scene);

        // Set Fullscreen Mode
        primaryStage.setFullScreen(true);

        primaryStage.setTitle("Hostel Management System");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}



