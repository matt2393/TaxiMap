package com.matt2393.taximap;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.gson.Gson;
import com.matt2393.taximap.Clases.Logout_enviar;
import com.matt2393.taximap.Model.BaseDatos;
import com.matt2393.taximap.Model.Datos_Table;
import com.matt2393.taximap.main.Constantes;
import com.matt2393.taximap.mapa.MapaActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Apagando extends BroadcastReceiver {

    private SimpleDateFormat datef;
    private Context ctx;
    private Location loc;
    private GoogleApiClient apiClient;
    private String ci,id_login,id_ubicacion;
    public Apagando() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        if(intent.getAction().equalsIgnoreCase(Intent.ACTION_SHUTDOWN))
        {
            Log.i("MATT","se esta apagando...");
            BaseDatos bd=new BaseDatos(context);
            Datos_Table datos=bd.getDatos();
            if(datos!=null) {
                ci=String.valueOf(datos.getCi());
                id_login=String.valueOf(datos.getId_login());
                id_ubicacion=String.valueOf(datos.getId_ubicacion());
                logout();
                Log.i("aaa","logout");
            }
           /* else {
                logout();
                Log.i("error", "no se deslogout");
            }*/
        }
        //Log.i("BROADCAST",""+intent.getAction());
        //throw new UnsupportedOperationException("Not yet implemented");
    }

    public void logout()
    {
        //bd.logout(ci);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            if (ctx.checkSelfPermission(android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ctx.checkSelfPermission(android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                return;
            } else {
                loc= LocationServices.FusedLocationApi.getLastLocation(apiClient);

            }
        } else {
            loc=LocationServices.FusedLocationApi.getLastLocation(apiClient);
        }

        if(loc!=null) {
            Logout_enviar logout_enviar = new Logout_enviar();
            logout_enviar.setCi(ci);
            logout_enviar.setId_login(id_login);
            logout_enviar.setId_ubicacion(id_ubicacion);
            datef = new SimpleDateFormat("yyyy-MM-dd");
            logout_enviar.setFecha(datef.format(new Date()));
            datef = new SimpleDateFormat("HH:mm:ss");
            logout_enviar.setHora(datef.format(new Date()));
            logout_enviar.setLat(String.valueOf(loc.getLatitude()));
            logout_enviar.setLon(String.valueOf(loc.getLongitude()));



            jsonPutLogout(Constantes.URL_PUT_LOGOUT, LogoutparseJson(logout_enviar),ctx);
            BaseDatos bd=new BaseDatos(ctx);
            bd.eliminarDatos();
            bd.close();

        }
    }

    public void jsonPutLogout(String url, JSONObject datos,Context ctx)
    {
        JsonObjectRequest js=new JsonObjectRequest(
                Request.Method.PUT,
                url,
                datos,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i("MATT","Se envio Logout!!!!!!");

                        // Lista_gps lista=parseJsonArray(response);
                        // crearMarkers(lista);
                        // jsonGetGps(Constantes.URL_GET_GPS,fecha);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("matt","error 404 "+error.getMessage());
                        logout();
                    }
                });
        TaxiMap_Singleton.getInstance(ctx).addToRequestQueue(js);
    }

    private JSONObject LogoutparseJson(Logout_enviar logout_enviar)
    {
        Gson gson=new Gson();
        String dat=gson.toJson(logout_enviar);
        JSONObject jObj=null;
        try {
            jObj=new JSONObject(dat);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jObj;
    }

}
