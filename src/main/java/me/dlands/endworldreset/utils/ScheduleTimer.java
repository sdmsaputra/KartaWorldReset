package me.dlands.endworldreset.utils;

import me.dlands.endworldreset.settings.Config;
import me.dlands.endworldreset.tasks.CountDown;

import java.util.Calendar;
import java.util.Date;
import java.util.Timer;

public class ScheduleTimer {

    private static Timer timer;

    public static void init(){
        if(timer != null){
            timer.cancel();
        }
        timer = new Timer();
        Calendar calendar = (Calendar) Config.getSettings().getNextReset().clone();
        calendar.add(Calendar.SECOND, -10);

        timer.scheduleAtFixedRate(new CountDown(timer), calendar.getTime(), 1000);
    }

    public static String getCDNormal() {

        Date start_date = new Date();
        Date end_date = Config.getSettings().getNextReset().getTime();

        long difference_In_Time = end_date.getTime() - start_date.getTime();
        long difference_In_Seconds = (difference_In_Time / 1000) % 60;
        long difference_In_Minutes = (difference_In_Time / (1000 * 60)) % 60;
        long difference_In_Hours = (difference_In_Time / (1000 * 60 * 60)) % 24;
        long difference_In_Days = (difference_In_Time / (1000 * 60 * 60 * 24)) % 365;

        String period =
                (difference_In_Days>0?difference_In_Days + " day(s), " : "")
                + (difference_In_Hours>0?difference_In_Hours + " hour(s), " : "")
                + (difference_In_Minutes>0? difference_In_Minutes + " minute(s) " : "")
                + difference_In_Seconds + " second(s)";

        return period;
    }

    public static String getCDShort() {

        Date start_date = new Date();
        Date end_date = Config.getSettings().getNextReset().getTime();

        long difference_In_Time = end_date.getTime() - start_date.getTime();
        long difference_In_Seconds = (difference_In_Time / 1000) % 60;
        long difference_In_Minutes = (difference_In_Time / (1000 * 60)) % 60;
        long difference_In_Hours = (difference_In_Time / (1000 * 60 * 60)) % 24;
        long difference_In_Days = (difference_In_Time / (1000 * 60 * 60 * 24)) % 365;

        String period =
                (difference_In_Days>0? difference_In_Days + ":" : "")
                + String.format("%02d", difference_In_Hours) + ":"
                + String.format("%02d", difference_In_Minutes) + ":"
                + String.format("%02d", difference_In_Seconds);

        return period;
    }


}