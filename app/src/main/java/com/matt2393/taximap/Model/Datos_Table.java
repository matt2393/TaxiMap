package com.matt2393.taximap.Model;



public class Datos_Table {

    public final static String NOM_TAB="datos";
    public final static String CI="ci";
    public final static String ID_LOGIN="id_login";
    public final static String ID_UBICACION="d_ubicacion";
    public final static String OCUPADO="ocupado";
    public final static String ALERTA="alerta";

    public final static String CREAR="CREATE TABLE "+NOM_TAB+"("
                                + CI +" INTEGER PRIMARY KEY,"
                                + ID_LOGIN +" INTEGER,"
                                + ID_UBICACION +" INTEGER,"
                                + OCUPADO + " INTEGER,"
                                + ALERTA + " INTEGER)";


    private int ci,id_login,id_ubicacion,ocupado,alerta;

    public Datos_Table() {
    }

    public Datos_Table(int ci, int id_login, int id_ubicacion, int ocupado, int alerta) {
        this.ci = ci;
        this.id_login = id_login;
        this.id_ubicacion = id_ubicacion;
        this.ocupado = ocupado;
        this.alerta = alerta;
    }

    public int getCi() {
        return ci;
    }

    public void setCi(int ci) {
        this.ci = ci;
    }

    public int getId_login() {
        return id_login;
    }

    public void setId_login(int id_login) {
        this.id_login = id_login;
    }

    public int getId_ubicacion() {
        return id_ubicacion;
    }

    public void setId_ubicacion(int id_ubicacion) {
        this.id_ubicacion = id_ubicacion;
    }

    public int getOcupado() {
        return ocupado;
    }

    public void setOcupado(int ocupado) {
        this.ocupado = ocupado;
    }

    public int getAlerta() {
        return alerta;
    }

    public void setAlerta(int alerta) {
        this.alerta = alerta;
    }
}
