package com.example.memory1;

import android.app.Activity;
import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.annotation.StringRes;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.transition.Explode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;
import static android.view.View.combineMeasuredStates;
import static android.view.View.generateViewId;

public class MainActivity extends AppCompatActivity {
    public static final double SOUND_IMAGE0 = 923.251;
    public static final double SOUND_IMAGE1 = 1423.251;
    public static final double SOUND_IMAGE2 = 1923.251;
    public static final double SOUND_IMAGE3 = 2423.251;
    public static final double SOUND_IMAGE4 = 2923.251;
    public static final double SOUND_IMAGE5 = 3423.251;
    public int[] imageOrderArray = new int[6];
    public int[] storedOrderArray = new int[6];
    public int im0, im1, im2, im3, im4, im5;
    public static double [] mostRecentGame = new double[7];
    public static double[] secondGame = new double[7];
    public static double[] thirdGame = new double[7];
    public static double[] fourthGame = new double[7];
    protected static int roundsThisGame, pressesThisGame, scoreAtLastRound;
    protected static double actualTimePlayedThisGame;
    protected static int roundsPerGame = 5;
    protected static int imagesToPress = 6;
    public static boolean gameOver;
    protected static int gameScore = 0;
    protected static int scoreThisRound;
    protected static int perfectRounds =0, perfectRoundsUnderTwoSeconds = 0;
    protected static String nextRoundButton = "Start";
    protected static boolean resetByMidRoundExit;
    // public Button againButton;
    public int length = 6;
    public static int longDelay = 500;  //Milliseconds. 1/2 second
    public static int shortDelay = 250; //              1/4 second
    public static int displayDelay = longDelay;
    public boolean slowSpeed;
    public boolean fastSpeed;
    public boolean x6images;
    public boolean x9images;
    protected static  SharedPreferences sharedPreferences;
   // protected static PreferenceManager =sharedPreferences;
    protected Date today;
    protected String dateOutput, dateEnd;
    SimpleDateFormat formatter;
    public boolean backWasPressed = false;
    public static boolean testTimer = false ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // boolean testTimer = false;
        setContentView(R.layout.activity_main);
        backWasPressed = false;
       //Temporary EXPIRATION CODE
        /*
        formatter = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
        today =  new Date();
        dateOutput = formatter.format(today);
        dateEnd = "20201231";  //MUST update Toast Below
      //  dateEnd = "20201231";
        if (Integer.valueOf(dateOutput) > Integer.valueOf(dateEnd)) {
            Toast.makeText(getApplicationContext(), "TRIAL EXPIRED" + " Please go to GooglePlay"
                    , Toast.LENGTH_LONG).show();
            finish();
        }
                else
        Toast.makeText(getApplicationContext(),"Trial ends 12/31/20",Toast.LENGTH_LONG).show();
                */
        //END Temporary EXPIRATION CODE        ;
    }

    protected void onResume() {
        super.onResume();
        //boolean testTimer =false;
        backWasPressed = false;
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        slowSpeed = sharedPreferences.getBoolean("b1",true);
        fastSpeed = sharedPreferences.getBoolean("b2",false);
        x6images  = sharedPreferences.getBoolean("b3",true);
        x9images  = sharedPreferences.getBoolean("b4",false);
        mostRecentGame[0] = sharedPreferences.getFloat ("GameScore1",0);
        mostRecentGame[1] = sharedPreferences.getFloat ("ATP1",0);
        mostRecentGame[2] = sharedPreferences.getFloat ("PTG1",0);
        mostRecentGame[3] = sharedPreferences.getFloat ("PR1", 0);
        mostRecentGame[4] = sharedPreferences.getFloat ("PRU21",0);
        mostRecentGame[5] = sharedPreferences.getFloat ("ITP1",0);
        mostRecentGame[6] = sharedPreferences.getFloat ("RPG1",0);

        secondGame[0] = sharedPreferences.getFloat ("GameScore2",0);
        secondGame[1] = sharedPreferences.getFloat ("ATP2",0);
        secondGame[2] = sharedPreferences.getFloat ("PTG2",0);
        secondGame[3] = sharedPreferences.getFloat ("PR2", 0);
        secondGame[4] = sharedPreferences.getFloat ("PRU22",0);
        secondGame[5] = sharedPreferences.getFloat ("ITP2",0);
        secondGame[6] = sharedPreferences.getFloat ("RPG2",0);

        thirdGame[0] = sharedPreferences.getFloat ("GameScore3",0);
        thirdGame[1] = sharedPreferences.getFloat ("ATP3",0);
        thirdGame[2] = sharedPreferences.getFloat ("PTG3",0);
        thirdGame[3] = sharedPreferences.getFloat ("PR3", 0);
        thirdGame[4] = sharedPreferences.getFloat ("PRU23",0);
        thirdGame[5] = sharedPreferences.getFloat ("ITP3",0);
        thirdGame[6] = sharedPreferences.getFloat ("RPG3",0);

        fourthGame[0] = sharedPreferences.getFloat ("GameScore4",0);
        fourthGame[1] = sharedPreferences.getFloat ("ATP4",0);
        fourthGame[2] = sharedPreferences.getFloat ("PTG4",0);
        fourthGame[3] = sharedPreferences.getFloat ("PR4", 0);
        fourthGame[4] = sharedPreferences.getFloat ("PRU24",0);
        fourthGame[5] = sharedPreferences.getFloat ("ITP4",0);
        fourthGame[6] = sharedPreferences.getFloat ("RPG4",0);

        if (fastSpeed) displayDelay = shortDelay;

        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setupImageDisplayHints();
        //  Toast.makeText(this, "In onCreate", Toast.LENGTH_LONG).show();
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Main2Activity.class);
                startActivity(intent);
            }
        });
    }

    private void setupImageDisplayHints() {
        ImageView miv0 = findViewById(R.id.mimageView0);
        ImageView miv1 = findViewById(R.id.mimageView1);
        ImageView miv2 = findViewById(R.id.mimageView2);
        ImageView miv3 = findViewById(R.id.mimageView3);
        ImageView miv4 = findViewById(R.id.mimageView4);
        ImageView miv5 = findViewById(R.id.mimageView5);
        storedOrderArray[0] = im0 = miv0.getId();  // Default setup in image order LTR
        storedOrderArray[1] = im1 = miv1.getId();
        storedOrderArray[2] = im2 = miv2.getId();
        storedOrderArray[3] = im3 = miv3.getId();
        storedOrderArray[4] = im4 = miv4.getId();
        storedOrderArray[5] = im5 = miv5.getId();

        String nextPress = "321045";
        for (int i = 0; i < length; i++)    //Load order/sequence of images
            imageOrderArray[i] = storedOrderArray[Integer.valueOf(nextPress.substring(i, i + 1))];
        //DO Animation Hints MAIN PAGE PREVIEW here----

        for (int i = 0; i < length; i++) {
            switch (Integer.valueOf(nextPress.substring(i, i + 1))) {
                case 0:
                    displayImageAfterDelay((i + 1) * displayDelay - 250, miv0);
                    break;
                case 1:
                    displayImageAfterDelay((i + 1) * displayDelay - 250, miv1);
                    break;
                case 2:
                    displayImageAfterDelay((i + 1) * displayDelay - 250, miv2);
                    break;
                case 3:
                    displayImageAfterDelay((i + 1) * displayDelay - 250, miv3);
                    break;
                case 4:
                    displayImageAfterDelay((i + 1) * displayDelay - 250, miv4);
                    break;
                case 5:
                    displayImageAfterDelay((i + 1) * displayDelay - 250, miv5);
                    break;
            }

        }
    }

    public void displayImageAfterDelay(final int delay, final ImageView iv) {
        Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                iv.setAlpha(0f);
                iv.animate().alpha(1f).setDuration(500);
                iv.setVisibility(View.VISIBLE);
            }
        };
        if (!backWasPressed) handler.postDelayed(runnable, delay);
    }
    public void updateGameStats(boolean forcedReset) {
        //Bump em   mostrecent to 2nd, 2nd to 3rd, 3rd to 4th
        fourthGame = Arrays.copyOf(thirdGame, thirdGame.length);
        thirdGame = Arrays.copyOf(secondGame, secondGame.length);
        secondGame = Arrays.copyOf(mostRecentGame, mostRecentGame.length);
        // store most recent game
       if (!forcedReset) {   //is this a normal update OR a forced RESET from menu
                             //if not forced take the last game stats
           mostRecentGame[0] = gameScore;
           mostRecentGame[1] = actualTimePlayedThisGame;
           mostRecentGame[2] = pressesThisGame;
           mostRecentGame[3] = perfectRounds;
           mostRecentGame[4] = perfectRoundsUnderTwoSeconds;
           mostRecentGame[5] = imagesToPress;
           mostRecentGame[6] = roundsPerGame;
       }
     //   Toast.makeText(this, "UPDATE Stats...", Toast.LENGTH_LONG).show();
     // Now store in SharedPreferences
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences (this);
        SharedPreferences.Editor editor = sharedPreferences.edit();


        editor.putFloat ("GameScore1",(float)mostRecentGame[0]); // Probably should use string array?
        editor.putFloat ("ATP1",      (float)mostRecentGame[1]);
        editor.putFloat ("PTG1",      (float)mostRecentGame[2]);
        editor.putFloat ("PR1",       (float)mostRecentGame[3]);
        editor.putFloat ("PRU21",     (float)mostRecentGame[4]);
        editor.putFloat ("ITP1",      (float)mostRecentGame[5]);
        editor.putFloat ("RPG1",      (float)mostRecentGame[6]);

        editor.putFloat ("GameScore2",(float)secondGame[0]);
        editor.putFloat ("ATP2",      (float)secondGame[1]);
        editor.putFloat ("PTG2",      (float)secondGame[2]);
        editor.putFloat ("PR2",       (float)secondGame[3]);
        editor.putFloat ("PRU22",     (float)secondGame[4]);
        editor.putFloat ("ITP2",      (float)secondGame[5]);
        editor.putFloat ("RPG2",      (float)secondGame[6]);

        editor.putFloat ("GameScore3",(float)thirdGame[0]);
        editor.putFloat ("ATP3",      (float)thirdGame[1]);
        editor.putFloat ("PTG3",      (float)thirdGame[2]);
        editor.putFloat ("PR3",       (float)thirdGame[3]);
        editor.putFloat ("PRU23",     (float)thirdGame[4]);
        editor.putFloat ("ITP3",      (float)thirdGame[5]);
        editor.putFloat ("RPG3",      (float)thirdGame[6]);

        editor.putFloat ("GameScore4",(float)fourthGame[0]);
        editor.putFloat ("ATP4",      (float)fourthGame[1]);
        editor.putFloat ("PTG4",      (float)fourthGame[2]);
        editor.putFloat ("PR4",       (float)fourthGame[3]);
        editor.putFloat ("PRU24",     (float)fourthGame[4]);
        editor.putFloat ("ITP4",      (float)fourthGame[5]);
        editor.putFloat ("RPG4",      (float)fourthGame[6]);
        editor.apply();



    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //boolean testTimer = false;
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        //     Toast.makeText(this,"In onCreateOptionsMenu",Toast.LENGTH_LONG).show();
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
       // boolean testTimer = false;
        int id = item.getItemId();
        CharSequence cid = item.getTitle();
        String sid = "1";   //Fixes next line where cid is NOT NULL and sid WAS NULL
        if (cid != null) sid = cid.toString();

        if (sid.equals("Reset Score")) {
            Toast.makeText(this, cid + " Selected", Toast.LENGTH_LONG).show();
            gameScore = 0;
            TextView currentScore = findViewById(R.id.score);
            if (currentScore != null) currentScore.setText(String.valueOf(gameScore));
        }
        if (sid.equals("Game Settings")) {
            Intent intent = new Intent(getApplicationContext(), DifficultyDialog.class);
            startActivity(intent);
       }
        if (sid.equals("Game Statistics")) {
          // if (roundsThisGame >= 1) {
                Intent intent = new Intent(this, GameStats.class);
                startActivity(intent);
        }
        if (sid.equals("Reset Statistics")) {
            resetGameStats();

        }
        if (sid.equals("Info + How to Play")) {
            //Toast.makeText(this, "Info + How to Play...", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(getApplicationContext(), HowToPlay.class);
            startActivity(intent);
        }
        if (sid.equals("Toggle PRACTICE MODE")) {
            testTimer = !testTimer;
            if (testTimer)
                Toast.makeText(getApplicationContext(),"Practice Mode ON",Toast.LENGTH_LONG).show();
            else
                Toast.makeText(getApplicationContext(),"Practice Mode OFF",Toast.LENGTH_LONG).show();
        }
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    public void resetGameStats() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setIcon(R.drawable.redtriangle);
        alertDialogBuilder.setTitle("MEMORY1 -- Confirm Reset Statistics");
        alertDialogBuilder.setMessage("Are you sure you want to Reset Statistics?");
        alertDialogBuilder.setCancelable(false);
        alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
              //  Toast.makeText(getApplicationContext(), "Yes... ", Toast.LENGTH_LONG).show();
                for (int i = 0; i < 7; i++) {
                    mostRecentGame[i] = 0;
                    secondGame[i] = 0;
                    thirdGame[i] = 0;
                    fourthGame[i] = 0;
                    updateGameStats(true);

                }
            }
        });

        alertDialogBuilder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
           //     Toast.makeText(getApplicationContext(), "NO.....", Toast.LENGTH_LONG).show();
            }
        });
        alertDialogBuilder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
             //   Toast.makeText(getApplicationContext(), "CANCEL.....", Toast.LENGTH_LONG).show();
            }
        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
    @Override
    protected void onStop() {
        super.onStop();
    }
}


  /*  @Override
    public  Dialog onCreateDialog(Bundle savedInstanceState) {
       // setContentView(R.layout.difficultydialog);
       selectedItems = new RadioButton()// radioButtonChoices = new RadioButton.r
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.difficultydialog,null))
                .set

    }  */

