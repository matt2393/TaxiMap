package com.matt2393.taximap.Clases;


public class Recorridos_obtener {

    private String ci,usuario,nombre,apellidoP,apellidoM,num_vehiculo;
    private String id_tipo,tipo,latitud,longitud,ocupado,alerta;

    public Recorridos_obtener() {
    }

    public Recorridos_obtener(String ci, String usuario, String nombre, String apellidoP, String apellidoM, String num_vehiculo, String id_tipo, String tipo, String latitud, String longitud, String ocupado, String alerta) {
        this.ci = ci;
        this.usuario = usuario;
        this.nombre = nombre;
        this.apellidoP = apellidoP;
        this.apellidoM = apellidoM;
        this.num_vehiculo = num_vehiculo;
        this.id_tipo = id_tipo;
        this.tipo = tipo;
        this.latitud = latitud;
        this.longitud = longitud;
        this.ocupado = ocupado;
        this.alerta = alerta;
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

    public String getNum_vehiculo() {
        return num_vehiculo;
    }

    public void setNum_vehiculo(String num_vehiculo) {
        this.num_vehiculo = num_vehiculo;
    }

    public String getId_tipo() {
        return id_tipo;
    }

    public void setId_tipo(String id_tipo) {
        this.id_tipo = id_tipo;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getLatitud() {
        return latitud;
    }

    public void setLatitud(String latitud) {
        this.latitud = latitud;
    }

    public String getLongitud() {
        return longitud;
    }

    public void setLongitud(String longitud) {
        this.longitud = longitud;
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
