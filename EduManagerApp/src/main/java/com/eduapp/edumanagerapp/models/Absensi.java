package com.eduapp.edumanagerapp.models;


public class Absensi {
    private int id;
    private String nama;
    private String role;

    public Absensi(int id, String nama, String role) {
        this.id = id;
        this.nama = nama;
        this.role = role;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
