package com.fst.myapplication.db;

public class User {

    public static final String TABLE_NAME = "User";
    public static final String KEY_ID = "user_id";
    public static final String KEY_PASSWORD = "password";
    public static final String KEY_SIGNUP_DATE  = "signup_date";

    String user_id;
    String password;
    String signup_date;

    public User(String user_id, String password, String signup_date) {
        this.user_id = user_id;
        this.password = password;
        this.signup_date = signup_date;
    }

    public String getUserId() {
        return user_id;
    }

    public void setUserId(String user_id) {
        this.user_id = user_id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSignupDate() {
        return signup_date;
    }

    public void setSignupDate(String signup_date) {
        this.signup_date = signup_date;
    }


}




