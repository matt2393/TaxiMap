package com.matt2393.taximap.main;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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
import com.matt2393.taximap.Clases.ListUsuarios;
import com.matt2393.taximap.Clases.Login_enviar;
import com.matt2393.taximap.Clases.Usuario_Datos;
import com.matt2393.taximap.Clases.Usuarios;
import com.matt2393.taximap.Model.BaseDatos;
import com.matt2393.taximap.Model.Login_Table;
import com.matt2393.taximap.R;
import com.matt2393.taximap.TaxiMap_Singleton;
import com.matt2393.taximap.mapa.MapActivity;
import com.matt2393.taximap.mapa.MapaActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;


public class fragment_login extends Fragment
        implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,LocationListener{

    EditText user,pass;

    TextView usuario_recordado,texto_recordar;

    LinearLayout login_normal,login_recordado;
    BaseDatos baseDatos;

    int indice;
    ListUsuarios lista;
    LocationManager lm;
    CheckBox recordar;
    private GoogleApiClient apiClient;

    boolean sw_push,sw_recordado;

    static JSONObject jObject;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_main,container,false);


        texto_recordar=(TextView)view.findViewById(R.id.texto_recordar);
        recordar=(CheckBox)view.findViewById(R.id.check_login);

        AppCompatButton acept_recordado,acept,olvidar;
        acept=(AppCompatButton)view.findViewById(R.id.loguear);
        acept_recordado=(AppCompatButton)view.findViewById(R.id.loguear_recordado);
        olvidar=(AppCompatButton)view.findViewById(R.id.olvidar);

        baseDatos=new BaseDatos(getActivity());


        login_normal=(LinearLayout)view.findViewById(R.id.login_no_guardado);
        login_recordado=(LinearLayout)view.findViewById(R.id.login_guardado);

        usuario_recordado=(TextView)view.findViewById(R.id.usuario_recordado);

        if(baseDatos.getLogin()==null) {
            login_recordado.setVisibility(View.GONE);
            login_normal.setVisibility(View.VISIBLE);
            usuario_recordado.setText("");
            sw_recordado=true;
        }
        else {
            login_normal.setVisibility(View.GONE);
            login_recordado.setVisibility(View.VISIBLE);
            usuario_recordado.setText(baseDatos.getLogin().getUsuario());
            sw_recordado=false;
        }

        sw_push=true;


        user=(EditText)view.findViewById(R.id.user);
        pass=(EditText)view.findViewById(R.id.pass);



        apiClient=new GoogleApiClient.Builder(getActivity())
                    .addOnConnectionFailedListener(this)
                    .addConnectionCallbacks(this)
                    .addApi(LocationServices.API)
                    .build();

        /*
        IntentFilter intentFilter=new IntentFilter(".Apagando");
        intentFilter.addAction(Intent.ACTION_SHUTDOWN);
        Apagando apag=new Apagando();
        LocalBroadcastManager.getInstance(getContext()).registerReceiver(apag,intentFilter);*/

        acept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Intent in=new Intent(getActivity(), MapActivity.class);
                startActivity(in);*/
          //      user.setText(rest.getEstado());
            //    pass.setText(rest.getMensaje());
              //  JsonRequest("http://192.168.43.144/TaxiMap/api/Api_rest/user/ci/2222");
                if(sw_push) {
                    if (!user.getText().toString().equals("") && !pass.getText().toString().equals("")) {
                        sw_push=false;
                        jsonGetUsuarios(Constantes.URL_GET_USUARIOS);
                    }
                    else {
                        Toast.makeText(getContext(), "Escriba su usuario y contraseña", Toast.LENGTH_SHORT).show();
                    }
                }
                else
                    Log.i("LOGIN","Ya no envia mas peticiones");

            }
        });

        acept_recordado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(sw_push){
                    Login_Table lg=baseDatos.getLogin();
                    if(lg!=null)
                    {
                        sw_push=false;
                        user.setText(lg.getUsuario());
                        pass.setText(lg.getPassword());
                        jsonGetUsuarios(Constantes.URL_GET_USUARIOS);
                    }
                    else{
                        Toast.makeText(getActivity(),"Ocurrio un error, precione \"olvidar usuario y contraseña\" e intente denuevo",Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

        olvidar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                baseDatos.eliminarLogin();
                login_normal.setVisibility(View.VISIBLE);
                login_recordado.setVisibility(View.GONE);
                sw_recordado=true;
            }
        });

        texto_recordar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(recordar.isChecked())
                    recordar.setChecked(false);
                else
                    recordar.setChecked(true);
            }
        });

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        apiClient.connect();

    }

    @Override
    public void onStop() {
        super.onStop();
        if(apiClient.isConnected())
            LocationServices.FusedLocationApi.removeLocationUpdates(apiClient,fragment_login.this);
        apiClient.disconnect();
    }

    public void jsonGetUsuarios(String url)
    {

        JsonObjectRequest js=new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        lista=parseJsonArray(response);
                        //user.setText(lista.getUsers().get(1).getUsuario());
                        if(isUser(lista))
                        {
                            if(lista.getUsers().get(indice).getHabilitado().equals("1")) {

                                Location loc=null;
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                                    if (getContext().checkSelfPermission(android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && getContext().checkSelfPermission(android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                                        Log.i("LOGIN","Error en los permisos");
                                        sw_push=true;
                                        ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION},1);
                                        //return;
                                    } else {
                                        loc = LocationServices.FusedLocationApi.getLastLocation(apiClient);
                                    }
                                } else {
                                    loc = LocationServices.FusedLocationApi.getLastLocation(apiClient);
                                }

                                if (loc != null) {
                                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                                    String fech = sdf.format(new Date());
                                    sdf = new SimpleDateFormat("HH:mm:ss");
                                    String hora = sdf.format(new Date());

                                    if(recordar.isChecked()){
                                       baseDatos.guardarLogin(lista.getUsers().get(indice).getUsuario(),lista.getUsers().get(indice).getPassword());
                                    }


                                    Login_enviar login_enviar = new Login_enviar();
                                    login_enviar.setCi(lista.getUsers().get(indice).getCi());
                                    login_enviar.setFecha(fech);
                                    login_enviar.setHora(hora);
                                    login_enviar.setLat(String.valueOf(loc.getLatitude()));
                                    login_enviar.setLon(String.valueOf(loc.getLongitude()));



                                    jsonPostLogin(Constantes.URL_POST_LOGIN, parseJsonLogin(login_enviar), lista.getUsers().get(indice).getCi());
                                } else {
                                    sw_push=true;
                                    Toast.makeText(getContext(), "No podemos obtener su ubicacion, intente nuevamente.", Toast.LENGTH_SHORT).show();
                                }
                            }
                            else {
                                sw_push=true;
                                Toast.makeText(getContext(), "Lo sentimos, su cuenta no esta habilitada.", Toast.LENGTH_SHORT).show();
                            }

                        }
                        else{
                            sw_push=true;
                            Toast.makeText(getContext(),"Error en los Datos Ingresados... ",Toast.LENGTH_SHORT).show();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("matt","error 404 "+error.getMessage());
                        sw_push=true;
                        Toast.makeText(getContext(),"Error en la Conexion...",Toast.LENGTH_SHORT).show();
                    }
                });
        TaxiMap_Singleton.getInstance(getContext()).addToRequestQueue(js);
    }

    public void guardarDatos(String ci,String id_login,String id_ubicacion,String ocupado,String alerta)
    {
        BaseDatos db=new BaseDatos(getActivity());
        if(db.guardarDatos(Integer.parseInt(ci),Integer.parseInt(id_login),
                Integer.parseInt(id_ubicacion),Integer.parseInt(ocupado),
                Integer.parseInt(alerta)) == -1)
            Log.i("ERROR_BD","Error al guardar");
        else
            Log.i("Base D","se guardo");

    }


    public void jsonGetUsuario(String url,String ci)
    {
        final Gson gson=new Gson();
        Usuario_Datos usDat;
        JsonObjectRequest js=new JsonObjectRequest(
                Request.Method.GET,
                url+"/ci/"+ci,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                     /*   Usuario_Datos usDat=gson.fromJson(response.toString(),Usuario_Datos.class);

                        BaseDatos bd=new BaseDatos(getActivity());
                        bd.guardarUsuario(Integer.parseInt(usDat.getCi()),
                                        usDat.getUsuario(),usDat.getNombre(),
                                        usDat.getApellidoP(),usDat.getApellidoM(),
                                        usDat.getTipo(),Integer.parseInt(usDat.getNum_vehiculo()),
                                        usDat.getDireccion());

                        bd.close();*/
                        Log.i("LoginDatos",response.toString());
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("matt","error 404 "+error.getMessage());
                    }
                });
        TaxiMap_Singleton.getInstance(getContext()).addToRequestQueue(js);
    }

    private JSONObject parseJson(Usuarios datosUs)
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


    public void jsonPostLogin(String url, JSONObject datos, final String ci_usuario)
    {
        JsonObjectRequest js=new JsonObjectRequest(
                Request.Method.POST,
                url,
                datos,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i("MATT","Se envio datos Login!!!!!! "+response.toString());
                        String id_login="",id_ubicacion="";
                        try {
                            id_login=response.getString("id_login");
                            id_ubicacion=response.getString("id_ubicacion");

                            guardarDatos(ci_usuario,id_login,id_ubicacion,"0","0");
                            startActivity(new Intent(getActivity(),MapaActivity.class)
                                            .putExtra("id_login",id_login)
                                            .putExtra("id_ubicacion",id_ubicacion)
                                            .putExtra("ci",ci_usuario)
                                            .putExtra("ocupado","0")
                                            .putExtra("alerta","0"));
                            getActivity().finish();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        sw_push=true;
                        Toast.makeText(getActivity(),"Error al iniciar sesión, intente nuevamente",Toast.LENGTH_SHORT).show();
                        baseDatos.eliminarLogin();
                        Log.i("matt","error 404");
                    }
                });
        TaxiMap_Singleton.getInstance(getContext()).addToRequestQueue(js);
    }

    private ListUsuarios parseJsonArray(JSONObject jObject)
    {
        int i;
        JSONObject objet;
        ListUsuarios lu;
        List<Usuarios> lista=new ArrayList<>();
        try {
            JSONArray array=jObject.getJSONArray("usuarios");
            for(i=0;i<array.length();i++)
            {
                objet=array.getJSONObject(i);
                Usuarios us=new Usuarios();
                us.setCi(objet.getString("ci"));
                us.setUsuario(objet.getString("usuario"));
                us.setPassword(objet.getString("password"));
                us.setHabilitado(objet.getString("habilitado"));
                lista.add(us);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        lu=new ListUsuarios(lista);

        return lu;

    }

    private boolean isUser(ListUsuarios lis)
    {
        int i;
        String us,ps;
        us=user.getText().toString();
        ps=pass.getText().toString();
        if(sw_recordado)
            ps=Constantes.cryptMD5(ps);

        if(ps!=null) {
            for (i = 0; i < lis.getUsers().size(); i++) {

                if (us.equals(lis.getUsers().get(i).getUsuario()) && ps.equals(lis.getUsers().get(i).getPassword())) {
                    indice = i;
                    return true;
                }
            }
        }
        return false;
    }


    private JSONObject parseJsonLogin(Login_enviar login)
    {
        Gson gson=new Gson();
        String dat=gson.toJson(login);
        JSONObject jObj=null;
        try {
            jObj=new JSONObject(dat);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jObj;
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Log.i("API","Se conecto a google Api");
        LocationRequest locReq=new LocationRequest()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(1000)
                .setFastestInterval(1000);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            if (getActivity().checkSelfPermission(android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && getActivity().checkSelfPermission(android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                //logout();
                //Toast.makeText(this,"Error, No concedio permisos de Ubicación",Toast.LENGTH_SHORT).show();
                //finish();
                ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION},1);

            } else {
                LocationServices.FusedLocationApi.requestLocationUpdates(apiClient,locReq,this);
                Log.i("location","Se esta pidiendo ubicacion");
            }
        } else {
            LocationServices.FusedLocationApi.requestLocationUpdates(apiClient,locReq,this);
            Log.i("location","Se esta pidiendo ubicacion");

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

    }

}