// OLD CODE with a CALENDAR
// Snackbar.make(toolbar,"just TESTING",Snackbar.LENGTH_LONG).show();
     /*   CalendarView cv = findViewById(R.id.calendarView2);
        cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View cv) {
                //        Toast.makeText(getApplicationContext(), "clk on CAL", Toast.LENGTH_LONG).show();
                cv.setVisibility(INVISIBLE);
            }
        });  */

   /*     Button bv = findViewById(R.id.button1);
        bv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View bv) {
      //          toggleCalendarVisibility(bv);
            }
        });  */

   /*  public void toggleCalendarVisibility(View view) {
        CalendarView cv = findViewById(R.id.calendarView2);
        TextView tvi = findViewById(R.id.textView);
        if (cv.getVisibility() == VISIBLE) {
            cv.setVisibility(INVISIBLE);
            tvi.setText(R.string.CInvisible);
            //     Snackbar.make(view,"Calendar Invisible",Snackbar.LENGTH_LONG).show();
            EditText ed = findViewById(R.id.textView2);
            ed.setText("  Invisible");

        }else {
            cv.setVisibility(VISIBLE);
            tvi.setText(R.string.CVisible);
            //    Snackbar.make(view,"Calendar VISIBLE",Snackbar.LENGTH_LONG).show();
            EditText ed = findViewById(R.id.textView2);
            ed.setText("  VISIBLE");
        }
        //  TextView tvi = findViewById(R.id.textView);
        //  tvi.setText("invisible");
    }  */
