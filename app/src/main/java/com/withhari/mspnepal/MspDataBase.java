package com.withhari.mspnepal;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MspDataBase extends SQLiteOpenHelper {

    private final static String TABLE_NAME = "MSPS";
    private final static String COL_NAME = "FullName", COL_COLLEGE = "College", COL_BIO = "Bio", COL_ID = "ID";

    public MspDataBase(Context context) {
        super(context, "MSPList", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "`" + COL_NAME + "` varchar," +
                "`" + COL_BIO + "` varchar," +
                "`" + COL_COLLEGE + "` varchar," +
                "`" + COL_ID + "` integer NOT NULL PRIMARY KEY AUTOINCREMENT");
    }

    public long add(MSP msp) {
        ContentValues cv = new ContentValues();
        cv.put(COL_NAME, msp.FullName);
        cv.put(COL_COLLEGE, msp.College);
        cv.put(COL_BIO, msp.Bio);
        return getWritableDatabase().insert(TABLE_NAME, null, cv);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE " + TABLE_NAME);
        onCreate(db);
    }
}
