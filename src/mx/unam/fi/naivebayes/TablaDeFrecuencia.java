/**
 * TablaDeFrecuencia.java
 */
package mx.unam.fi.naivebayes;

import com.csvreader.CsvReader;
import com.imarinr.util.Utilities;
import java.io.IOException;

/**
 * Representacion de una tabla de frecuencias para una columna de la base de datos
 * @version 0.4 - 7 de diciembre de 2017
 * @author Ivan Marin
 */
public class TablaDeFrecuencia {
    public static final int NUM_INTERVALOS = 14;
    private static final String CARPETA = "db/propiedades/";
    private static final String EXT = ".csv";
    private String archivo;
    private String[] headers;
    private double[][] dataTablaFrecuencias;

    /**
     * Crea una nueva tabla de frecuencias. 
     * NOTA: no calcula probabilidades, solo representa a la tabla especificada
     * por la propiedad dada.
     * @param propiedad Propiedad para la cual se desea la tabla de frecuencias.
     */
    public TablaDeFrecuencia(String propiedad) {
        this.archivo = propiedad;
        try {
            CsvReader lector = new CsvReader(CARPETA + this.archivo + EXT);
            lector.readHeaders();
            headers = lector.getHeaders();
            dataTablaFrecuencias = new double[NUM_INTERVALOS][lector.getColumnCount()];
            int i = 0;
            while (lector.readRecord()) {
                dataTablaFrecuencias[i] = Utilities.parseData(lector.getValues());
                i++;
            }
            lector.close();
        } catch (IOException ioe) {}
    }

    /**
     * 
     * @return Los datos numericos obtenidos de la carga de archivo de la base de datos
     */
    public double[][] getDataTablaFrecuencias() {
        return dataTablaFrecuencias;
    }
    
    public TablaDeFrecuencia getByProperty(String propiedad){
        if (propiedad.equalsIgnoreCase(this.archivo)) {
            return this;
        }
        return null;
    }
    
    public int indexOfHeader(String header) {
        for (int i = 0; i < headers.length; i++) {
            if (headers[i].equalsIgnoreCase(header)) {
                return i;
            }
        }
        return -1;
    }

    public String[] getHeaders() {
        return headers;
    }
    
    @Override
    public String toString() {
        return "TablaDeFrecuencia{" + "archivo=" + archivo + ", dataTablaFrecuencias=" + dataTablaFrecuencias + '}';
    }
    
}
