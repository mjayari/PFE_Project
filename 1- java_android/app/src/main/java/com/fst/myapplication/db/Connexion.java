package com.fst.myapplication.db;

public class Connexion {

    public static final String TABLE_NAME  = "Connexion";
    public static final String KEY_ID = "connexion_id";
    public static final String KEY_CONNEXION_TIME = "connexion_time";
    public static final String KEY_NUMBER_DOWNLOADS  = "number_downloads";


    int connexion_id;
    String connexion_time;
    int number_downloads;



    public Connexion(int connexion_id, String connexion_time, int number_downloads) {
        this.connexion_id = connexion_id;
        this.connexion_time = connexion_time;
        this.number_downloads = number_downloads;

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
}
