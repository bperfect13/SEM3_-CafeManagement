import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginPage {
    private Stage stage;
    private Scene scene;

    @FXML
    private TextField UserField;
    @FXML
    private PasswordField PassField;
    @FXML
    private Button cancelButton;

    public void CancelAction(ActionEvent event) {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }
    @FXML
    void SignupAction(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("D_signup.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    public void LoginAction(ActionEvent event) {
        String username = UserField.getText();
        String password = PassField.getText();

        // Database connection
        String Url = "jdbc:mysql://localhost:3306/ryandbit";
        String DBUser = "root";
        String DBPassword = "Ryandsouza123$";

        if (UserField.getText().isEmpty() || PassField.getText().isEmpty()) {
            showError("NULL ERROR", "Username or Password field cannot be empty.");
        } else {
            try (Connection connection = DriverManager.getConnection(Url, DBUser, DBPassword)) {
                String query = "SELECT * FROM login WHERE Username = ? AND Password = ?";
                try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                    preparedStatement.setString(1, username);
                    preparedStatement.setString(2, password);
                    try (ResultSet resultSet = preparedStatement.executeQuery()) {
                        if (resultSet.next()) {
                            Parent root = FXMLLoader.load(getClass().getResource("Dashboard.fxml"));
                            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                            scene = new Scene(root);
                            stage.setScene(scene);
                            stage.show();
                        } else {
                            showError("Login Failed", "Invalid username or password.");
                        }
                    }
                }
            } catch (SQLException e) {
                // Handle database connection or query errors
                e.printStackTrace();
                showError("Database Error", "An error occurred while accessing the database.");
            } catch (IOException e) {
                // Handle FXML loading errors
                e.printStackTrace();
                showError("FXML Loading Error", "An error occurred while loading the FXML file.");
            }
        }
    }

    // Helper method to display error messages
    public void showError(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
