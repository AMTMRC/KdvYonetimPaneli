package util;

import java.sql.Connection;
import java.sql.Statement;

public class AddEmailColumn {
    public static void main(String[] args) {
        try (Connection conn = DBUtil.getConnection();
             Statement stmt = conn.createStatement()) {

            stmt.executeUpdate("ALTER TABLE users ADD COLUMN email VARCHAR(100)");
            System.out.println("✅ E-posta alanı eklendi!");

        } catch (Exception e) {
            System.out.println("⚠️ Ekleme başarısız (muhtemelen zaten var): " + e.getMessage());
        }
    }
}
