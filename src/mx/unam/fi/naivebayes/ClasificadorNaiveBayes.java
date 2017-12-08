/**
 * CalsificadorNaiveBayes.java
 */
package mx.unam.fi.naivebayes;

import com.csvreader.CsvReader;
import java.io.IOException;
import java.util.HashMap;

/**
 * @version 0.2 - 5 de diciembre de 2017
 * @version 0.4 - 8 de diciembre de 2017
 * @author Ivan Marin
 */
public class ClasificadorNaiveBayes {

    private static HashMap<String, Double> resultados;
    private static final String[] PROPIEDADES = {
        "Porosidad Minima",
        "Porosidad Maxima",
        "Permeabilidad Minima",
        "Permeabilidad Maxima"
    };

    private static final String[] CLASES = {
        "Acid gas miscible",
        "CO2 inmiscible",
        "CO2 miscible",
        "Hidrocarburo miscible",
        "Hidrocarburo no miscible",
        "Nitrogeno no miscible",
        "Polimeros",
        "Quimica, polimero y surfactante ",
        "Vapor"
    };

    public static HashMap init(String rutaArchivoClases) {
        CsvReader lectorInit;
        HashMap<String, Double> probabilidadClase = new HashMap<>();
        try {
            lectorInit = new CsvReader(rutaArchivoClases);
            lectorInit.readHeaders();
            while (lectorInit.readRecord()) {
                probabilidadClase.put(
                        lectorInit.get(lectorInit.getHeaders()[0]),
                        new Double(lectorInit.get(lectorInit.getHeaders()[2]))
                );
            }
            lectorInit.close();
        } catch (IOException ioe) {
            System.out.println("Existe un error con la base de datos");
            System.exit(0);
        }
        return probabilidadClase;
    }

    public static TablaDeFrecuencia[] cargarTablasDeFrecuencia() {
        TablaDeFrecuencia[] tablas = new TablaDeFrecuencia[PROPIEDADES.length];
        for (int i = 0; i < tablas.length; i++) {
            tablas[i] = new TablaDeFrecuencia(PROPIEDADES[i]);
        }
        return tablas;
    }

    /**
     * Busca la tabla de frecuencias de la propiedad dada
     *
     * @param propiedad la propiedad de la que se busca su tabla de frecuencias
     * @param tablas las tablas de frecuencia previamente cargadas
     * @return la tabla correspondiente a la propiedad o null si no la encuentra
     */
    public static TablaDeFrecuencia get(String propiedad, TablaDeFrecuencia[] tablas) {
        for (TablaDeFrecuencia tabla : tablas) {
            tabla = tabla.getByProperty(propiedad);
            if (tabla != null) {
                return tabla;
            }
        }
        return null;
    }

    /**
     * Calcula la probabilidad de un dato x dado un tipo o metodo de
     * recuperacion
     *
     * @param xDato Dato cuya probabilidad se desea
     * @param tipo La clase o metodo que se asume cierto
     * @param tablaDeFrecuencia Tabla para buscar la probabilidad del dato
     * @return P(xDato | tipo) o -1 si no hay informacion
     */
    public static double probabilidadXDadoTipo(double xDato, String tipo, TablaDeFrecuencia tablaDeFrecuencia) {
        double min, max;
        for (double[] reg : tablaDeFrecuencia.getDataTablaFrecuencias()) {
            min = reg[0];
            max = reg[1];
            if (xDato >= min && xDato <= max) {
                return reg[tablaDeFrecuencia.indexOfHeader(tipo)];
            }
        }
        return -1;
    }

    public static void clasificarRegistro(double[] registro, HashMap<String, Double> probClase, TablaDeFrecuencia[] tablas) {
        TablaDeFrecuencia tabla = null;
        double numerador = 1.0;
        double probabilidadXC;
        double probabilidadX = 0.0;
        double probabilidadCX;
        double[] numeradores = new double[CLASES.length];
        resultados = new HashMap<>();
        for (int i = 0; i < CLASES.length; i++) {
            System.out.println("-> para clase " + CLASES[i]);
            for (int j = 0; j < PROPIEDADES.length; j++) {
                System.out.println("--> propiedad " + PROPIEDADES[j]);
                for (TablaDeFrecuencia tabla1 : tablas) {
                    tabla = tabla1.getByProperty(PROPIEDADES[j]);
                    if (tabla != null) {
                        System.out.println("---> tabla " + PROPIEDADES[j] + " encontrada");
                        break;
                    }
                }
                probabilidadXC = probabilidadXDadoTipo(registro[j], CLASES[i], tabla);
                System.out.println("p(x|" + CLASES[i] + ") = " + probabilidadXC);
                numerador *= probabilidadXC;
            }
            numeradores[i] = numerador* probClase.get(CLASES[i]);
            
            System.out.println("p(x|" + CLASES[i] + ")* p(" + CLASES[i] + ") = " + numeradores[i]);
            probabilidadX += numeradores[i];
            System.out.println("----> p(x) = " + probabilidadX);
            numerador = 1.0;
        }

        for (int i = 0; i < CLASES.length; i++) {
            probabilidadCX = numeradores[i] / probabilidadX;
            System.out.println(CLASES[i] + ": " + probabilidadCX);
            resultados.put(CLASES[i], probabilidadCX);
        }
    }
    
    public static HashMap<String, Double> getResultados(){
        if (resultados != null) {
            return resultados;
        }
        return null;
    }

    public static String[] getPROPIEDADES() {
        return PROPIEDADES;
    }

    public static String[] getCLASES() {
        return CLASES;
    }
    
}
