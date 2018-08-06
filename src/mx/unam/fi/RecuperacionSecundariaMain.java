/**
 * RecuperacionSecundariaMain.java
 */
package mx.unam.fi;

import java.util.HashMap;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
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
 * @version 1.1 - 06 de agosto de 2018
 * @author Ivan Marin
 */
public class RecuperacionSecundariaMain {

    private static final String ARCHIVO_CLASE = "db/tipo.csv";
    private static VentanaApp win;
    private static boolean hasHeader;
    public static HashMap<String, Double> probabilidadPrioriClases;
    public static double[] registroParaOperar;
    public static TablaDeFrecuencia[] tablasFrecuencia;

    public static void main(String[] args) {
        //inicializacion
            //probabilidad a priori de cada clase
        probabilidadPrioriClases = ClasificadorNaiveBayes.init(ARCHIVO_CLASE);

            // carga de las tablas de frecuencia
        tablasFrecuencia = ClasificadorNaiveBayes.cargarTablasDeFrecuencia();
        
        //inicio del programa para el usuario
        System.out.println("RECUPERACION SECUANDARIA Y MEJORADA");
        System.out.println("Este programa aplica el metodo clasificador NAIVE BAYES para encontrar "
                + "la probabilidad de un caso nuevo dados los registros de la base de datos");
        String rutaNuevo = cargarArchivo();
        registroParaOperar = OperacionesDB.getRegistroNuevo(rutaNuevo, hasHeader);
        
        // Clasificacion de los datos ingresados
        HashMap<String, Double> resultados = ClasificadorNaiveBayes.clasificarRegistro(
                registroParaOperar,
                probabilidadPrioriClases,
                tablasFrecuencia
        );
        
        
        //Mostrar resultados
        win = new VentanaApp(resultados);
        win.start();
        win.setVisible(true);
        /**
         * A partir de aqui el control lo tiene la ventana de la aplicacion
         */
    }
    
    /**
     * Carga un archivo para trabajar, pregunta si tiene encabezados y devuelve la ruta del archivo seleccionado
     * Pensado para esta aplicacion aunque se puede generalizar
     * @return La ruta del archivo seleccionado por el usuario
     */
    private static String cargarArchivo() {
        JFileChooser select = new JFileChooser();
        String pathToOpen = null;
        select.setDialogTitle("Nuevo Registro");
        switch (select.showOpenDialog(null)) {
            case JFileChooser.APPROVE_OPTION:
                pathToOpen = select.getSelectedFile().getPath();
                break;
            case JFileChooser.CANCEL_OPTION:
            case JFileChooser.ERROR_OPTION:
            default:
                System.out.println("Cancelado por el usuario");
                System.exit(0);
                break;
        }
        
        switch (JOptionPane.showConfirmDialog(
                select,
                "¿El archivo contiene encabezados de tabla?",
                "Mensaje", JOptionPane.YES_NO_OPTION)) {
            case JOptionPane.YES_OPTION:
                hasHeader = true;
                break;
            case JOptionPane.NO_OPTION:
                hasHeader = false;
            default:
                break;
        }
        
        return pathToOpen;
    }
    
    public static void reset() {
        win.setVisible(false);
        win = null;
        RecuperacionSecundariaMain.probabilidadPrioriClases = null;
        RecuperacionSecundariaMain.registroParaOperar = null;
        RecuperacionSecundariaMain.tablasFrecuencia = null;
        
        RecuperacionSecundariaMain.main(null);
    }
}
