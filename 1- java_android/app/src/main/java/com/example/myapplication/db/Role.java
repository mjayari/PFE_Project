package com.example.myapplication.db;

public class Role {

    int role_id;
    String title_user_or_admin;
    String definition;

    public Role(int role_id, String title_user_or_admin, String definition) {
        this.role_id = role_id;
        this.title_user_or_admin = title_user_or_admin;
        this.definition = definition;
    }

    public int getRole_id() {
        return role_id;
    }

    public void setRole_id(int role_id) {
        this.role_id = role_id;
    }

    public String getTitle_user_or_admin() {
        return title_user_or_admin;
    }

    public void setTitle_user_or_admin(String title_user_or_admin) {
        this.title_user_or_admin = title_user_or_admin;
    }

    public String getDefinition() {
        return definition;
    }

    public void setDefinition(String definition) {
        this.definition = definition;
    }
}
