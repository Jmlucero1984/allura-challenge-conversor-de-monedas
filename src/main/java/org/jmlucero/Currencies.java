package org.jmlucero;

import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Currencies {
    @SerializedName("result")

    String resultado;
    @SerializedName("time_last_update_utc")
    String ultima_actualizacion;

    public Map<String, Double> getConversiones() {
        return conversiones;
    }

    @SerializedName("conversion_rates")
    Map<String,Double> conversiones = new HashMap<>();


}
