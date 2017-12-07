/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imarinr.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


/**
 *
 * @author Ivan Marin
 */
public class Lector {
    public static String leerString(){
        String s = "";
        InputStreamReader isr = new InputStreamReader(System.in);//el teclado
        BufferedReader bf = new BufferedReader(isr); //Agrupara los datos con sus metodos
        try {
            s = bf.readLine();
        } catch (IOException ex) {
            System.out.println("Error al leer el teclado " + ex);
        }
        return s;
    }
    
    public static float leerFloat(){
        float f = 0.0f;
        String s = leerString();
        try{
            f = Float.parseFloat(s);
        }catch (NumberFormatException e){}
        return f;
    }

    public static int leerInt(){
        int i = 0;
        String s = leerString();
        try{
            i = Integer.parseInt(s);
        }catch (NumberFormatException e){}
        return i;
    }
}
