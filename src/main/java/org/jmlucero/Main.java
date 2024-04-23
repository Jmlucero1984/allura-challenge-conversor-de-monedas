package org.jmlucero;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;

import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.math.MathContext;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.*;

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
        Date date = new Date();

        DateTimeFormatter customDateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm:ss");

        // Currencies currencies = gson.fromJson(json,Currencies.class);
        String outputConverted, resultMessage, fullURL, conversionCodes = "";
        double valueToConvert = 0.0;
        int option;
        boolean continuar = true;
        while (continuar) {
            System.out.println("*********************************************\n");
            System.out.println("Sea bienvenido/a al Conversor de Moneda =]\n");
            options = fl.getTopRequestedOptionsEntity().getTopRequestedOptionsList();
            Collections.sort(options);
            for (int i = 0; i < Math.min(options.size(), 6); i++) {
                System.out.println(i + 1 + ") " + options.get(i).getOption());
            }
            System.out.println("7) Otras monedas");
            System.out.println("8) Mostrar Historial");
            System.out.println("9) Salir");
            System.out.println();
            option = checkIntInput(sc, 1, 9, "Elija una opción válida...");
            Option newOption = null;
            String finalConversionCodes;
            if (option > 0 && option < 10) {
                finalConversionCodes = null;
                if (option == 9) {
                    System.out.println("Gracias por usar el Conversor de Monedas!");
                    break;
                }
                if (option == 8) {
                    System.out.println("HISTORIAL DE TUS CONSULTAS");
                    fl.getOperationsHistory().getOperationsHistory().forEach(System.out::println);
                    System.out.println("Presione una tecla para continuar...");
                    sc.nextLine();
                    continue;
                }
                if (option == 7) {
                    int cantDisponibles = fl.getUNrecognizedCurrencies().length;
                    int origin, destiny;
                    System.out.println("LISTADO DE TODAS LAS MONEDAS DISPONIBLES");
                    for (int i = 0; i < cantDisponibles; i++) {
                        System.out.println(i + 1 + " " + fl.getUNrecognizedCurrencies()[i]);
                    }
                    origin = checkIntInput(sc, 1, cantDisponibles, "Introduzca la opción de la moneda orígen");
                    destiny = checkIntInput(sc, 1, cantDisponibles, "Introduzca la opción de la moneda destino");
                    conversionCodes = fl.getUNrecognizedCurrencies()[origin - 1].currencyCode + "/" + fl.getUNrecognizedCurrencies()[destiny - 1].currencyCode;
                   String fcc = conversionCodes;
                    if (options.stream().filter(t -> t.getCode().equals(fcc)).toArray().length == 0) {
                        newOption = new Option(
                                fl.getUNrecognizedCurrencies()[origin - 1].currencyName + " =>> " + fl.getUNrecognizedCurrencies()[destiny - 1].currencyName,
                                conversionCodes, 0);

                    }
                } else {

                    conversionCodes = options.get(option - 1).getCode();

                }
                valueToConvert =   checkDoubleInput(sc, 0, Double.MAX_VALUE, "Introduzca una cantidad a convertir");
                fullURL = baseApiURL + conversionCodes + "/" + valueToConvert;
                request = HttpRequest.newBuilder()
                        .uri(URI.create(fullURL))
                        .build();
                response = client.send(request, HttpResponse.BodyHandlers.ofString());
                if(response.statusCode()==200) {
                    if(newOption!=null) {
                        options.add(newOption);
                    }
                    gson = new Gson();

                    apiResult = gson.fromJson(response.body(), ApiResult.class);
                    double converted = apiResult.getConversionResult();
                    if (converted < 0) {
                        outputSignificant = BigDecimal.valueOf(converted);
                        outputConverted = String.valueOf(outputSignificant.round(new MathContext(3)));

                    } else {
                        outputConverted = String.format("%.2f", converted);
                    }
                    resultMessage = "El valor " + valueToConvert + " [" + apiResult.getBaseCode() + "] corresponde al valor final de =>>> " + outputConverted + " [" + apiResult.getTargetCode() + "]";
                    System.out.println(resultMessage);
                    String fcc = conversionCodes;

                    Optional<Option> opt= options.stream().filter(t -> t.getCode().equals(fcc)).findFirst();
                    if(opt.isPresent()) {
                        Option op = opt.get();
                        op.incrementTimes();

                        TimeZone tz= TimeZone.getDefault();
                        //System.out.println("DATE: "+date.toInstant());
                        //System.out.println("ZONE: "+tz);
                        ZonedDateTime nowZonedTime = ZonedDateTime.ofInstant(date.toInstant(), tz.toZoneId());

                        fl.getOperationsHistory().getOperationsHistory().add(new Operation(
                                op.getOption(),
                                op.getCode(),
                                nowZonedTime.format(customDateFormatter),
                                apiResult.getConversionRate(),
                                valueToConvert,
                                apiResult.getConversionResult()
                        ));

                    }

                    Collections.sort(options);
                    fl.saveTopRequested();
                    fl.saveHistory();
                    System.out.println("Presione cualquier tecla para continuar...");
                    sc.nextLine();
                } else {
                    System.out.println("""
                La URL de la API no ha podido enviar una respuesta exitosa para la petición realizada.
                Posibles causas:
                -URL en el archivo config.json no se ha definido correctamente.
                -Alguna de las divisas elegidas para la petición pueden no estar disponibles en este momento en el servidor
                de la API.
                Compruebe la URL o intente nuevamente más tarde.
                """);
                }
            } else {
                finalConversionCodes = null;
            }
        }
    }

    /*
    Debería poder resumirse estas dos funciones mediante el uso de un generico que extienda de Number?
     */
    public static double checkDoubleInput(Scanner sc, double min, double max, String msg) {
        double input = 0.0;
        while (true) {
            System.out.println(msg);
            try {
                input = Double.parseDouble(sc.nextLine());
                if (input < min || input > max) continue;
                break;
            } catch (Exception e) {
                System.out.println("Ha introducido un valor no válido");
            }
        }
        return input;
    }

    public static int checkIntInput(Scanner sc, int min, int max, String msg) {
        int input = 0;
        while (true) {
            System.out.println(msg);
            try {
                input = Integer.parseInt(sc.nextLine());
                if (input < min || input > max) continue;
                break;
            } catch (Exception e) {
                System.out.println("Ha introducido un valor no válido");
            }
        }
        return input;
    }

}

