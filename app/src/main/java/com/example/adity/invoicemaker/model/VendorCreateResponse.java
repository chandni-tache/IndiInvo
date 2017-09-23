package com.example.adity.invoicemaker.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by chandnigarg on 02/09/17.
 */

public class VendorCreateResponse {

    @SerializedName("error")
    private boolean error;

    @SerializedName("message")
    private String message;

    @SerializedName("vendor_id")
    private String vendorId;

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getVendorId() {
        return vendorId;
    }

    public void setVendorId(String vendorId) {
        this.vendorId = vendorId;
    }
}
