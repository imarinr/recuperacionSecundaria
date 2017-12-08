/**
 * VentanaApp.java
 */
package mx.unam.fi;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import mx.unam.fi.db.OperacionesDB;
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
    private JFileChooser select;
    private JTable tab_vistaResultado;
    private JLabel infoEquipo;
    private JLabel icono;
    private GridBagConstraints cons;
    private String[] tabHeaders;
    private String[][] tabData;
    private String pathToOpen = "";
    private boolean hasHeader = false;
    private Font fnt_nombres, fnt_tabla;

    public VentanaApp() {
        super("Recuperacion Secundaria y Mejorada");
        this.init();
    }

    private void init() {
        String nombres = "<html><head></head><body>Equipo 11<br />Ayala Aguilar Alan<br />Alonso Morales Alfredo<br />"
                + "Lira Meneses Juan Carlos<br />Diaz Del Valle Francia Carolina<br />Trejo Duran Luis<br />"
                + "Machorro Ponce Jaqueline</body></html>";
        ImageIcon ico = new ImageIcon("th.jpg");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.getContentPane().setLayout(new GridBagLayout());

        tabHeaders = new String[2];
        tabHeaders[0] = "Metodo";
        tabHeaders[1] = "Probabilidad (%)";
        btn_nuevo = new JButton("Nuevo");
        btn_ayuda = new JButton("Ayuda");
        select = new JFileChooser();
        icono = new JLabel(ico);
        infoEquipo = new JLabel(nombres);
        diag_ayuda = new JDialog(this, "Ayuda");
        

        infoEquipo.setHorizontalAlignment(SwingConstants.CENTER);
        cons = new GridBagConstraints();

    }

    public void start() {
        ClasificadorNaiveBayes.clasificarRegistro(
                RecuperacionSecundariaMain.registroParaOperar,
                RecuperacionSecundariaMain.probabilidadPrioriClases,
                RecuperacionSecundariaMain.tablasFrecuencia);
        HashMap<String, Double> resultados = ClasificadorNaiveBayes.getResultados();
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

    public String getPathToOpen() {
        return pathToOpen;
    }

    public boolean HasHeader() {
        return hasHeader;
    }

    public void cargarArchivo() {
        if (this.isVisible()) {
            this.setVisible(false);
        }
        select.setDialogTitle("Nuevo Registro");
        switch (select.showOpenDialog(null)) {
            case JFileChooser.APPROVE_OPTION:
                pathToOpen = select.getSelectedFile().getPath();
                break;
            case JFileChooser.CANCEL_OPTION:
            case JFileChooser.ERROR_OPTION:
                System.exit(0);
                break;
            default:
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
                cargarArchivo();
                RecuperacionSecundariaMain.registroParaOperar
                        = OperacionesDB.getRegistroNuevo(pathToOpen, hasHeader);
                start();
                configurarVentana();
                paintAll(getGraphics());
                if (!isVisible()) {
                    setVisible(true);
                }
            }
        });

        btn_ayuda.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                try {
                    BufferedReader reader = new BufferedReader(new FileReader("hlp.txt"));
                    JTextArea info = new JTextArea();
                    JScrollPane scroll = new JScrollPane(info);
                    diag_ayuda.setSize(640, 480);
                    diag_ayuda.getContentPane().setLayout(new GridLayout(1, 1));
                    diag_ayuda.setLocationRelativeTo(diag_ayuda.getParent());
                    StringBuilder sb = new StringBuilder();
                    Scanner sc = new Scanner(new File("hlp.txt"));
                    while (sc.hasNextLine() == true) {
                        sb.append(sc.nextLine()).append("\n");
                    }
                    sc.close();
                    info.setText(sb.toString());
                    info.setEditable(false);
                    diag_ayuda.add(scroll);
                    diag_ayuda.setVisible(true);
                } catch (IOException ioe) {

                }
            }
        });

    }
}
