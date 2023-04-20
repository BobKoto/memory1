package com.example.memory1;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.transition.Explode;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;

import static com.example.memory1.MainActivity.actualTimePlayedThisGame;
import static com.example.memory1.MainActivity.gameScore;
import static com.example.memory1.MainActivity.imagesToPress;
import static com.example.memory1.MainActivity.mostRecentGame;
import static com.example.memory1.MainActivity.secondGame;
import static com.example.memory1.MainActivity.sharedPreferences;
import static com.example.memory1.MainActivity.thirdGame;
import static com.example.memory1.MainActivity.fourthGame;

import static com.example.memory1.MainActivity.perfectRounds;
import static com.example.memory1.MainActivity.perfectRoundsUnderTwoSeconds;
import static com.example.memory1.MainActivity.pressesThisGame;
import static com.example.memory1.MainActivity.roundsPerGame;

public class GameStats extends Activity {

       @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.gamestats);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //    getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);  /bombs
            getWindow().setEnterTransition(new Explode());
            displayStats();
        }
        else displayStats();

        FloatingActionButton fab = findViewById(R.id.fab);
        FloatingActionButton resetStatsFab = findViewById(R.id.resetStatsFab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                     /*  if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    //    getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);  /bombs
            //        getWindow().setExitTransition(new Explode());
                  //  Intent intent = new Intent(this, Main2Activity.class);
                    Intent intent = new Intent(getApplicationContext(), Main2Activity.class);
                    startActivity(intent,
                            ActivityOptions.makeSceneTransitionAnimation(Main2Activity.class)).toBundle();
                }
                else {  */
                    Intent intent = new Intent(getApplicationContext(), Main2Activity.class);
                    startActivity(intent);
                    }
        });
        resetStatsFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Context context;
                resetGameStats();
             //   displayStats();
             //   setContentView(R.layout.gamestats);
             //   displayStats();
              //  Intent intent = new Intent(getApplicationContext(),GameStats.class);
              //  startActivity(intent);

                //   Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                //           .setAction("Action", null).show();
            }
        });
    }
    protected void displayStats () {
      //  setContentView(R.layout.gamestats);
        double[] statsTank = new double[7];
        TextView vscoreGame0   = findViewById(R.id.scoreGame0);
        TextView vtimePlayed0  = findViewById(R.id.ATP0);
        TextView vPTG0         = findViewById(R.id.PTG0);
        TextView vPRnds0       = findViewById(R.id.PRnds0);
        TextView vPU2s0        = findViewById(R.id.PU2s0);

        for (int iStats =0; iStats < 4; iStats ++) {
            switch (iStats) {
                case 0:
                    statsTank = Arrays.copyOf(mostRecentGame, mostRecentGame.length);
                    vscoreGame0 = findViewById(R.id.scoreGame0);
                    vtimePlayed0 = findViewById(R.id.ATP0);
                    vPTG0 = findViewById(R.id.PTG0);
                    vPRnds0 = findViewById(R.id.PRnds0);
                    vPU2s0 = findViewById(R.id.PU2s0);
                    break;
                case 1:
                    statsTank = Arrays.copyOf(secondGame, secondGame.length);
                    vscoreGame0 = findViewById(R.id.scoreGame1);
                    vtimePlayed0 = findViewById(R.id.ATP1);
                    vPTG0 = findViewById(R.id.PTG1);
                    vPRnds0 = findViewById(R.id.PRnds1);
                    vPU2s0 = findViewById(R.id.PU2s1);
                    break;
                case 2:
                    statsTank = Arrays.copyOf(thirdGame, thirdGame.length);
                    vscoreGame0 = findViewById(R.id.scoreGame2);
                    vtimePlayed0 = findViewById(R.id.ATP2);
                    vPTG0 = findViewById(R.id.PTG2);
                    vPRnds0 = findViewById(R.id.PRnds2);
                    vPU2s0 = findViewById(R.id.PU2s2);
                    break;
                case 3:
                    statsTank = Arrays.copyOf(fourthGame, fourthGame.length);
                    vscoreGame0 = findViewById(R.id.scoreGame3);
                    vtimePlayed0 = findViewById(R.id.ATP3);
                    vPTG0 = findViewById(R.id.PTG3);
                    vPRnds0 = findViewById(R.id.PRnds3);
                    vPU2s0 = findViewById(R.id.PU2s3);
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + iStats);
            }

            double xscore = statsTank[0];  // score
            double xatp = statsTank[1];  // actual time played
            double xptg = statsTank[2];  // presses this game
            double xpr = statsTank[3];  // perfect rounds
            double xpu2 = statsTank[4];  // perfect under 2 secs
            double ximgs = statsTank[5];  // images to press
            double xrpg = statsTank[6];  // rounds per game

            //Score
            vscoreGame0.setText(String.format("%.0f", xscore));

            //Time Played
            double i = xatp / 1000;
            vtimePlayed0.setText(String.format("%.1f", i) + " Secs.");

            //Presses in Game
            double x = ximgs * xrpg;// * mostRecentGame[2];
            double percent = (x * 100) / xptg;
            vPTG0.setText(String.format("%.0f", xptg) + "/"
                    + String.format("%.0f", x) + ", "
                    + String.format("%.1f", percent) + "%");

            // Perfect Rounds
            percent = (xpr * 100) / xrpg;   //roundsPerGame;
            vPRnds0.setText(String.format("%.0f", xpr) + "/"
                    + String.format("%.0f", xrpg) + ", "
                    + String.format("%.1f", percent) + "%");

            // Perfect Rounds Under 2 Seconds
            percent = (xpu2 * 100) / xrpg;
            vPU2s0.setText(String.format("%.0f", xpu2) + "/"
                    + String.format("%.0f", xrpg) + ", "
                    + String.format("%.1f", percent) + "%");
        }

    }
    public  void resetGameStats() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setIcon(R.drawable.redtriangle);
        alertDialogBuilder.setTitle("MEMORY1 -- Confirm Reset Statistics");
        alertDialogBuilder.setMessage("Are you sure you want to Reset Statistics?");
        alertDialogBuilder.setCancelable(false);
        alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //  Toast.makeText(getApplicationContext(), "Yes... ", Toast.LENGTH_LONG).show();
                for (int i = 0; i < 7; i++) {   //ZERO OUT ALL the Stats arrays
                    mostRecentGame[i] = 0;
                    secondGame[i] = 0;
                    thirdGame[i] = 0;
                    fourthGame[i] = 0;
                 //   updateGameStats(true);
                }
                updateGameStats(true);
                Toast.makeText(getApplicationContext(), "Statistics Reset", Toast.LENGTH_LONG).show();
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
    public  void updateGameStats(boolean forcedReset) {
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

    displayStats();

    }

    @Override
    protected void onStop() {
    super.onStop();
    }
}



  /*   TextView thisGameScore = findViewById(R.id.gameScore);

        TextView vscoreGame1   = findViewById(R.id.scoreGame1);
        TextView vscoreGame2   = findViewById(R.id.scoreGame2);
        TextView vscoreGame3   = findViewById(R.id.scoreGame3);

        TextView vtimePlayed1  = findViewById(R.id.ATP1);
        TextView vtimePlayed2  = findViewById(R.id.ATP2);
        TextView vtimePlayed3  = findViewById(R.id.ATP3);

        TextView vPTG1         = findViewById(R.id.PTG1);
        TextView vPTG2         = findViewById(R.id.PTG2);
        TextView vPTG3         = findViewById(R.id.PTG3);

        TextView vPRnds1       = findViewById(R.id.PRnds1);
        TextView vPRNDS2       = findViewById(R.id.PRnds2);
        TextView vPRnds3       = findViewById(R.id.PRnds3);

        TextView vPU2s1        = findViewById(R.id.PU2s1);
        TextView vPU2s2        = findViewById(R.id.PU2s2);
        TextView vPU2s3        = findViewById(R.id.PU2s3);  */