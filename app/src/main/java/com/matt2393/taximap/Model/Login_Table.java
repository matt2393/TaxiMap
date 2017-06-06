package com.matt2393.taximap.Model;



public class Login_Table {

    public final static String NOM_TABLE="login_table";
    public final static String ID="id";
    public final static String USUARIO="usuario";
    public final static String PASSWORD="password";


    public final static String CREAR="create table " + NOM_TABLE + "("
                                    + ID + " integer primary key autoincrement,"
                                    + USUARIO + " text not null,"
                                    + PASSWORD + " text not null)";


    private int id;
    private String usuario,password;

    public Login_Table() {
    }

    public Login_Table(int id, String usuario, String password) {
        this.id = id;
        this.usuario = usuario;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

}
