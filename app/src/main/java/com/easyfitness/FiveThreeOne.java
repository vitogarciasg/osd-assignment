package com.easyfitness;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;


import java.text.DecimalFormat;


public class FiveThreeOne extends Fragment {


    int selectedLift = 1;
    int a = 1, b = 0;
    boolean fiveThreeOne = true;
    boolean secondLift = false;
    boolean offDay = false;
    static boolean rounding = true;

    public static FiveThreeOne newInstance(String name, int id) {
        FiveThreeOne f = new FiveThreeOne();

        // Supply index input as an argument.
      //  Bundle args = new Bundle();
       // args.putString("name", name);
       // args.putInt("id", id);
       // f.setArguments(args);

        return f;
    }




    //// UI REF SETUP ///////////////////////////

    ConstraintLayout mainLayout;
    HorizontalScrollView horizontalScrollView;
    LinearLayout bigFourLinearLayout;
    ScrollView verticalScrollView;
    LinearLayout workoutLinearLayout;
    LinearLayout bottomButtonsLayout;

    EditText squatEditText;
    EditText benchEditText;
    EditText deadliftEditText;
    EditText overheadpressEditText;
    TextView squatTextView;
    TextView benchTextView;
    TextView deadliftTextView;
    TextView overheadpressTextView;

    CustomSpinner secondLiftSpinner;
    TextView fiveThreeOneTextView;
    TextView offDayTextView;

    TextView set1TextView;
    TextView set1NumTextView;

    TextView set2TextView;
    TextView set2NumTextView;

    TextView set3TextView;
    TextView set3NumTextView;

    TextView set4TextView;
    TextView set4NumTextView;

    TextView set5TextView;
    TextView set5NumTextView;

    TextView set6TextView;
    TextView set6NumTextView;

    TextView set7TextView;
    TextView set7NumTextView;

    TextView set8TextView;
    TextView set8NumTextView;

    TextView set9TextView;
    TextView set9NumTextView;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setContentView(R.layout.activity_531);

        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        uiRefSetup();

        squatEditText.setOnKeyListener(onKeyListener);
        benchEditText.setOnKeyListener(onKeyListener);
        deadliftEditText.setOnKeyListener(onKeyListener);
        overheadpressEditText.setOnKeyListener(onKeyListener);

        String[] spinnerItems = new String[] {"C.G. Bench", "Front Squats", "Incline Bench", "OHP", "Sumo DL"};
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(getActivity().getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, spinnerItems);
        secondLiftSpinner.setAdapter(spinnerAdapter);
        secondLiftSpinner.setOnItemSelectedEvenIfUnchangedListener(itemSelectedListener);


        ///// GETTING SAVED SHARED PREFERENCES FOR THE FOUR BIG EDIT TEXTS ///////////////////////////////////////////////
        SharedPreferences sharedPref = getActivity().getApplicationContext().getSharedPreferences("com.jalbers.nsunstest", Context.MODE_PRIVATE);
        int count = sharedPref.getInt("Count", 0);
        float[] savedWeight = new float[4];

        for (int i = 0; i < count; i++) {

            savedWeight[i] = sharedPref.getFloat("savedWeight_" + i, -1);
        }

        squatEditText.setText(String.valueOf(savedWeight[0]));
        benchEditText.setText(String.valueOf(savedWeight[1]));
        deadliftEditText.setText(String.valueOf(savedWeight[2]));
        overheadpressEditText.setText(String.valueOf(savedWeight[3]));
        //////////////////////////////////////////////////////////////////////////////////////////////

