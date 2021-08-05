package com.example.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {
    public static final String DBNAME = "User.db";
    public static final String TABLE_NAME ="user_table";
    public static final String COL1 = "user_id";
    public static final String COL2 = "password";
    public static final String COL3 = "signup_date";
    public static final String COL4 = "role_id";

    public static final String DBNAME1 = "Role.db";
    public static final String TABLE_NAME1 ="role_table";
    public static final String COL5 = "title";
    public static final String COL6 = "definition";
    public static final String COL7 = "role_id";

    public static final String DBNAME3 = "Connexion.db";
    public static final String TABLE_NAME3 ="connexion_table";
    public static final String COL8 = "user_id";
    public static final String COL9 = "connexion_time";
    public static final String COL10 = "number_download";
    public static final String COL11 = "number_upload";

    public static final String DBNAME4 = "FileTransfer.db";
    public static final String TABLE_NAME4 ="filetransfer_table";
    public static final String COL12 = "Transfer_id";
    public static final String COL13 = "Transfer_type";
    public static final String COL14 = "file_name";
    public static final String COL15 = "user_id";

    public static final String DBNAME5 = "ServerConfig.db";
    public static final String TABLE_NAME5 ="serverconfig_table";
    public static final String COL16 = "config_id";
    public static final String COL17 = "Tport_number";
    public static final String COL18 = "upload_path";
    public static final String COL19 = "download_path";




    public DBHelper(Context context) {

        super(context, "User.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase MyDB) {
        MyDB.execSQL("create Table users(username TEXT primary key, password TEXT)");
        MyDB.execSQL("create table role(role_id,title,definition) ");
        MyDB.execSQL("create table connexion(user_id,connexion_time,number_download,number_upload) ");
        MyDB.execSQL("create table filetransfer(transfer_id,transfer_type,file_name");
        MyDB.execSQL("create table serverconfig(role_id) ");


    }

    @Override
    public void onUpgrade(SQLiteDatabase MyDB, int i, int i1) {
        MyDB.execSQL("drop Table if exists users");
    }


}