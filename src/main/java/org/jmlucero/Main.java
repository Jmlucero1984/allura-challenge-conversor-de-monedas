package org.jmlucero;

import com.google.gson.Gson;

import java.io.IOException;

import java.math.BigDecimal;
import java.math.MathContext;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        // TxtToJson.transformTxtToJson();
        FileManager fl = new FileManager();
        ArrayList<Option> options;
        String baseApiURL = fl.getApiUrl();
        Scanner sc = new Scanner(System.in);
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request;
        HttpResponse<String> response;
        BigDecimal outputSignificant;


        Gson gson;
        ApiResult apiResult;
        // Currencies currencies = gson.fromJson(json,Currencies.class);
        String outputConverted, resultMessage,fullURL,conversionCodes="";
        double valueToConvert=0.0;
        String[] codes;


        int option;

        // "El valor de 25.0 [USD] corresponde al valor final de =>>> 26592.75 ARS
        boolean continuar = true;
        while (continuar) {
            System.out.println("******************************************************");
            System.out.println("Sea bienvenido/a al Conversor de Moneda =]");
            options = fl.getTopRequestedOptions().getTopRequestedOptions();
            Collections.sort(options);
            for (int i = 0; i < Math.min(options.size(), 6); i++) {
                System.out.println(i + 1 + ") " + options.get(i).getOption());

            }
            System.out.println("7) Otras monedas");
            System.out.println("8) Salir");
            System.out.println("Elija una opción válida");
            String input = sc.nextLine();
            try {
                option = Integer.parseInt(input);
                if (option > 0 && option <9) {
                    if(option==8) {
                        System.out.println("Gracias por usar el Conversor de Monedas");
                        break;
                    }
                    if(option==7) {
                        int cantDisponibles = fl.getUNrecognizedCurrencies().length;

                        int origin, destiny;
                        System.out.println("LISTADO DE TODAS LAS MONEDAS DISPONIBLES");
                        for (int i = 0; i < cantDisponibles; i++) {
                            System.out.println(i+1+" "+fl.getUNrecognizedCurrencies()[i]);

                        }

                        while(true) {
                            System.out.println("Introduzca la opción de la moneda orígen");
                            try {
                                origin = Integer.parseInt(sc.nextLine());
                                if (origin > cantDisponibles || origin < 1) continue;
                                break;

                            } catch (Exception e) {
                                System.out.println("Ha introducido un valor no valido");
                            }
                        }
                        while(true) {
                            System.out.println("Introduzca la opción de la moneda destino");
                            try {
                                destiny = Integer.parseInt(sc.nextLine());
                                if (destiny > cantDisponibles || destiny < 1) continue;
                                break;

                            } catch (Exception e) {
                                System.out.println("Ha introducido un valor no valido");
                            }
                        }

                        conversionCodes = fl.getUNrecognizedCurrencies()[origin-1].currencyCode+"/"+fl.getUNrecognizedCurrencies()[destiny-1].currencyCode;
                        String finalConversionCodes = conversionCodes;
                        if(options.stream().filter(t->t.getCode().equals(finalConversionCodes)).toArray().length==0){
                            Option newOption = new Option(
                                    fl.getUNrecognizedCurrencies()[origin-1].currencyName+" =>> "+fl.getUNrecognizedCurrencies()[destiny-1].currencyName,
                                    conversionCodes,0);
                            options.add(newOption);
                        }
                        while(true) {
                            System.out.println("Introduzca una cantidad a convertir");
                            try {
                                valueToConvert = Double.parseDouble(sc.nextLine());
                                break;

                            } catch (Exception e) {
                                System.out.println("Ha introducido un valor no numérico");
                            }

                        }

                    } else {
                        while(true) {
                            System.out.println("Introduzca una cantidad a convertir");
                            try {
                                valueToConvert = Double.parseDouble(sc.nextLine());
                                break;

                            } catch (Exception e) {
                                System.out.println("Ha introducido un valor no numérico");
                            }

                        }
                        conversionCodes=options.get(option - 1).getCode();

                    }
                    fullURL = baseApiURL + conversionCodes + "/" + valueToConvert;

                    request = HttpRequest.newBuilder()
                            .uri(URI.create(fullURL))
                            .build();
                    response = client.send(request, HttpResponse.BodyHandlers.ofString());
                    gson = new Gson();
                    apiResult = gson.fromJson(response.body(), ApiResult.class);
                    double converted = apiResult.getConversionResult();
                    if(converted<0) {
                        outputSignificant = BigDecimal.valueOf(converted);
                        outputConverted=String.valueOf(outputSignificant.round(new MathContext(3)));

                    } else {
                        outputConverted = String.format("%.2f",converted);
                    }

                    resultMessage = "El valor "+valueToConvert+" ["+apiResult.getBaseCode()+"] corresponde al valor final de =>>> "+outputConverted+" ["+apiResult.getTargetCode()+"]";

                    System.out.println(resultMessage);
                    options.get(option - 1).incrementTimes();
                    Collections.sort(options);
                    fl.saveTopRequested();
                    System.out.println("Presione cualquier tecla para continuar...");
                    sc.nextLine();
                } else {

                    System.out.println("No ha introducido una opción válida");
                    System.out.println("Presione cualquier tecla para continuar...");
                }
            } catch (NumberFormatException e) {
                System.out.println("Ha introducido una opcion no valida");
                System.out.println("Presione cualquier tecla para continuar...");
                sc.nextLine();
            }

        }

    }
}