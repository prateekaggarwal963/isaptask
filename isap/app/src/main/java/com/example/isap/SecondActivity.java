package com.example.isap;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SecondActivity extends AppCompatActivity {
    TextView name,address,mobile;
    Button uploadbtn,gobtn;
    ImageView img2;
    String server_url="https://studentlivedata.000webhostapp.com/info/userregister.php";
    String login_url="https://studentlivedata.000webhostapp.com/info/userlogin.php";
    String sname,saddress,smobile;
    AlertDialog.Builder builder;
    StringBuffer buffer = new StringBuffer();
    StringBuffer buffer1 = new StringBuffer();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        name=(TextView) findViewById(R.id.name1);
        address=(TextView) findViewById(R.id.address1);
        mobile=(TextView) findViewById(R.id.mobile1);
        uploadbtn=(Button) findViewById(R.id.uploadbtn);
         gobtn=(Button) findViewById(R.id.qwebtn);
        Bundle j = getIntent().getExtras();
        sname=j.getString("uname");
        saddress=j.getString("uaddress");
        smobile=j.getString("umobile");
        name.setText("Name: " +sname);
        address.setText("Address: "+saddress);
        mobile.setText("Mobile: "+smobile);
        builder=new AlertDialog.Builder(SecondActivity.this);
        //button for sharing data in json
        uploadbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //sending data to server

                StringRequest stringRequest = new StringRequest(Request.Method.POST, server_url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        builder.setTitle("Server Response");
                        builder.setMessage("Response"+response);
                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                             name.setText("");
                             address.setText("");
                             mobile.setText("");
                                Toast.makeText(SecondActivity.this, "Data added to server", Toast.LENGTH_SHORT).show();

                            }
                        });
                        AlertDialog alertDialog = builder.create();
                        alertDialog.show();


                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(SecondActivity.this, "Error...", Toast.LENGTH_SHORT).show();
                        error.printStackTrace();

                    }
                }){
                    @Nullable
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String,String> params = new HashMap<String,String>();
                        params.put("name",sname);
                        params.put("address",saddress);
                        params.put("mobile",smobile);
                        return params;
                    }
                };

                MySingleton.getInstance(SecondActivity.this).addTorequestque(stringRequest);

            }
        });


        //go btn

        gobtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                JsonObjectRequest jsonObjectRequest= new JsonObjectRequest(Request.Method.POST, login_url, null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {

                                try {
                                    Toast.makeText(SecondActivity.this, "good", Toast.LENGTH_SHORT).show();
                                    buffer1.append(response.getString("users"));

                                    AlertDialog.Builder builder1=new AlertDialog.Builder(SecondActivity.this);
                                    builder1.setCancelable(true);
                                    builder1.setTitle("User info from server");
                                    builder1.setMessage(buffer1.toString());
                                    builder1.show();

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(SecondActivity.this, "Error in server"+error.toString(), Toast.LENGTH_SHORT).show();
                        error.printStackTrace();
                    }
                });



                MySingleton.getInstance(SecondActivity.this).addTorequestque(jsonObjectRequest);

            }
        });










    }
}