        ///// SETS THE "CENTER SNAPPING" MOTION OF THE TOP HORIZONTAL SCROLL ///////////////
        horizontalScrollView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                if (motionEvent.getAction() == MotionEvent.ACTION_UP || motionEvent.getAction() == MotionEvent.ACTION_CANCEL) {

                    int center = horizontalScrollView.getScrollX() + horizontalScrollView.getWidth()/2;
                    int childNum = bigFourLinearLayout.getChildCount();

                    for (int i = 0; i < childNum; i++) {

                        View view1 = bigFourLinearLayout.getChildAt(i);
                        int viewLeft = view1.getLeft();
                        int viewWidth = view1.getWidth();

                        if (center >= viewLeft && center <= viewLeft + viewWidth) {

                            horizontalScrollView.scrollBy((viewLeft + viewWidth /2 ) - center, 0);
                            selectedLift = i;

                            if (i == 1) {
                                //SQUAT
                                squatEditText.setTextColor(Color.WHITE);
                                squatTextView.setTextColor(Color.WHITE);

                                benchEditText.setTextColor(Color.BLACK);
                                benchTextView.setTextColor(Color.BLACK);
                                deadliftEditText.setTextColor(Color.BLACK);
                                deadliftTextView.setTextColor(Color.BLACK);
                                overheadpressEditText.setTextColor(Color.BLACK);
                                overheadpressTextView.setTextColor(Color.BLACK);

                            } else if (i == 2) {
                                //BENCH
                                benchEditText.setTextColor(Color.WHITE);
                                benchTextView.setTextColor(Color.WHITE);

                                squatEditText.setTextColor(Color.BLACK);
                                squatTextView.setTextColor(Color.BLACK);
                                deadliftEditText.setTextColor(Color.BLACK);
                                deadliftTextView.setTextColor(Color.BLACK);
                                overheadpressEditText.setTextColor(Color.BLACK);
                                overheadpressTextView.setTextColor(Color.BLACK);
                            } else if (i == 3) {
                                //DEADLIFT
                                deadliftEditText.setTextColor(Color.WHITE);
                                deadliftTextView.setTextColor(Color.WHITE);

                                benchEditText.setTextColor(Color.BLACK);
                                benchTextView.setTextColor(Color.BLACK);
                                squatEditText.setTextColor(Color.BLACK);
                                squatTextView.setTextColor(Color.BLACK);
                                overheadpressEditText.setTextColor(Color.BLACK);
                                overheadpressTextView.setTextColor(Color.BLACK);
                            }else if (i == 4) {
                                //OHP
                                overheadpressEditText.setTextColor(Color.WHITE);
                                overheadpressTextView.setTextColor(Color.WHITE);

                                benchEditText.setTextColor(Color.BLACK);
                                benchTextView.setTextColor(Color.BLACK);
                                deadliftEditText.setTextColor(Color.BLACK);
                                deadliftTextView.setTextColor(Color.BLACK);
                                squatEditText.setTextColor(Color.BLACK);
                                squatTextView.setTextColor(Color.BLACK);
                            }

                            //activeLift(selectedLift);
                            if (fiveThreeOne) setFiveThreeOneText();

                            if (offDay) setOffDayText();

                            break;
                        }
                    }
                }
                return false;
            }
        });////////////////////////////////////////////////////////////////////////////////////

        /////////////// SET HORIZONTAL SCROLL INITIAL POSITION ////////////////////////////////////////////////
        horizontalScrollView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                horizontalScrollView.smoothScrollTo(200,0); //// NEED TO SET EXACT WIDTH TO MID SQUAT LAYOUT POINT
                setFiveThreeOne(fiveThreeOneTextView);
                squatEditText.setTextColor(Color.WHITE);
                squatTextView.setTextColor(Color.WHITE);
                horizontalScrollView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });/////////////////////////////////////////////////////////////////////////////////////////////////

        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }



    ////////////// SET SECOND LIFT SPINNER LISTENER //////////////////////////////////////////////
    AdapterView.OnItemSelectedListener itemSelectedListener = (new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

            if ( b < a){
                b++;
            } else {
                setSecondLift(view);
                ((TextView) view).setTextColor(Color.WHITE);
            }
        }
        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {}
    }); ////////////////////////////////////////////////////////////////////////////////////////







    ////// SETTING NUMBERS FOR ACTUAL WORKOUT ///////////////////////

    public String round5(double n) {

        DecimalFormat format = new DecimalFormat("0.#");

        if (rounding) {
            double temp = n % 5;

            if (temp < 2.5)
                return format.format(n - temp);
            else
                return format.format(n + 5 - temp);
        } else {

            double temp2 = n % 2.5;

            if (temp2 < 1.25)
                return String.valueOf(n - temp2);
            else
                return String.valueOf(n + 2.5 - temp2);
        }

    }


    public void setFiveThreeOne (View view) {

        fiveThreeOne = true;
        secondLift = false;
        offDay = false;

        fiveThreeOneTextView.setTextColor(Color.WHITE);
        ((TextView) secondLiftSpinner.getSelectedView()).setTextColor(Color.BLACK);
        offDayTextView.setTextColor(Color.BLACK);

        LinearLayout set9LinearLayout = (LinearLayout) getActivity().findViewById(R.id.set9LinearLayout);
        set9LinearLayout.setVisibility(View.VISIBLE);

        setFiveThreeOneText();

    }
    //// SETS TEXT/WEIGHTS FOR THE MAIN 5/3/1 LIFT WHEN CLICKED OR DRAGGED //////////////
    public void setFiveThreeOneText () {

        double weight;

        if (selectedLift == 1) {
            //SQUAT
            weight = Double.parseDouble(squatEditText.getText().toString());

            set1NumTextView.setText(round5(weight * .75) + " x 5");
            set2NumTextView.setText(round5(weight * .85) + " x 3");
            set3NumTextView.setText(round5(weight * .95) + " x 1+");
            set4NumTextView.setText(round5(weight * .90) + " x 3");
            set5NumTextView.setText(round5(weight * .85) + " x 3");
            set6NumTextView.setText(round5(weight * .80) + " x 3");
            set7NumTextView.setText(round5(weight * .75) + " x 5");
            set8NumTextView.setText(round5(weight * .70) + " x 5");
            set9NumTextView.setText(round5(weight * .65) + " x 5+");

            set1TextView.setText("Set 1 - 75%");
            set2TextView.setText("Set 2 - 85%");
            set3TextView.setText("Set 3 - 95%");
            set4TextView.setText("Set 4 - 90%");
            set5TextView.setText("Set 5 - 85%");
            set6TextView.setText("Set 6 - 80%");
            set7TextView.setText("Set 7 - 75%");
            set8TextView.setText("Set 8 - 70%");
            set9TextView.setText("Set 9 - 65%");

        } else if (selectedLift == 2) {
            //BENCH
            weight = Double.parseDouble(benchEditText.getText().toString());

            set1NumTextView.setText(round5(weight * .75) + " x 5");
            set2NumTextView.setText(round5(weight * .85) + " x 3");
            set3NumTextView.setText(round5(weight * .95) + " x 1+");
            set4NumTextView.setText(round5(weight * .90) + " x 3");
            set5NumTextView.setText(round5(weight * .85) + " x 5");
            set6NumTextView.setText(round5(weight * .80) + " x 3");
            set7NumTextView.setText(round5(weight * .75) + " x 5");
            set8NumTextView.setText(round5(weight * .70) + " x 3");
            set9NumTextView.setText(round5(weight * .65) + " x 5+");

            set1TextView.setText("Set 1 - 75%");
            set2TextView.setText("Set 2 - 85%");
            set3TextView.setText("Set 3 - 95%");
            set4TextView.setText("Set 4 - 90%");
            set5TextView.setText("Set 5 - 85%");
            set6TextView.setText("Set 6 - 80%");
            set7TextView.setText("Set 7 - 75%");
            set8TextView.setText("Set 8 - 70%");
            set9TextView.setText("Set 9 - 65%");

        } else if (selectedLift == 3) {
            //DEAD LIFT
            weight = Double.parseDouble(deadliftEditText.getText().toString());

            set1NumTextView.setText(round5(weight * .75) + " x 5");
            set2NumTextView.setText(round5(weight * .85) + " x 3");
            set3NumTextView.setText(round5(weight * .95) + " x 1+");
            set4NumTextView.setText(round5(weight * .90) + " x 3");
            set5NumTextView.setText(round5(weight * .85) + " x 3");
            set6NumTextView.setText(round5(weight * .80) + " x 3");
            set7NumTextView.setText(round5(weight * .75) + " x 3");
            set8NumTextView.setText(round5(weight * .70) + " x 3");
            set9NumTextView.setText(round5(weight * .65) + " x 3+");

            set1TextView.setText("Set 1 - 75%");
            set2TextView.setText("Set 2 - 85%");
            set3TextView.setText("Set 3 - 95%");
            set4TextView.setText("Set 4 - 90%");
            set5TextView.setText("Set 5 - 85%");
            set6TextView.setText("Set 6 - 80%");
            set7TextView.setText("Set 7 - 75%");
            set8TextView.setText("Set 8 - 70%");
            set9TextView.setText("Set 9 - 65%");

        } else if (selectedLift == 4) {
            //OHP
            weight = Double.parseDouble(overheadpressEditText.getText().toString());

            set1NumTextView.setText(round5(weight * .75) + " x 5");
            set2NumTextView.setText(round5(weight * .85) + " x 3");
            set3NumTextView.setText(round5(weight * .95) + " x 1+");
            set4NumTextView.setText(round5(weight * .90) + " x 3");
            set5NumTextView.setText(round5(weight * .85) + " x 3");
            set6NumTextView.setText(round5(weight * .80) + " x 3");
            set7NumTextView.setText(round5(weight * .75) + " x 5");
            set8NumTextView.setText(round5(weight * .70) + " x 5");
            set9NumTextView.setText(round5(weight * .65) + " x 5+");

            set1TextView.setText("Set 1 - 75%");
            set2TextView.setText("Set 2 - 85%");
            set3TextView.setText("Set 3 - 95%");
            set4TextView.setText("Set 4 - 90%");
            set5TextView.setText("Set 5 - 85%");
            set6TextView.setText("Set 6 - 80%");
            set7TextView.setText("Set 7 - 75%");
            set8TextView.setText("Set 8 - 70%");
            set9TextView.setText("Set 9 - 65%");
        }

    } /////////////////////////////////////////////////////////////////

    //// SETS TEXT/WEIGHTS FOR THE SECONDARY LIFT WHEN CLICKED OR DRAGGED //////////////
    public void setSecondLift (View view) {

        secondLift = true;
        fiveThreeOne = false;
        offDay = false;


        fiveThreeOneTextView.setTextColor(Color.BLACK);
        offDayTextView.setTextColor(Color.BLACK);

        LinearLayout set9LinearLayout = (LinearLayout) getActivity().findViewById(R.id.set9LinearLayout);
        set9LinearLayout.setVisibility(View.GONE);

        setSecondLiftText();
    }

    public void setSecondLiftText () {

        double weight;

        if (secondLiftSpinner.getSelectedItemPosition() == 4) {
            //SUMO DL
            weight = Double.parseDouble(deadliftEditText.getText().toString());

            set1NumTextView.setText(round5(weight * .5) + " x 5");
            set2NumTextView.setText(round5(weight * .6) + " x 5");
            set3NumTextView.setText(round5(weight * .7) + " x 3");
            set4NumTextView.setText(round5(weight * .7) + " x 5");
            set5NumTextView.setText(round5(weight * .7) + " x 7");
            set6NumTextView.setText(round5(weight * .7) + " x 4");
            set7NumTextView.setText(round5(weight * .7) + " x 6");
            set8NumTextView.setText(round5(weight * .7) + " x 8");

            set1TextView.setText("Set 1 - 50%");
            set2TextView.setText("Set 2 - 60%");
            set3TextView.setText("Set 3 - 70%");
            set4TextView.setText("Set 4 - 70%");
            set5TextView.setText("Set 5 - 70%");
            set6TextView.setText("Set 6 - 70%");
            set7TextView.setText("Set 7 - 70%");
            set8TextView.setText("Set 8 - 70%");

        } else if (secondLiftSpinner.getSelectedItemPosition() == 0) {
            //CG BENCH
            weight = Double.parseDouble(benchEditText.getText().toString());

            set1NumTextView.setText(round5(weight * .4) + " x 5");
            set2NumTextView.setText(round5(weight * .5) + " x 5");
            set3NumTextView.setText(round5(weight * .6) + " x 3");
            set4NumTextView.setText(round5(weight * .6) + " x 5");
            set5NumTextView.setText(round5(weight * .6) + " x 7");
            set6NumTextView.setText(round5(weight * .6) + " x 4");
            set7NumTextView.setText(round5(weight * .6) + " x 6");
            set8NumTextView.setText(round5(weight * .6) + " x 8");

            set1TextView.setText("Set 1 - 40%");
            set2TextView.setText("Set 2 - 50%");
            set3TextView.setText("Set 3 - 60%");
            set4TextView.setText("Set 4 - 60%");
            set5TextView.setText("Set 5 - 60%");
            set6TextView.setText("Set 6 - 60%");
            set7TextView.setText("Set 7 - 60%");
            set8TextView.setText("Set 8 - 60%");

        } else if (secondLiftSpinner.getSelectedItemPosition() == 1) {
            //FRONT SQUAT
            weight = Double.parseDouble(squatEditText.getText().toString());

            set1NumTextView.setText(round5(weight * .35) + " x 5");
            set2NumTextView.setText(round5(weight * .45) + " x 5");
            set3NumTextView.setText(round5(weight * .55) + " x 3");
            set4NumTextView.setText(round5(weight * .55) + " x 5");
            set5NumTextView.setText(round5(weight * .55) + " x 7");
            set6NumTextView.setText(round5(weight * .55) + " x 4");
            set7NumTextView.setText(round5(weight * .55) + " x 6");
            set8NumTextView.setText(round5(weight * .55) + " x 8");

            set1TextView.setText("Set 1 - 35%");
            set2TextView.setText("Set 2 - 45%");
            set3TextView.setText("Set 3 - 55%");
            set4TextView.setText("Set 4 - 55%");
            set5TextView.setText("Set 5 - 55%");
            set6TextView.setText("Set 6 - 55%");
            set7TextView.setText("Set 7 - 55%");
            set8TextView.setText("Set 8 - 55%");

        } else if (secondLiftSpinner.getSelectedItemPosition() == 2) {
            //INCLINE BENCH
            weight = Double.parseDouble(benchEditText.getText().toString());

            set1NumTextView.setText(round5(weight * .4) + " x 5");
            set2NumTextView.setText(round5(weight * .5) + " x 5");
            set3NumTextView.setText(round5(weight * .6) + " x 3");
            set4NumTextView.setText(round5(weight * .6) + " x 5");
            set5NumTextView.setText(round5(weight * .6) + " x 7");
            set6NumTextView.setText(round5(weight * .6) + " x 4");
            set7NumTextView.setText(round5(weight * .6) + " x 6");
            set8NumTextView.setText(round5(weight * .6) + " x 8");

            set1TextView.setText("Set 1 - 40%");
            set2TextView.setText("Set 2 - 50%");
            set3TextView.setText("Set 3 - 60%");
            set4TextView.setText("Set 4 - 60%");
            set5TextView.setText("Set 5 - 60%");
            set6TextView.setText("Set 6 - 60%");
            set7TextView.setText("Set 7 - 60%");
            set8TextView.setText("Set 8 - 60%");

        } else if (secondLiftSpinner.getSelectedItemPosition() == 3) {

            weight = Double.parseDouble(overheadpressEditText.getText().toString());

            set1NumTextView.setText(round5(weight * .4) + " x 5");
            set2NumTextView.setText(round5(weight * .5) + " x 5");
            set3NumTextView.setText(round5(weight * .6) + " x 3");
            set4NumTextView.setText(round5(weight * .6) + " x 5");
            set5NumTextView.setText(round5(weight * .6) + " x 7");
            set6NumTextView.setText(round5(weight * .6) + " x 4");
            set7NumTextView.setText(round5(weight * .6) + " x 6");
            set8NumTextView.setText(round5(weight * .6) + " x 8");

            set1TextView.setText("Set 1 - 50%");
            set2TextView.setText("Set 2 - 60%");
            set3TextView.setText("Set 3 - 70%");
            set4TextView.setText("Set 4 - 70%");
            set5TextView.setText("Set 5 - 70%");
            set6TextView.setText("Set 6 - 70%");
            set7TextView.setText("Set 7 - 70%");
            set8TextView.setText("Set 8 - 70%");


        }
    }

    //// SETS TEXT/WEIGHTS FOR THE OFF DAY WHEN CLICKED OR DRAGGED //////////////
    public void setOffDay(View view) {

        fiveThreeOne = false;
        secondLift = false;
        offDay = true;

        fiveThreeOneTextView.setTextColor(Color.BLACK);

        ((TextView) secondLiftSpinner.getSelectedView()).setTextColor(Color.BLACK);
        offDayTextView.setTextColor(Color.WHITE);

        LinearLayout set9LinearLayout = (LinearLayout) getActivity().findViewById(R.id.set9LinearLayout);
        set9LinearLayout.setVisibility(View.VISIBLE);

        setOffDayText();

    }

    public void setOffDayText() {

        double weight;

        if (selectedLift == 1) {
            //SQUAT
            weight = Double.parseDouble(squatEditText.getText().toString());

            set1NumTextView.setText(round5(weight * .725) + " x 3");
            set2NumTextView.setText(round5(weight * .725) + " x 3");
            set3NumTextView.setText(round5(weight * .725) + " x 3");
            set4NumTextView.setText(round5(weight * .725) + " x 3");
            set5NumTextView.setText(round5(weight * .725) + " x 3");
            set6NumTextView.setText(round5(weight * .725) + " x 3");
            set7NumTextView.setText(round5(weight * .725) + " x 3");
            set8NumTextView.setText(round5(weight * .725) + " x 3");
            set9NumTextView.setText(round5(weight * .725) + " x 3+");

            set1TextView.setText("Set 1 - 72.5%");
            set2TextView.setText("Set 2 - 72.5%");
            set3TextView.setText("Set 3 - 72.5%");
            set4TextView.setText("Set 4 - 72.5%");
            set5TextView.setText("Set 5 - 72.5%");
            set6TextView.setText("Set 6 - 72.5%");
            set7TextView.setText("Set 7 - 72.5%");
            set8TextView.setText("Set 8 - 72.5%");
            set9TextView.setText("Set 9 - 72.5%");

        } else if (selectedLift == 2) {
            //BENCH
            weight = Double.parseDouble(benchEditText.getText().toString());

            set1NumTextView.setText(round5(weight * .65) + " x 8");
            set2NumTextView.setText(round5(weight * .75) + " x 6");
            set3NumTextView.setText(round5(weight * .85) + " x 4");
            set4NumTextView.setText(round5(weight * .85) + " x 4");
            set5NumTextView.setText(round5(weight * .85) + " x 4");
            set6NumTextView.setText(round5(weight * .80) + " x 5");
            set7NumTextView.setText(round5(weight * .75) + " x 6");
            set8NumTextView.setText(round5(weight * .70) + " x 7");
            set9NumTextView.setText(round5(weight * .65) + " x 8+");

            set1TextView.setText("Set 1 - 65%");
            set2TextView.setText("Set 2 - 75%");
            set3TextView.setText("Set 3 - 85%");
            set4TextView.setText("Set 4 - 85%");
            set5TextView.setText("Set 5 - 85%");
            set6TextView.setText("Set 6 - 80%");
            set7TextView.setText("Set 7 - 75%");
            set8TextView.setText("Set 8 - 70%");
            set9TextView.setText("Set 9 - 65%");

        } else if (selectedLift == 3) {
            //DEAD LIFT
            weight = Double.parseDouble(deadliftEditText.getText().toString());

            set1NumTextView.setText(round5(weight * .725) + " x 3");
            set2NumTextView.setText(round5(weight * .725) + " x 3");
            set3NumTextView.setText(round5(weight * .725) + " x 3");
            set4NumTextView.setText(round5(weight * .725) + " x 3");
            set5NumTextView.setText(round5(weight * .725) + " x 3");
            set6NumTextView.setText(round5(weight * .725) + " x 3");
            set7NumTextView.setText(round5(weight * .725) + " x 3");
            set8NumTextView.setText(round5(weight * .725) + " x 3");
            set9NumTextView.setText(round5(weight * .725) + " x 3+");

            set1TextView.setText("Set 1 - 72.5%");
            set2TextView.setText("Set 2 - 72.5%");
            set3TextView.setText("Set 3 - 72.5%");
            set4TextView.setText("Set 4 - 72.5%");
            set5TextView.setText("Set 5 - 72.5%");
            set6TextView.setText("Set 6 - 72.5%");
            set7TextView.setText("Set 7 - 72.5%");
            set8TextView.setText("Set 8 - 72.5%");
            set9TextView.setText("Set 9 - 72.5%");

        } else if (selectedLift == 4) {
            //OHP
            weight = Double.parseDouble(overheadpressEditText.getText().toString());

            set1NumTextView.setText(round5(weight * .65) + " x 8");
            set2NumTextView.setText(round5(weight * .75) + " x 6");
            set3NumTextView.setText(round5(weight * .85) + " x 4");
            set4NumTextView.setText(round5(weight * .85) + " x 4");
            set5NumTextView.setText(round5(weight * .85) + " x 4");
            set6NumTextView.setText(round5(weight * .80) + " x 5");
            set7NumTextView.setText(round5(weight * .75) + " x 6");
            set8NumTextView.setText(round5(weight * .70) + " x 7");
            set9NumTextView.setText(round5(weight * .65) + " x 8+");

            set1TextView.setText("Set 1 - 65%");
            set2TextView.setText("Set 2 - 75%");
            set3TextView.setText("Set 3 - 85%");
            set4TextView.setText("Set 4 - 85%");
            set5TextView.setText("Set 5 - 85%");
            set6TextView.setText("Set 6 - 80%");
            set7TextView.setText("Set 7 - 75%");
            set8TextView.setText("Set 8 - 70%");
            set9TextView.setText("Set 9 - 65%");
        }

    } ////////////////////////////////////////////////////////////////////
    //////////////// END SETTING WORKOUT TEXTS/NUMBERS ////////////////////


    //// CODE FOR EDITING MAIN LIFT TEXTS AT TOP OF FIELD ////////////////////////
    View.OnKeyListener onKeyListener = new View.OnKeyListener() {
        @Override
        public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
            // If the event is a key-down event on the "enter" button
            if ((keyEvent.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER)) {
                // Perform action on key press

                float[] savedWeights = {Float.parseFloat(squatEditText.getText().toString()), Float.parseFloat(benchEditText.getText().toString()),
                    Float.parseFloat(deadliftEditText.getText().toString()), Float.parseFloat(overheadpressEditText.getText().toString())};

                saveWeight(savedWeights);

                try {
                    if (fiveThreeOne) {
                        setFiveThreeOneText();
                    } else if (secondLift) {
                        setSecondLiftText();
                    } else if (offDay) {
                        setOffDayText();
                    }
                    InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
                    inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
                    return true;

                } catch (Exception e) {

                    Toast.makeText(getActivity().getApplicationContext(), "Please insert a number", Toast.LENGTH_SHORT).show();
                }

            }

            return false;
        }
    }; ////////////////////////////////////////////////////////////////////////////////////












    public void dimScreen() {

        if (horizontalScrollView.ALPHA.get(horizontalScrollView) == 1) {

            horizontalScrollView.setAlpha((float) .25);
            verticalScrollView.setAlpha((float) .25);
            workoutLinearLayout.setAlpha((float) .25);
            bottomButtonsLayout.setAlpha((float) .25);

        } else if (horizontalScrollView.ALPHA.get(horizontalScrollView) == .25) {

            horizontalScrollView.setAlpha((float) 1);
            verticalScrollView.setAlpha((float) 1);
            workoutLinearLayout.setAlpha((float) 1);
            bottomButtonsLayout.setAlpha((float) 1);
        }
    }









    public void saveWeight (float[] array) {

        SharedPreferences sharedPref = getActivity().getApplicationContext().getSharedPreferences("com.jalbers.nsunstest", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt("Count", array.length);

        int count = 0;
        for (float i : array) {
            editor.putFloat("savedWeight_" + count++, i);
        }
        editor.apply();
    }





    ////////////////////////////////////ALL THE UI FINDVIEWBYID STUFF/////////////////////////////////////////////////////
    public void uiRefSetup () {

        mainLayout = (ConstraintLayout) getActivity().findViewById(R.id.mainlayout);
        horizontalScrollView = (HorizontalScrollView) getActivity().findViewById(R.id.horizontalScrollView);
        bigFourLinearLayout = (LinearLayout) getActivity().findViewById(R.id.bigFourLayout);
        verticalScrollView = (ScrollView) getActivity().findViewById(R.id.verticalScrollView);
        workoutLinearLayout = (LinearLayout) getActivity().findViewById(R.id.workoutLinearLayout);
        bottomButtonsLayout = (LinearLayout) getActivity().findViewById(R.id.bottomButtonsLayout);


        squatEditText = (EditText) getActivity().findViewById(R.id.squatEditText);
        benchEditText = (EditText) getActivity().findViewById(R.id.benchEditText);
        deadliftEditText = (EditText) getActivity().findViewById(R.id.deadliftEditText);
        overheadpressEditText = (EditText) getActivity().findViewById(R.id.overheadpressEditText);
        squatTextView = (TextView) getActivity().findViewById(R.id.squatTextView);
        benchTextView = (TextView) getActivity().findViewById(R.id.benchTextView);
        deadliftTextView = (TextView) getActivity().findViewById(R.id.deadliftTextView);
        overheadpressTextView = (TextView) getActivity().findViewById(R.id.overheadpressTextView);

        secondLiftSpinner = (CustomSpinner) getActivity().findViewById(R.id.secondLiftSpinner);
        fiveThreeOneTextView = (TextView) getActivity().findViewById(R.id.fiveThreeOneTextView);
        offDayTextView = (TextView) getActivity().findViewById(R.id.offdayTextView);


        set1TextView = (TextView) getActivity().findViewById(R.id.set1TextView);
        set1NumTextView = (TextView) getActivity().findViewById(R.id.set1NumTextView);

        set2TextView = (TextView) getActivity().findViewById(R.id.set2TextView);
        set2NumTextView = (TextView) getActivity().findViewById(R.id.set2NumTextView);

        set3TextView = (TextView) getActivity().findViewById(R.id.set3TextView);
        set3NumTextView = (TextView) getActivity().findViewById(R.id.set3NumTextView);

        set4TextView = (TextView) getActivity().findViewById(R.id.set4TextView);
        set4NumTextView = (TextView) getActivity().findViewById(R.id.set4NumTextView);

        set5TextView = (TextView) getActivity().findViewById(R.id.set5TextView);
        set5NumTextView = (TextView) getActivity().findViewById(R.id.set5NumTextView);

        set6TextView = (TextView) getActivity().findViewById(R.id.set6TextView);
        set6NumTextView = (TextView) getActivity().findViewById(R.id.set6NumTextView);

        set7TextView = (TextView) getActivity().findViewById(R.id.set7TextView);
        set7NumTextView = (TextView) getActivity().findViewById(R.id.set7NumTextView);

        set8TextView = (TextView) getActivity().findViewById(R.id.set8TextView);
        set8NumTextView = (TextView) getActivity().findViewById(R.id.set8NumTextView);

        set9TextView = (TextView) getActivity().findViewById(R.id.set9TextView);
        set9NumTextView = (TextView) getActivity().findViewById(R.id.set9NumTextView);
    }
}

