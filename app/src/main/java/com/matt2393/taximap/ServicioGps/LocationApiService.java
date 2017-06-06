package com.matt2393.taximap.ServicioGps;

import android.Manifest;
import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.gson.Gson;
import com.matt2393.taximap.Apagando;
import com.matt2393.taximap.Clases.Recorrido_enviar;
import com.matt2393.taximap.TaxiMap_Singleton;
import com.matt2393.taximap.main.Constantes;

import org.json.JSONException;
import org.json.JSONObject;

public class LocationApiService extends Service
        implements GoogleApiClient.ConnectionCallbacks
        , GoogleApiClient.OnConnectionFailedListener,LocationListener {

    private GoogleApiClient apiClient;
    private String ci,id_login,id_ubicacion,ocupado,alerta;

    public LocationApiService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        apiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

//        apiClient.connect();


    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(apiClient.isConnected())
            LocationServices.FusedLocationApi.removeLocationUpdates(apiClient,this);
        apiClient.disconnect();
        Log.i("SERVICE","Se destruyo servicio");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        ci=intent.getStringExtra("CI");
        id_login=intent.getStringExtra("ID_LOGIN");
        id_ubicacion=intent.getStringExtra("ID_UBICACION");
        ocupado=intent.getStringExtra("OCUPADO");
        alerta=intent.getStringExtra("ALERTA");
        apiClient.connect();

  /*      IntentFilter intentFilter=new IntentFilter(Intent.ACTION_SHUTDOWN);
        Apagando apag=new Apagando(apiClient,ci,id_login,id_ubicacion,this);
        LocalBroadcastManager.getInstance(this).registerReceiver(apag,intentFilter);*/

        //  Log.i("SERVICE","llego aqui, Se Inicio el servicio");
        return super.onStartCommand(intent, flags, startId);


    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        LocationRequest locReq=new LocationRequest()
                .setInterval(3000)
                .setFastestInterval(2000)
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            if (getApplication().checkSelfPermission(android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && getApplicationContext().checkSelfPermission(android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {


                stopService(new Intent(this,LocationApiService.class));

            } else {
                LocationServices.FusedLocationApi.requestLocationUpdates(apiClient,locReq,this);
            }
        } else {
            LocationServices.FusedLocationApi.requestLocationUpdates(apiClient,locReq,this);
        }


    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {
        Recorrido_enviar recc_enviar=new Recorrido_enviar();
        recc_enviar.setId_ubicacion(id_ubicacion);
        recc_enviar.setLat(String.valueOf(location.getLatitude()));
        recc_enviar.setLon(String.valueOf(location.getLongitude()));
        recc_enviar.setOcupado(ocupado);
        recc_enviar.setAlerta(alerta);

        jsonPostRecorrido(Constantes.URL_POST_RECORRIDO,parseJsonPostRecorridos(recc_enviar));
    }

    public void jsonPostRecorrido(String url, JSONObject datos)
    {
        JsonObjectRequest js=new JsonObjectRequest(
                Request.Method.POST,
                url,
                datos,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i("MATT_SERVICE","Se envio!!!!!! Recorrido");

                        // Lista_gps lista=parseJsonArray(response);
                        // crearMarkers(lista);
                        // jsonGetGps(Constantes.URL_GET_GPS,fecha);
                       // crearMarkers(RecorridosJsonparseArray(response));
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("matt","error 404");
                    }
                });
        TaxiMap_Singleton.getInstance(getApplicationContext()).addToRequestQueue(js);
    }

    private JSONObject parseJsonPostRecorridos(Recorrido_enviar recc_datos)
    {
        Gson gson=new Gson();
        String dat=gson.toJson(recc_datos);
        JSONObject jObj=null;
        try {
            jObj=new JSONObject(dat);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jObj;
    }
}
