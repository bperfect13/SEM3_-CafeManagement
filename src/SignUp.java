import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class SignUp implements Initializable {

    @FXML
    private Button loginpageButton;
    @FXML
    private Button SignupButton;
    @FXML
    private Label loginmessagelabel;
    @FXML
    private TextField firstNameTextField;
    @FXML
    private TextField lastNameTextField;
    @FXML
    private DatePicker dateOfBirthDatePicker;
    @FXML
    private TextField usernameTextField;
    @FXML
    private TextField passwordTextField;

    private Stage stage;
    private Scene scene;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Initialize any necessary data here
    }

    @FXML
    private void OpenLoginPage(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("login_page.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void SignupAction(ActionEvent event) {
        String firstName = firstNameTextField.getText();
        String lastName = lastNameTextField.getText();
        LocalDate dateOfBirth = dateOfBirthDatePicker.getValue();
        String username = usernameTextField.getText();
        String password = passwordTextField.getText();

        String Url = "jdbc:mysql://localhost:3306/ryandbit";
        String DBUser = "root";
        String DBPassword = "Ryandsouza123$";

        if (username.isEmpty() || password.isEmpty() || firstName.isEmpty() || lastName.isEmpty() || dateOfBirth == null) {
            showError("NULL ERROR", "Please fill in all fields.");
        } else {
            try (Connection connection = DriverManager.getConnection(Url, DBUser, DBPassword)) {
                String query = "INSERT INTO login (first_name, last_name, date_of_birth, username, password) VALUES (?, ?, ?, ?, ?)";
                try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                    preparedStatement.setString(1, firstName);
                    preparedStatement.setString(2, lastName);
                    Date sqlDate = Date.valueOf(dateOfBirth);
                    preparedStatement.setDate(3, sqlDate);
                    preparedStatement.setString(4, username);
                    preparedStatement.setString(5, password);

                    int rowsAffected = preparedStatement.executeUpdate();
                    if (rowsAffected > 0) {
                        Parent root = FXMLLoader.load(getClass().getResource("login_page.fxml"));
                        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                        scene = new Scene(root);
                        stage.setScene(scene);
                        stage.show();
                    } else {
                        showError("Registration Failed", "Failed to insert data into the database.");
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
                showError("Database Error", "An error occurred while accessing the database.");
            } catch (IOException e) {
                e.printStackTrace();
                showError("FXML Loading Error", "An error occurred while loading the FXML file.");
            }
        }
    }

    // Helper method to display error messages
    private void showError(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
