package com.example.myapplication.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String MyDatabase = "MYDATA";
    private static final String TABLE_USER = "user";
    private static final String KEY_ID = "user-id";
    private static final String KEY_PASSWORD = "Password";
    private static final String KEY_SIGNUP = "Signup_date";


    public DatabaseHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE_USER = "CREATE TABLE " + TABLE_USER + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_PASSWORD + " TEXT,"
                + KEY_SIGNUP + " TEXT" + ")";
        db.execSQL(CREATE_TABLE_USER);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        onCreate(db);

    }

    void addUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_PASSWORD, user.get_password());
        values.put(KEY_SIGNUP, user.get_Signup_date());

    }
}
