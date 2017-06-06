package com.matt2393.taximap.mapa;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.matt2393.taximap.Clases.GpsDatos;
import com.matt2393.taximap.Clases.GpsDatos_Enviar;
import com.matt2393.taximap.Clases.Gps_crear;
import com.matt2393.taximap.Clases.Lista_gps;
import com.matt2393.taximap.Clases.Lista_recorridos;
import com.matt2393.taximap.Clases.Logout_enviar;
import com.matt2393.taximap.Clases.Recorrido_enviar;
import com.matt2393.taximap.Clases.Recorridos_obtener;
import com.matt2393.taximap.Clases.Usuarios;
import com.matt2393.taximap.MainActivity;
import com.matt2393.taximap.Model.BaseDatos;
import com.matt2393.taximap.R;
import com.matt2393.taximap.TaxiMap_Singleton;
import com.matt2393.taximap.main.Constantes;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private LocationManager lm;
    private Location loc;
    private String ci,id_login,id_ubicacion,ocupado,alerta;
    private SimpleDateFormat datef;
    private String fecha;
    private String lat_logout,lon_logout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        Toolbar toolbar=(Toolbar)findViewById(R.id.toolbarMap);
        setSupportActionBar(toolbar);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        ocupado="0";
        alerta="0";
        FloatingActionButton lib,ocp,al;
        lib=(FloatingActionButton)findViewById(R.id.autolibre);
        ocp=(FloatingActionButton)findViewById(R.id.autoocupado);
        al=(FloatingActionButton)findViewById(R.id.autoalerta);

        lib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ocupado="0";
            }
        });

        ocp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ocupado="1";
            }
        });

        al.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(alerta.equals("1"))
                    alerta="0";
                else
                    alerta="1";
            }
        });

        ci=getIntent().getStringExtra("ci");
        id_login=getIntent().getStringExtra("id_login");
        id_ubicacion=getIntent().getStringExtra("id_ubicacion");

        lm=(LocationManager)this.getSystemService(LOCATION_SERVICE);
        datef=new SimpleDateFormat("yyyy-MM-dd");
        fecha=datef.format(new Date());
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_map,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id=item.getItemId();

        if(id==R.id.logout)
        {
            logout();
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            if (checkSelfPermission(android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                return;
            } else {
                if ( !lm.isProviderEnabled( LocationManager.GPS_PROVIDER ) ) {
                    loc = lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                }
                else{
                    loc = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                }


            }
        } else {
            if ( !lm.isProviderEnabled( LocationManager.GPS_PROVIDER) ) {
                loc = lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            }
            else{
                loc = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            }
        }

        if(loc!=null) {
            /*BaseDatos bd=new BaseDatos(getApplicationContext());
            ci=bd.getLogueado().getCi();
            */


           /* Gps_crear gps_crear=new Gps_crear();
            gps_crear.setCi(String.valueOf(ci));
            gps_crear.setLatitud(String.valueOf(loc.getLatitude()));
            gps_crear.setLongitud(String.valueOf(loc.getLongitude()));
            gps_crear.setFecha(fecha);

            Log.i("TAXI",gps_crear.getLatitud());*/
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(loc.getLatitude(),loc.getLongitude()),13));


           // jsonPostGps(Constantes.URL_POST_GPS, parseJson(gps_crear));

            lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 3000, 1, new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    /*GpsDatos_Enviar gp=new GpsDatos_Enviar();
                    gp.setCi(String.valueOf(ci));
                    gp.setLatitud(String.valueOf(location.getLatitude()));
                    gp.setLongitud(String.valueOf(location.getLongitude()));
                    gp.setFecha(fecha);

                    jsonPutGps(Constantes.URL_PUT_USUARIO,parseJsonPut(gp));*/

                    Recorrido_enviar recc_enviar=new Recorrido_enviar();
                    recc_enviar.setId_ubicacion(id_ubicacion);
                    recc_enviar.setLat(String.valueOf(location.getLatitude()));
                    recc_enviar.setLon(String.valueOf(location.getLongitude()));
                    recc_enviar.setOcupado(ocupado);
                    recc_enviar.setAlerta(alerta);

                    jsonPostRecorrido(Constantes.URL_POST_RECORRIDO,parseJsonPostRecorridos(recc_enviar));
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
        }


        // Add a marker in Sydney and move the camera
       /* LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
*/
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
                        Log.i("MATT","Se envio!!!!!! Recorrido");

                        // Lista_gps lista=parseJsonArray(response);
                        // crearMarkers(lista);
                       // jsonGetGps(Constantes.URL_GET_GPS,fecha);
                        crearMarkers(RecorridosJsonparseArray(response));
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

    private void crearMarkers(Lista_recorridos recorridos)
    {
        int i;
        List<Recorridos_obtener> datos=recorridos.getLista();
        Recorridos_obtener recObt;
        LatLng pos;
        String nombre,ciRecorrido;
        mMap.clear();
        for(i=0; i < datos.size();i++)
        {
            recObt=datos.get(i);
            ciRecorrido=recObt.getCi();
            pos=new LatLng(Double.parseDouble(recObt.getLatitud()),Double.parseDouble(recObt.getLongitud()));

            if(recObt.getOcupado().equals("1"))
                Log.i("ESTADO","OCUPADO!!!");
            else
                Log.i("ESTADO","LIBRE!!!");
            if(recObt.getAlerta().equals("1"))
                Toast.makeText(getApplicationContext(),"Hay Alertas",Toast.LENGTH_SHORT).show();
            mMap.addMarker(new MarkerOptions()
                    .position(pos)
                    .title(ciRecorrido));
        }

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
        //bd.logout(ci);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            if (checkSelfPermission(android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                return;
            } else {
                if ( !lm.isProviderEnabled( LocationManager.GPS_PROVIDER ) ) {
                    loc = lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                }
                else{
                    loc = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                }


            }
        } else {
            if ( !lm.isProviderEnabled( LocationManager.GPS_PROVIDER) ) {
                loc = lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            }
            else{
                loc = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
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

            lm.removeUpdates(new LocationListener() {
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
            lm=null;
            jsonPutLogout(Constantes.URL_PUT_LOGOUT, LogoutparseJson(logout_enviar));

            BaseDatos bd=new BaseDatos(getApplicationContext());
            bd.eliminarDatos();
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
                      //  startActivity(new Intent(getApplicationContext(), MainActivity.class));

                        BaseDatos bd=new BaseDatos(getApplicationContext());
                        bd.eliminarDatos();
                        bd.close();
                        finish();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("matt","error 404 "+error.getMessage());
                        logout();
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

    public void guardarDatos()
    {
        BaseDatos db=new BaseDatos(getApplicationContext());
        db.guardarDatos(Integer.parseInt(ci),Integer.parseInt(id_login),Integer.parseInt(id_ubicacion),Integer.parseInt(ocupado),Integer.parseInt(alerta));
        db.close();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("CI",ci);
        outState.putString("ID_LOGIN",id_login);
        outState.putString("ID_UBICACION",id_ubicacion);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if(savedInstanceState!=null){
            ci=savedInstanceState.getString("CI");
            id_login=savedInstanceState.getString("ID_LOGIN");
            id_ubicacion=savedInstanceState.getString("ID_UBICACION");
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i("PAUSE","se llego al pause");
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();

        Log.i("RESUME","se llego al resume");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        guardarDatos();

        Log.i("DESTROY","la actividad se destruyo");
       // logout();
    }


    ////obsoleto
    public void jsonPutGps(String url, JSONObject datos)
    {
        JsonObjectRequest js=new JsonObjectRequest(
                Request.Method.PUT,
                url,
                datos,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i("MATT","Se envio!!!!!!");

                       // Lista_gps lista=parseJsonArray(response);
                       // crearMarkers(lista);
                        jsonGetGps(Constantes.URL_GET_GPS,fecha);
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

    public void jsonGetGps(String url,String fech)
    {
        JsonObjectRequest js=new JsonObjectRequest(
                Request.Method.GET,
                url+fech,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                  /*   Lista_gps lista=parseJsonArray(response);
                     crearMarkers(lista);*/
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


    public void jsonPostGps(String url, JSONObject datos)
    {
        JsonObjectRequest js=new JsonObjectRequest(
                Request.Method.POST,
                url,
                datos,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i("MATT","Se envio!!!!!!");
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

    private JSONObject parseJson(Gps_crear gps_crear)
    {
        Gson gson=new Gson();
        String dat=gson.toJson(gps_crear);
        JSONObject jObj=null;
        try {
            jObj=new JSONObject(dat);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jObj;
    }
    private JSONObject parseJsonPut(GpsDatos_Enviar gps_datos)
    {
        Gson gson=new Gson();
        String dat=gson.toJson(gps_datos);
        JSONObject jObj=null;
        try {
            jObj=new JSONObject(dat);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jObj;
    }


    private Lista_gps parseJsonArray(JSONObject jObject)
    {
        int i;
        JSONObject objet;
        Lista_gps lg;
        List<GpsDatos> lista=new ArrayList<>();
        try {
            JSONArray array=jObject.getJSONArray("gps");
            for(i=0;i<array.length();i++)
            {
                objet=array.getJSONObject(i);
                GpsDatos gp=new GpsDatos();
                gp.setCi(objet.getString("ci"));
                gp.setNombre(objet.getString("nombre"));
                gp.setApellidoP(objet.getString("apellidoP"));
                gp.setApellidoM(objet.getString("apellidoM"));
                gp.setUltimo_lat(objet.getString("ultimo_lat"));
                gp.setUltimo_lon(objet.getString("ultimo_lon"));
                lista.add(gp);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        lg=new Lista_gps(lista);

        return lg;

    }
    public void jsonPutUsuarios(String url,JSONObject datos)
    {
        JsonObjectRequest js=new JsonObjectRequest(
                Request.Method.PUT,
                url,
                datos,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i("MATT","Se envio!!!!!!");
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
    private JSONObject parseJsonUs(Usuarios datosUs)
    {
        Gson gson=new Gson();
        String dat=gson.toJson(datosUs);
        JSONObject jObj=null;
        try {
            jObj=new JSONObject(dat);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jObj;
    }

    //////

}
