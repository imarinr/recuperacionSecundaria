package com.imarinr.util;

/**
 * Provee algunas funciones que considero de utilidad
 *
 * @version 0.1 - 06 de diciembre de 2017
 * @version 0.4 -8 de diciembre de 2016
 * @author Ivan Marin
 */
public class Utilities {

    public static double[] parseData(String[] data) {
        double parsedData[] = new double[data.length];

        for (int i = 0; i < data.length; i++) {
            try {
                parsedData[i] = Double.parseDouble(data[i]);
            } catch (NumberFormatException ne) {
                System.out.println("Dato no valido");
                System.exit(1);
            }
        }
        return parsedData;
    }

    public static void imprimeArregloDouble(double[] arr) {
        for (double d : arr) {
            System.out.println(" " + d);
        }
    }
}
