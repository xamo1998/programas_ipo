package com.example.xamo.qrcodeipo;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.app.AppCompatDelegate;
import android.widget.TextView;

/**
 * Created by xamo1 on 11/28/2017.
 */

public class Chrono{
    private Handler handler = new Handler();
    private long startTime=0L, timeInMillis=0L, timeSwap=0L, updateTime=0L;
    private TextView timer, mili;
    private boolean hours,running,noMilliTextView;
    private int secs,min,hour,milliseconds;

    /**
     * Constructor of the Chrono class.
     * @param timer TextView with the timer. (Hh:Mm:Ss)
     * @param milli TextView with the milliseconds. (.Mmm)
     * @param hours Boolean that tell if the user want hours or not.
     */
    public Chrono(TextView timer, TextView milli, boolean hours){
        this.timer=timer;
        this.mili=milli;
        noMilliTextView=false;
        this.hours=hours;
    }

    /**
     * Constructor of the Chrono class.
     * @param timer TextView with the timer. (Hh:Mm:Ss:Mmm)
     * @param hours Boolean that tell if the user want hours or not.
     */
    public Chrono(TextView timer, boolean hours){
        this.timer=timer;
        noMilliTextView=true;
        this.hours=hours;
    }


    Runnable updateTimerThread = new Runnable() {
        @Override
        public void run() {
            timeInMillis= SystemClock.uptimeMillis()-startTime;
            updateTime=timeSwap+timeInMillis;
            secs=(int) (updateTime/1000);
            min=secs/60;
            secs%=60;
            milliseconds=(int)(updateTime%1000);
            hour=min/60;

            if(noMilliTextView){
                if(hours)
                    timer.setText(String.format("%02d:",hour)+String.format("%02d:",min)+String.format("%02d.",secs)+String.format("%03d",milliseconds));
                else timer.setText(String.format("%02d:",min)+String.format("%02d.",secs)+String.format("%03d",milliseconds));
            }else {
                mili.setText(String.format(".%3d", milliseconds));
                if (hours)
                    timer.setText(String.format("%02d:", hour) + String.format("%02d:", min) + String.format("%02d", secs));
                else timer.setText(String.format("%02d:", min) + String.format("%02d", secs));
            }
            handler.postDelayed(this,0);
        }};

    /**
     * Method that returns a ChronoData instance in order of saving the values.
     * @return ChronoData instance with the values of the current state.
     */
    public ChronoData getCurrent(){
        return new ChronoData(secs,min,hour,milliseconds,startTime,timeInMillis,timeSwap,updateTime,hours,running, noMilliTextView);
    }

    /**
     * Method that set Data from a ChronoData instance.
     * @param data Data of the Chronometer.
     */
    public void setCurrent(ChronoData data){
        secs=data.getSec();
        min=data.getMin();
        hour=data.getHour();
        milliseconds=data.getMilliseconds();
        startTime=data.getStartTime();
        timeInMillis=data.getTimeInMillis();
        timeSwap=data.getTimeSwap();
        updateTime=data.getUpdateTime();
        hours=data.getHours();
        running=data.getRunning();
    }

    /**
     * Methos that tell us if the Chronometer is running or not.
     * @return boolean with the value of running.
     */
    public boolean isRunning(){
        if(running)
            return true;
        else
            return false;
    }

    /**
     * Start the Chrono.
     */
    public void startChrono(){
        running=true;
        startTime= SystemClock.uptimeMillis();

        handler.postDelayed(updateTimerThread,0);
    }

    /**
     * Method that resume the chronometer after saving the data because of changings the screen mode for example.
     */
    public void resumeChrono(){
        handler.postDelayed(updateTimerThread,0);
    }

    /**
     * Method that stops the Chronometer.
     */
    public void stopChrono(){
        running=false;
        timeSwap=timeInMillis;
        handler.removeCallbacks(updateTimerThread);
    }

    /**
     * Method that update the values of the Chronometer
     */
    public void update(){
        mili.setText(String.format(".%3d",milliseconds));
        if(hours)
            timer.setText(String.format("%02d:",hour)+String.format("%02d:",min)+String.format("%02d",secs));
        else timer.setText(String.format("%02d:",min)+String.format("%02d",secs));
    }

    /**
     * Method that restore the Chronometer and put it at 00.00.00.000
     */
    public void restore(){
        running=false;
        timeSwap=timeInMillis=startTime=updateTime=0L;
        secs=min=hour=milliseconds=0;
        handler.removeCallbacks(updateTimerThread);
        if(mili!=null)
            mili.setText(".000");
        if(hours)
            timer.setText(String.format("%02d:",hour)+String.format("%02d:",min)+String.format("%02d",secs));
        else timer.setText(String.format("%02d:",min)+String.format("%02d",secs));
    }


    public void setHours(boolean hours){
        this.hours=hours;
    }



}
