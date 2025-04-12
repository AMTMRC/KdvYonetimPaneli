package controller;

import dao.UserDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import model.UserData;

import java.util.List;

public class AdminController {

    @FXML private TableView<UserData> userTable;
    @FXML private TableColumn<UserData, String> colUsername;
    @FXML private TableColumn<UserData, String> colEmail;
    @FXML private TableColumn<UserData, String> colRole;
    @FXML private TableColumn<UserData, String> colPassword;

    @FXML private TextField usernameField;
    @FXML private TextField emailField;
    @FXML private PasswordField passwordField;
    @FXML private ComboBox<String> roleBox;

    @FXML private TextField searchField;
    @FXML private ComboBox<String> roleFilterBox;

    @FXML private VBox kdvMenuBox;

    private final ObservableList<UserData> userList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        colUsername.setCellValueFactory(cellData -> cellData.getValue().usernameProperty());
        colEmail.setCellValueFactory(cellData -> cellData.getValue().emailProperty());
        colRole.setCellValueFactory(cellData -> cellData.getValue().roleProperty());
        colPassword.setCellValueFactory(cellData -> cellData.getValue().passwordProperty());

        roleBox.setItems(FXCollections.observableArrayList("ADMIN", "OPERATOR", "USER"));
        roleFilterBox.setItems(FXCollections.observableArrayList("Tümü", "ADMIN", "OPERATOR", "USER"));
        roleFilterBox.setValue("Tümü");

        loadUsersFromDatabase();
        kdvMenuBox.setVisible(true);
    }

    private void loadUsersFromDatabase() {
        List<UserData> users = UserDAO.getAllUsers();
        userList.setAll(users);
        userTable.setItems(userList);
    }

    @FXML
    private void handleAddUser() {
        String username = usernameField.getText();
        String email = emailField.getText();
        String password = passwordField.getText();
        String role = roleBox.getValue();

        if (username.isEmpty() || email.isEmpty() || password.isEmpty() || role == null) {
            showAlert("Hata", "Lütfen tüm alanları doldurunuz", Alert.AlertType.WARNING);
            return;
        }

        UserData newUser = new UserData(username, email, role, password);
        UserDAO.addUser(newUser);
        userList.add(newUser);
        clearFields();
    }

    @FXML
    private void handleUpdateUser() {
        UserData selected = userTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert("Hata", "Lütfen güncellenecek kullanıcıyı seçin", Alert.AlertType.WARNING);
            return;
        }

        selected.setUsername(usernameField.getText());
        selected.setEmail(emailField.getText());
        selected.setPassword(passwordField.getText());
        selected.setRole(roleBox.getValue());

        UserDAO.updateUser(selected);
        userTable.refresh();
        clearFields();
    }

    @FXML
    private void handleDeleteUser() {
        UserData selected = userTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            UserDAO.deleteUser(selected.getUsername());
            userList.remove(selected);
            clearFields();
        }
    }

    @FXML
    private void handleSearch() {
        String keyword = searchField.getText().toLowerCase();
        String selectedRole = roleFilterBox.getValue();

        ObservableList<UserData> filtered = FXCollections.observableArrayList();
        for (UserData user : userList) {
            boolean matches = user.getUsername().toLowerCase().contains(keyword) ||
                    user.getEmail().toLowerCase().contains(keyword);
            boolean roleMatches = selectedRole.equals("Tümü") || user.getRole().equalsIgnoreCase(selectedRole);

            if (matches && roleMatches) filtered.add(user);
        }
        userTable.setItems(filtered);
    }

    @FXML
    private void handleExportPDF() {
        System.out.println("PDF dışa aktarılıyor...");
    }

    @FXML
    private void handleExportExcel() {
        System.out.println("Excel dışa aktarılıyor...");
    }

    @FXML
    private void handlePrint() {
        System.out.println("Yazdırılıyor...");
    }

    @FXML
    private void handleSendMail() {
        System.out.println("Mail gönderiliyor...");
    }

    @FXML
    private void logout() {
        System.out.println("Çıkış yapılıyor...");
        System.exit(0);
    }

    private void clearFields() {
        usernameField.clear();
        emailField.clear();
        passwordField.clear();
        roleBox.setValue(null);
    }

    private void showAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    private void handleResetPassword() {
        UserData selected = userTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert("Hata", "Lütfen şifresi sıfırlanacak kullanıcıyı seçin", Alert.AlertType.WARNING);
            return;
        }

        // Varsayılan şifre
        String defaultPassword = "123456";
        selected.setPassword(defaultPassword);
        UserDAO.updateUser(selected);

        showAlert("Bilgi", selected.getUsername() + " kullanıcısının şifresi sıfırlandı.", Alert.AlertType.INFORMATION);
        userTable.refresh();
    }
    @FXML
    private void handleResetPassword(ActionEvent event) {
        System.out.println("Şifre sıfırlama işlemi henüz tanımlanmadı.");
        // Buraya şifre sıfırlama için gerekli işlemleri entegre edeceğiz
    }

}
