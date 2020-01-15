package com.royce.sololeveling;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "RepeatDays", foreignKeys = {
        @ForeignKey(entity = Mission_DB.class, parentColumns = "id", childColumns = "missionID", onDelete = ForeignKey.CASCADE)
}, indices = {@Index("missionID")})
public class RepeatDate_DB {

    @PrimaryKey(autoGenerate = true)
    private int id; // unique identifier

    private int missionID; // used as foreign key

    private boolean sunday;

    private boolean monday;

    private boolean tuesday;

    private boolean wednesday;

    private boolean thursday;

    private boolean friday;

    private boolean saturday;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMissionID() {
        return missionID;
    }

    public void setMissionID(int missionID) {
        this.missionID = missionID;
    }

    public boolean getSunday() {
        return sunday;
    }

    public void setSunday(boolean sunday) {
        this.sunday = sunday;
    }

    public boolean getMonday() {
        return monday;
    }

    public void setMonday(boolean monday) {
        this.monday = monday;
    }

    public boolean getTuesday() {
        return tuesday;
    }

    public void setTuesday(boolean tuesday) {
        this.tuesday = tuesday;
    }

    public boolean getWednesday() {
        return wednesday;
    }

    public void setWednesday(boolean wednesday) {
        this.wednesday = wednesday;
    }

    public boolean getThursday() {
        return thursday;
    }

    public void setThursday(boolean thursday) {
        this.thursday = thursday;
    }

    public boolean getFriday() {
        return friday;
    }

    public void setFriday(boolean friday) {
        this.friday = friday;
    }

    public boolean getSaturday() {
        return saturday;
    }

    public void setSaturday(boolean saturday) {
        this.saturday = saturday;
    }
}
