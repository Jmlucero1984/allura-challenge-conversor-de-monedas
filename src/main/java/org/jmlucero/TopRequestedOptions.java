package org.jmlucero;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class TopRequestedOptions {
    @SerializedName("topRequested")
    ArrayList<Option> topRequestedOptions;

    public ArrayList<Option> getTopRequestedOptions() {
        return topRequestedOptions;
    }

    public void setTopRequestedOptions(ArrayList<Option> topRequestedOptions) {
        this.topRequestedOptions = topRequestedOptions;
    }

}
