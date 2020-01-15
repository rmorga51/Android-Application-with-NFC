package com.royce.sololeveling;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class CreateMission extends AppCompatActivity implements View.OnClickListener, View.OnFocusChangeListener {

    public static FragmentManager fragmentManager;
    int numOfLines = 0; // track number of edittext lines
    TableLayout tableLayout;
    FloatingActionButton startMission;
    EditText goal;
    EditText task;
    String [][] goalsAndTasks;
    EditText clicked;
    int hasTextCount;
    EditText enterName;
    String missionName;

    @Override
    public void onClick(View v) { // determine which edittext was clicked
        clicked = v.findViewById(v.getId()); // should return clicked edittext
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_mission);

        /////////////////////////////////////////////////////////////////////////////////////////////////
        ////////////////////////adds home fragment to the main activity//////////////////////////////////
        /////////////////////////////////////////////////////////////////////////////////////////////////
        fragmentManager = getSupportFragmentManager();

        if(findViewById(R.id.fragmentContainer) != null){
            if(savedInstanceState != null){
                return;
            }
            fragmentManager.beginTransaction().add(R.id.fragmentContainer, new Add_Mission_Fragment(), "add_mission").commit();
        }
        /////////////////////////////////////////////////////////////////////////////////////////////////
        /////////////////////////////////////////////////////////////////////////////////////////////////
        /////////////////////////////////////////////////////////////////////////////////////////////////



        startMission = findViewById(R.id.floatingActionButton2);
        startMission.setEnabled(false);
        startMission.setAlpha(0.3f);
        tableLayout = findViewById(R.id.tableLayerCreate);
        enterName = findViewById(R.id.enterName);

        FloatingActionButton addTask = findViewById(R.id.floatingActionButton4);
        FloatingActionButton removeTask = findViewById(R.id.floatingActionButton5);

        removeTask.setOnClickListener(new View.OnClickListener() { // listener for removing task edit text
            @Override
            public void onClick(View v) {
                remove_line();
            }
        });

        addTask.setOnClickListener(new View.OnClickListener() { // listener for adding a new task edit text
            @Override
            public void onClick(View v) {
                add_line();
            }
        });

        startMission.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkEditTexts();
                goalsAndTasks = new String [tableLayout.getChildCount()][4]; // should contain all the added rows before final submission
                if (hasTextCount == tableLayout.getChildCount() && !missionName.equals("")){
                    // proceed here
                    Intent intent = new Intent(CreateMission.this, MainActivity.class);
                    Bundle mBundle = new Bundle();
                    for (int i=0; i<tableLayout.getChildCount(); i++){
                        TableRow row = (TableRow) tableLayout.getChildAt(i);
                        EditText goal = (EditText) row.getChildAt(0);
                        goalsAndTasks[i][0] = "[INCOMPLETE] " + goal.getText().toString().toUpperCase();
                        EditText task = (EditText) row.getChildAt(1);
                        goalsAndTasks[i][1] = "[0/" + task.getText().toString() + "]";
                    }
                    startActivity(intent);
                }
                else {
                    Toast.makeText(CreateMission.this, "Please fill in all inputs", Toast.LENGTH_SHORT).show();
                }
            }
        });
        /*if (isClicked) {
            clicked.setOnEditorActionListener(new TextView.OnEditorActionListener() { // put this whole block in a method to be called
                @Override
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                    if (actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_ACTION_NEXT) {
                        // call method that checks if there is text in all edittexts
                        checkEditTexts();
                    }
                    return false;
                }
            });
        }*/

    }
/////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////
    public void checkEditTexts(){
        hasTextCount = 0; // counter for how rows with text. Should equal the amount of rows if correct
        if(tableLayout.getChildCount() == 0){
            startMission.setAlpha(0.3f);
            startMission.setEnabled(false);
        }
        else {
            for (int i = 0; i < tableLayout.getChildCount(); i++) { // loop through the tablelayout rows
                TableRow row = (TableRow) tableLayout.getChildAt(i);
                EditText taskCheck = (EditText) row.getChildAt(0);
                EditText goalCheck = (EditText) row.getChildAt(1);
                if (!taskCheck.getText().toString().equals("") && !goalCheck.getText().toString().equals("")) {
                    hasTextCount++;
                }
            }
            if (hasTextCount == tableLayout.getChildCount() && !missionName.equals("")) { // if the counter has the same value as the number of rows
                // unhide the button
                startMission.setAlpha(1.0f);
                startMission.setEnabled(true);

            } else { // if the counter does not equal the number of rows, set button opacity down and disable
                startMission.setAlpha(0.3f);
                startMission.setEnabled(false);
            }
        }
    }
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////
    public void remove_line(){
        if(tableLayout.getChildCount() > 0){
            tableLayout.removeViewAt(tableLayout.getChildCount()-1);
            checkEditTexts();
        }
    }
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////
    public void add_line(){

        /*TableLayout.LayoutParams tableParams = new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT);
        TableRow.LayoutParams rowParams = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);*/
        TableRow tableRow = new TableRow(CreateMission.this); // create tablerow and initialize with context
        goal = new EditText(this); // initialize goal
        task = new EditText(this); // initialize task

        // add edittext
        task.setOnFocusChangeListener(this);
        task.setOnClickListener(CreateMission.this);
        task.setImeOptions(EditorInfo.IME_ACTION_NEXT);
        task.setInputType(InputType.TYPE_CLASS_TEXT);
        task.setHint("Enter Task");
        task.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_ACTION_NEXT){
                    checkEditTexts();
                }
                return false;
            }
        });
        goal.setOnFocusChangeListener(this);
        goal.setOnClickListener(CreateMission.this);
        goal.setHint("Enter completion condition ");
        /*LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        goal.setLayoutParams(p);*/
        goal.setInputType(InputType.TYPE_CLASS_NUMBER);
        goal.setId(numOfLines + 1);
        goal.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_ACTION_NEXT){
                    checkEditTexts();
                }
                return false;
            }
        });
        task.setId(numOfLines + 1);

        // add edittext to table
        tableRow.addView(task);
        tableRow.addView(goal);
        tableLayout.addView(tableRow);
        tableLayout.getChildAt(0); // returns a row view
        numOfLines++;
        checkEditTexts();

    }
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        checkEditTexts();

    }
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////
}
// TODO: Add tablerow to tablelayout
//TODO : tablelayout getchildat to get tablerow then getchildat to get the
// TODO: figure out how to check each generated editText for content.