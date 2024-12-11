package com.example.project;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class MenuStage {

    public void show() {
        Stage menuStage = new Stage();

        // Root layout
        VBox root = new VBox(20);
        root.setStyle("-fx-background-color: linear-gradient(to bottom right, #0f0c29, #302b63, #24243e);");
        root.setPadding(new Insets(20));
        root.setAlignment(Pos.TOP_LEFT);

        // Button style
        String buttonStyle = "-fx-background-color: linear-gradient(to bottom right, #000428, #004e92);"
                + "-fx-text-fill: white;"
                + "-fx-font-size: 16px;"
                + "-fx-font-weight: bold;"
                + "-fx-padding: 15px 30px;"
                + "-fx-background-radius: 10;"
                + "-fx-pref-width: 300;";

        // Buttons
        Button addButton = new Button("ADD A NEW STUDENT RECORD");
        addButton.setStyle(buttonStyle);
        addButton.setFont(Font.font("Arial", 14));
        addButton.setOnAction(e -> handleAddStudentAction());

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

        // Scene and stage setup
        Scene scene = new Scene(root, 800, 600);
        menuStage.setScene(scene);
        menuStage.setTitle("Menu");
        menuStage.show();
    }

    private void handleAddStudentAction() {
        new AddStudentStage().show();
    }

    private void handleUpdateStudentAction() {
        new UpdateStudentStage().show();
    }


    private void handleDeleteStudentAction() {
        new DeleteStudentStage().show();
    }


    private void handleViewLiveInStudents() {
        new ViewLiveInStudentsStage().show();
    }
}

