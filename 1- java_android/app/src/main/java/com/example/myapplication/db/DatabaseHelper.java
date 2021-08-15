package com.example.myapplication.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME  = "MyDatabase";
    private static final String TABLE_USER = "User";
    private static final String KEY_ID = "user_id";
    private static final String KEY_PASSWORD = "password";
    private static final String KEY_SIGNUP_DATE  = "signup_date";


    public DatabaseHelper(@Nullable Context context) {
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE_USER = "CREATE TABLE " + TABLE_USER
                + "("
                + KEY_ID + " TEXT,"
                + KEY_PASSWORD + " TEXT,"

                + KEY_SIGNUP_DATE + " TEXT,"

                + ")";
        db.execSQL(CREATE_TABLE_USER);

    }



    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        onCreate(db);

    }


    public void addUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();


        values.put(KEY_ID, user.get_id());
        values.put(KEY_PASSWORD, user.get_password());
        values.put(KEY_SIGNUP_DATE, user.get_Signup_date());

        db.insert(TABLE_USER, null, values);
        db.close();









    }
}
