package com.withhari.mspnepal;

import android.database.Cursor;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LinearLayout a2z = (LinearLayout) findViewById(R.id.A2Z);
        assert a2z != null;
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        Button btnSearch = (Button) findViewById(R.id.BtnSearch);
        assert btnSearch != null;
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText txtS = (EditText) findViewById(R.id.TxtSearch);
                assert txtS != null;
                if (txtS.getText().toString().trim().length() > 0) {
                    Show(txtS.getText().toString());
                }
            }
        });
        params.weight = 1;
        for (char a = 'A'; a <= 'Z'; a++) {
            TextView b = new TextView(this);
            b.setText(String.format("%c", a));
            b.setLayoutParams(params);
            b.setTextColor(Color.parseColor("#8888dd"));
            b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Show(((TextView) v).getText().toString(), false);
                }
            });
            a2z.addView(b);
        }
        if (Helpers.isFirstRun(this)) {
            try {
                new DownloadAndStore().execute();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        Show();
    }

    private class DownloadAndStore extends AsyncTask<String, String, String> {
        String resp;

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Helpers.setFirstRun(MainActivity.this, false);
            Show();
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                resp = Helpers.getData();
                MspDataBase db = new MspDataBase(MainActivity.this);
                JSONArray jsonList = new JSONArray(resp);
                for(int i = jsonList.length() -1 ; i >= 0; --i) {
                    JSONObject m = jsonList.getJSONObject(i);
                    db.add(new MSP(m.getString("full_name"), m.getString("college"), null));
                }
            } catch (Exception e) {
                resp = "Error: " + e.getMessage();
            }
            return resp;
        }
    }

    private void Show() {
        Show(null, false);
    }

    private void Show(String Str) {
        Show(Str, false);
    }

    private void Show(String Str, boolean Filter) {
        List<MSP> msps = new ArrayList<>();
        Cursor c = new MspDataBase(this).getMsp();
        if (c.moveToFirst()) {
            do {
                MSP msp = new MSP(
                        c.getString(c.getColumnIndex(MspDataBase.COL_NAME)),
                        c.getString(c.getColumnIndex(MspDataBase.COL_COLLEGE)),
                        c.getString(c.getColumnIndex(MspDataBase.COL_BIO))
                );
                if (Str == null) {
                    msps.add(msp);
                } else {
                    if (Filter && (msp.FullName.startsWith(Str) || msp.College.startsWith(Str))) {
                        msps.add(msp);
                    }
                    if (!Filter && (msp.FullName.contains(Str) || msp.College.contains(Str))) {
                        msps.add(msp);
                    }
                }
            } while (c.moveToNext());
        }
        ListView mspList = (ListView) findViewById(R.id.MspListView);
        assert mspList != null;
        mspList.setDivider(null);
        MspAdapter mspAdapter = new MspAdapter(this, R.layout.msp_list, msps);
        mspList.setAdapter(mspAdapter);
        mspAdapter.notifyDataSetChanged();
    }
}
