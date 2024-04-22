package org.jmlucero;

import java.io.*;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


public class FileManager {

    private  String configFile = "src/main/resources/config.json";
    private  String topRequestedFile = "src/main/resources/topRequested.json";
    private  Config configs;

    private TopRequestedOptions topRequestedOptions;

    public TopRequestedOptions getTopRequestedOptionsEntity() {
        return topRequestedOptions;
    }

    public FileManager() throws FileNotFoundException {
        readKeyValues();
    }

    public String getApiUrl() {
        return configs.getApiURL();
    }
    public Currency[] getUNrecognizedCurrencies() {
        return configs.getSupportedCurrencies();
    }

    public void saveTopRequested() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(topRequestedOptions);
        File file = new File(topRequestedFile);
        try {
            // Crear el FileWriter y escribir el JSON en el archivo

            FileWriter writer = new FileWriter(file);
            writer.write(json);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void readKeyValues() throws FileNotFoundException {
/*
        File file = new File(configFile);
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String st;
            while ((st = br.readLine()) != null) {
                String[] pair = st.toString().split("->");
                System.out.println("PAIR");
                System.out.println(pair[0]+":"+ pair[1]);
                configs.put(pair[0], pair[1]);
            }


        } catch (FileNotFoundException e) {
            System.out.println("""
        No se ha encontrado ningun archivo de configuración en la carpeta 'resources'
        Se requiere un archivo llamado 'config.json' con la siguiente linea:
        apiUrl->[endpoint de la API, sin comillas]
        """);
        }
        catch (Exception e ){
            System.out.println(e);
        }
        */

        configs = new Gson().fromJson(new FileReader(configFile),Config.class);

        topRequestedOptions = new Gson().fromJson(new FileReader(topRequestedFile),TopRequestedOptions.class);

    }
}


