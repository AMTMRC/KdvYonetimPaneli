package util;

import org.mindrot.jbcrypt.BCrypt;
import java.sql.*;

public class InitDatabase {

    public static void initialize() {
        try (Connection conn = DBUtil.getConnection()) {
            Statement stmt = conn.createStatement();

            // users tablosunu oluştur
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS users (" +
                    "id IDENTITY PRIMARY KEY, " +
                    "username VARCHAR(255), " +
                    "password VARCHAR(255), " +
                    "email VARCHAR(255), " +
                    "role VARCHAR(50))");

            // admin kullanıcısı zaten var mı kontrol et
            ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM users WHERE username='admin'");
            rs.next();
            int adminCount = rs.getInt(1);

            if (adminCount == 0) {
                String hashedPassword = BCrypt.hashpw("admin", BCrypt.gensalt());
                PreparedStatement pstmt = conn.prepareStatement(
                        "INSERT INTO users (username, password, email, role) VALUES (?, ?, ?, ?)");
                pstmt.setString(1, "admin");
                pstmt.setString(2, hashedPassword);
                pstmt.setString(3, "admin@example.com");
                pstmt.setString(4, "ADMIN");
                pstmt.executeUpdate();
                System.out.println("✅ Admin kullanıcısı eklendi.");
            } else {
                System.out.println("ℹ️ Admin kullanıcısı zaten var, ekleme yapılmadı.");
            }

            // kdv_entries tablosunu oluştur
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS kdv_entries (" +
                    "id IDENTITY PRIMARY KEY, " +
                    "receipt_no VARCHAR(50), " +
                    "date DATE, " +
                    "amount DOUBLE, " +
                    "kdv_rate VARCHAR(10), " +
                    "kdv DOUBLE, " +
                    "total DOUBLE, " +
                    "description VARCHAR(255))");

            System.out.println("✅ kdv_entries tablosu kontrol edildi.");
            System.out.println("Veritabanı hazırlandı.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
