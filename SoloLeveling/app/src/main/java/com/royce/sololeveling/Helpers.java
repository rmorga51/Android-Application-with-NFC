package com.royce.sololeveling;

import java.util.Calendar;
import java.util.List;

public class Helpers {

    // method to compare goal and progress
    // Method for comparing the progress to the goal. Takes one argument (String task) to be parsed for the int progress and int goal
    // int id contains the missionID of the selected task
    public static boolean isTaskComplete(int id, String task, int missionID){
        int progress, goal;
        // logic to parse the progress and goal from the task string
        progress = Integer.parseInt(task.substring(task.indexOf('[')+1, task.indexOf('/')));
        goal = Integer.parseInt(task.substring(task.indexOf('/')+1, task.indexOf(']')));

        if (progress >= goal){ // Only continue if the statement is true
            // TODO: add call to update status in task_db table from [INCOMPLETE] to [COMPLETE]
            MainActivity.database.dataAccessObj().updateTaskStatus(id);
            // TODO: After task status is updated immediately check if all task's statuses have been updated. Add logic for when all tasks have been completed
            List<String> statuses = MainActivity.database.dataAccessObj().checkTaskStatus(missionID);
            if (!statuses.contains("[INCOMPLETE]")){ // if there are no incomplete tasks
                MainActivity.database.dataAccessObj().updateMissionStatus(id);
            }
            return true;
        }
        return false;
    }

    // receives id as argument and checks if the mission status is "complete"
    // returns a boolean
    public static void isMissionComplete(){
        MainActivity.database.dataAccessObj().getMission();

    }



}
