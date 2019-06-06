package com.example.xamo.qrcodeipo;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

/**
 * Created by xamo1 on 11/29/2017.
 */

public class ChronoData {
    private int sec, min, hour, milliseconds;
    private long startTime, timeInMillis, timeSwap, updateTime;
    private Handler handler = new Handler();
    private boolean hours, running, noMilliTextView,dnf, plus2;

    /**
     * Empty contructor.
     */
    public ChronoData(){}

    /**
     * Constructor of ChronoData. (All the params are in the Chrono class)
     * @param sec Value of the seconds.
     * @param min Value of the minutes.
     * @param hour Value of the hours.
     * @param milliseconds Value of the milliseconds.
     * @param startTime Value of the star time.
     * @param timeInMillis Value of the time in millis.
     * @param timeSwap Value of the time swap.
     * @param updateTime Value of the update time.
     * @param hours Value of the hours.
     * @param running Value of the running
     * @param noMilliTextView Value of the noMilliTextView
     */
    public ChronoData(int sec, int min, int hour, int milliseconds, long startTime, long timeInMillis, long timeSwap, long updateTime, boolean hours, boolean running, boolean noMilliTextView) {
        this.sec = sec;
        this.min = min;
        this.hour = hour;
        this.milliseconds=milliseconds;
        this.startTime = startTime;
        this.timeInMillis = timeInMillis;
        this.timeSwap = timeSwap;
        this.updateTime = updateTime;
        this.hours = hours;
        this.running=running;
        this.noMilliTextView=noMilliTextView;
        dnf=false;
        plus2=false;
    }

    public float getTotalSec(){
        float total=0;
        total+=hour*3600;
        total+=min*60;
        total+=sec;
        total+=(float)milliseconds/1000;
        return total;
    }

    /**
     * Method that returns a ChronoData method from a bundle that contains the ChronoData (not empty) constructor.
     * @param bundle Bundle with the data. Generate this bundle on the OnSaveInstanceState method on your class.
     * @return ChronoData with the data of the bundle.
     */
    public ChronoData getChronoDataFromBundle(Bundle bundle){
        ChronoData chronoData= new ChronoData(bundle.getInt("sec",0),bundle.getInt("min",0),bundle.getInt("hour",0),
                bundle.getInt("milliseconds",0),bundle.getLong("startTime"),bundle.getLong("timeInMillis"),
                bundle.getLong("timeSwap"),bundle.getLong("updateTime"),bundle.getBoolean("hours",false),bundle.getBoolean("running"),
                bundle.getBoolean("noMilliTextView"));
        return chronoData;
    }

    /**
     * Return a bundle with the current values.
     * @return Bundle with current values.
     */
    public Bundle getBundleOfData(){
        Bundle bundle= new Bundle();
        bundle.putInt("sec",sec);
        bundle.putInt("min",min);
        bundle.putInt("hour",hour);
        bundle.putInt("milliseconds",milliseconds);
        bundle.putLong("startTime",startTime);
        bundle.putLong("timeInMillis",timeInMillis);
        bundle.putLong("timeSwap",timeSwap);
        bundle.putLong("updateTime",updateTime);
        bundle.putBoolean("hours",hours);
        bundle.putBoolean("running", running);
        bundle.putBoolean("noMilliTextView", noMilliTextView);
        return bundle;
    }
    public void add2Seconds(){
        sec+=2;
        if(sec>=60){
            sec-=60;
            min++;
            if(hours) {
                min -= 60;
                hour++;
            }
        }
        plus2=true;

    }
    public void putDnfSolve(){
        dnf=true;
    }
    public void removeDnfSolve(){
        dnf=false;
    }
    public void decrese2Seconds(){
        sec-=2;
        if(sec<0){
            sec=60+sec;
            min--;
            if(hours){
                min=60+min;
                hour--;
            }
        }
        plus2=false;
    }

    /**
     * Getter for the var plus2.
     * @return plus2
     */
    public boolean getPlus2(){
        return plus2;
    }

    /**
     * Getter for the var dnf.
     * @return dnf
     */
    public boolean getDnf(){
        return dnf;
    }

    /**
     * Getter for the var sec.
     * @return sec
     */
    public int getSec() {
        return sec;
    }

    /**
     * Getter for the var min.
     * @return min
     */
    public int getMin() {
        return min;
    }

    /**
     * Getter for the var hour.
     * @return hour
     */
    public int getHour() {
        return hour;
    }

    /**
     * Getter for the var milliseconds.
     * @return milliseconds
     */
    public int getMilliseconds() {
        return milliseconds;
    }

    /**
     * Getter for the var startTime.
     * @return startTime
     */
    public long getStartTime() {
        return startTime;
    }

    /**
     * Getter for the var timeInMillis.
     * @return timeInMillis
     */
    public long getTimeInMillis() {
        return timeInMillis;
    }

    /**
     * Getter for the var timeSwap.
     * @return timeSwap
     */
    public long getTimeSwap() {
        return timeSwap;
    }

    /**
     * Getter for the var updateTime.
     * @return updateTime
     */
    public long getUpdateTime() {
        return updateTime;
    }

    /**
     * Getter for the var hours.
     * @return hours
     */
    public boolean getHours() {
        return hours;
    }

    /**
     * Getter for the var running.
     * @return running
     */
    public boolean getRunning(){
        return running;
    }

    /**
     * Getter for the var noMilliTextView.
     * @return noMilliTextView
     */
    public boolean getNoMilliTextView(){return noMilliTextView;}


}
