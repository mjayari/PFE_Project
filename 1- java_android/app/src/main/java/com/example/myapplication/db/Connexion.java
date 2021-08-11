package com.example.myapplication.db;

public class Connexion {

    int connexion_id;
    String connexion_time;
    float number_downloads;

    public Connexion(int connexion_id, String connexion_time, float number_downloads) {
        this.connexion_id = connexion_id;
        this.connexion_time = connexion_time;
        this.number_downloads = number_downloads;
    }

    public int getConnexion_id() {
        return connexion_id;
    }

    public void setConnexion_id(int connexion_id) {
        this.connexion_id = connexion_id;
    }

    public String getConnexion_time() {
        return connexion_time;
    }

    public void setConnexion_time(String connexion_time) {
        this.connexion_time = connexion_time;
    }

    public float getNumber_downloads() {
        return number_downloads;
    }

    public void setNumber_downloads(float number_downloads) {
        this.number_downloads = number_downloads;
    }
}
