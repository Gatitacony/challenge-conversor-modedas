package com.miempresa.miapp;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.hc.client5.http.fluent.Request;
import org.apache.hc.client5.http.fluent.Content;

import java.util.Scanner;

public class ConversorApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int opcion;
        double cantidad;

        do {
            System.out.println("***********************************************");
            System.out.println("    Bienvenido/a al Conversor de Moneda :)    ");
            System.out.println("***********************************************");
            System.out.println("1) Dólar => Peso chileno");
            System.out.println("2) Peso chileno => Dólar");
            System.out.println("3) Salir");
            System.out.println("***********************************************");
            System.out.print("Elija una opción válida: ");
            opcion = scanner.nextInt();

            if (opcion == 1 || opcion == 2) {
                System.out.print("Ingrese la cantidad que desea convertir: ");
                cantidad = scanner.nextDouble();

                double resultado = realizarConversion(opcion, cantidad);
                if (resultado != 0.0) {
                    System.out.println("El resultado de la conversión es: " + resultado);
                } else {
                    System.out.println("No se pudo realizar la conversión. Verifique su conexión a Internet o intente más tarde.");
                }
            } else if (opcion == 3) {
                System.out.println("Gracias por usar el conversor de monedas. ¡Adiós!");
            } else {
                System.out.println("Opción no válida. Por favor, intente nuevamente.");
            }
        } while (opcion != 3);

        scanner.close();
    }

    public static double realizarConversion(int opcion, double cantidad) {
        // Monedas de origen y destino
        String from = (opcion == 1) ? "USD" : "CLP";
        String to = (opcion == 1) ? "CLP" : "USD";

        // Obtener la tasa de cambio desde la API
        double tasaCambio = obtenerTasaCambio(from, to);

        // Si la tasa es válida, realizar la conversión
        if (tasaCambio > 0.0) {
            return cantidad * tasaCambio;
        } else {
            return 0.0;
        }
    }

    public static double obtenerTasaCambio(String from, String to) {
        // API Key para acceder al servicio de tasas de cambio
        String apiKey = "26e8c9a8eede3f087b2bf045";
        String url = "https://v6.exchangerate-api.com/v6/" + apiKey + "/pair/" + from + "/" + to;

        try {
            // Realizar la solicitud GET a la API
            String response = Request.get(url).execute().returnContent().asString();

            // Parsear el JSON de respuesta
            JsonObject jsonObject = JsonParser.parseString(response).getAsJsonObject();

            // Obtener y retornar la tasa de cambio
            return jsonObject.get("conversion_rate").getAsDouble();
        } catch (Exception e) {
            System.out.println("Error al obtener la tasa de cambio: " + e.getMessage());
            return 0.0;
        }
    }
}


