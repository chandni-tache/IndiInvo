package com.example.adity.invoicemaker.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by chandnigarg on 30/08/17.
 */

public class LoginResponse {

    @SerializedName("error")
    private String error;

    @SerializedName("users")
    private UserLogin users;

    @SerializedName("message")
    private String message;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public UserLogin getUsers() {
        return users;
    }

    public void setUsers(UserLogin users) {
        this.users = users;
    }


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
