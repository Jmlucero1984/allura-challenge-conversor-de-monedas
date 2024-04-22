package org.jmlucero;

import com.google.gson.annotations.SerializedName;

public class ApiResult {
    @SerializedName("time_last_update_utc")
    private String lastTimeUpdated;

    @SerializedName("conversion_rate")
    private double conversionRate;

    @SerializedName("base_code")
    private String baseCode;

    @SerializedName("target_code")
    private String targetCode;

    public String getBaseCode() {
        return baseCode;
    }

    public String getTargetCode() {
        return targetCode;
    }

    @SerializedName("conversion_result")
    private double conversionResult;

    public String getLastTimeUpdated() {
        return lastTimeUpdated;
    }

    public double getConversionRate() {
        return conversionRate;
    }

    public double getConversionResult() {
        return conversionResult;
    }
}

