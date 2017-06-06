package com.matt2393.taximap.Clases;


public class Logout_enviar {
    private String ci,id_login,id_ubicacion,fecha,hora,lat,lon;

    public Logout_enviar() {
    }

    public Logout_enviar(String ci, String id_login, String id_ubicacion, String fecha, String hora, String lat, String lon) {
        this.ci = ci;
        this.id_login = id_login;
        this.id_ubicacion = id_ubicacion;
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

    public String getId_login() {
        return id_login;
    }

    public void setId_login(String id_login) {
        this.id_login = id_login;
    }

    public String getId_ubicacion() {
        return id_ubicacion;
    }

    public void setId_ubicacion(String id_ubicacion) {
        this.id_ubicacion = id_ubicacion;
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
