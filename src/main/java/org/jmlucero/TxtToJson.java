package org.jmlucero;

import java.io.BufferedReader;
import java.io.File;

import java.io.FileReader;

public class TxtToJson {
    private static String currencies = "src/main/resources/currencies.txt";

    public static void transformTxtToJson() {
        String output = "[";

        File file = new File(currencies);
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String st;
            String[] threesome;
            while ((st = br.readLine()) != null) {
                threesome = st.toString().split("\t");
                output += "{\"CurrencyCode\":\"" + threesome[0] + "\",";
                output += "\"CurrencyName\":\"" + threesome[1] + "\",";
                output += "\"Country\":\"" + threesome[2] + "\"},";

            }
            output+="]";
            System.out.println(output);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}