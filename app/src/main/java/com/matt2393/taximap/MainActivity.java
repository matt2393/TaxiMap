package com.matt2393.taximap;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.matt2393.taximap.Model.BaseDatos;
import com.matt2393.taximap.Model.Datos_Table;
import com.matt2393.taximap.Model.Login_Table;
import com.matt2393.taximap.ServicioGps.GpsService;
import com.matt2393.taximap.ServicioGps.LocationApiService;
import com.matt2393.taximap.main.fragment_login;
import com.matt2393.taximap.mapa.MapActivity;
import com.matt2393.taximap.mapa.MapaActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

      /*  FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        BaseDatos bd=new BaseDatos(this);
        Datos_Table dat=bd.getDatos();

        Log.i("MAIN","llego aqui");
        if(dat!=null)
        {
            String ci,id_login,id_ubicacion,ocupado,alerta;
            ci=String.valueOf(dat.getCi());
            id_login=String.valueOf(dat.getId_login());
            id_ubicacion=String.valueOf(dat.getId_ubicacion());
            ocupado=String.valueOf(dat.getOcupado());
            alerta=String.valueOf(dat.getAlerta());
            //bd.eliminarDatos();
            startActivity(new Intent(this, MapaActivity.class)
                    .putExtra("ci",ci)
                    .putExtra("id_login",id_login)
                    .putExtra("id_ubicacion",id_ubicacion)
                    .putExtra("ocupado",ocupado)
                    .putExtra("alerta",alerta));

            finish();
        }
        else {
            FragmentTransaction frt = getSupportFragmentManager().beginTransaction();
            frt.add(R.id.contenedor_main, new fragment_login(), "fragment_Login").commit();
        }

       // startService(new Intent(this, LocationApiService.class));
      //  stopService(new Intent(this,GpsService.class));
        //startService(new Intent(this, GpsService.class).putExtra("matt","enviado ci"));
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("MAIN_AC","Se destruyo");
    }

    /*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {

           // stopService(new Intent(this,LocationApiService.class));
            //stopService(new Intent(this,GpsService.class));

            return true;
        }

        return super.onOptionsItemSelected(item);
    }*/
}
