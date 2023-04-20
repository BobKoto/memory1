package com.example.memory1;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.RectF;
import android.icu.util.IslamicCalendar;
import android.opengl.Matrix;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.EventLog;
import android.util.Log;
import android.util.TypedValue;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import static com.example.memory1.MainActivity.gameScore;

public class HowToPlay extends MainActivity {  //  AppCompatActivity {
    private ScaleGestureDetector scaleGestureDetector;
    private GestureDetector myGestureDetector;
    TextView HowToPlay;
    View howToViewX;

    private float mLastTouchX;
    private float mLastTouchY;
    private float mPosX;
    private float mPosY;
    private float scaleFactor, scaleProduct;

    //   private RectF mCurrentViewport =
    //           new RectF(AXIS_X_MIN, AXIS_Y_MIN, AXIS_X_MAX, AXIS_Y_MAX);

    // ViewGroup vg = findViewById(R.id.howToView);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    protected void onResume() {
        super.onResume();
        setContentView(R.layout.activity_how_to_play);
        HowToPlay = findViewById(R.id.HowToPlayText1);
        howToViewX = findViewById(R.id.howToView);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        scaleGestureDetector = new ScaleGestureDetector(this, new ScaleListener());
        myGestureDetector = new GestureDetector(this, new MyGestureListener());
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Main2Activity.class);
                startActivity(intent);
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        float gx = howToViewX.getX();
        float gy = howToViewX.getY();
    //    Toast.makeText(getApplicationContext(), "x/y: " + String.valueOf(gx) + "/" + String.valueOf(gy), Toast.LENGTH_LONG).show();
        // howToViewX.getParent().requestDisallowInterceptTouchEvent(false);  who knows
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {             //Was dispatchTouchEvent... works with scroll view
        //DISABLED BY Scrollview
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Main2Activity.class);
                startActivity(intent);
            }
        });
        scaleGestureDetector.onTouchEvent(ev);
        myGestureDetector.onTouchEvent(ev);    //BOTH NEEDED?
        final int action = ev.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN: {
                final float x = ev.getX();
                final float y = ev.getY();

                //   Toast.makeText(getApplicationContext(),"action down",Toast.LENGTH_SHORT).show();
                // Remember where we started
                mLastTouchX = x;
                mLastTouchY = y;
                // howToViewX.setX(x/2);
                // howToViewX.setY(y/2);
                break;
            }
            case MotionEvent.ACTION_MOVE: {
                float x = ev.getX();   //was FINAL
                float y = ev.getY();   //was FINAL

                //    Toast.makeText(getApplicationContext(),"evX/Y: "
                //                    + String.valueOf(x) + "/" + String.valueOf(y),Toast.LENGTH_LONG).show();
                //   Toast.makeText(getApplicationContext(),"action move",Toast.LENGTH_LONG).show();
                //Calciulate distance moved
                float dx = x - mLastTouchX;   //was FINAL
                float dy = y - mLastTouchY;   //was FINAL

                // Move object
                mPosX += dx;
                mPosY += dy;
                //  howToViewX.setFillViewport(true);
                //  howToViewX.setOverScrollMode(View.OVER_SCROLL_ALWAYS);


                float gw = howToViewX.getWidth();
                float gh = howToViewX.getHeight();
                howToViewX.setX(mPosX);   //SET LIMITS HERE?????????????
                howToViewX.setY(mPosY);

                Toast.makeText(getApplicationContext(), "gw/gh" + String.valueOf(gw) + "/" + String.valueOf(gh)
                        , Toast.LENGTH_LONG).show();
         /*      Toast.makeText(getApplicationContext(),"SCALEFACTOR/MPOSX/MPOSY= "
                        + String.valueOf(scaleFactor) + " / " // +String.valueOf(scaleProduct) + " / "
                                + String.valueOf(mPosX)
                        + " / " + String.valueOf(mPosY)
                        ,Toast.LENGTH_LONG).show();     */
                //   howToViewX.setX(Math.max(1.0f, Math.min(mPosX + 0.01f, 700.0f)));  //
                //   howToViewX.setY(Math.max(1.0f, Math.min(mPosY + 0.01f, 1000.0f)));
                // Remember this touch position for the next move event
                mLastTouchX = x;
                mLastTouchY = y;

                // Invalidate to request a redraw
                //invalidate();
                float gx = howToViewX.getX();
                float gy = howToViewX.getY();
                // Toast.makeText(getApplicationContext(),"mPosX/Y: "
                //         + String.valueOf(mPosX) + "/" + String.valueOf(mPosY),Toast.LENGTH_LONG).show();
                // howToViewX.setX( mPosX+mLastTouchX/10);
                // howToViewX.setY( mPosY+mLastTouchY/10);
                break;
            }
        }

        return true;
    }

    private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
        // private static final String DEBUG_TAG = "Gestures";
        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            float sizeX = howToViewX.getScaleX();
            float sizeY = howToViewX.getScaleY();
            //  Toast.makeText(getApplicationContext(),"X/Y = "+ String.valueOf(sizeX) +"/" + String.valueOf(sizeY),Toast.LENGTH_LONG).show();
            float size = howToViewX.getScaleX();  //GSX =1 --made pinch zoom work for entire View == now SCROLL???
            float factor = detector.getScaleFactor();

            // Toast.makeText(getApplicationContext(),"size = "+ String.valueOf(size) +"factor = " + String.valueOf(factor),Toast.LENGTH_LONG).show();
            float product = size * factor;  //PRODUCT = 1
            scaleFactor = factor;
            scaleProduct = product;
            // Toast.makeText(getApplicationContext(),"prod = "+ String.valueOf(product) ,Toast.LENGTH_LONG).show();
            howToViewX.setScaleX(Math.max(1.0f, Math.min(product + 0.01f, 3.0f)));  //Works! enforces Max/Min!
            howToViewX.setScaleY(Math.max(1.0f, Math.min(product + 0.01f, 3.0f)));
            size = HowToPlay.getTextSize();
            return true;
        }
    }

    private class MyGestureListener extends GestureDetector.SimpleOnGestureListener {
        private static final String DEBUG_TAG = "Gestures";
        float AXIS_X_MIN = 1;
        float AXIS_X_MAX = howToViewX.getWidth();
        float AXIS_Y_MIN = 1;
        float AXIS_Y_MAX = howToViewX.getHeight();
        //    private RectF mCurrentViewport =
        //            new RectF(AXIS_X_MIN, AXIS_Y_MIN, AXIS_X_MAX, AXIS_Y_MAX);
        /*    @Override
            public boolean onDown(MotionEvent event) {
             //   Toast.makeText(getApplicationContext(),"ondown",Toast.LENGTH_LONG).show();  //YES IT DOES onDown!
                Log.d(DEBUG_TAG,"onDown: " + event.toString());

                return true;
            }
             @Override
            public boolean onScroll(MotionEvent event1, MotionEvent event2, float distanceX, float distanceY) {
                Toast.makeText(getApplicationContext(),"distanceX/Y: " + String.valueOf(distanceX)
                        + "/ " + String.valueOf(distanceY),Toast.LENGTH_LONG).show();

                Log.d(DEBUG_TAG, "onScroll: " + event1.toString() + event2.toString());
                float vOffsetX = distanceX * howToViewX.getWidth()
                        / howToViewX.getWidth();
                float vOffsety = distanceY * howToViewX.getHeight()
                        / howToViewX.getHeight();
                setViewBottomLeft (
                        howToViewX.getLeft() + vOffsetX,
                        howToViewX.getBottom() + vOffsety);
                return true;
            }
            private void setViewBottomLeft(float x, float y) {
                float curWidth = howToViewX.getWidth();
                float curHeight = howToViewX.getHeight();
                x = Math.max(AXIS_X_MIN,Math.min(x, AXIS_X_MAX - curWidth));
                y= Math.max(AXIS_Y_MIN + curHeight, Math.min(y, AXIS_Y_MAX));
             //   int ix = Math.round(x); int iy = Math.round(y);
             //   howToViewX.scrollTo(ix,iy);
             //   howToViewX.setScrollY(iy);

             //   howToViewX.set(x,y - curHeight, x + curWidth, y);  ORIGINAL meant for RectF
             //   Toast.makeText(getApplicationContext(),"setView",Toast.LENGTH_LONG).show();
                ViewCompat.postInvalidateOnAnimation(howToViewX);
            } */
    }

 /*   @Override
    public boolean onCreateOptionsMenu(Menu menu) {
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
          //resetGameStats();
            Toast.makeText(this, "Stats unavailable from here...", Toast.LENGTH_LONG).show();
        }
        if (sid.equals("Info + How to Play")) {
            //Toast.makeText(this, "Info + How to Play...", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(getApplicationContext(), HowToPlay.class);
            startActivity(intent);
        }
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }      */
}
 /*   public class MyViewGroup  extends ViewConfiguration {
        public  int mTouchSlop;
        public  boolean mIsScrolling;
        View howToViewX = (View) findViewById(R.id.howToView);
        ViewConfiguration vc = ViewConfiguration.get(howToViewX.getContext());
        //MotionEvent ev;

        mTouchSlop = ViewConfiguration.get().getScaledTouchSlop();
        boolean b1;
        int x1, x2, x3, x4;
       // @Override boolean onLayout    (b1,x1, x2, x3, x4) {super.onLayout();}
        @Override public boolean onInterceptTouchEvent(MotionEvent ev) {
            super.onInterceptTouchEvent(ev);
            final int action = MotionEventCompat.getActionMasked(ev);

            if (action == MotionEvent.ACTION_CANCEL || action == MotionEvent.ACTION_UP)  {
                mIsScrolling = false;
                return false;
            }
            switch (action) {
                case MotionEvent.ACTION_MOVE: {
                    if (mIsScrolling) {
                        return true;
                    }
                    final int xDiff = calculateDistanceX(ev);
                    if (xDiff > mTouchSlop)  {
                        mIsScrolling = true;
                        return true;
                    }
                    break;
                }
            }
            return false;
        }
        @Override
        public boolean onTouchEvent( MotionEvent.ev) {}

    }   */



