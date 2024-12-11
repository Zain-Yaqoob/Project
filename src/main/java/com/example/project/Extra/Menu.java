package com.example.project.Extra;

import com.example.project.Student;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Menu extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Root Pane
        Pane root = new Pane();
        root.setStyle("-fx-background-color: linear-gradient(to right, #0a0f2e, #a75ebc);");

        // Title Label (Center the title)
        Label title = new Label("HOSTEL MANAGEMENT SYSTEM");
        title.setFont(Font.font("Arial", 36));
        title.setTextFill(Color.WHITE);
        title.setLayoutX((250 - title.getPrefWidth()) / 2);
        title.setLayoutY(100);

        // Login Form Container (Centering)
        VBox loginBox = new VBox(15);
        loginBox.setAlignment(Pos.CENTER);
        loginBox.setSpacing(10);
        loginBox.setPrefWidth(300);
        loginBox.setPrefHeight(300);

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

        // Position the loginBox at the center of the screen
        loginBox.setLayoutX((800 - loginBox.getPrefWidth()) / 2);
        loginBox.setLayoutY((600 - loginBox.getPrefHeight()) / 2);

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

                        // Open the menu when login is successful
                        openMenuStage();

                        // Optionally, hide the login window after successful login
                        primaryStage.close();

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
        primaryStage.setTitle("Hostel Management System");
        primaryStage.show();
    }

    private void openMenuStage() {
        // Create the menu window
        Stage menuStage = new Stage();

        // Create the main layout for the menu (using VBox for vertical button alignment)
        VBox root = new VBox(20); // 20px spacing between buttons
        root.setStyle("-fx-background-color: linear-gradient(to bottom right, #0f0c29, #302b63, #24243e);");
        root.setPadding(new Insets(20)); // Add some padding around the VBox
        root.setAlignment(Pos.TOP_LEFT); // Align buttons to the top left

        // Define the button styles
        String buttonStyle = "-fx-background-color: linear-gradient(to bottom right, #000428, #004e92);"
                + "-fx-text-fill: white;"
                + "-fx-font-size: 16px;"
                + "-fx-font-weight: bold;"
                + "-fx-padding: 15px 30px;"
                + "-fx-background-radius: 10;"
                + "-fx-pref-width: 300;"; // Set a fixed width for buttons

        // Create buttons and assign actions
        Button addButton = new Button("ADD A NEW STUDENT RECORD");
        addButton.setStyle(buttonStyle);
        addButton.setFont(Font.font("Arial", 14));
        addButton.setOnAction(e -> handleAddStudentAction()); // Add action for this button

        Button updateButton = new Button("UPDATE A STUDENT RECORD");
        updateButton.setStyle(buttonStyle);
        updateButton.setFont(Font.font("Arial", 14));
        updateButton.setOnAction(e -> handleUpdateStudentAction()); // Add action for this button

        Button deleteButton = new Button("DELETE A STUDENT RECORD");
        deleteButton.setStyle(buttonStyle);
        deleteButton.setFont(Font.font("Arial", 14));
        deleteButton.setOnAction(e -> handleDeleteStudentAction()); // Add action for this button

        Button viewLiveInButton = new Button("VIEW THE LIVE-IN STUDENT DETAILS");
        viewLiveInButton.setStyle(buttonStyle);
        viewLiveInButton.setFont(Font.font("Arial", 14));
        viewLiveInButton.setOnAction(e -> handleViewLiveInStudents()); // Add action for this button

//        Button viewLeftOutButton = new Button("VIEW THE LEFT-OUT STUDENTS DETAILS");
//        viewLeftOutButton.setStyle(buttonStyle);
//        viewLeftOutButton.setFont(Font.font("Arial", 14));
//        viewLeftOutButton.setOnAction(e -> handleViewLeftOutStudents()); // Add action for this button


        // Add buttons to the VBox layout
        root.getChildren().addAll(addButton, updateButton, deleteButton, viewLiveInButton);

        // Create the scene and set it for the menu stage
        Scene scene = new Scene(root, 800, 600);
        menuStage.setScene(scene);
        menuStage.setTitle("Menu");

        // Set the stage to full screen
//        menuStage.setFullScreen(true);
        menuStage.show();
    }

    private void handleAddStudentAction() {
        // Create a new stage for adding student details
        Stage addStudentStage = new Stage();

        // Create a VBox layout to organize the form fields
        VBox layout = new VBox(15); // 15px spacing between fields
        layout.setStyle("-fx-background-color: linear-gradient(to bottom right, #0f0c29, #302b63, #24243e); -fx-padding: 20px;");
        layout.setAlignment(Pos.CENTER);

        // Define universal styles for the UI elements
        String labelStyle = "-fx-text-fill: white; -fx-font-size: 14px; -fx-font-weight: bold;";

        // Update textFieldStyle to make the TextFields much smaller
        String textFieldStyle = "-fx-background-color: white; -fx-border-radius: 8px; -fx-padding: 5px; -fx-font-size: 8px; -fx-pref-width: 50px;";

        String buttonStyle = "-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-size: 16px; -fx-padding: 12px 20px; -fx-border-radius: 8px;";

        // Create Labels and TextFields for Name, Guardian Name, Age, CNIC
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

        // Create an "Add" button to save the student details
        Button addButton = new Button("Add");
        addButton.setStyle(buttonStyle);

        // Add hover effect for the "Add" button
        addButton.setOnMouseEntered(e -> addButton.setStyle("-fx-background-color: #45a049; -fx-text-fill: white; -fx-font-size: 16px; -fx-padding: 12px 20px; -fx-border-radius: 8px;"));
        addButton.setOnMouseExited(e -> addButton.setStyle(buttonStyle));

        // Add event handler for the "Add" button
        addButton.setOnAction(e -> {
            String name = nameField.getText();
            String guardianName = guardianNameField.getText();
            String age = ageField.getText();
            String cnic = cnicField.getText();

            if (name.isEmpty() || guardianName.isEmpty() || age.isEmpty() || cnic.isEmpty()) {
                // Show an alert if any field is empty
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("All fields are required");
                alert.setContentText("Please fill in all the fields.");
                alert.show();
            } else {
                // Write the student's information to the Students.txt file
                try (BufferedWriter writer = new BufferedWriter(new FileWriter("Students.txt", true))) {
                    // Create a string with student details
                    String studentData = name + "," + guardianName + "," + age + "," + cnic + System.lineSeparator();

                    // Write to the file
                    writer.write(studentData);

                    // Show success message
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Success");
                    alert.setHeaderText("Student Added");
                    alert.setContentText("The student has been added successfully.");
                    alert.show();

                    // Close the add student stage after successful addition
                    addStudentStage.close();
                } catch (IOException ex) {
                    // Handle file write error
                    ex.printStackTrace();
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("Failed to add student");
                    alert.setContentText("There was an error saving the student data.");
                    alert.show();
                }
            }
        });

        // Add all components to the layout
        layout.getChildren().addAll(nameLabel, nameField, guardianNameLabel, guardianNameField, ageLabel, ageField, cnicLabel, cnicField, addButton);

        // Create and show the scene for the add student stage
        Scene scene = new Scene(layout, 500, 400); // Adjusted scene size to ensure fields don't get cut off
        addStudentStage.setScene(scene);
        addStudentStage.setTitle("Add New Student");
        addStudentStage.show();
    }


    private void handleUpdateStudentAction() {
        // Create a new stage for updating student details
        Stage updateStudentStage = new Stage();

        // Create a VBox layout to organize the form fields
        VBox layout = new VBox(15); // 15px spacing between fields
        layout.setStyle("-fx-background-color: linear-gradient(to bottom right, #0f0c29, #302b63, #24243e); -fx-padding: 20px;");
        layout.setAlignment(Pos.CENTER);

        // Define a universal label style (white text color and other styles)
        String labelStyle = "-fx-text-fill: white; -fx-font-size: 14px; -fx-font-weight: bold;";

        // Label and TextField to search by student's name
        Label searchLabel = new Label("Enter Student Name to Update:");
        searchLabel.setStyle(labelStyle);
        TextField searchField = new TextField();
        searchField.setPromptText("Student Name");

        // Button to search the student
        Button searchButton = new Button("Search");
        searchButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-size: 16px; -fx-padding: 12px 20px; -fx-border-radius: 8px;");

        // Create fields for displaying and updating student details (now editable)
        Label nameLabel = new Label("Name:");
        nameLabel.setStyle(labelStyle); // Apply the universal label style
        TextField nameField = new TextField();

        Label guardianNameLabel = new Label("Guardian Name:");
        guardianNameLabel.setStyle(labelStyle); // Apply the universal label style
        TextField guardianNameField = new TextField();

        Label ageLabel = new Label("Age:");
        ageLabel.setStyle(labelStyle); // Apply the universal label style
        TextField ageField = new TextField();

        Label cnicLabel = new Label("CNIC:");
        cnicLabel.setStyle(labelStyle); // Apply the universal label style
        TextField cnicField = new TextField();

        // Create an "Update" button to save the updated student details
        Button updateButton = new Button("Update");
        updateButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-size: 16px; -fx-padding: 12px 20px; -fx-border-radius: 8px;");
        updateButton.setDisable(true); // Initially disabled until the student is found

        // Event handler for search button
        searchButton.setOnAction(e -> {
            String searchName = searchField.getText().trim();
            if (searchName.isEmpty()) {
                // Show alert if no name is entered
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Name is required");
                alert.setContentText("Please enter a student name to search.");
                alert.show();
            } else {
                try (BufferedReader reader = new BufferedReader(new FileReader("Students.txt"))) {
                    String line;
                    boolean found = false;

                    while ((line = reader.readLine()) != null) {
                        String[] studentData = line.split(",");
                        if (studentData[0].equalsIgnoreCase(searchName)) {
                            // If student is found, populate the fields with existing data
                            nameField.setText(studentData[0]);
                            guardianNameField.setText(studentData[1]);
                            ageField.setText(studentData[2]);
                            cnicField.setText(studentData[3]);

                            // Enable the update button
                            updateButton.setDisable(false);
                            found = true;
                            break;
                        }
                    }

                    if (!found) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Student Not Found");
                        alert.setHeaderText("Student Not Found");
                        alert.setContentText("No student found with the name: " + searchName);
                        alert.show();
                    }

                } catch (IOException ex) {
                    ex.printStackTrace();
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("Failed to Search");
                    alert.setContentText("There was an error searching for the student.");
                    alert.show();
                }
            }
        });

        // Event handler for update button
        updateButton.setOnAction(e -> {
            String newName = nameField.getText().trim();
            String guardianName = guardianNameField.getText().trim();
            String age = ageField.getText().trim();
            String cnic = cnicField.getText().trim();

            if (newName.isEmpty() || guardianName.isEmpty() || age.isEmpty() || cnic.isEmpty()) {
                // Show an alert if any field is empty
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("All fields are required");
                alert.setContentText("Please fill in all the fields.");
                alert.show();
            } else {
                try {
                    // Read all the data from the Students.txt file
                    List<String> lines = Files.readAllLines(Paths.get("Students.txt"));
                    boolean updated = false;

                    for (int i = 0; i < lines.size(); i++) {
                        String[] studentData = lines.get(i).split(",");
                        if (studentData[0].equalsIgnoreCase(searchField.getText().trim())) {
                            // Update the record with new data
                            lines.set(i, newName + "," + guardianName + "," + age + "," + cnic);
                            updated = true;
                            break;
                        }
                    }

                    if (updated) {
                        // Write the updated data back to the file
                        Files.write(Paths.get("Students.txt"), lines);
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Success");
                        alert.setHeaderText("Student Updated");
                        alert.setContentText("The student's record has been updated successfully.");
                        alert.show();

                        // Close the update student stage after successful update
                        updateStudentStage.close();
                    } else {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error");
                        alert.setHeaderText("Update Failed");
                        alert.setContentText("No matching student found to update.");
                        alert.show();
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("Failed to Update");
                    alert.setContentText("There was an error updating the student record.");
                    alert.show();
                }
            }
        });

        // Add all components to the layout
        layout.getChildren().addAll(searchLabel, searchField, searchButton, nameLabel, nameField, guardianNameLabel, guardianNameField, ageLabel, ageField, cnicLabel, cnicField, updateButton);

        // Create and show the scene for the update student stage
        Scene scene = new Scene(layout, 600, 600); // Adjusted scene size to ensure fields are fully visible
        updateStudentStage.setScene(scene);
        updateStudentStage.setTitle("Update Student Record");
        updateStudentStage.show();
    }


    private void handleDeleteStudentAction() {
        System.out.println("Delete Student button clicked");

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

        // Create a Label and TextField for student name input
        Label nameLabel = new Label("Enter Student Name to Delete:");
        nameLabel.setStyle(labelStyle);
        TextField nameField = new TextField();
        nameField.setStyle(textFieldStyle);

        // Create a "Delete" button to delete the student record
        Button deleteButton = new Button("Delete");
        deleteButton.setStyle(buttonStyle);

        // Add event handler for the "Delete" button
        deleteButton.setOnAction(e -> {
            String studentName = nameField.getText();

            if (studentName.isEmpty()) {
                // Show an alert if no name is entered
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Name is required");
                alert.setContentText("Please enter the name of the student to delete.");
                alert.show();
            } else {
                // Read the file and search for the student
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
                        // Show an alert if student is not found
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Student Not Found");
                        alert.setHeaderText(null);
                        alert.setContentText("No student found with the name: " + studentName);
                        alert.show();
                    } else {
                        // Overwrite the file with the updated content (without the deleted student)
                        try (BufferedWriter writer = new BufferedWriter(new FileWriter(studentFile))) {
                            for (String lineContent : updatedContent) {
                                writer.write(lineContent);
                                writer.newLine();
                            }
                        }

                        // Show a success message
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Success");
                        alert.setHeaderText("Student Deleted");
                        alert.setContentText("The student record has been successfully deleted.");
                        alert.show();
                        // Close the delete student stage
                        deleteStudentStage.close();
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("Failed to Delete Student");
                    alert.setContentText("There was an error reading or writing the student data.");
                    alert.show();
                }
            }
        });

        // Add the input field and button to the layout
        layout.getChildren().addAll(nameLabel, nameField, deleteButton);

        // Create and show the scene for the delete student stage
        Scene scene = new Scene(layout, 400, 300);
        deleteStudentStage.setScene(scene);
        deleteStudentStage.show();
    }


    private void handleViewLiveInStudents() {
        System.out.println("View Live-In Students button clicked");

        // Create a new stage to display the live-in students
        Stage viewStage = new Stage();
        viewStage.setTitle("Live-In Students");

        // Create a VBox layout to hold the TableView
        VBox layout = new VBox(10);
        layout.setStyle("-fx-background-color: linear-gradient(to bottom right, #0f0c29, #302b63, #24243e); -fx-padding: 20px;");
        layout.setAlignment(Pos.CENTER);

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
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Failed to Load Students");
            alert.setContentText("There was an error reading the student data.");
            alert.show();
        }

        // Add the TableView to the layout
        layout.getChildren().add(tableView);

        // Create and show the scene for the view students stage
        Scene scene = new Scene(layout, 800, 600);
        viewStage.setScene(scene);
        viewStage.show();
    }

//    private void handleViewLeftOutStudents() {
//        System.out.println("View Left-Out Students button clicked");
//        // Add specific functionality to view left-out student details
//    }

    public static void main(String[] args) {
        launch(args);
    }
}


