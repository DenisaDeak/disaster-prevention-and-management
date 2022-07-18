package com.example.disasterpreventionmanagement.travel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.disasterpreventionmanagement.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class DisastersPlan extends AppCompatActivity {
    ListView disastersList, currentDisasterList;
    ImageView backbutton;
    TextView countryname;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_move_plan);

        backbutton = findViewById(R.id.backIcon);
        disastersList = findViewById(R.id.disasterList);
        currentDisasterList = findViewById(R.id.disasterList_ongoing);
        countryname = findViewById(R.id.countryName);

        backbutton.setOnClickListener(view -> {
            Intent intent = new Intent(DisastersPlan.this, TravelActivity.class);
            startActivity(intent);
        });



        //retrieve the input location from prev activity
        String travelLocation = getIntent().getStringExtra("travel location");
        countryname.setText(travelLocation);
        String url = "https://api.reliefweb.int/v1/disasters?appname=apidoc&filter[operator]=AND&filter[conditions][0][field]=status&filter[conditions][0][value]=past&filter[conditions][1][field]=country&filter[conditions][1][value]=",
                    url_final = url + travelLocation;

        //sending get request
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url_final)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if(response.isSuccessful()){
                    String _response = response.body().string();
                    DisastersPlan.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            disastersList.setAdapter(parseJson(_response));
                        }
                    });
                }

            }
        });




        //retrieve ongoing disasters
        String url_ongoing = "https://api.reliefweb.int/v1/disasters?appname=apidoc&filter[operator]=AND&filter[conditions][0][field]=status&filter[conditions][0][value]=current&filter[conditions][1][field]=country&filter[conditions][1][value]=",
                url_final2 = url_ongoing + travelLocation;

        //sending get request
        OkHttpClient client2 = new OkHttpClient();
        Request request2 = new Request.Builder()
                .url(url_final2)
                .build();
        client2.newCall(request2).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if(response.isSuccessful()){
                    String _response = response.body().string();
                    DisastersPlan.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            currentDisasterList.setAdapter(parseJson(_response));
                        }
                    });
                }
            }
        });



    }


    protected ArrayAdapter parseJson(String jsonString) {
        List<disasterData> al = new ArrayList<>();
        try {
            JSONObject object = new JSONObject(jsonString);
            JSONArray dataArray = object.getJSONArray("data");
            //ArrayList al = new ArrayList();
            int length = object.getInt("count");


            for(int i = 0; i < length; ++i) {

                JSONObject jsonObject1 = dataArray.getJSONObject(i);
                JSONObject fields = jsonObject1.getJSONObject("fields");
                String title = fields.optString("name");
                String link = "https://reliefweb.int/disasters?search=" + title;
                al.add(new disasterData(title, link));

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        ArrayAdapter<disasterData> adapter;
        if(al.isEmpty()) {
            al.add(new disasterData("No natural disasters found !", "https://reliefweb.int/disasters?view=ongoing"));
            adapter = new ArrayAdapter<>(this, R.layout.list_disasters, al);
        }else {
            adapter = new ArrayAdapter<>(this, R.layout.list_disasters, al);
        }
        disastersList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String url = adapter.getItem(position).getdLink();
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });

        return adapter;

    }


}