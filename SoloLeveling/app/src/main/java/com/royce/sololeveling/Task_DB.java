package com.royce.sololeveling;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;


@Entity(tableName = "Task", foreignKeys = {
        @ForeignKey(entity = Mission_DB.class, parentColumns = "id", childColumns = "missionID", onDelete = ForeignKey.CASCADE)
        }, indices = {@Index("missionID")})
public class Task_DB {

    @PrimaryKey(autoGenerate = true)
    private int id; // unique identifier

    private int missionID; // used as foreign key

    private String name; // name of task to be completed

    private int goal; // goal as an integer

    private int progress; // progress to completing the goal as integer

    private String status; // either COMPLETE or INCOMPLETE

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getGoal() {
        return goal;
    }

    public void setGoal(int goal) {
        this.goal = goal;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
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

    public void setMissionID(int missionID) {
        this.missionID = missionID;
    }

    public int getMissionID() {
        return missionID;
    }
}
//TODO Add four fields to the database: Task Completion, Mission Completion, Progress, Reoccuring
