// TODO: Make sure that the correct variables are being passed to the isTaskComplete method. First return the updated progress value from the database

package com.royce.sololeveling;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class GoalDialog extends AppCompatDialogFragment {
    String task, mission;
    int missionID, tag;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final EditText editText = new EditText(getActivity());
        editText.setInputType(InputType.TYPE_CLASS_NUMBER);
        final Bundle bundle = this.getArguments();
        if(bundle != null) {
            task = bundle.getString("TASK", "");
            mission = bundle.getString("MISSION", "");
            missionID = bundle.getInt("ID", 0);
            tag = bundle.getInt("TAG", 0);

        }
        editText.setGravity(Gravity.CENTER);
        FrameLayout.LayoutParams frameLayout = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,FrameLayout.LayoutParams.MATCH_PARENT);
        editText.setLayoutParams(frameLayout);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(editText);
        builder.setTitle("Add To Goal Progress").setMessage(task).setPositiveButton("ADD", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(!editText.getText().toString().equals("")){ // if edit text is not empty
                    MainActivity.database.dataAccessObj().updateProgress(bundle.getInt("TAG"), Integer.parseInt(editText.getText().toString())); // update the progress where the id matches.
                    Task_DB task_db = MainActivity.database.dataAccessObj().getMissionTask(tag);
                    task = "[" + task_db.getProgress() + "/" + task_db.getGoal() + "]";
                    if(Helpers.isTaskComplete(tag, task, missionID)){
                        Toast.makeText(getActivity(), "Task Completed!", Toast.LENGTH_SHORT).show();
                    }
                    // switch fragment to MissionScreen
                    MissionScreen missionScreen = new MissionScreen();
                    String selected = mission; // contains the string value of the clicked view
                    Bundle bundle = new Bundle();
                    bundle.putInt("ID", missionID);
                    bundle.putString("KEY", selected);
                    missionScreen.setArguments(bundle);
                    MainActivity.fragmentManager.beginTransaction().replace(R.id.fragmentContainer, missionScreen).addToBackStack(null).commit();

                }
            }
        });
       return builder.create();
    }

}

