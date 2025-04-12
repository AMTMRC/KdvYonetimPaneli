package model;

import java.time.LocalDate;

public class KdvEntry {
    private String receipt_no;
    private double tutar;
    private double kdvOrani;
    private double kdvTutari;
    private double toplamTutar;
    private String aciklama;
    private LocalDate tarih;

    public KdvEntry(String receipt_no, double tutar, double kdvOrani, double kdvTutari, double toplamTutar, String aciklama, LocalDate tarih) {
        this.receipt_no = receipt_no;
        this.tutar = tutar;
        this.kdvOrani = kdvOrani;
        this.kdvTutari = kdvTutari;
        this.toplamTutar = toplamTutar;
        this.aciklama = aciklama;
        this.tarih = tarih;
    }

    public String getFisNo() {
        return receipt_no;
    }

    public double getTutar() {
        return tutar;
    }

    public double getKdvOrani() {
        return kdvOrani;
    }

    public double getKdvTutari() {
        return kdvTutari;
    }

    public double getToplamTutar() {
        return toplamTutar;
    }

    public String getAciklama() {
        return aciklama;
    }

    public LocalDate getTarih() {
        return tarih;
    }
}
