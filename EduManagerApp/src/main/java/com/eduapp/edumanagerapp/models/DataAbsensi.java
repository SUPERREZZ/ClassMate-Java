package com.eduapp.edumanagerapp.models;

public class DataAbsensi {
    private int idUser;
    private String tanggal;
    private String keterangan;

    public DataAbsensi(int idUser, String tanggal, String keterangan) {
        this.idUser = idUser;
        this.tanggal = tanggal;
        this.keterangan = keterangan;
    }


    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }
}
