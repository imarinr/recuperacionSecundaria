/**
 * RecuperacionSecundariaMain.java
 */
package mx.unam.fi;

import com.csvreader.CsvReader;
import com.imarinr.util.Lector;
import com.imarinr.util.Utilities;
import java.io.IOException;
import java.util.ArrayList;
import mx.unam.fi.db.OperacionesDB;

/**
 * Clase Principal del programa
 *
 * @version 0.1 - 05 de diciembre de 2017
 * @version 0.2 - 05 de diciembre de 2017
 * @author Ivan Marin
 */
public class RecuperacionSecundariaMain {

    public static void main(String[] args) {
        CsvReader lectorIni;
        double probabilidadClase;
        double[] registroParaOperar;
        ArrayList[] modelo;
        //inicializacion
        probabilidadClase = ClasificadorNaiveBayes.init("db/tipo.csv");

        //inicio del programa para el usuario
        System.out.println("RECUPERACION SECUENDARIA Y MEJORADA");
        System.out.println("Este programa <descripcion del programa>");

        //leer un registro desde cualquier archivo dado por el usuario
        System.out.println("Por favor, ingrese a continuacion la ruta del archivo en donde se "
                + "encuentran los\ndatos del campo a evaluar:");
        
        registroParaOperar = OperacionesDB.getRegistroNuevo(Lector.leerString());

//parte de generación del modelo(1 caso)    
        //* cargar valores de cada tabla
        //** temporalmente se inicia con "Acid gas miscible"
        //*** los archivos de tipo no contienen encabezados
        
//parte de clasificacion de ejemplos
    }
}
