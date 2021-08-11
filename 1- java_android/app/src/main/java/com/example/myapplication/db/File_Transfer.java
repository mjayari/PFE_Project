package com.example.myapplication.db;

public class File_Transfer {

    int transfer_id;
    String transfer_time_upload_or_download;
    String file_name;
    String transfer_time;

    public File_Transfer(int transfer_id, String transfer_time_upload_or_download, String file_name, String transfer_time) {
        this.transfer_id = transfer_id;
        this.transfer_time_upload_or_download = transfer_time_upload_or_download;
        this.file_name = file_name;
        this.transfer_time = transfer_time;
    }

    public int getTransfer_id() {
        return transfer_id;
    }

    public void setTransfer_id(int transfer_id) {
        this.transfer_id = transfer_id;
    }

    public String getTransfer_time_upload_or_download() {
        return transfer_time_upload_or_download;
    }

    public void setTransfer_time_upload_or_download(String transfer_time_upload_or_download) {
        this.transfer_time_upload_or_download = transfer_time_upload_or_download;
    }

    public String getFile_name() {
        return file_name;
    }

    public void setFile_name(String file_name) {
        this.file_name = file_name;
    }

    public String getTransfer_time() {
        return transfer_time;
    }

    public void setTransfer_time(String transfer_time) {
        this.transfer_time = transfer_time;
    }

}

