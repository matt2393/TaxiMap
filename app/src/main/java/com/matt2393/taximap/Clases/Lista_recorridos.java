package com.matt2393.taximap.Clases;


import java.util.List;

public class Lista_recorridos {

    private List<Recorridos_obtener> lista;

    public Lista_recorridos() {
    }

    public Lista_recorridos(List<Recorridos_obtener> lista) {
        this.lista = lista;
    }

    public List<Recorridos_obtener> getLista() {
        return lista;
    }

    public void setLista(List<Recorridos_obtener> lista) {
        this.lista = lista;
    }
}
