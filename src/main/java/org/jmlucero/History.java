package org.jmlucero;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class History {
    @SerializedName("history")
    ArrayList<Operation> operationsHistory;

    public ArrayList<Operation> getOperationsHistory() {
        return operationsHistory;
    }
}
