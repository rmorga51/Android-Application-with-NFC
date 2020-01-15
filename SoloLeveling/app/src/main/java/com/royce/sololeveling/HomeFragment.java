package com.royce.sololeveling;


import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

// TODO: display if a mission has been completed
/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements View.OnClickListener {

    int numOfLines = 0;
    int fontSize = 15;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        TableLayout missionDisplay = rootView.findViewById(R.id.missionDisplay);
        TableLayout.LayoutParams tableParams = new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT);
        TableRow.LayoutParams rowParams = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT, 1.0f);
        GradientDrawable gd = new GradientDrawable();
        gd.setColor(0xFF00FF00); // Changes this drawbale to use a single color instead of a gradient
        gd.setCornerRadius(5);
        //missionDisplay.setLayoutParams(rowParams);
        FloatingActionButton createMission =  rootView.findViewById(R.id.floatingActionButton); // button for adding a new mission/quest
        createMission.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.fragmentManager.beginTransaction().replace(R.id.fragmentContainer, new Add_Mission_Fragment(), "add_mission").addToBackStack(null).commit();
            }
        });

        List<Mission_DB> mission_db = MainActivity.database.dataAccessObj().getMission();
        if (mission_db.size() > 0){

            for (int i=0; i<mission_db.size(); i++) {
                TableRow tableRow = new TableRow(getActivity());
                tableRow.setPadding(0,0,0,10);
                tableRow.setLayoutParams(tableParams);
                TextView mission = new TextView(getActivity());
                mission.setOnClickListener(this);
                mission.setLayoutParams(rowParams);
                mission.setGravity(Gravity.CENTER);

                if(mission_db.get(i).getStatus().equals("[COMPLETE]")){
                    String status = "[COMPLETE]" + mission_db.get(i).getName();
                    mission.setText(status);
                }
                else{
                    mission.setText(mission_db.get(i).getName());
                }
                mission.setTag(mission_db.get(i).getId());
                mission.setHeight(100);
                mission.setBackground(gd);
                mission.setPadding(0,0,0,0);
                mission.setTextSize(fontSize);
                tableRow.addView(mission);

                //////////////////////////////////////////////////////////
                //////////////////////////////////////////////////////////
                /*TextView task = new TextView(getActivity());
                task.setHeight(100);
                task.setBackground(gd);
                task.setPadding(0,0,0,0);
                task.setTextSize(fontSize);
                task.setText(users.get(i).getTask());
                task.setId(numOfLines+1);
                //////////////////////////////////////////////////////////
                //////////////////////////////////////////////////////////
                TextView goal = new TextView(getActivity());
                goal.setBackground(gd);
                goal.setPadding(496,0,0,0);
                task.setGravity(Gravity.LEFT);
                goal.setTextSize(fontSize);
                goal.setId(numOfLines+1);
                goal.setText("[0/" +users.get(i).getGoal()+ "]");
                tableRow.addView(task);
                tableRow.addView(goal);*/
                if(tableRow.getParent() != null){
                    ((ViewGroup) tableRow.getParent()).removeView(tableRow);
                }
                missionDisplay.addView(tableRow);
                numOfLines++;
            }
        }

        return rootView;


    }

    @Override
    public void onClick(View v) {
        // switch fragment to MissionScreen
        MissionScreen missionScreen = new MissionScreen();
        TextView textView = (TextView)v;
        int id = (int) textView.getTag(); // contains the string value of the clicked view
        String selected = MainActivity.database.dataAccessObj().getMissionName(id);
        Bundle bundle = new Bundle();
        bundle.putInt("ID", id);
        bundle.putString("KEY", selected);
        missionScreen.setArguments(bundle);
        MainActivity.fragmentManager.beginTransaction().replace(R.id.fragmentContainer, missionScreen, "Mission").addToBackStack(null).commit();
    }
}
