package com.example.disasterpreventionmanagement.kids;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.disasterpreventionmanagement.R;
import com.google.android.material.button.MaterialButton;

public class Tutorial extends AppCompatActivity {
    TextView tutorialText, tutorialText2;
    MaterialButton buttonTutorial;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial);
        tutorialText = findViewById(R.id.introTutorial);
        tutorialText.setText(getString(R.string.tutorialKids));
        tutorialText2 = findViewById(R.id.intro2Tutorial);
        tutorialText2.setText(getString(R.string.tutorial2Kids));

        buttonTutorial = findViewById(R.id.buttonTutorial);
        buttonTutorial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Tutorial.this, KidsActivity.class);
                startActivity(i);
            }
        });
    }
}