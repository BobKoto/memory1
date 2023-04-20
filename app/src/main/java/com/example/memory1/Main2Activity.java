package com.example.memory1;

import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.annotation.StringRes;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.transition.Explode;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.memory1.GameStats;

import java.security.SecureRandom;
import java.time.YearMonth;

public class Main2Activity extends MainActivity {
    boolean playEntered = false;
    int imagesPressed = 0;
    int expectedImagePress = 0;
    int correctPress = 25;
    int bonusPoints = 100;
    //int scoreThisRound = 0;
    // boolean resetByMidRoundExit;
    private String nextPress = "012345";  //private ok?
    private static final String CHAR_LIST = "012345";
    private int pressesThisRound;
    public boolean preStart = true;
    long roundStartTime, roundEndTime;
    double roundTimePlayed;
    boolean activeRound = false;
    boolean timerTrippedOn;
    TextView tv3;
    Button statsButton;
    Handler handlerSound1 = new Handler();
    Handler handlerSound2 = new Handler();
    Handler handlerSound3 = new Handler();
    Handler handlerSound4 = new Handler();
    Handler handlerSound5 = new Handler();
    Handler handlerSound6 = new Handler();
    //Runnable runnableSound1 = new Runnable()

   //Handler  handlerSound3, handlerSound4, handlerSound5, handlerSound6;
   Runnable runnableSound1, runnableSound2, runnableSound3, runnableSound4, runnableSound5, runnableSound6;

    //boolean backWasPressed = false;

    public int mBufferSize = AudioTrack.getMinBufferSize(
           22050,       //Ori = 44100
            AudioFormat.CHANNEL_OUT_MONO,
            AudioFormat.ENCODING_PCM_8BIT);
    AudioTrack mAudioTrack = new AudioTrack(
            AudioManager.STREAM_MUSIC,44100,  //Ori = 44100      //22050 works for KitKat
            AudioFormat.CHANNEL_OUT_MONO,
            AudioFormat.ENCODING_PCM_16BIT,
            mBufferSize, AudioTrack.MODE_STREAM);

