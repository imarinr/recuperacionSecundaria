/**
 * RecuperacionSecundariaMain.java
 */
package mx.unam.fi;

import com.csvreader.CsvReader;
import com.imarinr.util.Lector;
import com.imarinr.util.Utilities;
import java.io.IOException;
import java.util.HashMap;
import mx.unam.fi.db.OperacionesDB;
import mx.unam.fi.naivebayes.ClasificadorNaiveBayes;
import mx.unam.fi.naivebayes.TablaDeFrecuencia;

/**
 * Clase Principal del programa
 *
 * @version 0.1 - 05 de diciembre de 2017
 * @version 0.2 - 05 de diciembre de 2017
 * @version 0.4 - 07 de diciembre de 2017
 * @author Ivan Marin
 */
public class RecuperacionSecundariaMain {

    private static final String ARCHIVO_CLASE = "db/tipo.csv";

    public static void main(String[] args) {
        HashMap<String, Double> probabilidadPrioriClases;
        double[] registroParaOperar;
        TablaDeFrecuencia[] tablasFrecuencia;
        //inicializacion
        //probabilidad a priori de cada clase
        probabilidadPrioriClases = ClasificadorNaiveBayes.init(ARCHIVO_CLASE);

        // carga de las tablas de frecuencia
        tablasFrecuencia = ClasificadorNaiveBayes.cargarTablasDeFrecuencia();

        //inicio del programa para el usuario
        System.out.println("RECUPERACION SECUENDARIA Y MEJORADA");
        System.out.println("Este programa <descripcion del programa>");

        //leer un registro desde cualquier archivo dado por el usuario
        System.out.println("Por favor, ingrese a continuacion la ruta del archivo en donde se "
                + "encuentran los\ndatos del campo a evaluar:");

        registroParaOperar = OperacionesDB.getRegistroNuevo(Lector.leerString());
        System.out.println("Registro encontrado:");
        Utilities.imprimeArregloDouble(registroParaOperar);

        /**
         * Generacion del metodo corregido
         */
        ClasificadorNaiveBayes.clasificarRegistro(registroParaOperar, probabilidadPrioriClases, tablasFrecuencia);
    }
}
