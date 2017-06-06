package com.matt2393.taximap.Clases;

//recuperar usuarios d la api

public class Usuarios {

    private String ci,usuario,password,logueado,habilitado;

    public Usuarios() {
    }

    public Usuarios(String ci, String usuario, String password, String logueado,String habilitado) {
        this.ci=ci;
        this.usuario = usuario;
        this.password = password;
        this.logueado = logueado;
        this.habilitado=habilitado;
    }

    public String getCi() {
        return ci;
    }

    public void setCi(String ci) {
        this.ci = ci;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLogueado() {
        return logueado;
    }

    public void setLogueado(String logueado) {
        this.logueado = logueado;
    }

    public String getHabilitado() {
        return habilitado;
    }

    public void setHabilitado(String habilitado) {
        this.habilitado = habilitado;
    }
}
