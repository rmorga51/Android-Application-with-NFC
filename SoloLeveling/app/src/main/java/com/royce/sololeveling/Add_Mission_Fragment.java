package com.royce.sololeveling;


import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.text.InputType;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.time.DayOfWeek;
import java.time.Year;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
// TODO: and click listeners for time, date, and duration
public class Add_Mission_Fragment extends Fragment implements View.OnClickListener, View.OnFocusChangeListener{

    private  int numOfLines = 0; // track number of edittext lines
    private TableLayout tableLayout;
    private FloatingActionButton startMission;
    private EditText goal;
    private EditText task;
    private  String [][] goalsAndTasks;
    private EditText clicked;
    private  int hasTextCount;
    private EditText enterName;
    private String missionName;
    private CheckBox checkBox;
    private Button timeBtn, durationBtn, dateBtn;
    private TextView timeView, durationView, dateView;
    private NotificationHelper mNotificationHelper;
    private int missionID;
    private boolean recurring;

    private int year = 0;
    private int month = 0;
    private int day = 0;
    private int hour;
    private int minute;

    private CheckBox sunday;
    private CheckBox monday;
    private CheckBox tuesday;
    private CheckBox wednesday;
    private CheckBox thursday;
    private CheckBox friday;
    private CheckBox saturday;

    private boolean [] dayArray = {false, false, false, false, false, false, false};

    public Add_Mission_Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_add__mission_, container, false);
        //MainActivity.database.dataAccessObj().nukeTable();
        startMission = rootView.findViewById(R.id.floatingActionButton2);
        startMission.setEnabled(false);
        startMission.setAlpha(0.3f);
        tableLayout = rootView.findViewById(R.id.tableLayerCreate);
        enterName = rootView.findViewById(R.id.enterName);

        timeBtn = rootView.findViewById(R.id.time_btn);
        durationBtn = rootView.findViewById(R.id.duration_btn);
        dateBtn = rootView.findViewById(R.id.date_btn);

        timeView = rootView.findViewById(R.id.time_view);
        durationView = rootView.findViewById(R.id.duration_view);
        dateView = rootView.findViewById(R.id.date_view);

        sunday = rootView.findViewById(R.id.Sunday);
        monday = rootView.findViewById(R.id.Monday);
        tuesday = rootView.findViewById(R.id.Tuesday);
        wednesday = rootView.findViewById(R.id.Wednesday);
        thursday = rootView.findViewById(R.id.Thursday);
        friday = rootView.findViewById(R.id.Friday);
        saturday = rootView.findViewById(R.id.Saturday);




        mNotificationHelper = new NotificationHelper(getContext());

        FloatingActionButton addTask = rootView.findViewById(R.id.floatingActionButton4);
        FloatingActionButton removeTask = rootView.findViewById(R.id.floatingActionButton5);

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

        timeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePickerDialog(getView());
            }
        });

