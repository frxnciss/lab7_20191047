package com.example.tickets.dto;

import android.widget.ImageView;

import java.io.Serializable;

public class Bus implements Serializable {
    private String name;
    private String precioUnitario;
    private String precioMensual;
    private ImageView imagen1;
    private ImageView imgen2;
    private ImageView imagen3;

    public Bus() {
    }

    public Bus(ImageView imagen1, ImageView imagen3, ImageView imgen2, String name, String precioMensual, String precioUnitario) {
        this.imagen1 = imagen1;
        this.imagen3 = imagen3;
        this.imgen2 = imgen2;
        this.name = name;
        this.precioMensual = precioMensual;
        this.precioUnitario = precioUnitario;
    }

    public ImageView getImagen1() {
        return imagen1;
    }

    public void setImagen1(ImageView imagen1) {
        this.imagen1 = imagen1;
    }

    public ImageView getImagen3() {
        return imagen3;
    }

    public void setImagen3(ImageView imagen3) {
        this.imagen3 = imagen3;
    }

    public ImageView getImgen2() {
        return imgen2;
    }

    public void setImgen2(ImageView imgen2) {
        this.imgen2 = imgen2;
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