//OLD INTRO of Images
 /*public void previewImages()  {
        ImageView miv0 = findViewById(R.id.mimageView0);
        ImageView miv1 = findViewById(R.id.mimageView1);
        ImageView miv2 = findViewById(R.id.mimageView2);
        ImageView miv3 = findViewById(R.id.mimageView3);
        ImageView miv4 = findViewById(R.id.mimageView4);
        ImageView miv5 = findViewById(R.id.mimageView5);

        miv0.setAlpha(0f);
        miv0.animate().alpha(1f).setDuration(500);
        miv0.setVisibility(View.VISIBLE);
        miv1.setAlpha(0f);
        miv1.animate().alpha(1f).setDuration(1500);
        miv1.setVisibility(View.VISIBLE);
        miv2.setAlpha(0f);
        miv2.animate().alpha(1f).setDuration(2500);
        miv2.setVisibility(View.VISIBLE);
        miv3.setAlpha(0f);
        miv3.animate().alpha(1f).setDuration(3500);
        miv3.setVisibility(View.VISIBLE);
        miv4.setAlpha(0f);
        miv4.animate().alpha(1f).setDuration(4500);
        miv4.setVisibility(View.VISIBLE);
        miv5.setAlpha(0f);
        miv5.animate().alpha(1f).setDuration(5500);
        miv5.setVisibility(View.VISIBLE);
    }    */

