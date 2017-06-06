package com.matt2393.taximap.Model;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class BaseDatos extends SQLiteOpenHelper {

    private final static String name="taxiMap.db";
    private final static int version=2;
    private SQLiteDatabase database;

    public BaseDatos(Context context) {
        super(context, name, null, version);
        database=this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
       // sqLiteDatabase.execSQL(Login_Table.CREAR);
       // sqLiteDatabase.execSQL(Usuario_Table.CREAR);
        sqLiteDatabase.execSQL(Datos_Table.CREAR);
        sqLiteDatabase.execSQL(Login_Table.CREAR);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
    }

    public void cerrar()
    {
        database.close();
    }

    public long guardarDatos(int ci,int id_login,int id_ubicacion,int ocupado,int alerta)
    {
        ContentValues dats=new ContentValues();
        dats.put(Datos_Table.CI,ci);
        dats.put(Datos_Table.ID_LOGIN,id_login);
        dats.put(Datos_Table.ID_UBICACION,id_ubicacion);
        dats.put(Datos_Table.OCUPADO,ocupado);
        dats.put(Datos_Table.ALERTA,alerta);


        return database.insert(Datos_Table.NOM_TAB,null,dats);

    }

    public Datos_Table getDatos()
    {
        Datos_Table dat;
        try {
            Cursor datos = database.rawQuery("SELECT * FROM " + Datos_Table.NOM_TAB, null);
            if (datos.moveToFirst()) {
                dat = new Datos_Table();
                dat.setCi(datos.getInt(0));
                dat.setId_login(datos.getInt(1));
                dat.setId_ubicacion(datos.getInt(2));
                dat.setOcupado(datos.getInt(3));
                dat.setAlerta(datos.getInt(4));
                Log.i("DATOS", dat.getCi() + "");
            } else
                dat = null;
        }
        catch (Exception ex){
            Log.i("Base de datos","no hay datos en la bd");
            dat=null;
        }
        return dat;
    }

    public void eliminarDatos()
    {
        database.delete(Datos_Table.NOM_TAB,"1",null);
        Log.i("Base de Datos","Se borro la base de datos");
    }


    ///login
    public long guardarLogin(String usuario,String password)
    {
        ContentValues dats=new ContentValues();
        dats.put(Login_Table.USUARIO,usuario);
        dats.put(Login_Table.PASSWORD,password);

        return database.insert(Login_Table.NOM_TABLE,null,dats);

    }

    public Login_Table getLogin()
    {
        Login_Table dat;
        try {
            Cursor datos = database.rawQuery("SELECT * FROM " + Login_Table.NOM_TABLE, null);
            if (datos.moveToFirst()) {
                dat = new Login_Table();
                dat.setId(datos.getInt(0));
                dat.setUsuario(datos.getString(1));
                dat.setPassword(datos.getString(2));
                Log.i("DATOS", dat.getUsuario() + "");
            } else
                dat = null;
        }
        catch (Exception ex){
            Log.i("Base de datos","no hay datos en la bd");
            dat=null;
        }
        return dat;
    }

    public void eliminarLogin()
    {
        database.delete(Login_Table.NOM_TABLE,"1",null);
        Log.i("Base de Datos","Se borro la base de datos");
    }

    ///////////////////////
    /*
    public void guardarLogin(int ci,String usuario,int es_login)
    {
        ContentValues login=new ContentValues();
        login.put(Login_Table.CI_USUARIO,ci);
        login.put(Login_Table.USUARIO,usuario);
        login.put(Login_Table.ES_LOGIN,es_login);

        database.insert(Login_Table.NOM_TABLE,null,login);
    }

    public Login_Table getLogueado()
    {
        Login_Table log;
        Cursor user=database.rawQuery("SELECT * FROM "
                                    + Login_Table.NOM_TABLE + " WHERE "
                                    +Login_Table.ES_LOGIN + "= 1",null);
        if(user.moveToFirst())
        {
            log=new Login_Table();
            log.setCi(user.getInt(0));
            log.setUsuario(user.getString(1));
            log.setEs_login(user.getInt(2));
        }
        else
            log=null;
        return log;
    }

    public void logout(int ci)
    {
        database.rawQuery("UPDATE "+Login_Table.NOM_TABLE+" SET "
                +Login_Table.ES_LOGIN+"= 0 WHERE "+Login_Table.CI_USUARIO+"= \'"+ci+"\'",null);
    }


    public  void guardarUsuario(int ci,String usuario,String nombre,String apellidoP,String apellidoM,String tipoV,int numV,String direccion)
    {
        ContentValues users=new ContentValues();
        users.put(Usuario_Table.CI_USUARIO,ci);
        users.put(Usuario_Table.USUARIO,usuario);
        users.put(Usuario_Table.NOMBRE,nombre);
        users.put(Usuario_Table.APELLIDOP,apellidoP);
        users.put(Usuario_Table.APELLIDOM,apellidoM);
        users.put(Usuario_Table.TIPO_VEHICULO,tipoV);
        users.put(Usuario_Table.NUM_VEHICULO,numV);
        users.put(Usuario_Table.DIRECCION,direccion);

        database.insert(Usuario_Table.NOM_TABLE,null,users);
    }

    public Usuario_Table getUsuario(int ci)
    {
        Usuario_Table user;
        Cursor datos=database.rawQuery("SELECT * FROM "+Usuario_Table.NOM_TABLE
                                        + " WHERE " + Usuario_Table.CI_USUARIO
                                        + " = "+"\'"+ci+"\'",null);

        if(datos.moveToFirst())
        {
            user=new Usuario_Table();
            user.setCi(datos.getInt(0));
            user.setUsuario(datos.getString(1));
            user.setNombre(datos.getString(2));
            user.setApellidoP(datos.getString(3));
            user.setApellidoM(datos.getString(4));
            user.setTipoV(datos.getString(5));
            user.setNumV(datos.getInt(6));
            user.setDireccion(datos.getString(7));

        }
        else
            user=null;
        return user;
    }*/
}
