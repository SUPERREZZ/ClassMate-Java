package com.eduapp.edumanagerapp.models;
public class user {
    int id;
    String nama;
    String password;
    String role;
    public user(String nama, String password, String role) {
        this.nama = nama;
        this.password = password;
        this.role = role;
    }

    public user(String nama, String role, int id) {
        this.id = id;
        this.nama = nama;
        this.role = role;
    }

    public user(String nama, String password, String role, int id) {
        this.id = id;
        this.nama = nama;
        this.password = password;
        this.role = role;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public int getId() {
        return id;
    }
}
