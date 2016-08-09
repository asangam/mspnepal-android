package com.withhari.mspnepal;


import android.view.View;
import android.widget.TextView;

public class MspHolder {
    public TextView TxtName, TxtCollege, TxtF;
    public MspHolder(View v) {
        TxtName = (TextView) v.findViewById(R.id.fullName);
        TxtCollege = (TextView) v.findViewById(R.id.college);
        TxtF = (TextView) v.findViewById(R.id.firstLetter);
    }
}
