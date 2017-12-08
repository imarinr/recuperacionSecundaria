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
 * @version 0.4 - 8 de diciembre de 2017
 */
public class OperacionesDB {

    private static final String CARPETA_DB = "db/";
    private static final String EXTENSION_DB = ".csv";
    private static final String[] ARCHIVOS_DB = {
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
                CsvReader lector = new CsvReader(CARPETA_DB + nomArchivo + EXTENSION_DB);
                String[] registro;
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

    public static double[] getRegistroNuevo(String rutaArchivo, boolean encabezados) {
        double[] registroParaOperar = null;
        try {
            System.out.println("Abriendo archivo...");
            CsvReader lectorCSV = new CsvReader(rutaArchivo);
            if (encabezados) {
                lectorCSV.readHeaders();
            }

            lectorCSV.readRecord();
            String[] registro = lectorCSV.getValues();
            //Acondicionar el registro mediante la colocacion de valores antes de empezar a hacer operacones
            registroParaOperar = new double[registro.length];
            for (int i = 0; i < registroParaOperar.length; i++) {
                registroParaOperar[i] = Double.parseDouble(registro[i]);
            }
            lectorCSV.close();
        } catch (IOException ioe) {
            System.out.println("ERROR: no se pudo cargar el archivo especificado");
            System.out.println(rutaArchivo);
            System.exit(1);
        }
        return registroParaOperar;
    }
}
