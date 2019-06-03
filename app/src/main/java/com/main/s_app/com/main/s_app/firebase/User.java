package com.main.s_app.com.main.s_app.firebase;

public class User {

    private String emailAddress, firstName, surname, username, uId;

    User() {

    }

    public User(String username, String uId) {
        this.username = username;
        this.uId = uId;
    }

    public User(String emailAdress, String firstName, String surname, String username) {
        this.emailAddress = emailAdress;
        this.firstName = firstName;
        this.surname = surname;
        this.username = username;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    public String getEmailAdress() {
        return emailAddress;
    }

    public String getUsername() {
        return username;
    }
}
