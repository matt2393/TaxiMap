package com.matt2393.taximap.Clases;


//recuperar datos del usuario logueado
public class Usuario_Datos {

    private String ci,usuario,nombre,apellidoP,apellidoM,Tipo,num_vehiculo,direccion;

    public Usuario_Datos() {
    }

    public Usuario_Datos(String ci, String usuario, String nombre, String apellidoP, String apellidoM, String tipo, String num_vehiculo, String direccion) {
        this.ci = ci;
        this.usuario = usuario;
        this.nombre = nombre;
        this.apellidoP = apellidoP;
        this.apellidoM = apellidoM;
        Tipo = tipo;
        this.num_vehiculo = num_vehiculo;
        this.direccion = direccion;
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

    public String getTipo() {
        return Tipo;
    }

    public void setTipo(String tipo) {
        Tipo = tipo;
    }

    public String getNum_vehiculo() {
        return num_vehiculo;
    }

    public void setNum_vehiculo(String num_vehiculo) {
        this.num_vehiculo = num_vehiculo;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
}
