package org.jmlucero;

import com.google.gson.annotations.SerializedName;

public class Currency {
    @SerializedName("CurrencyCode")
    String currencyCode;
    @SerializedName("CurrencyName")
    String currencyName;
    @SerializedName("Country")
    String country;


    public String getCurrencyCode() {
        return currencyCode;
    }

    public String getCurrencyName() {
        return currencyName;
    }

    public String getCountry() {
        return country;
    }

    @Override
    public String toString() {
        return currencyCode+" "+currencyName+" "+ country;
    }
}
