package com.imarinr.util;

/**
 * Provee algunas funciones que considero de utilidad
 * @version 0.1 - 06 de diciembre de 2017
 * @author Ivan Marin
 */
public class Utilities {
    public static double[] parseData(String[] data) {
        double parsedData[] = new double[data.length];
        for (int i = 0; i < data.length; i++) {
            parsedData[i] = Double.parseDouble(data[i]);
        }
        return parsedData;
    }
    
    public static void imprimeArregloDouble(double[] arr){
        for (double d : arr) {
            System.out.println(" " + d);
        }
    }
}
