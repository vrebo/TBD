/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.silo.modelos.pdf;

import com.sun.pdfview.PDFFile;
import com.sun.pdfview.PDFPage;
import com.sun.pdfview.PagePanel;
import java.awt.BorderLayout;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author Adrián
 */
public class PanelPDF extends JPanel {

    private JButton jbAnterior;
    private JButton jbSiguiente;
    private JButton jbRestablecer;
    private final String ruta;
    private PagePanel panel;
    private PDFFile file;
    private int indice;
    private JLabel jlPaginaActual;
    private JLabel jlTotalPaginas;

    public PanelPDF(String ruta) {
        this.ruta = ruta;
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createLineBorder(getBackground(), 15));
        addComponentes();
    }

    private void addComponentes() {
        JPanel panelNorte = new JPanel();
        panel = new PagePanel();
        JPanel panelSur = new JPanel();
        getPanel().setSize(600, 800);
        setIndice(1);
        try {
            RandomAccessFile raf = new RandomAccessFile(ruta, "r");
            FileChannel fc = raf.getChannel();
            ByteBuffer bb = fc.map(FileChannel.MapMode.READ_ONLY, 0, fc.size());
            file = new PDFFile(bb);
            PDFPage pagina = getFile().getPage(getIndice());
            panel.showPage(pagina);
            repaint();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(getPanel(), e, "Advertencia", JOptionPane.ERROR_MESSAGE);
        }
        jlPaginaActual = new JLabel(Integer.toString(indice));
        jlTotalPaginas = new JLabel(Integer.toString(file.getNumPages()));
        panelNorte.add(new JLabel("Página: "));
        panelNorte.add(getJlPaginaActual());
        panelNorte.add(new JLabel("\\"));
        panelNorte.add(jlTotalPaginas);
        jbAnterior = new JButton("Anterior");
        jbSiguiente = new JButton("Siguiente");
        jbRestablecer = new JButton("Restablecer");
        panelSur.add(jbAnterior);
        panelSur.add(jbSiguiente);
        panelSur.add(jbRestablecer);
        panel.setClip(null);
        panel.useZoomTool(true);
        add(panelNorte, BorderLayout.NORTH);
        add(getPanel(), BorderLayout.CENTER);
        add(panelSur, BorderLayout.SOUTH);
    }

    public void addEventos(Listener listener) {
        jbAnterior.addActionListener(listener);
        jbSiguiente.addActionListener(listener);
        jbRestablecer.addActionListener(listener);
    }

    /**
     * @return the indice
     */
    public int getIndice() {
        return indice;
    }

    /**
     * @return the panel
     */
    public PagePanel getPanel() {
        return panel;
    }

    /**
     * @return the file
     */
    public PDFFile getFile() {
        return file;
    }

    /**
     * @param indice the indice to set
     */
    public void setIndice(int indice) {
        this.indice = indice;
    }

    /**
     * @return the jlPaginaActual
     */
    public JLabel getJlPaginaActual() {
        return jlPaginaActual;
    }

}
