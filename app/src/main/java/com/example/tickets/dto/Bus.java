package com.example.tickets.dto;

import android.widget.ImageView;

import java.io.Serializable;

public class Bus implements Serializable {
    private String name;
    private String precioUnitario;
    private String precioMensual;
    private int imagen1;

    public Bus() {
    }

    public Bus(String name, String precioMensual, String precioUnitario, int imagen1) {
        this.name = name;
        this.imagen1 = imagen1;
        this.precioMensual = precioMensual;
        this.precioUnitario = precioUnitario;
    }

    public int getImagen1() {
        return imagen1;
    }

    public void setImagen1(int imagen1) {
        this.imagen1 = imagen1;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrecioMensual() {
        return precioMensual;
    }

    public void setPrecioMensual(String precioMensual) {
        this.precioMensual = precioMensual;
    }

    public String getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(String precioUnitario) {
        this.precioUnitario = precioUnitario;
    }
}
