package com.example.disasterpreventionmanagement.kids;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.disasterpreventionmanagement.safetytips.Adapter;
import com.example.disasterpreventionmanagement.safetytips.Item;
import com.example.disasterpreventionmanagement.LoginRegister;
import com.example.disasterpreventionmanagement.MainActivity;
import com.example.disasterpreventionmanagement.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class KidsActivity extends AppCompatActivity {
    private final ArrayList<Item> items = new ArrayList<>();
    MaterialButton leavekidsbutton;
    Button tutorial, quiz, restart;
    ImageView menuIcon;
    DrawerLayout navDrawer;
    TextView username, userEmail;


    private static final int REQUESTCODE_QUIZ = 1;
    public static final String SCORE_SHAREPREFS = "scorePrefs";
    public static final String key_total_score = "totalscore_key";

    SharedPreferences sharedPreferences;
    TextView score_main;

   private int totalscore = 1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nav_activity_kids);
        leavekidsbutton= findViewById(R.id.leavekidsmodebutton);
        tutorial = findViewById(R.id.tutorial_kids);
        quiz = findViewById(R.id.quiz_kids);
        restart = findViewById(R.id.restart_kids);
        score_main = findViewById(R.id.score_main);
        menuIcon = findViewById(R.id.menuIconK);
        NavigationView navigationView = findViewById(R.id.nav_view); //just the nav drawer
        navDrawer = findViewById(R.id.drawer_layout);// includes main activity
        loadTotalScore();
        if(totalscore==100) {
            LayoutInflater inflater = this.getLayoutInflater();
            View v = inflater.inflate(R.layout.popup_kids, null);

            new MaterialAlertDialogBuilder(KidsActivity.this)
                    .setPositiveButton("Got it!", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    })
                    .setView(v)
                    .show();

        }



        String accountEmail;
        String accountName; // email and username shown in nav header

        SharedPreferences sp;
        sp = getSharedPreferences("UserCredentials",
                Context.MODE_PRIVATE);
        accountEmail = sp.getString("UserEmail", "");
        accountName = sp.getString("UserName", "");

        View header= navigationView.getHeaderView(0);
        username = (TextView) header.findViewById(R.id.username);
        userEmail = (TextView) header.findViewById(R.id.useremail);
        userEmail.setText(accountEmail);
        username.setText("Hello, " + accountName + "!");



        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.logoutbutton) {
                    new MaterialAlertDialogBuilder(KidsActivity.this)
                            .setTitle("Wait!")
                            .setMessage("Are you sure you want to log out? Your progress will be lost!")
                            .setPositiveButton("Yes, log me out!", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    SharedPreferences sharedPreferences = getSharedPreferences("UserCredentials", Context.MODE_PRIVATE); //store logged in user's mail to filter the data that can be accessed by user
                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                    editor.putBoolean("hasLoggedIn", false);
                                    editor.commit();


                                    //delete the current score
                                    SharedPreferences score_prefs = getSharedPreferences(SCORE_SHAREPREFS, MODE_PRIVATE);
                                    SharedPreferences.Editor editorScore = score_prefs.edit();
                                    editorScore.clear();
                                    editorScore.commit();


                                    Intent intent = new Intent(KidsActivity.this, LoginRegister.class);
                                    startActivity(intent);

                                }
                            })
                            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            })
                            .show();
                }
                    return true;

                }

        });

        menuIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    navDrawer.openDrawer(Gravity.LEFT);
                }
        });

        RecyclerView recyclerView = findViewById(R.id.recyclerview);// for the tips/stories
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(new Adapter(items, this));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        items.add(new Item(R.drawable.floodk, getString(R.string.floodsK), getString(R.string.event1descriptionK), getString(R.string.linkKidsFloods)));
        items.add(new Item(R.drawable.tornadok, getString(R.string.tornadoesK), getString(R.string.event2descriptionK), getString(R.string.linkKidsTornadoes)));
        items.add(new Item(R.drawable.earthquakek, getString(R.string.earthquakesK), getString(R.string.event3descriptionK), getString(R.string.linkKidsEarthquakes)));
        items.add(new Item(R.drawable.thunderstormk, getString(R.string.lightningK), getString(R.string.event4descriptionK), getString(R.string.linkKidsLightning)));
        items.add(new Item(R.drawable.extremeheatk, getString(R.string.heatK), getString(R.string.event5descriptionK), getString(R.string.linkKidsHeat)));
        items.add(new Item(R.drawable.heavysnowk, getString(R.string.blizzardK), getString(R.string.event6descriptionK), getString(R.string.linkKidsBlizzards)));
        items.add(new Item(R.drawable.wildfirek, getString(R.string.wildfiresK), getString(R.string.event7descriptionK), getString(R.string.linkKidsWildfires)));
        items.add(new Item(R.drawable.hurricanek, getString(R.string.hurricanesK), getString(R.string.event8descriptionK), getString(R.string.linkKidsHurricanes)));


        final int[] checkedItem = {-1};


        tutorial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(KidsActivity.this, Tutorial.class);
                startActivity(i);
            }
        });

        quiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MaterialAlertDialogBuilder alertDialog = new MaterialAlertDialogBuilder(KidsActivity.this);
                alertDialog.setTitle("Choose a section and start the quiz: ");
                final String[] listItems = new String[]{"Floods", "Tornadoes", "Earthquakes", "Thunderstorms", "Extreme heat", "Blizzards", "Wildfires", "Hurricanes"};
                alertDialog.setSingleChoiceItems(listItems, checkedItem[0], new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        checkedItem[0] = which;
                        //dialog.dismiss();
                        //listItems[which] is the selected item

                        sharedPreferences = getSharedPreferences("SectionQuiz", Context.MODE_PRIVATE); //store logged in user's mail to filter the data that can be accessed by user
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.clear();
                        editor.putString("SectionSelected", listItems[which]);
                        editor.commit();


                    }
                });

                alertDialog.setPositiveButton("Start quiz", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent i = new Intent(KidsActivity.this, QuizKids.class);
                        startActivityForResult(i, REQUESTCODE_QUIZ);
                    }
                });

                alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                AlertDialog customAlertDialog = alertDialog.create();
                customAlertDialog.show();
            }
        });



        //Restart progress
        restart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(KidsActivity.this);
                alertDialog.setTitle("Are you sure you want to restart and lose all progress?");
                alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        updateTotalScore(1);
                        loadTotalScore();
                    }
                });

                alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                AlertDialog customAlertDialog = alertDialog.create();
                customAlertDialog.show();
            }
        });

    }

    public void onClickLeaveKids(View v){
        if(leavekidsbutton.isChecked()){
            Intent intent = new Intent(KidsActivity.this, MainActivity.class);
            startActivity(intent);
        }
        else {
            Intent intent = new Intent(KidsActivity.this, MainActivity.class);
            startActivity(intent);
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUESTCODE_QUIZ){
            if(resultCode == RESULT_OK){
                int score = data.getIntExtra(QuizKids.finalScore, 1 );
                if(totalscore<100)
                     totalscore = totalscore + score;
                updateTotalScore(totalscore);
            }
        }


    }

    private void loadTotalScore(){
        SharedPreferences score_prefs = getSharedPreferences(SCORE_SHAREPREFS, MODE_PRIVATE);
        totalscore = score_prefs.getInt(key_total_score, 1);
        score_main.setText("Your current progress is: " + totalscore + "%");
    }


    private void updateTotalScore(int new_score) {
        totalscore = new_score;
        score_main.setText("Your current progress is: " + totalscore + "%");
        SharedPreferences score_prefs = getSharedPreferences(SCORE_SHAREPREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = score_prefs.edit();
        editor.putInt(key_total_score, totalscore);
        editor.apply();


    }
}