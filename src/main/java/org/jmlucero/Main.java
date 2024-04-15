package org.jmlucero;

import com.google.gson.Gson;

import javax.sound.midi.Soundbank;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.MathContext;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        Scanner sc = new Scanner(System.in);
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request  = HttpRequest.newBuilder()
                .uri(URI.create("https://v6.exchangerate-api.com/v6/19434b4eaed1e7b93ebf7cee/latest/USD"))
                .build();
        HttpResponse<String> response = client.send(request,HttpResponse.BodyHandlers.ofString());

        String json = response.body();
        Gson gson = new Gson();
        Currencies currencies = gson.fromJson(json,Currencies.class);
        Double ratio;


        Boolean continuar = true;
        String opciones = """
        1) Dólar =>> Peso argentino
        2) Peso argentino =>> Dólar
        3) Dólar =>> Real brasileño
        4) Real brasileño =>> Dólar
        5) Dólar =>> Peso colombiano
        6) Peso colombiano =>> Dólar
        7) Salir
        Elija una opción válida:
        """;
        String selectedCurrency ="";
        boolean  inverseConversion;
        String inputText;
        int option;
        while(continuar) {
            inverseConversion=false;
            System.out.println("******************************************************");
            System.out.println("Sea bienvenido/a al Conversor de Mondea =]");
            System.out.println(opciones);
            String input = sc.nextLine();
            try {
                option = Integer.parseInt(input);
                if(option>0 && option <8) {
                    switch (option) {
                        case 1: {
                            selectedCurrency = "ARS";
                            break;
                        }
                        case 2: {
                            selectedCurrency = "ARS";
                            inverseConversion=true;
                            break;
                        }
                        case 3: {
                            selectedCurrency = "BRL";
                            break;
                        }
                        case 4: {
                            selectedCurrency = "BRL";
                            inverseConversion=true;
                            break;
                        }
                        case 5: {
                            selectedCurrency = "COP";
                            break;
                        }
                        case 6: {
                            selectedCurrency = "COP";
                            inverseConversion=true;
                            break;
                        }
                    }
                    System.out.println("Lo introducido es: "+ input);
                   // System.out.println(response.body());
                    System.out.println("///////////");
                    ratio = currencies.getConversiones().get(selectedCurrency);
                    BigDecimal output = BigDecimal.valueOf(inverseConversion?1.0/ratio:ratio);
                    output = output.round(new MathContext(5));
                    System.out.println(output);
                    System.out.println("Presione cualquier tecla para continuar...");
                    sc.nextLine();
                    System.out.println("///////////");
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