    //  mAudioTrack.setStereoVolume(AudioTrack.getMaxVolume(), AudioTrack.getMaxVolume());
    //int mAudioType = AudioTrack.CONTENT_TYPE_MUSIC;  */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        tv3 = findViewById(R.id.textView3);
        statsButton = findViewById(R.id.statsButton);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //    getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);  /bombs
            getWindow().setEnterTransition(new Explode());
        }
        Toolbar toolbar = findViewById(R.id.toolbar);
        Snackbar.make(toolbar,"onCreate",Snackbar.LENGTH_LONG).show();
        //ProgressBar progressBar = findViewById(R.id.progressBar);
       // progressBar.getProgressDrawable().setColorFilter(Color.GREEN,null);
       // progressBar.setProgress(0);
      //  displayDelay = 250;
     //   Chronometer timer = (Chronometer) findViewById(R.id.timer);
     }
    @Override
    protected void onResume() {
        super.onResume();

        setContentView(R.layout.activity_main2);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        if (ab !=null) ab.setDisplayHomeAsUpEnabled(true);
        TextView currentScore = findViewById(R.id.score);
        final Button statsButton = findViewById(R.id.statsButton);
        tv3 = findViewById(R.id.textView3);
        TextView roundTimer = findViewById(R.id.roundTimer);
        //Snackbar.make(toolbar,"onResume",Snackbar.LENGTH_LONG).show();
        currentScore.setText(String.valueOf(gameScore));
        backWasPressed = false;
      //  Handler handlerSound = new Handler();
      //  Chronometer timer = (Chronometer) findViewById(R.id.timer);
        //if (testTimer) Toast.makeText(getApplicationContext(),"testTimer is ON",Toast.LENGTH_LONG).show();
        //if (!testTimer) Toast.makeText(getApplicationContext(),"testTimer is OFF",Toast.LENGTH_LONG).show();

        final ImageView iv0 = findViewById(R.id.imageView0);
        final ImageView iv1 = findViewById(R.id.imageView1);
        final ImageView iv2 = findViewById(R.id.imageView2);
        final ImageView iv3 = findViewById(R.id.imageView3);
        final ImageView iv4 = findViewById(R.id.imageView4);
        final ImageView iv5 = findViewById(R.id.imageView5);
        //USE THE SAME BUTTON for START and GO AGAIN!
        final Button againButton = findViewById(R.id.againButton);
        againButton.setText(nextRoundButton);
        againButton.setVisibility(View.VISIBLE);
        // makeImagesInvisibleAfterDelay(500);

        storedOrderArray[0]= im0 = iv0.getId();  // Default setup in image order LTR
        storedOrderArray[1]= im1 = iv1.getId();
        storedOrderArray[2]= im2 = iv2.getId();
        storedOrderArray[3]= im3 = iv3.getId();
        storedOrderArray[4]= im4 = iv4.getId();
        storedOrderArray[5]= im5 = iv5.getId();
        //public int length = 6;
        for (int i =0; i < length; i ++)    //Load order/sequence of images
            imageOrderArray[i]= storedOrderArray[Integer.valueOf(nextPress.substring(i,i+1))] ;

        iv0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View iv) {processImagePress(im0,SOUND_IMAGE0,expectedImagePress);}});
        iv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View iv) {processImagePress(im1,SOUND_IMAGE1,expectedImagePress);}});
        iv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View iv) {processImagePress(im2,SOUND_IMAGE2,expectedImagePress);}});
        iv3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View iv) {processImagePress(im3,SOUND_IMAGE3,expectedImagePress);}});
        iv4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View iv) {processImagePress(im4,SOUND_IMAGE4,expectedImagePress);}});
        iv5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View iv) {processImagePress(im5, SOUND_IMAGE5, expectedImagePress);}});
        if (resetByMidRoundExit) {
            tv3.setText("Round Reset! by BackButton/Exit");
            tv3.setVisibility(View.VISIBLE);
            currentScore.setText(String.valueOf(gameScore));
            resetByMidRoundExit = false;
        }

        againButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activeRound = true;
                timerTrippedOn = false;
                TextView roundTimer = findViewById(R.id.roundTimer);
                roundTimer.setText("0");
                if (gameOver) {
                    actualTimePlayedThisGame = 0;
                    pressesThisGame = 0;
                    gameScore = 0;
                    perfectRounds = 0;
                    perfectRoundsUnderTwoSeconds = 0;
                    gameOver = false;
                 //   timerTrippedOn = false;
                    TextView currentScore = findViewById(R.id.score);
                    currentScore.setText(String.valueOf(gameScore));
                }
                if (preStart) {
                makeImagesInvisible();
                preStart = false;
                    tv3.setText("");
                    statsButton.setVisibility(View.INVISIBLE);
                    TextView currentScore = findViewById(R.id.score);
                    currentScore.setText(String.valueOf(gameScore));
                    againButton.setVisibility(View.INVISIBLE);
                setupRound();
                } else {
                 //   timerTrippedOn = false;
                    scoreThisRound = 0;
                    nextPress = scrambleImages(length);
                    againButton.setVisibility(View.INVISIBLE);
                    TextView tv3 = (TextView) findViewById(R.id.textView3);
                    statsButton.setVisibility(View.INVISIBLE);

                   // ProgressBar progressBar = findViewById(R.id.progressBar);
                    tv3.setText("");
                   // progressBar.setProgress(0);
                    makeImagesInvisible();
                    setupSoundHints();
                    setupImageDisplayHints();
            }
        }
        });
        statsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (gameOver) {   //Button Press for Game Statistics
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        //    getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);  /bombs
                        getWindow().setEnterTransition(new Explode());
                    }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                //    getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);  /bombs
                    getWindow().setExitTransition(new Explode());
                    Intent intent = new Intent(v.getContext(), GameStats.class);
                  //  startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
                    startActivity(intent);
                }
                else {
                    Intent intent = new Intent(v.getContext(), GameStats.class);
                    startActivity(intent);
                     }
                }
            }
        });
    }
       private void playSound (double frequency, int duration) {
        playEntered = true;
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
      //Sine wave
        double[] mSound = new double[4410];
        short[] mBuffer = new short[duration];
        for (int i=0; i< mSound.length; i++) {
            mSound[i]=Math.sin((2.0*Math.PI * i/(44100/frequency)));
            mBuffer[i]= (short) (mSound[i]*Short.MAX_VALUE);
        }
        //  mAudioTrack.setStereoVolume(AudioTrack.getMaxVolume(), AudioTrack.getMaxVolume());
        if (AudioTrack.STATE_INITIALIZED ==1) {
            try {   mAudioTrack.play();
            } catch (IllegalStateException e) { Log.e("mAt","Play uniNitted?"); }
        }
        mAudioTrack.write(mBuffer,0, mSound.length);  //makes the sound
     }
     private void processImagePress(int iv, double sio, int expected)  {
        // imagesPressed += 1;
         //pressesThisRound += 1; // moved under if (activeRound)
       /*  if (pressesThisRound == 1 && !timerTrippedOn) {    //Just start timer
             Chronometer timer = (Chronometer) findViewById(R.id.timer);
             timer.setBase(SystemClock.elapsedRealtime());  //Reset to zero
             timer.start();
             timerTrippedOn = true;
             roundStartTime = SystemClock.elapsedRealtime();
         }      */  //This block was completely WRONG -- could never get under 2 secs!
         if (activeRound ) {
             pressesThisRound += 1;
             if (iv != imageOrderArray[expected])     //WRONG BUTTON pressed!
             {
                 processWrongPress();
             } else {
                 processCorrectPress(sio, 44100, correctPress);
             }
         }
     }
     private void processWrongPress() {
       gameScore = gameScore - 25;
       scoreThisRound = scoreThisRound - 25;
       playSound(323.251, 44100);
       TextView currentScore = findViewById(R.id.score);
       currentScore.setText(String.valueOf(gameScore));
       TextView tv3 = (TextView) findViewById(R.id.textView3);
       tv3.setText("Keep going ...Guess!!");
    }
    private void  processCorrectPress (double freq, int duration, int addScore) {
        gameScore = gameScore + addScore;
        scoreThisRound = scoreThisRound + addScore;
        TextView currentScore = findViewById(R.id.score);
        playSound(freq, duration);
        currentScore.setText(String.valueOf(gameScore));
        TextView tv3 = (TextView) findViewById(R.id.textView3);
        //pressesThisRound += 1;   //set in processImagePress
        expectedImagePress += 1;
      //  ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);
      //  progressBar.setProgress(expectedImagePress*100);
        tv3.setText(String.valueOf(expectedImagePress) + " Right!");
        if (expectedImagePress == imagesToPress) {
           //Round is Complete == all expected Images Pressed
            activeRound = false;
            roundsThisGame += 1;
            roundEndTime = SystemClock.elapsedRealtime();             //new in test
            roundTimePlayed = (roundEndTime - roundStartTime) + (imagesToPress * displayDelay);
            actualTimePlayedThisGame += roundTimePlayed;
            pressesThisGame += pressesThisRound;
            TextView roundTimer = findViewById(R.id.roundTimer);
            double i = roundTimePlayed / 1000;
            roundTimer.setText(String.format("%.2f", i) + " Secs");
         //   Toast.makeText(getApplicationContext(),String.valueOf(roundTimePlayed),Toast.LENGTH_LONG).show();
        //    if (gameOver) actualTimePlayedThisGame = 0;
            if ( pressesThisRound == imagesToPress) {
                //The round was pressed PERFECTLY in order
                perfectRounds += 1;
                tv3.setText("Perfect!! " + String.valueOf(pressesThisRound)  + " of " + String.valueOf(expectedImagePress) );
                if (roundTimePlayed > 1999 + imagesToPress * displayDelay) {
                    Toolbar toolbar = findViewById(R.id.toolbar);
                    Snackbar.make(toolbar,"PERFECT!!! keep trying for SPEEDY!",
                            Snackbar.LENGTH_LONG).show();
                    audioCongratulations(false);
                }
                if (roundTimePlayed < 2000 + imagesToPress * displayDelay){  //in order AND under 2 secs
                    gameScore += bonusPoints; //BONUS Points
                    currentScore.setText(String.valueOf(gameScore));
                    tv3.setText("Perfect!! " + "+ 100 BONUS!!" );
                    audioCongratulations(true);
                    Toolbar toolbar = findViewById(R.id.toolbar);
                    Snackbar.make(toolbar,"PERFECT and SPEEDY Round!  100 POINT BONUS!!!",
                            Snackbar.LENGTH_LONG).show();
                    perfectRoundsUnderTwoSeconds += 1;
                 //   displayCongratulations(true,true);  Moved to Game Over condition
                }
                pressesThisRound = 0;
            }
         else {
             // Round done BUT not perfect
               tv3.setText("Done! " + String.valueOf(expectedImagePress) + " of " + String.valueOf(pressesThisRound));

         }
        }  //End round is over -- checked for perfection and under 2 seconds
        if (expectedImagePress >= imagesToPress)  {    //All Images Done  == Round is over
            expectedImagePress = 0; //will become imagesToPress???????  WTF?
            Button againButton = (Button) findViewById(R.id.againButton);
            if (gameOver = roundsThisGame == roundsPerGame) {    //GAME OVER!

                updateGameStats(false);
                againButton.setText(nextRoundButton = "Start New Game");
                //tv3.setText("Tap HERE for GAME STATS");
                Button statsButton = findViewById(R.id.statsButton);
                statsButton.setVisibility(View.VISIBLE);
                if (gameScore == 1250) {
                    displayCongratulations(true, true);
                    audioCongratulations(true);

                }
                //againButton.setBackgroundColor(getResources().getColor(R.color.colorNewGameButton));
                roundsThisGame = 0;

            } else {
                nextRoundButton = "Go Again Round " + String.valueOf(roundsThisGame+1) + " of " + String.valueOf(roundsPerGame);
                againButton.setText(nextRoundButton);
            }
            againButton.setVisibility(View.VISIBLE);
            pressesThisRound = 0;
        }
    }
    private void setupRound() {
        nextPress = scrambleImages(length); //Random image order generate
        setupSoundHints();
        setupImageDisplayHints();   //Also starts timer
        }
    private String scrambleImages (int length) {
        if (testTimer) {
            Toast.makeText(getApplicationContext(),"Practioce Mode ON",Toast.LENGTH_LONG).show();
            return "012345";
        }
        else {
            SecureRandom secureRandom = new SecureRandom();
            StringBuilder randStr = new StringBuilder(length);
              for (int i = 0; i < length; i++)
                randStr.append(CHAR_LIST.charAt(secureRandom.nextInt(CHAR_LIST.length())));
          //  Toast.makeText(getApplicationContext(), "npress = " + randStr.toString(), Toast.LENGTH_LONG).show();
            return randStr.toString();
        }
    }
    public void setupImageDisplayHints(){
        for (int i =0; i < length; i ++)    //Load order/sequence of images
            imageOrderArray[i]= storedOrderArray[Integer.valueOf(nextPress.substring(i,i+1))] ;
        //DO DISPLAY HINTS here----
        ImageView iv0 = findViewById(R.id.imageView0);
        ImageView iv1 = findViewById(R.id.imageView1);
        ImageView iv2 = findViewById(R.id.imageView2);
        ImageView iv3 = findViewById(R.id.imageView3);
        ImageView iv4 = findViewById(R.id.imageView4);
        ImageView iv5 = findViewById(R.id.imageView5);

        for (int i =0; i < length; i ++) {
            switch (Integer.valueOf(nextPress.substring(i, i + 1))) {
                case 0: displayImageAfterDelay((i+1) * displayDelay,iv0); break;
                case 1: displayImageAfterDelay((i+1) * displayDelay,iv1); break;
                case 2: displayImageAfterDelay((i+1) * displayDelay,iv2); break;
                case 3: displayImageAfterDelay((i+1) * displayDelay,iv3); break;
                case 4: displayImageAfterDelay((i+1) * displayDelay,iv4); break;
                case 5: displayImageAfterDelay((i+1) * displayDelay,iv5); break;
                };
            }
        startTimerAfterDelay(length*displayDelay);  //before new scheme   THINK about this
        }                                           //=6x500, 3secs OR =6x250, 1.5secs
    public void setupSoundHints(){
        for (int i =0; i < length; i ++)    //Load order/sequence of sounds
            switch (Integer.valueOf(nextPress.substring(i, i + 1))) {
                case 0: playSoundAfterDelay(SOUND_IMAGE0, (i+1)*displayDelay,i); break;
                case 1: playSoundAfterDelay(SOUND_IMAGE1, (i+1)*displayDelay,i); break;
                case 2: playSoundAfterDelay(SOUND_IMAGE2, (i+1)*displayDelay,i); break;
                case 3: playSoundAfterDelay(SOUND_IMAGE3, (i+1)*displayDelay,i); break;
                case 4: playSoundAfterDelay(SOUND_IMAGE4, (i+1)*displayDelay,i); break;
                case 5: playSoundAfterDelay(SOUND_IMAGE5, (i+1)*displayDelay,i); break;
            };
        }
     public void playSoundAfterDelay (final double sound, final int delay,final int seqNumber) {
        //Handler handler = new Handler();
         //Toast.makeText(getApplicationContext(),String.valueOf(seqNumber),Toast.LENGTH_LONG).show();
         //runnableSound[seqNumber] = new Runnable() {
         switch (seqNumber) {
             case 0:runnableSound1 = new Runnable() {
                 @Override
                 public void run() { playSound(sound,44100);
                 }
             };
             if (!backWasPressed) handlerSound1.postDelayed(runnableSound1, delay);break;

             case 1:runnableSound2 = new Runnable() {
                 @Override
                 public void run() {
                     playSound(sound,44100);
                 }
             };
             if (!backWasPressed) handlerSound2.postDelayed(runnableSound2, delay);
             break;

             case 2:runnableSound3 = new Runnable() {
                 @Override
                 public void run() {
                     playSound(sound,44100);
                 }
             };
                 if (!backWasPressed) handlerSound3.postDelayed(runnableSound3, delay);
                 break;

             case 3:runnableSound4 = new Runnable() {
                 @Override
                 public void run() {
                     playSound(sound,44100);
                 }
             };
                 if (!backWasPressed) handlerSound4.postDelayed(runnableSound4, delay);
                 break;

             case 4:runnableSound5 = new Runnable() {
                 @Override
                 public void run() {
                     playSound(sound,44100);
                 }
             };
                 if (!backWasPressed) handlerSound5.postDelayed(runnableSound5, delay);
                 break;

             case 5:runnableSound6 = new Runnable() {
                 @Override
                 public void run() {
                     playSound(sound,44100);
                 }
             };
                 if (!backWasPressed) handlerSound6.postDelayed(runnableSound6, delay);
                 break;
         }
    }
    public void startTimerAfterDelay (int delay) {
        Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                if (!timerTrippedOn) {
                    timerTrippedOn = true;
                    roundStartTime = SystemClock.elapsedRealtime();
                //    Toast.makeText(getApplicationContext(), "STAD " + String.valueOf(roundStartTime), Toast.LENGTH_LONG).show();
                }
            }
        };
         if (!backWasPressed)  handler.postDelayed(runnable, delay);
    }
    public void makeImagesInvisible()  {
        final ImageView iv0 = findViewById(R.id.imageView0);
        final ImageView iv1 = findViewById(R.id.imageView1);
        final ImageView iv2 = findViewById(R.id.imageView2);
        final ImageView iv3 = findViewById(R.id.imageView3);
        final ImageView iv4 = findViewById(R.id.imageView4);
        final ImageView iv5 = findViewById(R.id.imageView5);
        iv0.setVisibility(View.INVISIBLE);
        iv1.setVisibility(View.INVISIBLE);
        iv2.setVisibility(View.INVISIBLE);
        iv3.setVisibility(View.INVISIBLE);
        iv4.setVisibility(View.INVISIBLE);
        iv5.setVisibility(View.INVISIBLE);
    }
    public void displayCongratulations(boolean perfect, boolean speedy) {
        //audioCongratulations();
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setIcon(R.drawable.whitestar);
        alertDialogBuilder.setTitle("MEMORY1 -- Congratulations!");
        alertDialogBuilder.setMessage("You played a PERFECT AND SPEEDY Game!!!");
        alertDialogBuilder.setCancelable(false);
        alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();

    }
        public void audioCongratulations(boolean playBoth) {

            playSound(SOUND_IMAGE5, 44100);
            playSound(SOUND_IMAGE4, 44100);
            playSound(SOUND_IMAGE3, 44100);
            playSound(SOUND_IMAGE2, 44100);
            playSound(SOUND_IMAGE1, 44100);
            playSound(SOUND_IMAGE0, 44100);

           if (playBoth) {
               playSound(SOUND_IMAGE0, 44100);
               playSound(SOUND_IMAGE1, 44100);
               playSound(SOUND_IMAGE2, 44100);
               playSound(SOUND_IMAGE3, 44100);
               playSound(SOUND_IMAGE4, 44100);
               playSound(SOUND_IMAGE5, 44100);
           }
        }


     /*   alertDialogBuilder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
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
        });  */

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        backWasPressed = true;
        handlerSound1.removeCallbacks(runnableSound1);
        handlerSound2.removeCallbacks(runnableSound2);
        handlerSound3.removeCallbacks(runnableSound3);
        handlerSound4.removeCallbacks(runnableSound4);
        handlerSound5.removeCallbacks(runnableSound5);
        handlerSound6.removeCallbacks(runnableSound6);
    }
    @Override
    protected void onStop() {    //was onPause
        super.onStop();
      //  Chronometer timer = findViewById(R.id.timer);
      //  timer.stop();
        if (activeRound) {
            if (roundsThisGame == 0) gameScore = 0;
            gameScore = gameScore - scoreThisRound;
            if (roundsThisGame == 0) gameScore = 0; // Some sort of Kludge
            scoreThisRound = 0;
            pressesThisRound = 0;
            activeRound = false;
            resetByMidRoundExit = true;
            }
       // Toast.makeText(getApplicationContext(),"onStop BACK pressed...",Toast.LENGTH_LONG).show();
        Button againButton = (Button) findViewById(R.id.againButton);
        againButton.setText(nextRoundButton);

    }

}
      /*   public void displayImageAfterDelay (final int delay, final ImageView iv) { //Moved to MAIN
        Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                iv.setAlpha(0f);
                iv.animate().alpha(1f).setDuration(500);
                iv.setVisibility(View.VISIBLE);
            }
        };
        handler.postDelayed(runnable, delay);
    }  */

 /*  public void makeImagesInvisibleAfterDelay(int delay) {   //UNUSED
        Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                makeImagesInvisible();
            }
        };
        handler.postDelayed(runnable, delay);
    }     */



