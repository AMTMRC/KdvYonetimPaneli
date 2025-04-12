import util.DBUtil;

import java.sql.Connection;
import java.sql.Statement;
public class TestDB {
    public static void main(String[] args) {
        try {
            DBUtil.getConnection();
            System.out.println("✅ Veritabanına başarıyla bağlandı!");
        } catch (Exception e) {
            System.out.println("❌ HATA: Veritabanına bağlanılamadı.");
            e.printStackTrace();
        }
    }
}


