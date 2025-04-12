package util;

import java.sql.*;

public class KdvDebugUtil {
    public static void main(String[] args) {
        try (Connection conn = DBUtil.getConnection()) {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM kdv_entries");

            while (rs.next()) {
                System.out.println("Fiş No: " + rs.getString("receipt_no"));
                System.out.println("Tutar: " + rs.getDouble("amount"));
                System.out.println("KDV Oranı: " + rs.getDouble("kdv_rate"));
                System.out.println("Toplam: " + rs.getDouble("total"));
                System.out.println("------------");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
