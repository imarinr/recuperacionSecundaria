/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.unam.fi;

import com.csvreader.CsvReader;
import com.imarinr.util.Utilities;
import java.io.IOException;
import java.util.ArrayList;

/**
 * @version 0.2 - 5 de diciembre de 2017
 * @author Ivan Marin
 */
public class ClasificadorNaiveBayes {

    public static double init(String rutaArchivoClases) {
        CsvReader lectorIni;
        double probabilidadClase;
        try {
            lectorIni = new CsvReader("db/tipo.csv");
            lectorIni.readHeaders();
            lectorIni.readRecord();
            probabilidadClase = Double.parseDouble(lectorIni.get("prob_clase"));
            lectorIni.close();
            lectorIni = null;
        } catch (IOException ioe) {
            System.out.println("Existe un error en la base de datos");
            return -1;
        }
        return probabilidadClase;
    }

    public static ArrayList[] generarModelo(String rutaTipo) {
        double data[];
        double sumaTemp = 0;
        ArrayList[] todasLasColumnas = null;
        try {
            CsvReader lectorCSV = new CsvReader(rutaTipo);
            lectorCSV.readRecord();
            todasLasColumnas = new ArrayList[lectorCSV.getColumnCount()];

            for (int i = 0; i < todasLasColumnas.length; i++) {
                todasLasColumnas[i] = new ArrayList();
            }
            do {
                data = Utilities.parseData(lectorCSV.getValues());
                //sumar 1 a cada valor de la tabla para que los ceros o valores nulos no de problema
                for (int i = 0; i < data.length; i++) {
                    data[i] += 1;
                    sumaTemp += data[i];
                }
                //normalizar los valores
                for (int i = 0; i < data.length; i++) {
                    data[i] /= sumaTemp;
                    todasLasColumnas[i].add(data[i]);
                }

            } while (lectorCSV.readRecord());
            lectorCSV.close();
        } catch (IOException ioe) {
        }
        return todasLasColumnas;
    }
    
    public static double clasificarRegistro(double[] registro, ArrayList[] modelo) {
        return 0;
    }
}
