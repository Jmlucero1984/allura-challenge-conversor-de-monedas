package org.jmlucero;

import com.google.gson.annotations.SerializedName;

public class Option implements Comparable<Option> {
    @SerializedName("Option")
    String option;

    @SerializedName("Code")
    String code;

    @SerializedName("Times")
    int times;

    public String getOption() {
        return option;
    }

    public void setOption(String option) {
        this.option = option;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getTimes() {
        return times;
    }

    public void setTimes(int times) {
        this.times = times;
    }
    public void incrementTimes() {
        times++;
    }

    public Option(String option, String code, int times) {
        this.option = option;
        this.code = code;
        this.times = times;
}

    @Override
    public int compareTo(Option o) {
        if (this.getTimes()>o.getTimes()) {

            return -1;
        }else if (this.getTimes()<o.getTimes()) {
            return 1;
        }else {
            return 0;
        }
    }

}
