package dao;

import model.UserData;
import util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {

    public static void addUser(UserData user) {
        String sql = "INSERT INTO users (username, email, role, password) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getEmail());
            stmt.setString(3, user.getRole());
            stmt.setString(4, user.getPassword());
            stmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void updateUser(UserData user) {
        String sql = "UPDATE users SET email = ?, role = ?, password = ? WHERE username = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, user.getEmail());
            stmt.setString(2, user.getRole());
            stmt.setString(3, user.getPassword());
            stmt.setString(4, user.getUsername());
            stmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void deleteUser(String username) {
        String sql = "DELETE FROM users WHERE username = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);
            stmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static List<UserData> getAllUsers() {
        List<UserData> list = new ArrayList<>();
        String sql = "SELECT * FROM users";

        try (Connection conn = DBUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                UserData user = new UserData(
                        rs.getString("username"),
                        rs.getString("email"),
                        rs.getString("role"),
                        rs.getString("password")
                );
                list.add(user);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
}
