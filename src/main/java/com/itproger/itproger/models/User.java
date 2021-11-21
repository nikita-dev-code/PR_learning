package com.itproger.itproger.models;

import java.io.Serializable;

public class User implements Serializable {

    private String login;

    public String getLogin() {
        return login;
    }

    public User(String login) {
        this.login = login;
    }
}
