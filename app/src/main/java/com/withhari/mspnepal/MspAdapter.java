package com.withhari.mspnepal;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.List;

public class MspAdapter extends ArrayAdapter<MSP> {

    private List<MSP> msps;
    private Context c;
    private int res;

    public MspAdapter(Context context, int resource, List<MSP> objects) {
        super(context, resource, objects);
        msps = objects;
        res = resource;
        c = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MspHolder mh;
        if (convertView == null) {
            convertView = LayoutInflater.from(c).inflate(res, parent, false);
            mh = new MspHolder(convertView);
            convertView.setTag(mh);
        } else {
            mh = (MspHolder) convertView.getTag();
        }
        MSP msp = getItem(position);
        mh.TxtCollege.setText(msp.College);
        mh.TxtName.setText(msp.FullName);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            mh.TxtF.setBackground(getBackground(msp));
        } else {
            mh.TxtF.setBackgroundDrawable(getBackground(msp));
        }
        mh.TxtF.setText(msp.FullName.substring(0, 1));
        return convertView;
    }

    private Drawable getBackground(MSP msp) {
        GradientDrawable gd = new GradientDrawable();
        gd.setColor(Color.BLUE);
        return gd;
    }

    @Override
    public int getItemViewType(int position) {
        return 1;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public int getCount() {
        return msps.size();
    }

    @Override
    public MSP getItem(int position) {
        return msps.get(position);
    }
}
