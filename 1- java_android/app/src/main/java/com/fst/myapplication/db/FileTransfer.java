package com.fst.myapplication.db;

public class FileTransfer {

    public static final String TABLE_NAME  = "File_Transfer";
    public static final String KEY_ID = "transfer_id";
    public static final String KEY_FILE_NAME  = "file_name";
    public static final String KEY_TRANSFER_TYPE = "transfer_type";
    public static final String KEY_TRANSFER_TIME  = "transfer_time";
    public static final String KEY_CONNEXION_ID = "connexion_id";


    int transfer_id;
    String file_name;
    String transfer_type;
    String transfer_time;
    int connexion_id;

    public FileTransfer(int fileTransfer_id, String file_name, String transfer_type, String transfer_time, int connexion_id) {
        this.transfer_id = fileTransfer_id;
        this.file_name = file_name;
        this.transfer_type = transfer_type;
        this.transfer_time = transfer_time;
        this.connexion_id = connexion_id;
    }

    public int getFileTransferId() {
        return transfer_id;
    }

    public void setFileTransferId(int fileTransfer_id) {
        this.transfer_id = fileTransfer_id;
    }

    public String getTransferType() {
        return transfer_type;
    }

    public void setTransferType(String transfer_type) {
        this.transfer_type = transfer_type;
    }

    public String getTransferTime() {
        return transfer_time;
    }

    public void setTransferTime(String transfer_time) {
        this.transfer_time = transfer_time;
    }

    public String getFileName() {
        return file_name;
    }

    public void setFileName(String file_name) {
        this.file_name = file_name;
    }

    public int getConnexionId() {
        return connexion_id;
    }

    public void setConnexionId(int connexion_id) {
        this.connexion_id = connexion_id;
    }
}




