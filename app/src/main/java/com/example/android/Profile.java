package com.example.android;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Profile extends AppCompatActivity implements View.OnClickListener  {

    private TextView textViewUsername,textViewHr,textViewBp;
    private Button button;
    private ProgressDialog progressDialog;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ImageView imageViewWet, imageViewTempered;
    String username;
    private static  int wet,tempered;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile2);
        username = SharedPrefManager.getInstance(this).getUsername();


        textViewUsername = (TextView) findViewById(R.id.pUsername);
        textViewHr = (TextView) findViewById(R.id.heartRate);
        textViewBp = (TextView) findViewById(R.id.bloodPressure);
        swipeRefreshLayout = findViewById(R.id.swipeToRefresh);
        imageViewTempered = (ImageView) findViewById(R.id.imageViewTempered);
        imageViewWet =(ImageView) findViewById(R.id.imageViewWet);
        button = (Button) findViewById(R.id.refresh);


        wet = SharedPrefManager.getInstance(getApplicationContext()).getWetStatus();
        tempered = SharedPrefManager.getInstance(getApplicationContext()).getTemperedStatus();

        textViewUsername.setText(SharedPrefManager.getInstance(this).getUsername());
        textViewHr.setText(String.valueOf(SharedPrefManager.getInstance(this).getHeartRate()));
        textViewBp.setText(String.valueOf(SharedPrefManager.getInstance(this).getTemperature()));

        if(wet==1){
            imageViewWet.setImageResource(R.drawable.tick);
        }
        else{
            imageViewWet.setImageResource(R.drawable.cross);
        }

        if(tempered == 1){
            imageViewTempered.setImageResource(R.drawable.tick);
        }
        else{
            imageViewTempered.setImageResource(R.drawable.cross);
        }

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                data();

                swipeRefreshLayout.setRefreshing(false);


            }
        });



        button.setOnClickListener(this);

        /*if(!SharedPrefManager.getInstance(this).isLoggedIn()){
            finish();
            startActivity(new Intent(this,ProfileActivity.class));
            return;
        }*/


        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait...");


    }



    public void data(){



        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.URL_DATA,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        try {
                            JSONObject obj = new JSONObject(response);
                            if(!obj.getBoolean("error")){
                                System.out.println("IN SHARED SET VALES");
                                SharedPrefManager.getInstance(getApplicationContext()).data(obj.getInt("Id"),obj.getString("Username"),obj.getInt("Heart_Rate"), (float) obj.getDouble("Temperature"),obj.getInt("Wet"),obj.getInt("Tempered"));

                                textViewUsername.setText(SharedPrefManager.getInstance(getApplicationContext()).getUsername());
                                textViewHr.setText(String.valueOf(SharedPrefManager.getInstance(getApplicationContext()).getHeartRate()));
                                textViewBp.setText(String.valueOf(SharedPrefManager.getInstance(getApplicationContext()).getTemperature()));

                                wet = SharedPrefManager.getInstance(getApplicationContext()).getWetStatus();
                                tempered = SharedPrefManager.getInstance(getApplicationContext()).getTemperedStatus();

                                if(wet==1){
                                    imageViewWet.setImageResource(R.drawable.tick);
                                }
                                else{
                                    imageViewWet.setImageResource(R.drawable.cross);
                                }

                                if(tempered == 1){
                                    imageViewTempered.setImageResource(R.drawable.tick);
                                }
                                else{
                                    imageViewTempered.setImageResource(R.drawable.cross);
                                }

                            }else{
                                Toast.makeText(getApplicationContext(),obj.getString("message"),Toast.LENGTH_LONG).show();

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(),error.getMessage(),Toast.LENGTH_LONG).show();
            }
        }

        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("Username",username);
                return params;
            }
        };
        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);


    }





    @Override
    public void onClick(View v) {

        if(v == button){
            data();
        }

    }


    }




