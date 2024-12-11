module com.example.project {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.project to javafx.fxml;
    exports com.example.project;
    exports com.example.project.Extra;
    opens com.example.project.Extra to javafx.fxml;
}