package com.matt2393.taximap;

import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.matt2393.taximap.Model.BaseDatos;
import com.matt2393.taximap.Model.Datos_Table;
import com.matt2393.taximap.main.fragment_login;
import com.matt2393.taximap.mapa.MapaActivity;

public class SplashScreem extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splashscreem);

        ImageView img=(ImageView)findViewById(R.id.imagen_splash);
        TextView tit=(TextView)findViewById(R.id.titulo_splash);

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
           // bd.eliminarDatos();
            startActivity(new Intent(this, MapaActivity.class)
                    .putExtra("ci",ci)
                    .putExtra("id_login",id_login)
                    .putExtra("id_ubicacion",id_ubicacion)
                    .putExtra("ocupado",ocupado)
                    .putExtra("alerta",alerta));

        }
        else {

         /*   Animation aum_img= AnimationUtils.loadAnimation(this,R.anim.aumentar_img);
            aum_img.reset();
            img.startAnimation(aum_img);
*/

            final Thread nuevoThrend=new Thread(){
                @Override
                public void run() {
                    try{
                        sleep(3000);
                    }
                    catch (InterruptedException ex){
                        ex.printStackTrace();
                    }
                    finally {
                        startActivity(new Intent(SplashScreem.this,MainActivity.class));
                    }
                }
            };
            nuevoThrend.start();

        }

        bd.close();

    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
