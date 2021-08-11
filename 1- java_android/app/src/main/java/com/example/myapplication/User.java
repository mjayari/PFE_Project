package com.example.myapplication;

public class User {

        int _id;
        String _Password;
        String _Signup_date;

    public User(int _id, String _Password, String _Signup_date) {
        this._id = _id;
        this._Password = _Password;
        this._Signup_date = _Signup_date;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String get_Password() {
        return this._Password;
    }

    public void set_Password(String _Password) {
        this._Password = _Password;
    }

    public String get_Signup_date() {
        return this._Signup_date;
    }

    public void set_Signup_date(String _Signup_date) {
        this._Signup_date = _Signup_date;
    }
}


