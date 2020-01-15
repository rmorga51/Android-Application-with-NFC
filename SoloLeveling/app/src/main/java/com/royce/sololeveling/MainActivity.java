package com.royce.sololeveling;

import android.app.Notification;
import android.app.NotificationManager;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity implements TimePickerFragment.TimePickedListener, DatePickerFragment.DatePickedListener {

    public static FragmentManager fragmentManager;
    public static Database database;
    Add_Mission_Fragment addMissionFragment;

/*    int numOfLines = 0;
    int fontSize = 15;*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // TODO: add push notifications
        /*NotificationManager nm;
        nm = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        String title = "Test Push Notification";
        Notification notification = new Notification(android.R.drawable.stat_notify_more,title,System.currentTimeMillis()
        );
*/
        /////////////////////////////////////////////////////////////////////////////////////////////////
        ////////////////////////adds home fragment to the main activity//////////////////////////////////
        /////////////////////////////////////////////////////////////////////////////////////////////////
        fragmentManager = getSupportFragmentManager();
        database = Room.databaseBuilder(getApplicationContext(), Database.class, "userdb").allowMainThreadQueries().build();

        Intent intent = getIntent();
        String missionName = intent.getStringExtra("KEY");
        int missionID = intent.getIntExtra("ID", 0);
        if(intent != null){
            if(missionName != null && missionID != 0){
                MissionScreen missionScreen = new MissionScreen();
                Bundle bundle = new Bundle();
                bundle.putString("KEY", missionName);
                bundle.putInt("ID", missionID);
                missionScreen.setArguments(bundle);
                MainActivity.fragmentManager.beginTransaction().replace(R.id.fragmentContainer, missionScreen).commit();
            }
        }
        if(findViewById(R.id.fragmentContainer) != null && !intent.getAction().equals("ACTION")){
            if(savedInstanceState != null){
                return;
            }
            fragmentManager.beginTransaction().add(R.id.fragmentContainer, new HomeFragment()).commit();
        }
        /////////////////////////////////////////////////////////////////////////////////////////////////
        /////////////////////////////////////////////////////////////////////////////////////////////////
        /////////////////////////////////////////////////////////////////////////////////////////////////


        /*TableLayout missionDisplay = findViewById(R.id.missionDisplay);
        TableLayout.LayoutParams tableParams = new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT);
        TableRow.LayoutParams rowParams = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);
        GradientDrawable gd = new GradientDrawable();
        gd.setColor(0xFF00FF00); // Changes this drawbale to use a single color instead of a gradient
        gd.setCornerRadius(5);
        //missionDisplay.setLayoutParams(rowParams);
        FloatingActionButton createMission =  findViewById(R.id.floatingActionButton); // button for adding a new mission/quest
        final Intent changeToCreateMission = new Intent(MainActivity.this, CreateMission.class); // set intent to change pages when the add mission button is activated
        createMission.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(changeToCreateMission);
            }
        });

        Bundle extras = getIntent().getExtras();
        if (extras != null){
            String [][] goalsAndTasks = (String[][]) extras.getSerializable("MISSION");
            for (int i=0; i<goalsAndTasks.length; i++) {
                TableRow tableRow = new TableRow(MainActivity.this);
                tableRow.setPadding(0,0,0,10);
                tableRow.setLayoutParams(rowParams);
                //////////////////////////////////////////////////////////
                //////////////////////////////////////////////////////////
                TextView task = new TextView(MainActivity.this);
                task.setHeight(100);
                task.setBackground(gd);
                task.setPadding(0,0,0,0);
                task.setTextSize(fontSize);
                task.setText(goalsAndTasks[i][0]);
                task.setId(numOfLines+1);
                //////////////////////////////////////////////////////////
                //////////////////////////////////////////////////////////
                TextView goal = new TextView(MainActivity.this);
                goal.setBackground(gd);
                goal.setPadding(496,0,0,0);
                task.setGravity(Gravity.LEFT);
                goal.setTextSize(fontSize);
                goal.setId(numOfLines+1);
                goal.setText(goalsAndTasks[i][1]);
                tableRow.addView(task);
                tableRow.addView(goal);
                if(tableRow.getParent() != null){
                    ((ViewGroup) tableRow.getParent()).removeView(tableRow);
                }
                missionDisplay.addView(tableRow);
                numOfLines++;
            }
        }*/


    }

    @Override
    public void pickedTime(String time) {
        addMissionFragment = (Add_Mission_Fragment) getSupportFragmentManager().findFragmentByTag("add_mission");
        addMissionFragment.setTimeView(time);
    }

    @Override
    public void pickedDate(String date) {
        addMissionFragment = (Add_Mission_Fragment) getSupportFragmentManager().findFragmentByTag("add_mission");
        addMissionFragment.setDateView(date);
    }
}

/* TODO add table layer for mission name and goal. decide on color scheme
TODO: Figure out how to make a TextView appear in layout programatically
TODO : May be able to move the get extras code to a void method that will be called after a mission has been submitted; using a boolean
* */