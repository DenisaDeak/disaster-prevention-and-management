package com.example.disasterpreventionmanagement.travel;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.DialogInterface;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.disasterpreventionmanagement.BuildConfig;
import com.example.disasterpreventionmanagement.MainActivity;
import com.example.disasterpreventionmanagement.R;
import com.example.disasterpreventionmanagement.kids.KidsActivity;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.AddressComponent;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.model.TypeFilter;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;


public class TravelActivity extends AppCompatActivity {
    CardView movebutton, transitbutton;
    ImageView backbutton;
    EditText inputTravelLocation, inputTravelLocation1, inputTravelLocation2, inputTravelLocation3, inputTravelLocation4,
            inputDisastersLocation;
    ImageView addLocation;
    int count_clicks = 0;
    String GOOGLE_API_KEY = BuildConfig.GOOGLE_API_KEY;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_travel);

        backbutton = findViewById(R.id.backIcon);

        movebutton = findViewById(R.id.move);
        transitbutton = findViewById(R.id.transit);

        movebutton.setOnClickListener(v -> moveDialog());
        transitbutton.setOnClickListener(v -> transitDialog());

        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TravelActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void moveDialog() {
        LayoutInflater inflater = this.getLayoutInflater();
        MaterialAlertDialogBuilder dialog = new MaterialAlertDialogBuilder(this);
        View v = inflater.inflate(R.layout.enterlocation_travelplan, null);
        dialog.setView(v);
        dialog.setMessage("Enter location: ");
        inputDisastersLocation = v.findViewById(R.id.searchLocation_travel);

        //autocomplete places
        Places.initialize(getApplicationContext(), GOOGLE_API_KEY);
        inputDisastersLocation.setFocusable(false);
        inputDisastersLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Place.Field> placeList = Arrays.asList(Place.Field.ADDRESS, Place.Field.LAT_LNG, Place.Field.NAME);

                Intent intent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.FULLSCREEN,
                        placeList)
                        .setTypeFilter(TypeFilter.CITIES)
                        .build(TravelActivity.this);
                startActivityForResult(intent, 105);

            }
        });


        dialog.setPositiveButton("next", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

                //send the user's location input to another activity

                String travelLocation = inputDisastersLocation.getText().toString();
                if (inputDisastersLocation.getText().toString().isEmpty()) {
                    Toast.makeText(TravelActivity.this, "Location is required!", Toast.LENGTH_SHORT).show();
                    inputDisastersLocation.setError("Location is required!");

                } else {
                    Intent intent = new Intent(TravelActivity.this, DisastersPlan.class);
                    intent.putExtra("travel location", travelLocation);
                    startActivity(intent);
                }
            }
        });
        dialog.setNegativeButton("cancel", (dialog2, id) -> {
        });

        AlertDialog moveDialog = dialog.create();
        moveDialog.show();
    }


    private void transitDialog() {
        LayoutInflater inflater = this.getLayoutInflater();
        MaterialAlertDialogBuilder dialog = new MaterialAlertDialogBuilder(this);
        View v = inflater.inflate(R.layout.enterlocation_transitplan, null);
        dialog.setView(v);
        dialog.setMessage("Enter location(s): ");

        //add more locations
        inputTravelLocation = v.findViewById(R.id.searchLocation_transit);
        inputTravelLocation1 = v.findViewById(R.id.searchLocation_transit1);
        inputTravelLocation2 = v.findViewById(R.id.searchLocation_transit2);
        inputTravelLocation3 = v.findViewById(R.id.searchLocation_transit3);
        inputTravelLocation4 = v.findViewById(R.id.searchLocation_transit4);

        addLocation = v.findViewById(R.id.addLocation_transit);

        addLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count_clicks++;
                if (inputTravelLocation1.getVisibility() == View.GONE && count_clicks == 1)
                    inputTravelLocation1.setVisibility(View.VISIBLE);
                if (inputTravelLocation2.getVisibility() == View.GONE && count_clicks == 2)
                    inputTravelLocation2.setVisibility(View.VISIBLE);
                if (inputTravelLocation3.getVisibility() == View.GONE && count_clicks == 3)
                    inputTravelLocation3.setVisibility(View.VISIBLE);
                if (inputTravelLocation4.getVisibility() == View.GONE && count_clicks == 4)
                    inputTravelLocation4.setVisibility(View.VISIBLE);
            }
        });

        //autocomplete places
        Places.initialize(getApplicationContext(), GOOGLE_API_KEY);
        inputTravelLocation.setFocusable(false);
        inputTravelLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Place.Field> placeList = Arrays.asList(Place.Field.ADDRESS, Place.Field.LAT_LNG, Place.Field.NAME);

                Intent intent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.FULLSCREEN,
                        placeList).build(TravelActivity.this);
                startActivityForResult(intent, 100);

            }
        });

        inputTravelLocation1.setFocusable(false);
        inputTravelLocation1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Place.Field> placeList = Arrays.asList(Place.Field.ADDRESS, Place.Field.LAT_LNG, Place.Field.NAME);

                Intent intent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.FULLSCREEN,
                        placeList).build(TravelActivity.this);
                startActivityForResult(intent, 101);

            }
        });

        inputTravelLocation2.setFocusable(false);
        inputTravelLocation2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Place.Field> placeList = Arrays.asList(Place.Field.ADDRESS, Place.Field.LAT_LNG, Place.Field.NAME);

                Intent intent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.FULLSCREEN,
                        placeList).build(TravelActivity.this);
                startActivityForResult(intent, 102);

            }
        });

        inputTravelLocation3.setFocusable(false);
        inputTravelLocation3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Place.Field> placeList = Arrays.asList(Place.Field.ADDRESS, Place.Field.LAT_LNG, Place.Field.NAME);

                Intent intent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.FULLSCREEN,
                        placeList).build(TravelActivity.this);
                startActivityForResult(intent, 103);

            }
        });

        inputTravelLocation4.setFocusable(false);
        inputTravelLocation4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Place.Field> placeList = Arrays.asList(Place.Field.ADDRESS, Place.Field.LAT_LNG, Place.Field.NAME);

                Intent intent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.FULLSCREEN,
                        placeList).build(TravelActivity.this);
                startActivityForResult(intent, 104);

            }
        });


        dialog.setPositiveButton("next", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

                //send the user's location input to another activity
                Intent intent = new Intent(TravelActivity.this, WeatherTravelPlan.class);

                String travelLocation = inputTravelLocation.getText().toString();
                if (inputTravelLocation.getText().toString().isEmpty()) {
                    Toast.makeText(TravelActivity.this, "At least one location is required!", Toast.LENGTH_SHORT).show();
                    inputTravelLocation.setError("Location is required!");

                } else {
                    intent.putExtra("TransitLocation", travelLocation);

                }
                if (inputTravelLocation1.getText().toString().isEmpty() == false) {
                    intent.putExtra("TransitLocation1", inputTravelLocation1.getText().toString());
                }

                if (inputTravelLocation2.getText().toString().isEmpty() == false) {
                    intent.putExtra("TransitLocation2", inputTravelLocation2.getText().toString());
                }

                if (inputTravelLocation3.getText().toString().isEmpty() == false) {
                    intent.putExtra("TransitLocation3", inputTravelLocation3.getText().toString());
                }

                if (inputTravelLocation4.getText().toString().isEmpty() == false) {
                    intent.putExtra("TransitLocation4", inputTravelLocation4.getText().toString());
                }
                startActivity(intent);

            }
        });
        dialog.setNegativeButton("cancel", (dialog2, id) -> {
            count_clicks = 0;
        });

        AlertDialog moveDialog = dialog.create();
        moveDialog.show();


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK) {
            Place place = Autocomplete.getPlaceFromIntent(data);
            inputTravelLocation.setText(place.getAddress());
        }
        if (requestCode == 101 && resultCode == RESULT_OK) {
            Place place = Autocomplete.getPlaceFromIntent(data);
            inputTravelLocation1.setText(place.getAddress());
        }
        if (requestCode == 102 && resultCode == RESULT_OK) {
            Place place = Autocomplete.getPlaceFromIntent(data);
            inputTravelLocation2.setText(place.getAddress());
        }
        if (requestCode == 103 && resultCode == RESULT_OK) {
            Place place = Autocomplete.getPlaceFromIntent(data);
            inputTravelLocation3.setText(place.getAddress());
        }
        if (requestCode == 104 && resultCode == RESULT_OK) {
            Place place = Autocomplete.getPlaceFromIntent(data);
            inputTravelLocation4.setText(place.getAddress());
        }

        if (requestCode == 105 && resultCode == RESULT_OK) {
            if (resultCode == RESULT_OK) {
                Place place = Autocomplete.getPlaceFromIntent(data);
                LatLng latLng = place.getLatLng();
                double MyLat = latLng.latitude;
                double MyLong = latLng.longitude;
                Geocoder geocoder = new Geocoder(TravelActivity.this, Locale.getDefault());
                try {
                    List<Address> addresses = geocoder.getFromLocation(MyLat, MyLong, 1);
                    String stateName = addresses.get(0).getCountryName();
                    String cityName = addresses.get(0).getLocality();
                    inputDisastersLocation.setText(stateName);
                } catch (IOException e) {
                    e.printStackTrace();


                }


            }
        }
    }
}