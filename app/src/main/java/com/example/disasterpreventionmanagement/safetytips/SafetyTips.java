package com.example.disasterpreventionmanagement.safetytips;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.disasterpreventionmanagement.MainActivity;
import com.example.disasterpreventionmanagement.R;

import java.util.ArrayList;
public class SafetyTips extends AppCompatActivity {
    private ArrayList<Item> items = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_safety_tips);

        ImageView backbutton = findViewById(R.id.backIcon);

        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SafetyTips.this, MainActivity.class);
                startActivity(intent);
            }
        });

        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(new Adapter(items, this));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        items.add(new Item(R.drawable.flood1, getString(R.string.event1), getString(R.string.event1description),getString(R.string.linkFloods)));
        items.add(new Item(R.drawable.tornado1, getString(R.string.event2), getString(R.string.event2description), getString(R.string.linkTornadoes)));
        items.add(new Item(R.drawable.earthquake, getString(R.string.event3), getString(R.string.event3description),getString(R.string.linkEarthquakes)));
        items.add(new Item(R.drawable.lightning, getString(R.string.event4), getString(R.string.event4description), getString(R.string.linkStorms)));
        items.add(new Item(R.drawable.extremeheat, getString(R.string.event5), getString(R.string.event5description), getString(R.string.linkHeatWave)));
        items.add(new Item(R.drawable.heavysnow, getString(R.string.event6), getString(R.string.event6description), getString(R.string.linkIceStorms)));
        items.add(new Item(R.drawable.wildfire, getString(R.string.event7), getString(R.string.event7description), getString(R.string.linkWildfires)));
        items.add(new Item(R.drawable.hurricane, getString(R.string.event8), getString(R.string.event8description), getString(R.string.linkHurricanes)));


    }


}