/*        Bundle bundle = this.getArguments();
        if(bundle != null){
            String time = bundle.getString("TIME", "");
            timeView.setText(time);
        }*/

        dateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showDatePickerDialog(getView());
            }
        });

        // TODO set onclick listener for days of the week

        startMission.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkEditTexts();
                goalsAndTasks = new String [tableLayout.getChildCount()][2]; // should contain all the added rows before final submission
                if (hasTextCount == tableLayout.getChildCount() && !missionName.equals("")){
                    missionName = missionName.toUpperCase();
                    // put the mission queries here
                    //////////////////////////////////////////////////////////////////////
                    //////////////////////////////QUERY///////////////////////////////////
                    //////////////////////////////////////////////////////////////////////
                    //TODO: add timeview and dateview logic to database query. Reminder: setting a time and date is NOT required, if not set, will default to current

                    Mission_DB mission_db = new Mission_DB();
                    mission_db.setName(missionName);
                    mission_db.setStatus("[INCOMPLETE]");
                    mission_db.setTime(hour + ":" + minute);
                    MainActivity.database.dataAccessObj().insertMission(mission_db);
                    missionID = MainActivity.database.dataAccessObj().getID(missionName); // contains the current mission ID as integer
                    //////////////////////////////////////////////////////////////////////
                    //////////////////////////////QUERY///////////////////////////////////
                    //////////////////////////////////////////////////////////////////////
                    // proceed here
                    for (int i=0; i<tableLayout.getChildCount(); i++){
                        TableRow row = (TableRow) tableLayout.getChildAt(i);
                        EditText goal = (EditText) row.getChildAt(0);
                        goalsAndTasks[i][0] = goal.getText().toString().toUpperCase();
                        EditText task = (EditText) row.getChildAt(1);
                        goalsAndTasks[i][1] = task.getText().toString();
                        //////////////////////////////////////////////////////////////////////
                        //////////////////////////////QUERY///////////////////////////////////
                        //////////////////////////////////////////////////////////////////////
                        Task_DB task_db = new Task_DB();
                        task_db.setMissionID(missionID); // each task will share its mission's ID as foreign key
                        task_db.setName(goalsAndTasks[i][0]);
                        task_db.setGoal(Integer.parseInt(goalsAndTasks[i][1]));
                        task_db.setStatus("[INCOMPLETE]");
                        MainActivity.database.dataAccessObj().insertTask(task_db);
                        //////////////////////////////////////////////////////////////////////
                        //////////////////////////////QUERY///////////////////////////////////
                        //////////////////////////////////////////////////////////////////////
                        RepeatDate_DB repeatDate_db = new RepeatDate_DB();
                        repeatDate_db.setMissionID(missionID);
                        repeatDate_db.setSunday(dayArray[0]);
                        repeatDate_db.setMonday(dayArray[1]);
                        repeatDate_db.setTuesday(dayArray[2]);
                        repeatDate_db.setWednesday(dayArray[3]);
                        repeatDate_db.setThursday(dayArray[4]);
                        repeatDate_db.setFriday(dayArray[5]);
                        repeatDate_db.setSaturday(dayArray[6]);
                        //////////////////////////////////////////////////////////////////////
                        //////////////////////////////QUERY///////////////////////////////////
                        //////////////////////////////////////////////////////////////////////
                    }
                    //TODO: set first alarm here
                   /* AlarmManager alarm = (AlarmManager) getContext().getSystemService(Context.ALARM_SERVICE);
                    alarm.setAlarmClock(AlarmManager.); // set to value from timepicker*/
                    Calendar c = Calendar.getInstance();

                    String yearFormat = dateView.getText().toString().substring(dateView.getText().toString().indexOf("/")+1, dateView.getText().length());
                    year = Integer.parseInt(dateView.getText().toString().substring(yearFormat.indexOf("/")+1, yearFormat.length()));
                    month = Integer.parseInt(yearFormat.substring(0, yearFormat.indexOf("/")));
                    day = Integer.parseInt(dateView.getText().toString().substring(0, dateView.getText().toString().indexOf("/")));
                    hour = Integer.parseInt(timeView.getText().toString().substring(0, timeView.getText().toString().indexOf(":")));
                    minute = Integer.parseInt(timeView.getText().toString().substring(timeView.getText().toString().indexOf(":")+1, timeView.getText().length()));

                    //////////////////////////////////////////////////////////////////////
                    //////////////////////////////QUERY///////////////////////////////////
                    //////////////////////////////////////////////////////////////////////
                    mission_db.setHour(hour);
                    mission_db.setMinute(minute);
                    // TODO: check if the date is in the future or not

                    if(day == 0){
                        year = Calendar.YEAR;
                        month = Calendar.MONTH;
                        day = Calendar.DAY_OF_MONTH;
                    }
                    c.set(Calendar.YEAR, year);
                    c.set(Calendar.MONTH, month);
                    c.set(Calendar.DAY_OF_MONTH, day);
                    c.set(Calendar.HOUR_OF_DAY, hour);
                    c.set(Calendar.MINUTE, minute);
                    c.set(Calendar.SECOND, 0);
                    mission_db.setCalendar(c);

                    sendOnChannel1("New Quest Activated!", missionName, missionID, c);
                    // put info into database
                    Toast.makeText(getActivity(), "Mission Added!", Toast.LENGTH_SHORT).show();
                    // then go back to homefragment
                    MainActivity.fragmentManager.beginTransaction().replace(R.id.fragmentContainer, new HomeFragment()).addToBackStack(null).commit();

                }
                else {
                    Toast.makeText(getActivity(), "Please fill in all inputs", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return rootView;
    }

    public void checkEditTexts(){
        missionName = enterName.getText().toString();
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
        TableRow tableRow = new TableRow(getContext()); // create tablerow and initialize with context
        goal = new EditText(getContext()); // initialize goal
        task = new EditText(getContext()); // initialize task

        // add edittext
        task.setOnFocusChangeListener(this);
        task.setOnClickListener(this);
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
        goal.setOnClickListener(this);
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

    @Override
    public void onClick(View v) {
       /* switch (v.getId())
        {
            case R.id.floatingActionButton4:
                add_line();
                break;
            case R.id.floatingActionButton5:
                remove_line();
                break;
            case R.id.floatingActionButton2:

        }*/
    }
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        checkEditTexts();

    }
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////

    public void showTimePickerDialog(View v) {
        DialogFragment newFragment = new TimePickerFragment();
        newFragment.show(getFragmentManager(), "timePicker");
    }

    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getFragmentManager(), "datePicker");
    }

    public void setTimeView(String time){
        timeView.setText(time);

    }

    public void setDateView(String date){
        dateView.setText(date);

    }

    public void sendOnChannel1(String title, String message, int id, Calendar c){
        //TODO: find a way to pass correct mission information to be used to open specific missionscreen
        AlarmManager alarmManager = (AlarmManager) getContext().getSystemService(Context.ALARM_SERVICE);

        Intent intent = new Intent(getContext(), MainActivity.class);
        intent.putExtra("ID", id);
        intent.putExtra("KEY", message);
        intent.setAction("ACTION");
        //intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(getContext(), 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);

        Intent broadcastIntent = new Intent(getContext(), AlertReceiver.class);
        broadcastIntent.putExtra("ID", id); // contains mission id
        broadcastIntent.putExtra("KEY", message); // contains mission name
        broadcastIntent.putExtra("TITLE", title);
        broadcastIntent.putExtra("PINTENT", pendingIntent);
        PendingIntent actionIntent = PendingIntent.getBroadcast(getContext(), 0, broadcastIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        if(c.before(Calendar.getInstance())){ // if the time picked is in the past add 1 day
            c.add(Calendar.DATE, 1);
        }

        alarmManager.setExact(AlarmManager.RTC_WAKEUP, setAlarm(), actionIntent);



    }


    public void onCheckboxClicked(View view){
        // is this view now checked?
        boolean checked = ((CheckBox) view).isChecked();
        // check which box is clicked
        switch(view.getId()){
            case R.id.Sunday:
                if(checked){
                    // update table boolean for sunday = true
                    dayArray[0] = true;
                }
                else{
                    // update table boolean for sunday = false
                    dayArray[0] = false;
                }
                break;
            case R.id.Monday:
                if(checked){
                    dayArray[1] = true;
                }
                else {
                    dayArray[1] = false;

                }
                break;
            case R.id.Tuesday:
                if(checked){
                    // update table boolean for sunday = true
                    dayArray[2] = true;
                }
                else{
                    // update table boolean for sunday = false
                    dayArray[2] = false;
                }
                break;
            case R.id.Wednesday:
                if(checked){
                    // update table boolean for sunday = true
                    dayArray[3] = true;
                }
                else{
                    // update table boolean for sunday = false
                    dayArray[3] = false;
                }
                break;
            case R.id.Thursday:
                if(checked){
                    // update table boolean for sunday = true
                    dayArray[4] = true;
                }
                else{
                    // update table boolean for sunday = false
                    dayArray[4] = false;
                }
                break;
            case R.id.Friday:
                if(checked){
                    // update table boolean for sunday = true
                    dayArray[5] = true;
                }
                else{
                    // update table boolean for sunday = false
                    dayArray[5] = false;
                }
                break;
            case R.id.Saturday:
                if(checked){
                    // update table boolean for sunday = true
                    dayArray[6] = true;
                }
                else{
                    // update table boolean for sunday = false
                    dayArray[6] = false;
                }
                break;
        }
    }

    // sets the alarm equal to the nearest alarm value.
    public Long setAlarm(){
        Integer integerID;
        Long idAsLong;
        Calendar calendar = Calendar.getInstance();
        // TODO create a 2d arrayList
        ArrayList<ArrayList<Long>> arrayList2D = new ArrayList<>(); // arraylist containing the mission id[0] and alarm start time(inMillis)[1]
        ArrayList<Long> alarmTimes = new ArrayList<>();
        List<Mission_DB> dateList = MainActivity.database.dataAccessObj().getMission(); // return all missions to sort through

        // first check if a time is in the future or past.
        for (int i=0; i<dateList.size(); i++){

            // if the value is in the future
            if(dateList.get(i).getCalendar().getTimeInMillis() > calendar.getTimeInMillis()){

                // convert int to Integer
                integerID = dateList.get(i).getId();

                // convert Integer to Long
                idAsLong = Long.valueOf(integerID); // mission id as long

                // add it to the array
                alarmTimes.add(idAsLong);
                alarmTimes.add(dateList.get(i).getCalendar().getTimeInMillis());
                arrayList2D.add(alarmTimes);
            }
            // if the value is in the past
            // check if the alarm repeats or not
            else{
                // if the alarm repeats/ I need the repeating days that correspond to the mission ID
                if (dateList.get(i).isRecurring() == true){
                    // store the mission's unique ID
                    int id = dateList.get(i).getId();

                    integerID = dateList.get(i).getId();
                    idAsLong = Long.valueOf(integerID);

                    Calendar repeatCalendar = Calendar.getInstance(); // contains the unique day for the repeat alarm. Can use the above calendar instead of this one

                    int hour = dateList.get(i).getHour();
                    int minute = dateList.get(i).getMinute();
                    repeatCalendar.set(Calendar.HOUR_OF_DAY, hour);
                    repeatCalendar.set(Calendar.MINUTE, minute);

                    // determine what days it repeats
                    RepeatDate_DB day = MainActivity.database.dataAccessObj().getDay(id);
                    int desiredDay; // will contain the desired day
                    for (int days = 1; days<=7; days++){
                        switch (days){
                            // add day to alarmTimes if true
                            case 1: // Sunday
                                if (day.getSunday() == true){
                                    // TODO: use this logic for the rest of the cases
                                    desiredDay = Calendar.SUNDAY;
                                    repeatCalendar = setDay(repeatCalendar, desiredDay);
                                    alarmTimes.add(idAsLong); // mission id as a long
                                    alarmTimes.add(repeatCalendar.getTimeInMillis());
                                    arrayList2D.add(alarmTimes);
                                }
                                break;
                            case 2: // Monday
                                if (day.getMonday() == true){
                                    desiredDay = Calendar.MONDAY;
                                    repeatCalendar = setDay(repeatCalendar, desiredDay);
                                    alarmTimes.add(idAsLong); // mission id as a long
                                    alarmTimes.add(repeatCalendar.getTimeInMillis());
                                    arrayList2D.add(alarmTimes);
                                }
                                break;
                            case 3: // Tuesday
                                if (day.getTuesday() == true){
                                    desiredDay = Calendar.TUESDAY;
                                    repeatCalendar = setDay(repeatCalendar, desiredDay);
                                    alarmTimes.add(idAsLong); // mission id as a long
                                    alarmTimes.add(repeatCalendar.getTimeInMillis());
                                    arrayList2D.add(alarmTimes);
                                }
                                break;
                            case 4: // Wednesday
                                if (day.getWednesday() == true){
                                    desiredDay = Calendar.WEDNESDAY;
                                    repeatCalendar = setDay(repeatCalendar, desiredDay);
                                    alarmTimes.add(idAsLong); // mission id as a long
                                    alarmTimes.add(repeatCalendar.getTimeInMillis());
                                    arrayList2D.add(alarmTimes);
                                }
                                break;
                            case 5: // Thursday
                                if (day.getThursday() == true){
                                    desiredDay = Calendar.THURSDAY;
                                    repeatCalendar = setDay(repeatCalendar, desiredDay);
                                    alarmTimes.add(idAsLong); // mission id as a long
                                    alarmTimes.add(repeatCalendar.getTimeInMillis());
                                    arrayList2D.add(alarmTimes);
                                }
                                break;
                            case 6: // Friday
                                if (day.getFriday() == true){
                                    desiredDay = Calendar.FRIDAY;
                                    repeatCalendar = setDay(repeatCalendar, desiredDay);
                                    alarmTimes.add(idAsLong); // mission id as a long
                                    alarmTimes.add(repeatCalendar.getTimeInMillis());
                                    arrayList2D.add(alarmTimes);
                                }
                                break;
                            case 7: // Saturday
                                if (day.getSaturday() == true){
                                    desiredDay = Calendar.SATURDAY;
                                    repeatCalendar = setDay(repeatCalendar, desiredDay);
                                    alarmTimes.add(idAsLong); // mission id as a long
                                    alarmTimes.add(repeatCalendar.getTimeInMillis());
                                    arrayList2D.add(alarmTimes);
                                }
                                break;

                        }
                    }
                }

            }

        }
        // TODO: determine which alarm in millis is closest to the current time instance.
        // loop through the 2d array and find the alarm closest to the current time instance
        Calendar loopTimeInstance = Calendar.getInstance();
        Long minAlarm = arrayList2D.get(0).get(1); // will contain the minimum alarm value
        for (int i=0; i<arrayList2D.size(); i++){
            Long missionID = arrayList2D.get(i).get(0); // the mission id
            Long alarmMillis = arrayList2D.get(i).get(1); // the alarm date in millis

            // compare the current instance to the alarmMillis
            // if the value is less than the argument
            if (alarmMillis.compareTo(minAlarm) < 0){
                minAlarm = alarmMillis;
            }
        }
        // return the minimum alarm
        return minAlarm;

    }

    // changes the current day of the week of a calendar object to the desired day.
    public Calendar setDay(Calendar c, int desiredDay){
        // TODO: pick either algorithm
        int currentDay = c.get(Calendar.DAY_OF_WEEK); // get the current day of the week ex: TUESDAY
        int dayDiff = desiredDay - currentDay; // the difference between the current day and the desired day
/*        // Algorithm 1
        while (currentDay != desiredDay){
            // add 1 day to the current day until it reaches the desired day, thus ensuring a future date
            c.add(Calendar.DAY_OF_WEEK, 1);
            currentDay = c.get(Calendar.DAY_OF_WEEK);
        }*/

        // Algorithm 2
        if (dayDiff < 0){ // desired day is already in the past
            // add 7 days
            dayDiff += 7;
            // add the difference in days plus 7 to the reach the desired day in the future
        }
        c.add(Calendar.DAY_OF_YEAR, dayDiff);
        return c;

    }

    // returns true if there is an alarm set in the database
   /* public Mission_DB isAlarmSet(Calendar calendar){
        Calendar c = Calendar.getInstance();
        int start;
        int target = calendar.get(Calendar.DAY_OF_WEEK);
        for (start = c.get(Calendar.DAY_OF_WEEK); start <= target; start++){
            switch (start){
                case 1: // Sunday
                    break;
                case 2: // Monday
                    break;
                case 3: // Tuesday
                    break;
                case 4: // Wednesday
                    break;
                case 5: // Thursday
                    break;
                case 6: // Friday
                    break;
                case 7: // Saturday
                    break;

            }
        }
        List<Mission_DB> dateList = MainActivity.database.dataAccessObj().findFutureDates(c); // return only the selected mission
        // if there are no elements in the list, there are no future dates
        if(dateList.isEmpty() &&){
            return false;
        }
        else{
            return true;
        }
    }*/
}
