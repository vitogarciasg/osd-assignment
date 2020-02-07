package com.easyfitness;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;

import androidx.appcompat.widget.AppCompatSpinner;

public class CustomSpinner extends AppCompatSpinner {
    AdapterView.OnItemSelectedListener listener;
    private AdapterView<?> lastParent;
    private View lastView;
    private long lastId;

    public CustomSpinner(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
        initlistner();
    }

    @Override
    public void setSelection(int position) {
        if (position == getSelectedItemPosition() && listener != null) {
            listener.onItemSelected(lastParent, lastView, position, lastId);
        } else {
            super.setSelection(position);
        }

    }

    private void initlistner() {
        // TODO Auto-generated method stub
        super.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                // TODO Auto-generated method stub
                lastParent = parent;
                lastView = view;
                lastId = id;
                if (listener != null) {
                    listener.onItemSelected(parent, view, position, id);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
                if (listener != null) {
                    listener.onNothingSelected(parent);
                }
            }
        });

    }

    public void setOnItemSelectedEvenIfUnchangedListener(
            AdapterView.OnItemSelectedListener listener) {
        this.listener = listener;
    }

}
