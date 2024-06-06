package com.eduapp.edumanagerapp.models;

public class KasAdmin {
    private final int id;
    private final String nama;
    private final String role;

    public KasAdmin(int id, String nama, String role) {
        this.id = id;
        this.nama = nama;
        this.role = role;
    }

    // Getter untuk id, nama, dan role
    public int getId() {
        return id;
    }

    public String getNama() {
        return nama;
    }

    public String getRole() {
        return role;
    }
}

