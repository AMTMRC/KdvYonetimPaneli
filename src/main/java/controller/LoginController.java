package controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import util.BCryptUtil;
import util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class LoginController {

    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private Label errorLabel;

    @FXML
    private void login() {
        String username = usernameField.getText().trim();
        String password = passwordField.getText();

        try (Connection conn = DBUtil.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("SELECT password, role FROM users WHERE username = ?");
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String hashedPassword = rs.getString("password");
                String role = rs.getString("role");

                if (BCryptUtil.checkPassword(password, hashedPassword)) {
                    openRolePanel(role);
                } else {
                    errorLabel.setText("Şifre yanlış.");
                }
            } else {
                errorLabel.setText("Kullanıcı bulunamadı.");
            }

        } catch (Exception e) {
            errorLabel.setText("Veritabanı bağlantı hatası: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void openRolePanel(String role) {
        try {
            Stage stage = (Stage) usernameField.getScene().getWindow();
            FXMLLoader loader;

            switch (role.toUpperCase()) {
                case "ADMIN":
                    loader = new FXMLLoader(getClass().getResource("/view/admin.fxml"));
                    break;
                case "OPERATOR":
                    loader = new FXMLLoader(getClass().getResource("/view/operator.fxml"));
                    break;
                case "USER":
                    loader = new FXMLLoader(getClass().getResource("/view/user.fxml"));
                    break;
                default:
                    throw new IllegalStateException("Geçersiz rol: " + role);
            }

            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
            stage.setTitle(role + " Paneli");
        } catch (Exception e) {
            errorLabel.setText("Panel yükleme hatası: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void goToRegister() {
        try {
            Stage stage = (Stage) usernameField.getScene().getWindow();
            Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/view/register.fxml")));
            stage.setScene(scene);
        } catch (Exception e) {
            errorLabel.setText("Kayıt ekranı yüklenemedi: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
