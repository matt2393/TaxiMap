package com.matt2393.taximap.ServicioGps;

import android.Manifest;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.matt2393.taximap.Clases.Lista_recorridos;
import com.matt2393.taximap.Clases.Logout_enviar;
import com.matt2393.taximap.Clases.Recorrido_enviar;
import com.matt2393.taximap.Clases.Recorridos_obtener;
import com.matt2393.taximap.MainActivity;
import com.matt2393.taximap.TaxiMap_Singleton;
import com.matt2393.taximap.main.Constantes;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class GpsService extends Service /*implements
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener*/ {

    private GoogleApiClient mGoogleApi;
    private WakeLock wakeLock;

    private LocationManager locationManager;
    private Location loc;
    private SimpleDateFormat datef;
    private String ci, id_login, id_ubicacion;

    public GpsService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();


        PowerManager pm = (PowerManager) getSystemService(POWER_SERVICE);
        wakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "DoNotSleep");

    /*    mGoogleApi=new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
*/


        Log.i("MATT", "Servicio Creado");
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }


    @Override
    @Deprecated
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);

        locationManager = (LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Log.i("MATT", intent.getStringExtra("matt"));
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 3000, 0, listener);
    }

    private android.location.LocationListener listener = new android.location.LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            if (location != null) {
                Log.i("MATT", "Lat: " + location.getLatitude());
                Log.i("MATT", "Lon: " + location.getLongitude());

        /*        Recorrido_enviar recc=new Recorrido_enviar();
                recc.setId_ubicacion(id_ubicacion);
                recc.setLat(String.valueOf(location.getLatitude()));
                recc.setLon(String.valueOf(location.getLongitude()));
                recc.setOcupado("0");
                recc.setAlerta("0");

                jsonPostRecorrido(Constantes.URL_POST_RECORRIDO,parseJsonPostRecorridos(recc));*/
                if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                locationManager.removeUpdates(listener);
            }
        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {

        }

        @Override
        public void onProviderEnabled(String s) {

        }

        @Override
        public void onProviderDisabled(String s) {

        }
    };

    public void jsonPostRecorrido(String url, JSONObject datos)
    {
        JsonObjectRequest js=new JsonObjectRequest(
                Request.Method.POST,
                url,
                datos,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i("MATT","Se envio!!!!!! Recorrido");

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

    private Lista_recorridos RecorridosJsonparseArray(JSONObject jObject)
    {
        int i;
        Gson gson=new Gson();
        JSONObject objet;
        Lista_recorridos lg;
        List<Recorridos_obtener> lista=new ArrayList<>();
        try {
            JSONArray array=jObject.getJSONArray("gps");
            for(i=0;i<array.length();i++)
            {
                objet=array.getJSONObject(i);
                Recorridos_obtener recc=gson.fromJson(objet.toString(),Recorridos_obtener.class);

                /*recc.setCi(objet.getString("ci"));

                GpsDatos gp=new GpsDatos();
                gp.setCi(objet.getString("ci"));
                gp.setNombre(objet.getString("nombre"));
                gp.setApellidoP(objet.getString("apellidoP"));
                gp.setApellidoM(objet.getString("apellidoM"));
                gp.setUltimo_lat(objet.getString("ultimo_lat"));
                gp.setUltimo_lon(objet.getString("ultimo_lon"));*/

                lista.add(recc);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        lg=new Lista_recorridos(lista);

        return lg;

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


    public void logout()
    {
       /* BaseDatos bd=new BaseDatos(getApplicationContext());
        bd.logout(ci);*/
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            if (checkSelfPermission(android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                return;
            } else {
                if ( !locationManager.isProviderEnabled( LocationManager.GPS_PROVIDER ) ) {
                    loc = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                }
                else{
                    loc = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                }


            }
        } else {
            if ( !locationManager.isProviderEnabled( LocationManager.GPS_PROVIDER) ) {
                loc = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            }
            else{
                loc = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            }
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

            ///mostrar json de logout.. para conprobar el envio

            //     Log.i("LOGOUT",LogoutparseJson(logout_enviar).toString());
            locationManager.removeUpdates(new android.location.LocationListener() {
                @Override
                public void onLocationChanged(Location location) {

                }

                @Override
                public void onStatusChanged(String s, int i, Bundle bundle) {

                }

                @Override
                public void onProviderEnabled(String s) {

                }

                @Override
                public void onProviderDisabled(String s) {

                }
            });
            jsonPutLogout(Constantes.URL_PUT_LOGOUT, LogoutparseJson(logout_enviar));
        }
        /*Usuarios user=new Usuarios();
        user.setCi(ci);
        user.setUsuario("null");
        user.setPassword("null");
        user.setLogueado("0");
        jsonPutUsuarios(Constantes.URL_PUT_USUARIO,parseJsonUs(user));
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
        finish();*/
    }

    public void jsonPutLogout(String url, JSONObject datos)
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
                    //    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    //    finish();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("matt","error 404 "+error.getMessage());
                    }
                });
        TaxiMap_Singleton.getInstance(getApplicationContext()).addToRequestQueue(js);
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

    /*
    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
*/

}
