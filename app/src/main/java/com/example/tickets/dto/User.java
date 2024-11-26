package com.example.tickets.dto;

import android.widget.ImageView;

public class User {
    private String name;
    private String lastName;
    private String correo;
    private String pwd;
    private String saldo;
    private String rol;

    public User() {
    }

    public User(String name, String lastName, String pwd, String correo, String saldo, String rol) {
        this.name = name;
        this.lastName = lastName;
        this.pwd = pwd;
        this.correo = correo;
        this.saldo = saldo;
        this.rol = rol;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public String getSaldo() {
        return saldo;
    }

    public void setSaldo(String saldo) {
        this.saldo = saldo;
    }
}
