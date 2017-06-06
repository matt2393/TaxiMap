package com.matt2393.taximap.Clases;


public class Gps_crear {
    private String ci,latitud,longitud,fecha;

    public Gps_crear() {
    }

    public Gps_crear(String ci, String latitud, String longitud, String fecha) {
        this.ci = ci;
        this.latitud = latitud;
        this.longitud = longitud;
        this.fecha = fecha;
    }

    public String getCi() {
        return ci;
    }

    public void setCi(String ci) {
        this.ci = ci;
    }

    public String getLatitud() {
        return latitud;
    }

    public void setLatitud(String latitud) {
        this.latitud = latitud;
    }

    public String getLongitud() {
        return longitud;
    }

    public void setLongitud(String longitud) {
        this.longitud = longitud;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
}

