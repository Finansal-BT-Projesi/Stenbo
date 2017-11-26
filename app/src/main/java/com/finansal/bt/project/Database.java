package com.finansal.bt.project;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ecem on 26.11.2017.
 */

public class Database extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "favwords";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLO = "sozcukler";
    private static final String ROW_ID = "id";
    private static final String ROW_SOZCUK = "sozcuk";
    private static final String ROW_ANLAM = "anlami";
    public Database(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLO + "("
                + ROW_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + ROW_SOZCUK + " TEXT NOT NULL, "
                + ROW_ANLAM + " TEXT NOT NULL)");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLO);
        onCreate(db);
    }
    public void VeriEkle(String sozcuk, String anlam){
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            ContentValues cv = new ContentValues();
            cv.put(ROW_SOZCUK, sozcuk);
            cv.put(ROW_ANLAM, anlam);
            db.insert(TABLO, null,cv);
        }catch (Exception e){
        }
        db.close();
    }
    public List<String> VeriListele(){
        List<String> veriler = new ArrayList<String>();
        SQLiteDatabase db = this.getReadableDatabase();
        try {
            String[] stunlar = {ROW_ID,ROW_SOZCUK,ROW_ANLAM};
            Cursor cursor = db.query(TABLO, stunlar,null,null,null,null,null);
            while (cursor.moveToNext()){
                veriler.add(cursor.getInt(0)
                        + " - "
                        + cursor.getString(1)
                        + " : "
                        + cursor.getString(2));
            }
        }catch (Exception e){
        }
        db.close();
        return veriler;
    }
}
