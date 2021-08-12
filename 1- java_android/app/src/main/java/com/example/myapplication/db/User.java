package com.example.myapplication.db;

public class User {

        int user_id;
        String password;
        String signup_date;

    public User(int _id, String _Password, String _Signup_date) {
        this.user_id = _id ;
        this.password = _Password ;
        this.signup_date = _Signup_date;
    }

    public int get_id() {
        return user_id;
    }

    public void set_id(int user_id) {
        this.user_id = user_id;
    }

    public String get_password() {
        return this.password;
    }

    public void set_Password(String password) {
        this.password = password;
    }

    public String get_Signup_date() {
        return this.signup_date;
    }

    public void set_Signup_date(String signup_date) {
        this.signup_date = signup_date;
    }
}


