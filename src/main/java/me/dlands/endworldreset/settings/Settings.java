package me.dlands.endworldreset.settings;

import me.dlands.endworldreset.Endworldreset;
import org.bukkit.configuration.file.FileConfiguration;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.logging.Level;

public class Settings {

    private FileConfiguration config;
    private int many;
    private EveryType type;
    private Calendar nextReset;
    private Calendar futureReset;

    public Settings(FileConfiguration config){
        this.config = config;
        StringBuilder everyData = new StringBuilder((String) config.getString("Config.every"));
        String nextReset = (String) config.getString("Save.nextReset");
        switch (everyData.charAt(everyData.length()-1)){
            case 'd' : type = EveryType.DAYS; break;
            case 'w' : type = EveryType.WEEK; break;
            case 'm' : type = EveryType.MONTH; break;
        }
        many = Integer.parseInt(everyData.deleteCharAt(everyData.length()-1).toString());
        this.nextReset = Calendar.getInstance();
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm");
            this.nextReset.setTime(sdf.parse((String) config.getString("Save.nextReset") + " " + (String) config.getString("Config.time")));
        } catch (ParseException e) {
            this.nextReset = null;
        }
    }

    void setup(){
        if(nextReset != null){
            if(Calendar.getInstance().compareTo(this.nextReset) >= 0){
                Endworldreset.log.log(Level.WARNING, "[Error] The nextReset date in config is older than now date, Autogen...");
                set();
            }
            futureReset = (Calendar) this.nextReset.clone();
            futureReset.add(type.getCalendarType(), many);
        }
    }

    public void set(){
        this.nextReset = Calendar.getInstance();
        String[] time = config.getString("Config.time").split(":");
        this.nextReset.set(Calendar.HOUR_OF_DAY, Integer.parseInt(time[0]));
        this.nextReset.set(Calendar.MINUTE, Integer.parseInt(time[1]));
        this.nextReset.set(Calendar.SECOND, 0);
        this.nextReset.add(type.getCalendarType(), many);
        Config.save();
    }

    public EveryType getType() {
        return type;
    }
    public Calendar getNextReset() {
        return nextReset;
    }

    public Calendar getFutureReset() {
        return futureReset;
    }

    public int getMany() {
        return many;
    }

    enum EveryType {
        DAYS("Day(s)", Calendar.DATE),
        WEEK("Week(s)", Calendar.WEEK_OF_YEAR),
        MONTH("Month(s)", Calendar.MONTH);

        private String type;
        private int CalendarType;
        EveryType(String type, int CalendarType){
            this.type = type;
            this.CalendarType = CalendarType;
        }

        public String toString(){
            return this.type;
        }

        public int getCalendarType() {
            return CalendarType;
        }
    }

    public String getInfoPrint() {
        StringBuilder info = new StringBuilder();
        info.append("[Endworldreset] Settings :\n");
        info.append("============Setting============\n");
        info.append(" every : " + many + " " + type.toString() + "\n");
        info.append(" time  : " + config.getString("Config.time") + "\n");
        info.append("==========Reset World==========\n");
        if (nextReset != null) {
            SimpleDateFormat formater = new SimpleDateFormat("dd-MM-yyyy HH:mm");
            info.append(" next    : " + formater.format(nextReset.getTime()) + "\n");
            info.append(" future  : " + formater.format(futureReset.getTime()) + "\n");
        } else {
            info.append(" not set yet!");
        }
        info.append("===============================");
        return info.toString();
    }
}
