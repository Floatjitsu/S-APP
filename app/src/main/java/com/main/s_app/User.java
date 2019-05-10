package com.main.s_app;

public class User {

    private String emailAdress, firstName, surname, username;

    User() {

    }

    User(String emailAdress, String firstName, String surname, String username) {
        this.emailAdress = emailAdress;
        this.firstName = firstName;
        this.surname = surname;
        this.username = username;
    }

    public String getEmailAdress() {
        return emailAdress;
    }

    public void setEmailAdress(String emailAdress) {
        this.emailAdress = emailAdress;
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
