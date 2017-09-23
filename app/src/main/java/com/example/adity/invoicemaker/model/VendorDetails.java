package com.example.adity.invoicemaker.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by chandnigarg on 02/09/17.
 */

public class VendorDetails {

    @SerializedName("id")
    private String id;

    @SerializedName("name")
    private String name;

    @SerializedName("email")
    private String email;

    @SerializedName("phoneNo")
    private String phoneNo;

    @SerializedName("country")
    private String country;


    @SerializedName("state")
    private String state;

    @SerializedName("zipCode")
    private String zipCode;

    @SerializedName("address1")
    private String address1;

    @SerializedName("address2")
    private String address2;

    @SerializedName("gstin")
    private String gstin;



    @SerializedName("panNo")
    private  String panNo;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getGstin() {
        return gstin;
    }

    public void setGstin(String gstin) {
        this.gstin = gstin;
    }

    public String getPanNo() {
        return panNo;
    }

    public void setPanNo(String panNo) {
        this.panNo = panNo;
    }

    public VendorDetails(String id, String name, String email, String phoneNo, String country, String state, String zipCode, String address1, String address2, String gstin, String panNo) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phoneNo = phoneNo;
        this.country = country;
        this.state = state;
        this.zipCode = zipCode;
        this.address1 = address1;
        this.address2 = address2;
        this.gstin = gstin;
        this.panNo = panNo;
    }


}
