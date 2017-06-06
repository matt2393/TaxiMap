package com.matt2393.taximap.Model;


public class Usuario_Table {

    public final static String NOM_TABLE="usuario_table";
    public final static String CI_USUARIO="ci";
    public final static String USUARIO="usuario";
    public final static String NOMBRE="nombre";
    public final static String APELLIDOP="apellidoP";
    public final static String APELLIDOM="apellidoM";
    public final static String TIPO_VEHICULO="tipoV";
    public final static String NUM_VEHICULO="numV";
    public final static String DIRECCION="direccion";


    public final static String CREAR="create table " + NOM_TABLE + "("
                                    + CI_USUARIO + "integer primary key,"
                                    + USUARIO + " text not null,"
                                    + NOMBRE + " text not null,"
                                    + APELLIDOP + "text,"
                                    + APELLIDOM + "text,"
                                    + TIPO_VEHICULO + "text,"
                                    + NUM_VEHICULO + "integer,"
                                    + DIRECCION + "text)";


    private int ci;
    private String usuario,nombre,apellidoP,apellidoM,tipoV;
    private int numV;
    private String direccion;

    public Usuario_Table() {
    }

    public Usuario_Table(int ci, String usuario, String nombre, String apellidoP, String apellidoM, String tipoV, int numV, String direccion) {
        this.ci = ci;
        this.usuario = usuario;
        this.nombre = nombre;
        this.apellidoP = apellidoP;
        this.apellidoM = apellidoM;
        this.tipoV = tipoV;
        this.numV = numV;
        this.direccion = direccion;
    }

    public int getCi() {
        return ci;
    }

    public void setCi(int ci) {
        this.ci = ci;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
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

    public String getTipoV() {
        return tipoV;
    }

    public void setTipoV(String tipoV) {
        this.tipoV = tipoV;
    }

    public int getNumV() {
        return numV;
    }

    public void setNumV(int numV) {
        this.numV = numV;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
}
