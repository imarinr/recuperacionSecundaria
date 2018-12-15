/**
 * VentanaApp.java
 */
package mx.unam.fi;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import mx.unam.fi.naivebayes.ClasificadorNaiveBayes;

/**
 * Ventana principal de la parte grafica de la aplicacion
 *
 * @version 1.0 - 8 de diciembre de 2017
 * @author Ivan Marin
 */
public class VentanaApp extends JFrame {

    private JDialog diag_ayuda;
    private JButton btn_nuevo, btn_ayuda;
    private JTable tab_vistaResultado;
    private JLabel infoEquipo;
    private JLabel icono;
    private GridBagConstraints cons;
    private String[] tabHeaders;
    private String[][] tabData;
    private HashMap resultados;
    

    public VentanaApp(HashMap resultados) {
        super("Recuperacion Secundaria y Mejorada");
        this.resultados = resultados;
        this.init();
    }

    private void init() {
        String nombres = "<html><head></head><body>"
        		+ "Desarrollado por:<br />"
                + "Diaz Del Valle Francia Carolina<br />"
                + "Marín Roldán Iván"
                + "</body></html>";
        ImageIcon ico = new ImageIcon("th.jpg");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.getContentPane().setLayout(new GridBagLayout());

        tabHeaders = new String[2];
        tabHeaders[0] = "Metodo";
        tabHeaders[1] = "Probabilidad (0 - 1)";
        btn_nuevo = new JButton("Nuevo");
        btn_ayuda = new JButton("Ayuda");
        icono = new JLabel(ico);
        infoEquipo = new JLabel(nombres);
        diag_ayuda = new JDialog(this, "Ayuda");

        infoEquipo.setHorizontalAlignment(SwingConstants.CENTER);
        cons = new GridBagConstraints();

        JTextArea info = new JTextArea();
        JScrollPane scroll = new JScrollPane(info);
        diag_ayuda.setSize(640, 480);
        diag_ayuda.getContentPane().setLayout(new GridLayout(1, 1));
        diag_ayuda.setLocationRelativeTo(null);
        try {
            StringBuilder sb = new StringBuilder();
            Scanner sc = new Scanner(new File("hlp.txt"));
            while (sc.hasNextLine()) {
                sb.append(sc.nextLine()).append("\n");
            }
            sc.close();
            info.setText(sb.toString());
            info.setEditable(false);
            diag_ayuda.add(scroll);
        } catch (IOException ioe) {
            info.setText("La ayuda no esta disponible a causa de un error");
        }

    }

    public void start() {
        String[] clases = ClasificadorNaiveBayes.getCLASES();
        int filas = clases.length;
        int cols = 2;
        tabData = new String[filas][cols];
        for (int i = 0; i < filas; i++) {
            tabData[i][0] = clases[i];
            tabData[i][1] = resultados.get(clases[i]).toString();
        }
        tab_vistaResultado = new JTable(tabData, tabHeaders);
        configurarVentana();
    }

    private void configurarVentana() {
        cons.gridx = 0;
        cons.gridy = 0;
        cons.gridwidth = 2;
        cons.gridheight = 2;
        cons.weightx = 1.0;
        cons.weighty = 1.0;
        cons.fill = GridBagConstraints.BOTH;
        this.getContentPane().add(tab_vistaResultado, cons);
        cons.gridx = 2;
        cons.gridy = 0;
        cons.gridwidth = 1;
        cons.gridheight = 1;
        cons.weightx = 0.0;
        cons.weighty = 0.0;
        cons.fill = GridBagConstraints.NONE;
        this.getContentPane().add(btn_nuevo, cons);
        cons.gridx = 2;
        cons.gridy = 1;
        cons.gridwidth = 1;
        cons.gridheight = 1;
        cons.fill = GridBagConstraints.CENTER;
        this.getContentPane().add(btn_ayuda, cons);
        cons.gridx = 0;
        cons.gridy = 2;
        cons.gridwidth = 2;
        cons.gridheight = 1;
        cons.weightx = 1.0;
        cons.fill = GridBagConstraints.NONE;
        this.getContentPane().add(infoEquipo, cons);
        cons.gridx = 2;
        cons.gridy = 2;
        cons.gridwidth = 1;
        cons.gridheight = 1;
        cons.weightx = 0.0;
        cons.fill = GridBagConstraints.BOTH;
        this.getContentPane().add(icono, cons);
        this.repaint();

        btn_nuevo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                RecuperacionSecundariaMain.reset();
            }
        });

        btn_ayuda.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                diag_ayuda.setVisible(true);
            }
        });
    }
}
