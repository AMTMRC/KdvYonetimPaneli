package controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import util.BCryptUtil;
import util.DBUtil;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class RegisterController {

    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private ComboBox<String> roleBox;
    @FXML private Label statusLabel;

    @FXML
    public void initialize() {
        roleBox.getItems().addAll("ADMIN", "OPERATOR", "USER");
    }

    @FXML
    private void registerUser() {
        String username = usernameField.getText().trim();
        String password = passwordField.getText();
        String role = roleBox.getValue();

        if (username.isEmpty() || password.isEmpty() || role == null) {
            statusLabel.setText("Tüm alanları doldurun.");
            statusLabel.setStyle("-fx-text-fill: red;");
            return;
        }

        try (Connection conn = DBUtil.getConnection()) {
            String hashed = BCryptUtil.hashPassword(password);
            PreparedStatement stmt = conn.prepareStatement(
                    "INSERT INTO users (username, password, role) VALUES (?, ?, ?)"
            );
            stmt.setString(1, username);
            stmt.setString(2, hashed);
            stmt.setString(3, role);
            stmt.executeUpdate();

            statusLabel.setText("Kayıt başarılı!");
            statusLabel.setStyle("-fx-text-fill: green;");

            usernameField.clear();
            passwordField.clear();
            roleBox.getSelectionModel().clearSelection();

        } catch (Exception e) {
            statusLabel.setText("Kayıt başarısız!");
            statusLabel.setStyle("-fx-text-fill: red;");
            e.printStackTrace();
        }
    }
    @FXML
    private void goToLogin() throws Exception {
        Stage stage = (Stage) usernameField.getScene().getWindow();
        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/view/login.fxml")));
        stage.setScene(scene);
    }

}
