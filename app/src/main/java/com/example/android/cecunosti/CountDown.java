package com.example.android.cecunosti;

import android.content.Context;

/**
 * Created by Archangel on 4/26/2017.
 */

public class CountDown implements Runnable, AppConstants{

    public static final long MILLIS_TO_MINUTES = 600000;
    public static final long MILLIS_TO_HOURS = 3600000;



    private Context mContext;
    private long mStartTime;
    private boolean mIsRunning;

    public CountDown(Context mContext) {
        this.mContext = mContext;

    }

    public void start(){
        mStartTime = System.currentTimeMillis();
        mIsRunning = true;


    }

    public void stop(){
        mIsRunning = false;
    }

    @Override
    public void run() {

        while(mIsRunning){

            long since = System.currentTimeMillis() - mStartTime;

            int seconds =(int) (since / 1000) % 60;
            int minutes = (int) ((since/ MILLIS_TO_MINUTES) % 60);
            int hours = (int) ((since/ MILLIS_TO_HOURS) % 60);
            int millis = (int) since % 1000;

            ((MainActivity)mContext).updateTimerText(String.format(
                    "%02d:%02d", minutes, seconds
            ));


            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


        }
    }


}
