package com.fst.myapplication.db;

public class Connexion {

    public static final String TABLE_NAME  = "Connexion";
    public static final String KEY_ID = "connexion_id";
    public static final String KEY_CONNEXION_TIME = "connexion_time";
    public static final String KEY_NUMBER_DOWNLOADS  = "number_downloads";
    public static final String KEY_NUMBER_UPLOADS  = "number_uploads";
    public static final String KEY_USER_ID = "user_id";

    int connexion_id;
    String connexion_time;
    int number_downloads;
    int number_uploads;
    String user_id;

    public Connexion(int connexion_id, String connexion_time, int number_downloads, int number_uploads,String user_id) {
        this.connexion_id = connexion_id;
        this.connexion_time = connexion_time;
        this.number_downloads = number_downloads;
        this.number_uploads = number_uploads;
        this.user_id = user_id;

    }

    public int getConnexionId() {
        return connexion_id;
    }

    public void setConnexionId(int connexion_id) {
        this.connexion_id = connexion_id;
    }

    public String getConnexionTime() {
        return connexion_time;
    }

    public void setConnexionTime(String connexion_time) {
        this.connexion_time = connexion_time;
    }

    public float getNumberDownloads() {
        return number_downloads;
    }

    public void setNumberDownloads(int number_downloads) {
        this.number_downloads = number_downloads;
    }
    public float getNumberUploads() {
        return number_uploads;
    }

    public void setNumberUploads(int number_uploads) {
        this.number_uploads = number_uploads;
    }

    public String getUserId() {
        return user_id;
    }

    public void setUserId(String user_id) {
        this.user_id = user_id;
    }

}
