package com.matt2393.taximap.mapa;

import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
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
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.GroundOverlayOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.matt2393.taximap.Apagando;
import com.matt2393.taximap.Clases.Lista_recorridos;
import com.matt2393.taximap.Clases.Logout_enviar;
import com.matt2393.taximap.Clases.Recorrido_enviar;
import com.matt2393.taximap.Clases.Recorridos_obtener;
import com.matt2393.taximap.Clases.UsuariosAlerta;
import com.matt2393.taximap.MainActivity;
import com.matt2393.taximap.Model.BaseDatos;
import com.matt2393.taximap.R;
import com.matt2393.taximap.ServicioGps.LocationApiService;
import com.matt2393.taximap.TaxiMap_Singleton;
import com.matt2393.taximap.main.Constantes;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class MapaActivity extends AppCompatActivity implements OnMapReadyCallback
                        ,GoogleApiClient.OnConnectionFailedListener,GoogleApiClient.ConnectionCallbacks
                        , com.google.android.gms.location.LocationListener{

    private GoogleMap mMap;
    private GoogleApiClient apiClient;
    private LocationManager lm;
    private Location loc;
    private String ci,id_login,id_ubicacion,ocupado,alerta;
    private SimpleDateFormat datef;
    private boolean sw,sw_rastreo;
    private Vibrator vb;
    private MediaPlayer sonido;
    private BitmapDescriptor bitD;
    private int cantidad;

    private FloatingActionButton est;

    private Lista_recorridos recorridos_actuales=null;

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


        ci=getIntent().getStringExtra("ci");
        id_login=getIntent().getStringExtra("id_login");
        id_ubicacion=getIntent().getStringExtra("id_ubicacion");

        ocupado=getIntent().getStringExtra("ocupado");
        alerta=getIntent().getStringExtra("alerta");


        FloatingActionButton lib,ocp,al,ubi;
        lib=(FloatingActionButton)findViewById(R.id.autolibre);
        ocp=(FloatingActionButton)findViewById(R.id.autoocupado);
        al=(FloatingActionButton)findViewById(R.id.autoalerta);

        est=(FloatingActionButton)findViewById(R.id.estado_auto);
        ubi=(FloatingActionButton)findViewById(R.id.ubicacion_actual);

        lib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ocupado="0";
                alerta="0";
                est.setImageResource(R.drawable.ic_traffic_white_48dp_libre);
            }
        });


        ocp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ocupado="1";
                alerta="0";
                est.setImageResource(R.drawable.ic_traffic_white_48dp_ocupado);
            }
        });

        al.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alerta="1";
                est.setImageResource(R.drawable.ic_traffic_white_48dp_alerta);

            }
        });

        est.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(alerta.equals("1"))
                    Toast.makeText(MapaActivity.this,"Estado de Alerta...",Toast.LENGTH_SHORT).show();
                else {
                    if (ocupado.equals("1"))
                        Toast.makeText(MapaActivity.this, "Automovil Ocupado...", Toast.LENGTH_SHORT).show();
                    else
                        Toast.makeText(MapaActivity.this, "Automovil Libre...", Toast.LENGTH_SHORT).show();
                }
            }
        });

        ubi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                    if (getApplicationContext().checkSelfPermission(android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && getApplicationContext().checkSelfPermission(android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                        return;
                    } else {
                        loc=LocationServices.FusedLocationApi.getLastLocation(apiClient);
                    }
                } else {
                    loc=LocationServices.FusedLocationApi.getLastLocation(apiClient);
                }
                if(loc!=null)
                {
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(loc.getLatitude(), loc.getLongitude()),17));
                }
            }
        });



        sw=true;
        //lm=(LocationManager)this.getSystemService(LOCATION_SERVICE);
        datef=new SimpleDateFormat("yyyy-MM-dd");
        fecha=datef.format(new Date());
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_map,menu);


        MenuItem busq_menu=(MenuItem)menu.findItem(R.id.buscar_conectados);
        final SearchView busqueda=(SearchView) MenuItemCompat.getActionView(busq_menu);

        busqueda.setQueryHint("Buscar");
        busqueda.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                Log.i("Busq","Se detuvo la busqueda");
                sw_rastreo=true;
                //busqueda.setIconified(false);
                return false;
            }
        });
        busqueda.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                double lat_ac=0,lon_ac=0;
                boolean sw_ac;
                sw_ac=false;
                if(recorridos_actuales!=null) {
                    //Toast.makeText(MapaActivity.this, "Se envio Busqueda", Toast.LENGTH_SHORT).show();
                    for (int i=0;i<recorridos_actuales.getLista().size();i++){
                        if(recorridos_actuales.getLista().get(i).getCi().equalsIgnoreCase(query)
                                || recorridos_actuales.getLista().get(i).getNum_vehiculo().equalsIgnoreCase(query)
                                || recorridos_actuales.getLista().get(i).getUsuario().equalsIgnoreCase(query)){
                            lat_ac=Double.parseDouble(recorridos_actuales.getLista().get(i).getLatitud());
                            lon_ac=Double.parseDouble(recorridos_actuales.getLista().get(i).getLongitud());
                            sw_ac=true;
                            i=1000000;
                        }
                    }
                    if(sw_ac)
                    {
                        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat_ac,lon_ac),17));
                        sw_rastreo=false;
                    }
                    else
                        Toast.makeText(MapaActivity.this,"No se encontro a "+query+" o no se encuentra conectado",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(MapaActivity.this, "No se encontro a "+query+" o no se encuentra conectado", Toast.LENGTH_SHORT).show();
                }
                //busqueda.setQuery("",false);

                busqueda.setIconified(true);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return true;
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id=item.getItemId();

        if(id==R.id.logout)
        {
            logout();
        }
        else
            if(id==R.id.limpiar)
            {
                jsonGetLimpiar(Constantes.URL_GET_LIMPIAR);
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

        // Add a marker in Sydney and move the camera
       /* LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
*/
    }

    public void jsonGetLimpiar(String url)
    {
        JsonObjectRequest js=new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(MapaActivity.this,"Se actualizaron los datos",Toast.LENGTH_SHORT).show();

                     }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("matt","error 404");
                        Toast.makeText(MapaActivity.this,"Ocurrio un error, verifique si conexion a internet",Toast.LENGTH_SHORT).show();

                    }
                });
        TaxiMap_Singleton.getInstance(getApplicationContext()).addToRequestQueue(js);
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
                        Log.i("MATT_MAP","Se envio!!!!!! Recorrido");

                        // Lista_gps lista=parseJsonArray(response);
                        // crearMarkers(lista);
                        // jsonGetGps(Constantes.URL_GET_GPS,fecha);
                        recorridos_actuales=RecorridosJsonparseArray(response);
                        crearMarkers(recorridos_actuales);
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
        int i,cont=0;
        //ArrayList<UsuariosAlerta> datos_alerta=new ArrayList<>();
        List<Recorridos_obtener> datos=recorridos.getLista();
        Recorridos_obtener recObt;
        LatLng pos;
        String nombre,nombreRecorrido,numV,nomUs;
        mMap.clear();
        for(i=0; i < datos.size();i++)
        {
            recObt=datos.get(i);
            nombreRecorrido=recObt.getNombre();
            numV=recObt.getNum_vehiculo();
            nomUs=recObt.getUsuario();
            pos=new LatLng(Double.parseDouble(recObt.getLatitud()),Double.parseDouble(recObt.getLongitud()));
            if(ci.equalsIgnoreCase(recObt.getCi())){
                bitD=BitmapDescriptorFactory.fromResource(R.drawable.map_marker);
                if(sw_rastreo)
                {
                    Log.i("rastreo","rastreando");
                    mMap.animateCamera(CameraUpdateFactory.newLatLng(pos));
                }
            }
            else {
                if (recObt.getOcupado().equals("1")) {
                    Log.i("ESTADO_MAP", "OCUPADO!!!");
                    bitD=BitmapDescriptorFactory.fromResource(R.drawable.map_marker_ocupado);

                }
                else {
                    bitD=BitmapDescriptorFactory.fromResource(R.drawable.map_marker_libre);
                    Log.i("ESTADO_MAP", "LIBRE!!!");
                }
                if (recObt.getAlerta().equals("1")) {
                    //vb.vibrate(500);
                 //   datos_alerta.add(new UsuariosAlerta(nombreRecorrido,recObt.getLatitud(),recObt.getLongitud()));
                    cont++;
                    bitD=BitmapDescriptorFactory.fromResource(R.drawable.map_marker_alerta);
                }
            }
                mMap.addMarker(new MarkerOptions()
                        .position(pos)
                        .title("Nº: " + numV)
                        .snippet("Usuario: " + nomUs + "   Nombre: " + nombreRecorrido)
                        .icon(bitD));

        }
        if(cont>0) {
            if (cantidad==0) {
                vb.vibrate(1500);
                sonido.start();

            }
     /*       if(cantidad==1)
                sonido.pause();*/
            cantidad++;
            if (cantidad==7) {
                cantidad = 0;
            }

        }
        else
            cantidad=0;
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
                Toast.makeText(MapaActivity.this,"No se encuentra su ubicación",Toast.LENGTH_SHORT).show();
                return;
            } else {
                loc=LocationServices.FusedLocationApi.getLastLocation(apiClient);

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



            jsonPutLogout(Constantes.URL_PUT_LOGOUT, LogoutparseJson(logout_enviar));
          /*  BaseDatos bd=new BaseDatos(getApplicationContext());
            bd.eliminarDatos();
*/
            sw=false;

        }
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
                        startActivity(new Intent(MapaActivity.this, MainActivity.class));
                        BaseDatos bd=new BaseDatos(getApplicationContext());
                        bd.eliminarDatos();
                        bd.close();

                        finish();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        sw=true;
                        Toast.makeText(MapaActivity.this,"No se pudo desconectar\nverifique su conexión a Internet",Toast.LENGTH_LONG).show();
                        Log.i("matt","error 404 "+error.getMessage());
                        //logout();
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
        BaseDatos db=new BaseDatos(this);
        if(db.guardarDatos(Integer.parseInt(ci),Integer.parseInt(id_login),
                Integer.parseInt(id_ubicacion),Integer.parseInt(ocupado),
                Integer.parseInt(alerta)) == -1)
            Log.i("ERROR_BD","Error al guardar");
        db.close();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("CI",ci);
        outState.putString("ID_LOGIN",id_login);
        outState.putString("ID_UBICACION",id_ubicacion);
        outState.putString("ALERTA",alerta);
        outState.putString("OCUPADO",ocupado);

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if(savedInstanceState!=null){
            ci=savedInstanceState.getString("CI");
            id_login=savedInstanceState.getString("ID_LOGIN");
            id_ubicacion=savedInstanceState.getString("ID_UBICACION");
            ocupado=savedInstanceState.getString("OCUPADO");
            alerta=savedInstanceState.getString("ALERTA");
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        //apiClient.connect();
    }



    @Override
    protected void onPause() {
        super.onPause();

        if(apiClient.isConnected())
            LocationServices.FusedLocationApi.removeLocationUpdates(apiClient,this);
        if(sw) {
            startService(new Intent(this, LocationApiService.class)
                    .putExtra("CI", ci)
                    .putExtra("ID_LOGIN", id_login)
                    .putExtra("ID_UBICACION", id_ubicacion)
                    .putExtra("OCUPADO",ocupado)
                    .putExtra("ALERTA",alerta));
        }
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        sw_rastreo=true;
        if(alerta.equals("1"))
            est.setImageResource(R.drawable.ic_traffic_white_48dp_alerta);
        else
        {
            if(ocupado.equals("1"))
                est.setImageResource(R.drawable.ic_traffic_white_48dp_ocupado);
            else
                est.setImageResource(R.drawable.ic_traffic_white_48dp_libre);
        }

        stopService(new Intent(this,LocationApiService.class));
        vb=(Vibrator) getSystemService(VIBRATOR_SERVICE);
        sonido=MediaPlayer.create(this,R.raw.alerta_tono);
        sonido.setLooping(false);
        sonido.setVolume(100,100);

        apiClient=new GoogleApiClient.Builder(this)
                .addOnConnectionFailedListener(this)
                .addConnectionCallbacks(this)
                .addApi(LocationServices.API)
                .build();


        apiClient.connect();
        cantidad=0;
        /*
        IntentFilter intentFilter=new IntentFilter(Intent.ACTION_SHUTDOWN);
        Apagando apag=new Apagando(apiClient,ci,id_login,id_ubicacion,this);
        LocalBroadcastManager.getInstance(this).registerReceiver(apag,intentFilter);*/

        /*LocationRequest locReq=new LocationRequest()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(5000)
                .setFastestInterval(3000);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            if (checkSelfPermission(android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                logout();
                Toast.makeText(this,"Error, No concedio permisos de Ubicación",Toast.LENGTH_SHORT).show();
                finish();
            } else {
                LocationServices.FusedLocationApi.requestLocationUpdates(apiClient,locReq,this);

            }
        } else {
            LocationServices.FusedLocationApi.requestLocationUpdates(apiClient,locReq,this);
        }*/
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        /*if(sw)
            guardarDatos();*/
        apiClient.disconnect();
    }




    @Override
    public void onConnected(@Nullable Bundle bundle) {

        Location location=null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            if (checkSelfPermission(android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                logout();
                Toast.makeText(this,"Error, No concedio permisos de Ubicación",Toast.LENGTH_SHORT).show();
                finish();
            } else {
                location=LocationServices.FusedLocationApi.getLastLocation(apiClient);

            }
        } else {
            location=LocationServices.FusedLocationApi.getLastLocation(apiClient);
        }
        if(location!=null)
        {
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(),location.getLongitude()),17));
        }

        LocationRequest locReq=new LocationRequest()
                    .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                    .setInterval(3000)
                    .setFastestInterval(2000);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            if (checkSelfPermission(android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                logout();
                Toast.makeText(this,"Error, No concedio permisos de Ubicación",Toast.LENGTH_SHORT).show();
                finish();
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

    //////

}