//Newer AudioTrack setup w/backward compatibility issues
      /*  AudioTrack mAudioTrack = new Builder()
                .setAudioAttributes(new AudioAttributes.Builder()
                        .setUsage(AudioAttributes.USAGE_GAME)
                        .setContentType(AudioAttributes.CONTENT_TYPE_UNKNOWN)
                        .build())
                .setAudioFormat(new AudioFormat.Builder()
                        .setEncoding(AudioFormat.ENCODING_PCM_16BIT)
                        .setSampleRate(44100)
                        .setChannelMask(AudioFormat.CHANNEL_OUT_MONO)
                        .build())
                .setBufferSizeInBytes(mBufferSize)
                .build();     */
   // THREAD CODE PUT ON HOLD - SUPERSEDED BY <MethodNames>AfterDelay() Methods
     /*public void doADelay (final int delay, Handler handler) {
      //  final Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
             long endTime = System.currentTimeMillis() + delay;
                while (System.currentTimeMillis() < endTime) {
                    synchronized (this) {
                        try {
                            wait(endTime = System.currentTimeMillis());
                        } catch (Exception e) {
                       }
                   }
              //     handler.sendEmptyMessage(0);
                    Toast.makeText(getApplicationContext(),"send msg",Toast.LENGTH_SHORT).show();
                }
            }
        };
         Thread mythread = new Thread(runnable);
              mythread.start();
    }

     public void handleMessage (Message msg){
         Bundle bundle = msg.getData();
         String string = bundle.getString("myKey");
     }  */

     //  OLD ImagePress
       /* imagesPressed = imagesPressed + 1;
                if (activeRound ) {
                 //   imagesPressed = imagesPressed + 1;
                    if (im5 != imageOrderArray[expectedImagePress])     //WRONG BUTTON pressed!
                    {
                        processWrongPress();
                    } else {
                        processCorrectPress(SOUND_IMAGE5, 44100, correctPress);
                    }
                }  */