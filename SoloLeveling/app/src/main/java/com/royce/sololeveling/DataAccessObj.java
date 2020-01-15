package com.royce.sololeveling;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Dao
public interface DataAccessObj {

    @Insert
    public void insertMission(Mission_DB mission);

    @Insert
    public void insertTask(Task_DB task);

    @Query("SELECT id FROM Mission WHERE name = :name")
    public int getID(String name);

    @Query("UPDATE Mission SET status = '[COMPLETE]' WHERE id = :id")
    public void updateMissionStatus(int id);

    @Query("SELECT * FROM Mission")
    public List<Mission_DB> getMission();

    @Query("SELECT name FROM Mission")
    public List<String> getMissions();

    @Query("SELECT name FROM Mission WHERE id = :id")
    public String getMissionName(int id);

    @Query("SELECT status FROM Task WHERE missionID = :id")
    public List<String> checkTaskStatus(int id);

    @Query("SELECT * FROM Task WHERE missionID = :missionid") // where the missionid = the missionDB ID
    public List<Task_DB> getMissionTasks(int missionid);

    @Query("SELECT * FROM Task WHERE id = :id")
    Task_DB getMissionTask(int id);

    @Query("UPDATE Task SET progress = (progress + :upProgress) WHERE id = :id")
    public void updateProgress(int id, int upProgress);

    @Query("UPDATE Task SET status = '[COMPLETE]' WHERE id = :id")
    public void updateTaskStatus(int id);

    @Query("SELECT startDate From Mission WHERE isActive = 'false' AND status = '[INCOMPLETE]' AND startDate > :c")
    List<Mission_DB> findFutureDates(Calendar c); // c will be the current time/date instance

    //List<RepeatDate_DB> findFutureRepeatDates(); // returns repeat days between current instance and desired future alarm
    @Query("SELECT * FROM RepeatDays WHERE missionID = :id")
    RepeatDate_DB getDay(int id); // returns the mission matching the ID


    @Query("DELETE FROM Task")
    public void nukeTable();
}
