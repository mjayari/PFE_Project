package com.fst.myapplication.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.fst.myapplication.ui.Connexion.ConnexionFragment;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME  = "MyDatabase1.db";



    public DatabaseHelper(@Nullable ConnexionFragment context) {
        super(context.getContext(),DATABASE_NAME,null,DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        // Sql request for the creation of table USer
        String CREATE_TABLE_USER = "CREATE TABLE " + User.TABLE_NAME
                + "("
                + User.KEY_ID + " TEXT PRIMARY KEY,"
                + User.KEY_PASSWORD + " TEXT,"
                + User.KEY_SIGNUP_DATE + " TEXT"
                + ")";
        db.execSQL(CREATE_TABLE_USER);

        // Sql request for the creation of table Connexion
    }



    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + User.TABLE_NAME);
        onCreate(db);

    }


    public void addUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();


        values.put(User.KEY_ID, user.getUser_id());
        values.put(User.KEY_PASSWORD, user.getPassword());
        values.put(User.KEY_SIGNUP_DATE, user.getSignupDate());

        db.insert(User.TABLE_NAME, null, values);
        db.close();




    }

}
