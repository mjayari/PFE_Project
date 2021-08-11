package com.example.myapplication.db;

public class Server_Config {
    int config_id;
    float port_number;
    float upload_path;
    float download_path;

    public Server_Config(int config_id, float port_number, float upload_path, float download_path) {
        this.config_id = config_id;
        this.port_number = port_number;
        this.upload_path = upload_path;
        this.download_path = download_path;
    }

    public int getConfig_id() {
        return config_id;
    }

    public void setConfig_id(int config_id) {
        this.config_id = config_id;
    }

    public float getPort_number() {
        return port_number;
    }

    public void setPort_number(float port_number) {
        this.port_number = port_number;
    }

    public float getUpload_path() {
        return upload_path;
    }

    public void setUpload_path(float upload_path) {
        this.upload_path = upload_path;
    }

    public float getDownload_path() {
        return download_path;
    }

    public void setDownload_path(float download_path) {
        this.download_path = download_path;
    }
}
