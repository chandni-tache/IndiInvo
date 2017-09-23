package com.example.adity.invoicemaker.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by chandnigarg on 02/09/17.
 */

public class VendorListResponse {

    @SerializedName("error")
    private boolean error;

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public List<VendorDetails> getVendorDetailsList() {
        return vendorDetailsList;
    }

    public void setVendorDetailsList(List<VendorDetails> vendorDetailsList) {
        this.vendorDetailsList = vendorDetailsList;
    }

    @SerializedName("vendors")
    private List<VendorDetails> vendorDetailsList;
}
