package com.example.adity.invoicemaker.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by chandnigarg on 30/08/17.
 */

public class UserLogin {

    @SerializedName("email")
    private String email;

    @SerializedName("apiKey")
    private String apiKey;

    @SerializedName("companyName")
    private String companyName;

    @SerializedName("contactPerson")
    private String contactPerson;

    @SerializedName("phoneNo")
    private String phoneNo;

    public UserLogin(String email, String apiKey, String companyName, String contactPerson, String phoneNo) {
        this.email = email;
        this.apiKey = apiKey;
        this.companyName = companyName;
        this.contactPerson = contactPerson;
        this.phoneNo = phoneNo;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }



}
