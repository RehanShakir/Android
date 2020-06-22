package com.example.android;

import android.content.Context;
import android.content.SharedPreferences;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class SharedPrefManager {
    private static SharedPrefManager instance;
    private static Context mCtx;

    private static final String SHARED_PREF_NAME = "mysharedpref12";
    private static final String KEY_USER = "Username";
    private static final String KEY_USER_ID = "Id";
    private static final String KEY_HR = "Heart_Rate";
    private static final String KEY_BP = "Temperature";
    private static final String KEY_WET = "Wet";
    private static final String KEY_TEMPERED = "Tempered";



    private SharedPrefManager(Context context) {
        mCtx = context;

    }

    public static synchronized SharedPrefManager getInstance(Context context) {
        if (instance == null) {
            instance = new SharedPrefManager(context);
        }
        return instance;
    }

    public boolean userLogin(int Id, String Username,int Heart_Rate, float Temperature, int Wet, int Tempered){

         SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
         SharedPreferences.Editor editor = sharedPreferences.edit();
         editor.putInt(KEY_USER_ID,Id);
         editor.putString(KEY_USER,Username);
         editor.putInt(KEY_HR,Heart_Rate);
         editor.putFloat(KEY_BP,Temperature);
         editor.putInt(KEY_WET,Wet);
         editor.putInt(KEY_TEMPERED,Tempered);


         editor.apply();

         return true;

    }

    public boolean data(int Id, String Username,int Heart_Rate, float Temperature, int Wet, int Tempered){

        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(KEY_USER_ID,Id);
        editor.putString(KEY_USER,Username);
        editor.putInt(KEY_HR,Heart_Rate);
        editor.putFloat(KEY_BP,Temperature);
        editor.putInt(KEY_WET,Wet);
        editor.putInt(KEY_TEMPERED,Tempered);



        editor.apply();

        return true;

    }




    public boolean isLoggedIn(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);

        if(sharedPreferences.getString(KEY_USER,null) == null){
            return true;
        }
        return false;
    }

    public boolean logOut(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        return true;
    }
    //Fetching username from database
    public String getUsername(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_USER,null);
    }

    public int getHeartRate(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        return sharedPreferences.getInt(KEY_HR,0);
    }

    public float getTemperature(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        return sharedPreferences.getFloat(KEY_BP,0);

    }

    public int getWetStatus(){

        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        return sharedPreferences.getInt(KEY_WET,0);

    }

    public int getTemperedStatus(){

        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        return sharedPreferences.getInt(KEY_TEMPERED,0);

    }



}
