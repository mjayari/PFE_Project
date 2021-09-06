package com.fst.myapplication.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.fst.myapplication.HttpServer;
import com.fst.myapplication.MainActivity;
import com.fst.myapplication.ui.Configuration.ConfigurationFragment;
import com.fst.myapplication.ui.Connexion.ConnexionFragment;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME  = "MyDatabase5.db";

    public DatabaseHelper(@Nullable Fragment fragment) {
        super(fragment.getContext(),DATABASE_NAME,null,DATABASE_VERSION);
    }

    /*public DatabaseHelper(@Nullable ConnexionFragment context) {
        super(context.getContext(),DATABASE_NAME,null,DATABASE_VERSION);
    }

    public DatabaseHelper(@Nullable ConfigurationFragment context) {
        super(context.getContext(),DATABASE_NAME,null,DATABASE_VERSION);
    }*/


    /*public DatabaseHelper(@Nullable HttpServer context) {
        super(context.getContext(),DATABASE_NAME,null,DATABASE_VERSION);
    }*/




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
        String CREATE_TABLE_CONNEXION = "CREATE TABLE " + Connexion.TABLE_NAME
                + "("
                +Connexion.KEY_ID + " TEXT PRIMARY KEY,"
                +Connexion.KEY_CONNEXION_TIME + " TEXT,"
                +Connexion.KEY_NUMBER_DOWNLOADS + " INTEGER,"
                +Connexion.KEY_NUMBER_UPLOADS + " INTEGER,"
                +Connexion.KEY_USER_ID + " TEXT"
                + ")";
        db.execSQL(CREATE_TABLE_CONNEXION);

        // Sql request for the creation of table Configuration
        String CREATE_TABLE_CONFIGURATION = "CREATE TABLE " + Configuration.TABLE_NAME
                + "("
                +Configuration.KEY_ID + " INTEGER PRIMARY KEY,"
                +Configuration.KEY_PORT_NUMBER + " INTEGER,"
                +Configuration.KEY_UPLOAD_PATH + " TEXT,"
                +Configuration.KEY_DOWNLOAD_PATH + " TEXT"
                + ")";
        db.execSQL(CREATE_TABLE_CONFIGURATION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + User.TABLE_NAME);
        onCreate(db);

    }

    public void addUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(User.KEY_ID, user.getUserId());
        values.put(User.KEY_PASSWORD, user.getPassword());
        values.put(User.KEY_SIGNUP_DATE, user.getSignupDate());

        db.insert(User.TABLE_NAME, null, values);
        db.close();
    }


    public  void addConnexion(Connexion connexion){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values= new ContentValues();

        values.put(Connexion.KEY_ID,connexion.getConnexionId());
        values.put(Connexion.KEY_CONNEXION_TIME,connexion.getConnexionTime());
        values.put(Connexion.KEY_NUMBER_DOWNLOADS, connexion.getNumberDownloads());
        values.put(Connexion.KEY_NUMBER_UPLOADS, connexion.getNumberUploads());
        values.put(Connexion.KEY_USER_ID, connexion.getUserId());


        db.insert(Connexion.TABLE_NAME,null,values);
        db.close();
    }
    public  void addConfiguration(Configuration configuration){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values= new ContentValues();

        values.put(Configuration.KEY_ID,configuration.getConfigId());
        values.put(Configuration.KEY_PORT_NUMBER,configuration.getPortNumber());
        values.put(Configuration.KEY_UPLOAD_PATH, configuration.getUploadsPath());
        values.put(Configuration.KEY_DOWNLOAD_PATH, configuration.getDownloadPath());

        db.insert(Configuration.TABLE_NAME,null,values);
        db.close();
    }

    // below is the method for updating our courses
    public void updateConfiguration(int portNumber,String uploadPath,String downloadPath) {

        // calling a method to get writable database.
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        // on below line we are passing all values
        // along with its key and value pair.
        values.put(Configuration.KEY_PORT_NUMBER, portNumber);
        values.put(Configuration.KEY_UPLOAD_PATH, uploadPath);
        values.put(Configuration.KEY_DOWNLOAD_PATH, downloadPath);

        // on below line we are calling a update method to update our database and passing our values.
        // and we are comparing it with name of our course which is stored in original name variable.
        Log.d("log","portNumber = " + portNumber);
        db.update(Configuration.TABLE_NAME, values, "config_id=?", new String[]{"1"});
        db.close();
    }



    public int getConnexionRowsNumber(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM CONNEXION" , null);
        return cursor.getCount();
    }

    // request of check UserID  and password  in DB
    public Boolean checkusernamepassword(String user_id, String password){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("Select * from User where user_id = ? and password = ?", new String[] {user_id,password});
        if(cursor.getCount()>0)
            return true;
        else
            return false;
    }

    // request of check UserID in DB
    public Boolean checkusername(String user_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("Select * from User where user_id = ?", new String[]{user_id});
        if (cursor.getCount() > 0)
            return true;
        else
            return false;
    }

    /*public Cursor alldata() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from User ",null);
        return cursor;
    }*/

    public Configuration getConfiguration(int config_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            Cursor cursor = db.rawQuery("Select * from Configuration where config_id = ?", new String[]{String.valueOf(config_id)});
            //Boolean b = cur.moveToFirst();
            if (cursor.moveToFirst() == true) {
                String portNumber = cursor.getString(cursor.getColumnIndex(Configuration.KEY_PORT_NUMBER));

                Configuration config = new Configuration();
                config.setPortNumber(Integer.parseInt(portNumber));

                return config;
            } else
                return null;
        } catch (Exception e) {
            return null;
        }
    }


}
