package com.matt2393.taximap.Clases;


public class Login_recibir {

    private String id_login,id_ubicacion;

    public Login_recibir() {
    }

    public Login_recibir(String id_login, String id_ubicacion) {
        this.id_login = id_login;
        this.id_ubicacion = id_ubicacion;
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
}
