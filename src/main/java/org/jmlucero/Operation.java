package org.jmlucero;

import com.google.gson.annotations.SerializedName;

public class Operation {
    @SerializedName("Date")
    String date;
    @SerializedName("Option")
    String option;

    @SerializedName("Code")
    String code;



    @SerializedName("Ratio")
    Double ratio;

    @SerializedName("Input")
    Double input;
    @SerializedName("Converted")
    Double converted;

    public Operation(String option, String code, String date, Double ratio, Double input, Double converted) {
        this.option = option;
        this.code = code;
        this.date = date;
        this.ratio = ratio;
        this.input = input;
        this.converted = converted;
    }

    @Override
    public String toString() {
        return   "FECHA: "+date+" | OPERACION: "+code + " ("+option+") | RATIO: "+ratio+" | ENTRADA: "+input+" | CONVERTIDO: "+converted;



    }
}
