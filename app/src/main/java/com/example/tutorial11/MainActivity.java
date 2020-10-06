package com.example.tutorial11;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;

public class MainActivity extends AppCompatActivity {
    ListView lstData;
    MyAdapter adapter;
    RequestQueue reqestQueue;
    JsonArrayRequest jsonArrayRequest;
    StringRequest stringRequest;
    ProgressDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lstData = findViewById(R.id.lstData);
        volleyAPICall();

        lstData.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(MainActivity.this, Userdata.class);
                intent.putExtra("id", i);
                startActivity(intent);
            }
        });
    }

    private void volleyAPICall() {
        reqestQueue = Volley.newRequestQueue(MainActivity.this);
        jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                MyUtil.USER_URL,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        dialog = new ProgressDialog(MainActivity.this);
                        dialog.setTitle("Please wait a while...");
                        dialog.show();

                        MyUtil.UserData = response;
                        adapter = new MyAdapter(MainActivity.this);
                        lstData.setAdapter(adapter);
                        if(dialog.isShowing()){dialog.dismiss();}

                    }
                },
                new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this,"Somthing went wrong",Toast.LENGTH_LONG).show();
                    }
                }
        );
        reqestQueue.add(jsonArrayRequest);

    }

}