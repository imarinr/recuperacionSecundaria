/**
 * RecuperacionSecundariaMain.java
 */
package mx.unam.fi;

import com.imarinr.util.Lector;
import com.imarinr.util.Utilities;
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
 * @version 1.0 - 08 de diciembre de 2017
 * @author Ivan Marin
 */
public class RecuperacionSecundariaMain {

    private static final String ARCHIVO_CLASE = "db/tipo.csv";
    public static HashMap<String, Double> probabilidadPrioriClases;
    public static double[] registroParaOperar;
    public static TablaDeFrecuencia[] tablasFrecuencia;

    public static void main(String[] args) {

        VentanaApp win = new VentanaApp();
        //inicializacion
        //probabilidad a priori de cada clase
        probabilidadPrioriClases = ClasificadorNaiveBayes.init(ARCHIVO_CLASE);

        // carga de las tablas de frecuencia
        tablasFrecuencia = ClasificadorNaiveBayes.cargarTablasDeFrecuencia();

        //inicio del programa para el usuario
        System.out.println("RECUPERACION SECUENDARIA Y MEJORADA");
        System.out.println("Este programa aplica el metodo clasificador NAIVE BAYES para encontrar "
                + "la probabilidad de un caso nuevo dados los registros de la base de datos");
        win.cargarArchivo();
        registroParaOperar = OperacionesDB.getRegistroNuevo(win.getPathToOpen(), win.HasHeader());
        win.start();
        win.setVisible(true);
        /**
         * A partir de aqui el control lo tiene la ventana de la aplicacion
         */
    }
}
