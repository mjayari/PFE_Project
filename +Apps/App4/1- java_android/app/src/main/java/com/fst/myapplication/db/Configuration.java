package com.fst.myapplication.db;

public class Configuration {

        public static final String TABLE_NAME  = "Configuration";
        public static final String KEY_ID = "config_id";
        public static final String KEY_PORT_NUMBER = "port_number";
        public static final String KEY_UPLOAD_PATH  = "uploads_path";
        public static final String KEY_DOWNLOAD_PATH  = "download_path";


        int config_id;
        int port_number;
        String uploads_path;
        String download_path;

        public Configuration(int config_id, int port_number, String uploads_path, String download_path) {
            this.config_id = config_id;
            this.port_number = port_number;
            this.uploads_path = uploads_path;
            this.download_path = download_path;
        }

        public Configuration() {}

        public int getConfigId() {
        return config_id;
    }

        public void setConfigId(int config_id) {
        this.config_id = config_id;
    }

        public int getPortNumber() {
        return port_number;
    }

        public void setPortNumber(int port_number) {
        this.port_number = port_number;
    }

        public String getUploadsPath() {
        return uploads_path;
    }

        public void setUploadsPath(String uploads_path) {
        this.uploads_path = uploads_path;
    }

        public String getDownloadPath() {
        return download_path;
    }

        public void setDownloadPath(String download_path) {
        this.download_path = download_path;
    }
        }
