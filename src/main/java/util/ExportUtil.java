package util;

import javafx.scene.control.TableView;
import javafx.print.PrinterJob;
import model.KdvEntry;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.mail.*;
import javax.mail.internet.*;
import javax.swing.*;
import java.io.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Properties;

public class ExportUtil {

    public static void exportToTxt(List<KdvEntry> entries) {
        try (PrintWriter writer = new PrintWriter("kdv_records.txt")) {
            for (KdvEntry entry : entries) {
                writer.printf("Fiş No: %s, Tutar: %.2f, KDV: %.2f, Tarih: %s, Açıklama: %s%n",
                        entry.getFisNo(), entry.getTutar(), entry.getKdvTutari(), entry.getTarih(), entry.getAciklama());
            }
            JOptionPane.showMessageDialog(null, "TXT başarıyla dışa aktarıldı.");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "TXT dışa aktarma hatası: " + e.getMessage());
        }
    }

    public static void exportToPdf(List<KdvEntry> entries) {
        try (PrintWriter writer = new PrintWriter("kdv_records.pdf")) {
            writer.println("Fiş No\tTutar\tKDV\tTarih\tAçıklama");
            for (KdvEntry entry : entries) {
                writer.printf("%s\t%.2f\t%.2f\t%s\t%s%n",
                        entry.getFisNo(), entry.getTutar(), entry.getKdvTutari(), entry.getTarih(), entry.getAciklama());
            }
            JOptionPane.showMessageDialog(null, "PDF başarıyla dışa aktarıldı (metin formatında).");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "PDF dışa aktarma hatası: " + e.getMessage());
        }
    }

    public static void exportToExcel(List<KdvEntry> entries) {
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("KDV Records");
            Row header = sheet.createRow(0);
            header.createCell(0).setCellValue("Fiş No");
            header.createCell(1).setCellValue("Tutar");
            header.createCell(2).setCellValue("KDV");
            header.createCell(3).setCellValue("Tarih");
            header.createCell(4).setCellValue("Açıklama");

            int rowNum = 1;
            for (KdvEntry entry : entries) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(entry.getFisNo());
                row.createCell(1).setCellValue(entry.getTutar());
                row.createCell(2).setCellValue(entry.getKdvTutari());
                row.createCell(3).setCellValue(entry.getTarih().toString());
                row.createCell(4).setCellValue(entry.getAciklama());
            }

            try (FileOutputStream fileOut = new FileOutputStream("kdv_records.xlsx")) {
                workbook.write(fileOut);
            }

            JOptionPane.showMessageDialog(null, "Excel başarıyla dışa aktarıldı.");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Excel dışa aktarma hatası: " + e.getMessage());
        }
    }

    public static void printTable(TableView<?> tableView) {
        PrinterJob job = PrinterJob.createPrinterJob();
        if (job != null && job.showPrintDialog(tableView.getScene().getWindow())) {
            boolean success = job.printPage(tableView);
            if (success) {
                job.endJob();
                System.out.println("Yazdırma başarılı.");
            } else {
                System.out.println("Yazdırma başarısız.");
            }
        }
    }

    public static void sendMail(List<KdvEntry> entries, String email) {
        String host = "smtp.example.com";
        String from = "your@example.com";
        String password = "your_password";

        Properties props = new Properties();
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(props,
                new Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(from, password);
                    }
                });

        try {
            StringBuilder body = new StringBuilder("KDV Kayıtları:\n\n");
            for (KdvEntry entry : entries) {
                body.append(String.format("Fiş: %s, Tutar: %.2f, KDV: %.2f, Tarih: %s, Açıklama: %s%n",
                        entry.getFisNo(), entry.getTutar(), entry.getKdvTutari(), entry.getTarih(), entry.getAciklama()));
            }

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
            message.setSubject("KDV Kayıtları");
            message.setText(body.toString());

            Transport.send(message);
            JOptionPane.showMessageDialog(null, "Mail başarıyla gönderildi.");
        } catch (MessagingException e) {
            JOptionPane.showMessageDialog(null, "Mail gönderme hatası: " + e.getMessage());
        }
    }
}
