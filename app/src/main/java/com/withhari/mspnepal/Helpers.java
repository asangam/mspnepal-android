package com.withhari.mspnepal;

import android.content.Context;
import android.content.SharedPreferences;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class Helpers {
    private static final String APP_URL = "http://api.myjson.com/bins/3r0jd";

    public static boolean isFirstRun(Context c) {
        return getPrefs(c).getBoolean("first_run", true);
    }

    public static void setFirstRun(Context c, boolean b) {
        getPrefs(c).edit().putBoolean("first_run", b).commit();
    }
    public static SharedPreferences getPrefs(Context c) {
        return c.getSharedPreferences("app_prefs", Context.MODE_PRIVATE);
    }

    public static String getData() throws Exception {
        URLConnection con =  new URL(APP_URL).openConnection();
        con.connect();
        StringBuilder str = new StringBuilder();
        String line;
        BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
        while((line = br.readLine()) != null) {
            str.append(line);
        }
        return str.toString();
    }
}