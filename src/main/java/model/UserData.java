package model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class UserData {
    private final StringProperty username;
    private final StringProperty email;
    private final StringProperty role;
    private final StringProperty password;

    public UserData(String username, String email, String role, String password) {
        this.username = new SimpleStringProperty(username);
        this.email = new SimpleStringProperty(email);
        this.role = new SimpleStringProperty(role);
        this.password = new SimpleStringProperty(password);
    }

    // Property getter'lar
    public StringProperty usernameProperty() { return username; }
    public StringProperty emailProperty() { return email; }
    public StringProperty roleProperty() { return role; }
    public StringProperty passwordProperty() { return password; }

    // Normal getter-setter (gerekirse)
    public String getUsername() { return username.get(); }
    public void setUsername(String value) { username.set(value); }

    public String getEmail() { return email.get(); }
    public void setEmail(String value) { email.set(value); }

    public String getRole() { return role.get(); }
    public void setRole(String value) { role.set(value); }

    public String getPassword() { return password.get(); }
    public void setPassword(String value) { password.set(value); }
}
