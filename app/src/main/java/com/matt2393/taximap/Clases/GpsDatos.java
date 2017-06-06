package com.matt2393.taximap.Clases;

public class GpsDatos {

    private String ci,nombre,apellidoP,apellidoM,ultimo_lat,ultimo_lon;

    public GpsDatos() {
    }

    public GpsDatos(String ci, String nombre, String apellidoP, String apellidoM, String ultimo_lat, String ultimo_lon) {
        this.ci = ci;
        this.nombre = nombre;
        this.apellidoP = apellidoP;
        this.apellidoM = apellidoM;
        this.ultimo_lat = ultimo_lat;
        this.ultimo_lon = ultimo_lon;
    }

    public String getCi() {
        return ci;
    }

    public void setCi(String ci) {
        this.ci = ci;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidoP() {
        return apellidoP;
    }

    public void setApellidoP(String apellidoP) {
        this.apellidoP = apellidoP;
    }

    public String getApellidoM() {
        return apellidoM;
    }

    public void setApellidoM(String apellidoM) {
        this.apellidoM = apellidoM;
    }

    public String getUltimo_lat() {
        return ultimo_lat;
    }

    public void setUltimo_lat(String ultimo_lat) {
        this.ultimo_lat = ultimo_lat;
    }

    public String getUltimo_lon() {
        return ultimo_lon;
    }

    public void setUltimo_lon(String ultimo_lon) {
        this.ultimo_lon = ultimo_lon;
    }
}
