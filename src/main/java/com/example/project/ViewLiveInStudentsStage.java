package com.example.project;

import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ViewLiveInStudentsStage {

    public void show() {
        // Create a new stage to display the live-in students
        Stage viewStage = new Stage();
        viewStage.setTitle("Live-In Students");

        // Create a VBox layout to hold the TableView
        VBox layout = new VBox(10);
        layout.setStyle("-fx-background-color: linear-gradient(to bottom right, #0f0c29, #302b63, #24243e); -fx-padding: 20px;");
        layout.setAlignment(javafx.geometry.Pos.CENTER);

        // Create the TableView and its columns
        TableView<Student> tableView = new TableView<>();

        // Create the columns for the TableView
        TableColumn<Student, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Student, String> guardianNameColumn = new TableColumn<>("Guardian Name");
        guardianNameColumn.setCellValueFactory(new PropertyValueFactory<>("guardianName"));

        TableColumn<Student, String> ageColumn = new TableColumn<>("Age");
        ageColumn.setCellValueFactory(new PropertyValueFactory<>("age"));

        TableColumn<Student, String> cnicColumn = new TableColumn<>("CNIC");
        cnicColumn.setCellValueFactory(new PropertyValueFactory<>("cnic"));

        // Add columns to the TableView
        tableView.getColumns().addAll(nameColumn, guardianNameColumn, ageColumn, cnicColumn);

        // Read the data from the Students.txt file and populate the TableView
        try (BufferedReader reader = new BufferedReader(new FileReader("Students.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] studentData = line.split(",");
                if (studentData.length == 4) {
                    // Create a new Student object and add it to the TableView
                    Student student = new Student(studentData[0], studentData[1], studentData[2], studentData[3]);
                    tableView.getItems().add(student);
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
            showAlert("Error", "Failed to Load Students", "There was an error reading the student data.", Alert.AlertType.ERROR);
        }

        // Add the TableView to the layout
        layout.getChildren().add(tableView);

        // Create and show the scene for the view students stage
        Scene scene = new Scene(layout, 800, 600);
        viewStage.setScene(scene);
        viewStage.show();
    }

    private void showAlert(String title, String header, String content, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
}

