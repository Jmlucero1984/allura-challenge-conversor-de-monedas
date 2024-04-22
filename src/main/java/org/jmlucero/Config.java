package org.jmlucero;

import com.google.gson.annotations.SerializedName;


public class Config {
    @SerializedName("apiURL")
    String apiURL;
    @SerializedName("UNRecognizedCurrencies")
    Currency[] UNRecognizedCurrencies;

    public String getApiURL() {
        return apiURL;
    }

    public Currency[] getSupportedCurrencies() {
        return UNRecognizedCurrencies;
    }
}
