package com.matt2393.taximap.Clases;



public class Recorrido_enviar {

    private String id_ubicacion,lat,lon,ocupado,alerta;

    public Recorrido_enviar() {
    }

    public Recorrido_enviar(String id_ubicacion, String lat, String lon, String ocupado, String alerta) {
        this.id_ubicacion = id_ubicacion;
        this.lat = lat;
        this.lon = lon;
        this.ocupado = ocupado;
        this.alerta = alerta;
    }

    public String getId_ubicacion() {
        return id_ubicacion;
    }

    public void setId_ubicacion(String id_ubicacion) {
        this.id_ubicacion = id_ubicacion;
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

    public String getOcupado() {
        return ocupado;
    }

    public void setOcupado(String ocupado) {
        this.ocupado = ocupado;
    }

    public String getAlerta() {
        return alerta;
    }

    public void setAlerta(String alerta) {
        this.alerta = alerta;
    }
}
