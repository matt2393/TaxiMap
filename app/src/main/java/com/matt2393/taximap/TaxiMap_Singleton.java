package com.matt2393.taximap;


import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public final class TaxiMap_Singleton {

    private static TaxiMap_Singleton singleton;
    private RequestQueue requestQueue;
    private static Context context;

    private TaxiMap_Singleton(Context context)
    {
        TaxiMap_Singleton.context=context;
        requestQueue = getRequestQueue();
    }

    public static synchronized TaxiMap_Singleton getInstance(Context context)
    {
        if(singleton==null){
            singleton=new TaxiMap_Singleton(context);
        }
        return singleton;
    }
    public RequestQueue getRequestQueue()
    {
        if(requestQueue==null)
        {
            requestQueue= Volley.newRequestQueue(context.getApplicationContext());
        }
        return requestQueue;
    }
    public void addToRequestQueue(Request req)
    {
        getRequestQueue().add(req);
    }

}
