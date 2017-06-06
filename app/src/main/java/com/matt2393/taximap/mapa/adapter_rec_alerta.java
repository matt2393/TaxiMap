package com.matt2393.taximap.mapa;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.matt2393.taximap.Clases.UsuariosAlerta;
import com.matt2393.taximap.R;

import java.util.ArrayList;


public class adapter_rec_alerta extends RecyclerView.Adapter<adapter_rec_alerta.ViewHolder> {

    private ArrayList<UsuariosAlerta> datos;
    private GoogleMap map;

    public adapter_rec_alerta(ArrayList<UsuariosAlerta> datos, GoogleMap map) {
        this.datos = datos;
        this.map = map;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.rec_alerta,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final int pos=position;
        holder.nom.setText(datos.get(position).getNombre());
        holder.ubic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                map.animateCamera(CameraUpdateFactory.newLatLngZoom(
                        new LatLng(Double.parseDouble(datos.get(pos).getLat())
                                ,Double.parseDouble(datos.get(pos).getLon())),14));
            }
        });
    }

    @Override
    public int getItemCount() {
        return datos.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView nom;
        Button ubic;
        public ViewHolder(View itemView) {
            super(itemView);
            nom=(TextView)itemView.findViewById(R.id.nombre_alerta);
            ubic=(Button)itemView.findViewById(R.id.ubicacion_alerta);

        }
    }
}
