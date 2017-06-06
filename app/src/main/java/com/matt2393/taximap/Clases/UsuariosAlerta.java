package com.matt2393.taximap.Clases;


public class UsuariosAlerta {

    private String nombre,lat,lon;

    public UsuariosAlerta(String nombre, String lat, String lon) {
        this.nombre = nombre;
        this.lat = lat;
        this.lon = lon;
    }

    public UsuariosAlerta() {

    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
