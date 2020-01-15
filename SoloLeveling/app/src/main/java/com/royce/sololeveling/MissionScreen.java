package com.royce.sololeveling;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.List;

public class MissionScreen extends Fragment implements View.OnClickListener {
    TextView missionTitle;
    int missionID;
    String selected;
    GoalDialog goalDialog;
    public MissionScreen() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView =  inflater.inflate(R.layout.fragment_mission_screen, container, false);

        FloatingActionButton home = rootView.findViewById(R.id.home_button);
        missionTitle = rootView.findViewById(R.id.missionTitle);
        //////////////////////////////////////////////////////////
        //////////////////HOME FAB LISTENER///////////////////////
        //////////////////////////////////////////////////////////
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.fragmentManager.beginTransaction().replace(R.id.fragmentContainer, new HomeFragment()).addToBackStack(null).commit();
            }
        });
        //////////////////////////////////////////////////////////
        //////////////////////////////////////////////////////////
        TableLayout missionDisplay = rootView.findViewById(R.id.taskDisplay);
        TableLayout.LayoutParams tableParams = new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT);
        TableRow.LayoutParams rowParams = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT, 1.0f);

        Bundle bundle = this.getArguments();
        if (bundle != null){
            missionID = bundle.getInt("ID", 0);// don't forget the default value
            selected = bundle.getString("KEY", "");
        }
        missionTitle.setText(selected); // textview set to the name of the mission
        List<Task_DB> task_db = MainActivity.database.dataAccessObj().getMissionTasks(missionID); // return only the selected mission
        if(task_db.size() > 0){
            for (int i=0; i<task_db.size(); i++){
                //////////////////////////////////////////////////////////
                //////////////////////////////////////////////////////////
                TableRow tableRow = new TableRow(getActivity());
                tableRow.setPadding(0, 0, 0, 10);
                //////////////////////////////////////////////////////////
                //////////////////////////////////////////////////////////
                TextView task = new TextView(getActivity());
                task.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT, 1.0f));
                task.setMaxLines(1);
                task.setGravity(Gravity.LEFT);
                task.setBackgroundColor(Color.RED);
                task.setTag(Integer.valueOf(task_db.get(i).getId()));
                task.setPadding(50, 0, 0, 0);
                task.setText(task_db.get(i).getStatus() + "" + task_db.get(i).getName());
                //////////////////////////////////////////////////////////
                //////////////////////////////////////////////////////////
                TextView goal = new TextView(getActivity());
                goal.setBackgroundColor(Color.BLUE);
                goal.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT, 1.0f));
                goal.setMaxLines(1);
                goal.setGravity(Gravity.RIGHT);
                goal.setOnClickListener(this);
                goal.setTag(Integer.valueOf(task_db.get(i).getId()));
                goal.setPadding(0, 0, 50, 0);
                goal.setText("[" +task_db.get(i).getProgress() + "/" + task_db.get(i).getGoal() + "]");

                tableRow.addView(task);
                tableRow.addView(goal);

                if (tableRow.getParent() != null) {
                    ((ViewGroup) tableRow.getParent()).removeView(tableRow);
                }
                missionDisplay.addView(tableRow);
            }
        }

        return  rootView;
    }

    public void openDialog(){
        goalDialog.show(getFragmentManager(), "add diaglog");
    }


    @Override
    public void onClick(View v) {
        TextView textView = (TextView)v;
        int tag = (int) textView.getTag();
        TableRow goal = (TableRow) textView.getParent();
        TextView textView1 = (TextView) goal.getChildAt(0);
        String mission = missionTitle.getText().toString();
        String task = textView1.getText().toString().substring(12, textView1.length()) + " " + textView.getText().toString();
        // add tag and task to bundle also mission
        Bundle bundle = new Bundle();
        bundle.putString("MISSION", mission);
        bundle.putInt("ID", missionID);
        bundle.putInt("TAG",tag);
        bundle.putString("TASK", task);
        goalDialog = new GoalDialog();
        goalDialog.setArguments(bundle);
        openDialog();
    }

}
