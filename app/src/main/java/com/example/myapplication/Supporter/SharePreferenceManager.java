package com.example.myapplication.Supporter;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.myapplication.Model.WorkOutUnit.Routine.Routine;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;

public class SharePreferenceManager {

    SharedPreferences sharedPreferences;
    Context context;

    public SharePreferenceManager(Context context)
    {
        this.context = context;
        sharedPreferences = context.getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
    }

    public boolean saveObject(String key, Object value)
    {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String json = new Gson().toJson(value);
        editor.putString(key,json);
        return editor.commit();
    }

    public Object getObject(String key, Class classType)
    {
        String json = sharedPreferences.getString(key,null);
        if(json != null)
        {
            try{
                Object o = new Gson().fromJson(json,classType);
                return o;
            }
            catch (Exception e) {
                System.out.println(e);
            }
        }
        return null;
    }

    public boolean saveRoutine(Routine routine)
    {
        ArrayList<Routine> routines = new ArrayList<>();
        Routine[] data = (Routine[]) getObject("Routines", Routine[].class);
        if (data != null) routines = new ArrayList<>(Arrays.asList(data));
        boolean checkNew = true;
        for(int i =  0; i < routines.size(); i++)
        {
            Routine r = routines.get(i);
            if(r.getId() == routine.getId())
            {
                routines.set(i, routine);
                checkNew = false;
            }
        }
        if(checkNew)
        {
            routines.add(routine);
        }
        return saveObject("Routines", routines);
    }

    public boolean deleteRoutine(int id)
    {
        ArrayList<Routine> routines = new ArrayList<>();
        Routine[] data = (Routine[]) getObject("Routines", Routine[].class);
        if (data != null)
        {
            routines = new ArrayList<>(Arrays.asList(data));
            for(int i =  0; i < routines.size(); i++)
            {
                Routine r = routines.get(i);
                if(r.getId() == id)
                {
                    routines.remove(i);
                }
            }
        }
        return saveObject("Routines", routines);
    }

    public Routine getRoutine(int id)
    {
        ArrayList<Routine> routines = new ArrayList<>();
        Routine[] data = (Routine[]) getObject("Routines", Routine[].class);
        if (data != null) routines = new ArrayList<>(Arrays.asList(data));
        for(int i =  0; i < routines.size(); i++)
        {
            Routine r = routines.get(i);
            if(id == r.getId())
            {
                return  r;
            }
        }
        return null;
    }
}
