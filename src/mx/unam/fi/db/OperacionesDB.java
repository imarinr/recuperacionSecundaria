/*
 * OperacionesDB.java
 */
package mx.unam.fi.db;

import com.csvreader.CsvReader;
import com.imarinr.util.Lector;
import java.io.IOException;

/**
 * Contiene operaciones a realizar sobre una base de datos en archivos CSV
 *
 * @author Ivan Marin
 * @version 0.1 - 5 de diciembre de 2017
 * @version 0.2 - 5 de diciembre de 2017
 */
public class OperacionesDB {

    private static final String CARPETA = "db/";
    private static final String EXTENSION_DB = ".csv";
    private static final String[] ARCHIVOS_DB = {
        "estado",
        "formacion",
        "pais",
        "tipo"
    };

    /**
     * Busca el ID del valor de un campo en la base de datos
     *
     * @param valor El valor cuyo ID se quiere conocer
     * @return el ID del campo o -1 si no lo encuentra
     */
    public static int buscarID(String valor) {
        int result = -1;
        for (String nomArchivo : ARCHIVOS_DB) {
            try {
                CsvReader lector = new CsvReader(CARPETA + nomArchivo + EXTENSION_DB);
                String[] registro = null;
                lector.readHeaders();
                while (lector.readRecord()) {
                    registro = lector.getValues();
                    if (registro[1].equalsIgnoreCase(valor)) {
                        result = Integer.parseInt(registro[0]);
                        break;
                    }
                }
                lector.close();
            } catch (IOException ioe) {
                System.out.println("ERROR: no se pudo cargar el archivo especificado");
            }
        }
        return result;
    }
    
    public static double[] getRegistroNuevo(String rutaArchivo){
        double[] registroParaOperar = null;
        try {
            System.out.println("Abriendo archivo...");
            CsvReader lectorCSV = new CsvReader(rutaArchivo);
            System.out.println("El archivo contiene encabezados de tabla?(s/n)");
            if (Lector.leerString().equalsIgnoreCase("s")) {
                lectorCSV.readHeaders();
            }

            lectorCSV.readRecord();
            String[] registro = lectorCSV.getValues();
            //Acondicionar el registro mediante la colocacion de valores antes de empezar a hacer operacones
            registroParaOperar = new double[registro.length - 1];
            //buscar el id de los campos que no son numeros (los primeros 3)
            for (int i = 0; i < registroParaOperar.length; i++) {
                try {
                    registroParaOperar[i] = Double.parseDouble(registro[i]);
                } catch (NumberFormatException isNaN) {
                    int valor = OperacionesDB.buscarID(registro[i]);
                    if (valor != -1) {
                        registroParaOperar[i] = valor;
                    } else {
                        System.out.println("No se encontro el valor para " + registro[i]);
                        System.out.println("Es posible que el dato no se haya capturado correctamente");
                        System.exit(1);
                    }
                }
            }
            //abrir archivos de db/tipo para modificar su informacion

            lectorCSV.close();
        } catch (IOException ioe) {
            System.out.println("ERROR: no se pudo cargar el archivo especificado");
            System.out.println(rutaArchivo);
            System.exit(1);
        }
        return registroParaOperar;
    }
}
