package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.KdvEntry;
import util.DBUtil;
import util.ExportUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;

public class KdvController {
    @FXML private TextField tutarField;
    @FXML private TextField kdvOraniField;
    @FXML private TextField kdvTutariField;
    @FXML private TextField toplamTutarField;
    @FXML private TextField fisNoField;
    @FXML private DatePicker tarihPicker;
    @FXML private TextField aciklamaField;
    @FXML private TableView<KdvEntry> kdvTable;
    @FXML private TableColumn<KdvEntry, String> colFisNo;
    @FXML private TableColumn<KdvEntry, Double> colTutar;
    @FXML private TableColumn<KdvEntry, Double> colKdvOrani;
    @FXML private TableColumn<KdvEntry, Double> colKdvTutari;
    @FXML private TableColumn<KdvEntry, Double> colToplamTutar;
    @FXML private TableColumn<KdvEntry, String> colAciklama;
    @FXML private TableColumn<KdvEntry, LocalDate> colTarih;

    private ObservableList<KdvEntry> kdvList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        colFisNo.setCellValueFactory(new PropertyValueFactory<>("fisNo"));
        colTutar.setCellValueFactory(new PropertyValueFactory<>("tutar"));
        colKdvOrani.setCellValueFactory(new PropertyValueFactory<>("kdvOrani"));
        colKdvTutari.setCellValueFactory(new PropertyValueFactory<>("kdvTutari"));
        colToplamTutar.setCellValueFactory(new PropertyValueFactory<>("toplamTutar"));
        colAciklama.setCellValueFactory(new PropertyValueFactory<>("aciklama"));
        colTarih.setCellValueFactory(new PropertyValueFactory<>("tarih"));

        loadKdvData();
    }

    private void loadKdvData() {
        try (Connection conn = DBUtil.getConnection()) {
            String sql = "SELECT * FROM kdv_entries";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                double kdvOrani = Double.parseDouble(rs.getString("KDV_RATE").replace("%", "").trim());
                kdvList.add(new KdvEntry(
                        rs.getString("RECEIPT_NO"),
                        rs.getDouble("AMOUNT"),
                        kdvOrani,
                        rs.getDouble("KDV"),
                        rs.getDouble("TOTAL"),
                        rs.getString("DESCRIPTION"),
                        LocalDate.parse(rs.getString("DATE"))
                ));
            }
            kdvTable.setItems(kdvList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @FXML
    public void calculateKDV(ActionEvent event) {
        try {
            double tutar = Double.parseDouble(tutarField.getText());
            double kdvOrani = Double.parseDouble(kdvOraniField.getText());
            double kdvTutari = tutar * kdvOrani / 100;
            double toplamTutar = tutar + kdvTutari;

            kdvTutariField.setText(String.format("%.2f", kdvTutari));
            toplamTutarField.setText(String.format("%.2f", toplamTutar));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void saveKdvRecord(ActionEvent event) {
        try (Connection conn = DBUtil.getConnection()) {
            String sql = "INSERT INTO kdv_entries (RECEIPT_NO, AMOUNT, KDV_RATE, KDV, TOTAL, DESCRIPTION, DATE) VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, fisNoField.getText());
            pstmt.setDouble(2, Double.parseDouble(tutarField.getText()));
            pstmt.setDouble(3, Double.parseDouble(kdvOraniField.getText()));
            pstmt.setDouble(4, Double.parseDouble(kdvTutariField.getText()));
            pstmt.setDouble(5, Double.parseDouble(toplamTutarField.getText()));
            pstmt.setString(6, aciklamaField.getText());
            pstmt.setString(7, tarihPicker.getValue().toString());
            pstmt.executeUpdate();
            kdvList.clear();
            loadKdvData();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML public void exportToTxt(ActionEvent event) { ExportUtil.exportToTxt(kdvTable.getItems()); }
    @FXML public void exportToPdf(ActionEvent event) { ExportUtil.exportToPdf(kdvTable.getItems()); }
    @FXML public void exportToExcel(ActionEvent event) { ExportUtil.exportToExcel(kdvTable.getItems()); }
    @FXML public void printKdvTable(ActionEvent event) { ExportUtil.printTable(kdvTable); }
    @FXML public void sendKdvRecordsEmail(ActionEvent event) { ExportUtil.sendMail(kdvTable.getItems(), "example@example.com"); }

    @FXML
    public void searchKdvRecords(ActionEvent event) {
        String keyword = aciklamaField.getText().toLowerCase();
        ObservableList<KdvEntry> filteredList = FXCollections.observableArrayList();
        for (KdvEntry entry : kdvList) {
            if (entry.getAciklama().toLowerCase().contains(keyword)) {
                filteredList.add(entry);
            }
        }
        kdvTable.setItems(filteredList);
    }
}
