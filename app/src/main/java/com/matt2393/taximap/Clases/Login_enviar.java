package com.matt2393.taximap.Clases;


public class Login_enviar {

    private String ci,fecha,hora,lat,lon;

    public Login_enviar() {
    }

    public Login_enviar(String ci, String fecha, String hora, String lat, String lon) {
        this.ci = ci;
        this.fecha = fecha;
        this.hora = hora;
        this.lat = lat;
        this.lon = lon;
    }

    public String getCi() {
        return ci;
    }

    public void setCi(String ci) {
        this.ci = ci;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }
}
