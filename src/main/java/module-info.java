module com.example.tiptracker {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;

    opens com.example.tiptracker to javafx.fxml;
    opens com.example.tiptracker.controllers to javafx.fxml; // Allow reflection for controllers
    opens com.example.tiptracker.models to javafx.base; // Add this line
    opens com.example.tiptracker.utils to javafx.base; // Add this line




    exports com.example.tiptracker;
    exports com.example.tiptracker.controllers; // Export the controllers package if needed outside
}
