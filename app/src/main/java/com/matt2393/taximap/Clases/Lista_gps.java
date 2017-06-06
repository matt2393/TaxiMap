package com.matt2393.taximap.Clases;


import java.util.List;

public class Lista_gps {
    private List<GpsDatos> listaGps;

    public Lista_gps() {
    }

    public Lista_gps(List<GpsDatos> listaGps) {
        this.listaGps = listaGps;
    }

    public List<GpsDatos> getListaGps() {
        return listaGps;
    }

    public void setListaGps(List<GpsDatos> listaGps) {
        this.listaGps = listaGps;
    }
}
