package com.royce.sololeveling;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;

import java.sql.Date;
import java.sql.Time;
import java.util.Calendar;

@Entity(tableName = "Mission")
public class Mission_DB {

    @PrimaryKey(autoGenerate = true)
    private int id; // unique identifier

    private String name; // each mission has a unique name

    private String status; // either COMPLETE or INCOMPLETE

    private boolean isActive; // whether the mission is active (true or false)

    private boolean recurring; // whether the mission is recurring (true or false)

    private String date; // contains the calendar date the mission will be active format () SQL

    private String time; // contains the time the mission will go active

    private int hour; // hour of the day

    private int minute; // minute of the day

    private String timeToComplete; // contains the time the that is allowed to complete

    private Calendar startDate; // contains a calendar date converted from millis long

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean isActive() {
        return isActive;
    }

    public boolean isRecurring() {
        return recurring;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public int getHour() {
        return hour;
    }

    public int getMinute() {
        return minute;
    }

    public String getTimeToComplete() {
        return timeToComplete;
    }

    public Calendar getCalendar() {
        return startDate;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public void setRecurring(boolean recurring) {
        this.recurring = recurring;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public void setTimeToComplete(String timeToComplete) {
        this.timeToComplete = timeToComplete;
    }

    public void setCalendar(Calendar calendar) {
        this.startDate = calendar;
    }
}
