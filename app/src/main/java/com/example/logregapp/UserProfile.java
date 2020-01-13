package com.example.logregapp;

public class UserProfile {
    public String userName;
    public String userEmail;
    public String userMob;

    public UserProfile(){
    }

    public UserProfile(String userName,String userEmail,String userMob){
        this.userName = userName;
        this.userEmail = userEmail;
        this.userMob = userMob;
    }

    public String getuserName(){
        return userName;
    }
    public String getuserEmail(){
        return userEmail;
    }
    public String getuserMob(){
        return userMob;
    }
    public void setuserName(String name){
        userName = name;
    }
    public void setuserMob(String mob){
        userMob = mob;
    }
    public void setUserEmail(String mail){
        userEmail = mail;
    }


}
