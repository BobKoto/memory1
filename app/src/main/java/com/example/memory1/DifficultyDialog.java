package com.example.memory1;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RadioButton;
import android.widget.Toast;

public class DifficultyDialog  extends MainActivity {
    public boolean firstPassHere = true;
    public SharedPreferences sharedPreferences;
    public RadioButton rb1;
    public RadioButton rb2;
    public RadioButton rb3;
    public RadioButton rb4;
   // public SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.difficultydialog);
     //   rb1 = findViewById(R.id.radioButton1);
     //   rb2 = findViewById(R.id.radioButton2);
     //   RadioButton rb3 = findViewById(R.id.radioButton3);
     //   RadioButton rb4 = findViewById(R.id.radioButton4);


        //  setContentView(R.layout.difficultydialog);
    }


    protected void onResume() {
        super.onResume();

        setContentView(R.layout.difficultydialog);
        FloatingActionButton fab = findViewById(R.id.fab);
       // Toolbar toolbar = findViewById(R.id.toolbar);
      //  setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
         if (ab != null) ab.setDisplayHomeAsUpEnabled(true);

         Toast.makeText(getApplicationContext(), "on Resume DDialog", Toast.LENGTH_LONG).show();
         rb1 = findViewById(R.id.radioButton1);
         rb2 = findViewById(R.id.radioButton2);
         rb3 = findViewById(R.id.radioButton3);
         rb4 = findViewById(R.id.radioButton4);

        loadRadioButtons();
       // setContentView(R.layout.difficultydialog);

        rb1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "SLOW SPEED selected.", Toast.LENGTH_LONG).show();
                //RadioButton rb1 = findViewById(R.id.radioButton1);
                //RadioButton rb2 = findViewById(R.id.radioButton2);
                //slowSpeed = true;
                //fastSpeed = false;
                //rb1.setChecked(true);
                //rb2.setChecked(false);
                //  sharedPreferences.edit().putBoolean("slowPreview",rb1.isChecked()).apply();
                displayDelay = 500;
                saveRadioButtons();
            }
        });
        rb2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "FAST SPEED selected.", Toast.LENGTH_LONG).show();
                //RadioButton rb1 = findViewById(R.id.radioButton1);
                //RadioButton rb2 = findViewById(R.id.radioButton2);
                //slowSpeed = rb1.isChecked();
                //fastSpeed = rb2.isChecked();
                //rb1.setChecked(false);
                //rb2.setChecked(true);
                //   sharedPreferences.edit().putBoolean("fastPreview",rb2.isChecked()).apply();
                displayDelay = 250;
                saveRadioButtons();
            }
        });

        rb3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "rb3 6 IMAGES pressed", Toast.LENGTH_LONG).show();
                saveRadioButtons();
            }
        });
        rb4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "rb4 9 IMAGES pressed", Toast.LENGTH_LONG).show();
                saveRadioButtons();
            }
        });
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //   Snackbar.make(view, "START THE GAME!", Snackbar.LENGTH_LONG)
                //           .setAction("Action", null).show();
                //   Toast.makeText(getApplicationContext(),"Start the GAME!",Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getApplicationContext(), Main2Activity.class);
                startActivity(intent);

            }
        });
    }
    public void loadRadioButtons()  {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
       // SharedPreferences.Editor editor = sharedPreferences.edit();
        rb1.setChecked(sharedPreferences.getBoolean("b1",true));
        rb2.setChecked(sharedPreferences.getBoolean("b2",false));
        rb3.setChecked(sharedPreferences.getBoolean("b3",true));
        rb4.setChecked(sharedPreferences.getBoolean("b4",false));
      //  editor.apply();
        Toast.makeText(getApplicationContext(), "end of loadRB", Toast.LENGTH_LONG).show();
    }
    public void saveRadioButtons()  {
       // setContentView(R.layout.difficultydialog);
        //RadioButton rb1 = findViewById(R.id.radioButton1);
        //RadioButton rb2 = findViewById(R.id.radioButton2);
       // RadioButton rb3 = findViewById(R.id.radioButton3);
       // RadioButton rb4 = findViewById(R.id.radioButton4);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("b1",rb1.isChecked());
        editor.putBoolean("b2",rb2.isChecked());
        editor.putBoolean("b3",rb3.isChecked());
        editor.putBoolean("b4",rb4.isChecked());
        editor.apply();
     //   Toast.makeText(getApplicationContext(), "end of SAVERB", Toast.LENGTH_LONG).show();
    }
}
 /*      public void saveRadioButtons()  {
      //    setContentView(R.layout.difficultydialog);
           RadioButton rb1 = findViewById(R.id.radioButton1);
           RadioButton rb2 = findViewById(R.id.radioButton2);
           RadioButton rb3 = findViewById(R.id.radioButton3);
           RadioButton rb4 = findViewById(R.id.radioButton4);

           sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
           SharedPreferences.Editor editor = sharedPreferences.edit();
           editor.putBoolean("b1",rb1.isChecked());
           editor.putBoolean("b2",rb2.isChecked());
           editor.putBoolean("b3",rb3.isChecked());
           editor.putBoolean("b4",rb4.isChecked());
           editor.apply();
       }
    public void loadRadioButtons()  {
        setContentView(R.layout.difficultydialog);
        RadioButton rb1 = findViewById(R.id.radioButton1);
        RadioButton rb2 = findViewById(R.id.radioButton2);
        RadioButton rb3 = findViewById(R.id.radioButton3);
        RadioButton rb4 = findViewById(R.id.radioButton4);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        rb1.setChecked(sharedPreferences.getBoolean("b1",false));
        rb2.setChecked(sharedPreferences.getBoolean("b2",false));
        rb3.setChecked(sharedPreferences.getBoolean("b3",false));
        rb4.setChecked(sharedPreferences.getBoolean("b4",false));
        editor.apply();
    }
    */


/*    these methods did NOT work to save radio buttons
        @Override
        protected void onSaveInstanceState(Bundle savedInstanceState)  {
          super.onSaveInstanceState(savedInstanceState);
            RadioButton rb1 = findViewById(R.id.radioButton1);
            RadioButton rb2 = findViewById(R.id.radioButton2);
            RadioButton rb3 = findViewById(R.id.radioButton3);
            RadioButton rb4 = findViewById(R.id.radioButton4);

          savedInstanceState.putBoolean("slowSpeed", rb1.isChecked());
          savedInstanceState.putBoolean("fastSpeed", rb2.isChecked());
          savedInstanceState.putBoolean("x6Images", rb3.isChecked());
          savedInstanceState.putBoolean("x9Images", rb4.isChecked());
        }
        protected void onRestoreInstanceState(Bundle savedInstanceState)  {
            super.onRestoreInstanceState(savedInstanceState);

            savedInstanceState.getBoolean("slowSpeed");
            savedInstanceState.getBoolean("fastSpeed");
            savedInstanceState.getBoolean("x6Images");
            savedInstanceState.getBoolean("x9Images");
            Toast.makeText(getApplicationContext(), "on Restore DDialog", Toast.LENGTH_LONG).show();

        }
    */