package com.applet.jlf.Service;

import android.database.Cursor;
import android.database.SQLException;
import android.util.Log;

import com.applet.jlf.Model.StopModel;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.maps.model.RoundCap;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DatabaseMethods {

    public String[] getRouteList(DatabaseHelper myDbHelper){

        List<String> list = new ArrayList<String>();

        Cursor c = myDbHelper.query("list",null,null,null,null,null,null);
        if(c.moveToFirst()){
            do{
                list.add(c.getString(2));
            }while(c.moveToNext());
        }
        return list.toArray(new String[list.size()]);
    }

    public String[] getStopList(DatabaseHelper myDbHelper,String tableName){

        List<String> list = new ArrayList<String>();

        Cursor c = myDbHelper.query(tableName,null,null,null,null,null,null);
        if(c.moveToFirst()){
            do{
                if(c.getString(3)!=null){
                    list.add(c.getString(3));
                }
            }while(c.moveToNext());
        }

        return list.toArray(new String[list.size()]);
    }

    public PolylineOptions getPolyline(DatabaseHelper myDbHelper,String tableName){

        PolylineOptions pl = new PolylineOptions();
        Cursor c = myDbHelper.query(tableName,null,null,null,null,null,null);
        if(c.moveToFirst()){
            do{
                if(c.moveToFirst()){
                    do{
                        pl.add(new LatLng(Double.parseDouble(c.getString(1)),Double.parseDouble(c.getString(2))));
                    }while(c.moveToNext());
                }
            }while(c.moveToNext());
        }
        pl.geodesic(true).width(7).color(0xff0000ff).endCap(new RoundCap());
        return pl;
    }

    public List<StopModel> getStopModelArray(DatabaseHelper myDbHelper){

        List<StopModel> stopModelList = new ArrayList<StopModel>();

        Cursor c = myDbHelper.query("complete_data",null,null,null,null,null,null);
        if(c.moveToFirst()){
            do{
                Double lat = Double.parseDouble(c.getString(1));
                Double lon = Double.parseDouble(c.getString(2));
                String  name = c.getString(3);
                stopModelList.add(new StopModel(lat,lon,name));
            }while(c.moveToNext());
        }

        return stopModelList;
    }

    public void getSearchHistory(){

    }

    public void getSearchResult(String src, String dst){
    }


}
