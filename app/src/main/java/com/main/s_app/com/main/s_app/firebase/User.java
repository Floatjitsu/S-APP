package com.main.s_app.com.main.s_app.firebase;

public class User {

    private String emailAddress, firstName, surname, username, uId;

    User() {

    }

    User(String username, String uId) {
        this.username = username;
        this.uId = uId;
    }

    public User(String emailAdress, String firstName, String surname, String username) {
        this.emailAddress = emailAdress;
        this.firstName = firstName;
        this.surname = surname;
        this.username = username;
    }

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    public String getEmailAdress() {
        return emailAddress;
    }

    public void setEmailAdress(String emailAdress) {
        this.emailAddress = emailAdress;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
