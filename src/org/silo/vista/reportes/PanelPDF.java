package org.silo.vista.reportes;

import com.sun.pdfview.PDFFile;
import com.sun.pdfview.PDFPage;
import com.sun.pdfview.PagePanel;
import java.awt.BorderLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author AdriÃ¡n
 */
public class PanelPDF extends JPanel {

    private final String ruta;
    private PagePanel panelPDF;
    private PDFFile archivoPDF;
    private int indice;
    private JLabel etiquetaPagActual;
    private JLabel etiquetaTotPaginas;

    public PanelPDF(String ruta) {
        this.ruta = ruta;
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createLineBorder(getBackground(), 15));
        addComponentes();
        setFocusable(true);
        installListeners();
    }

    private void addComponentes() {
        JPanel panelNorte = new JPanel();
        panelPDF = new PagePanel();
        getPanel().setSize(600, 800);
        setIndice(1);
        try {
            RandomAccessFile raf = new RandomAccessFile(ruta, "r");
            FileChannel fc = raf.getChannel();
            ByteBuffer bb = fc.map(FileChannel.MapMode.READ_ONLY, 0, fc.size());
            archivoPDF = new PDFFile(bb);
            PDFPage pagina = getFile().getPage(getIndice());
            panelPDF.showPage(pagina);
            repaint();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(getPanel(), e, "Advertencia", JOptionPane.ERROR_MESSAGE);
        }
        etiquetaPagActual = new JLabel(Integer.toString(indice));
        etiquetaTotPaginas = new JLabel(Integer.toString(archivoPDF.getNumPages()));
        panelNorte.add(new JLabel("Página: "));
        panelNorte.add(getJlPaginaActual());
        panelNorte.add(new JLabel("\\"));
        panelNorte.add(etiquetaTotPaginas);
        panelPDF.setClip(null);
        panelPDF.useZoomTool(true);
        add(panelNorte, BorderLayout.NORTH);
        add(getPanel(), BorderLayout.CENTER);
    }

    private void installListeners() {
        KeyListener keyListener = new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent evt) {
                switch (evt.getKeyCode()) {
                    case KeyEvent.VK_LEFT:
                        paginaAnterior();
                        break;
                    case KeyEvent.VK_RIGHT:
                        paginaSiguiente();
                        break;
                    case KeyEvent.VK_ESCAPE:
                        restauraZoom();
                        break;
                }
            }
        };
        MouseListener mouseListener = new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent evt) {
                requestFocusInWindow();
            }
        };

        addKeyListener(keyListener);
        addMouseListener(mouseListener);
        panelPDF.addKeyListener(keyListener);
        panelPDF.addMouseListener(mouseListener);
    }

    public void paginaAnterior() {
        if (indice > 1) {
            restauraZoom();
            indice--;
            muestraPagina();
            repaint();
        }
    }

    public void paginaSiguiente() {
        if (indice < archivoPDF.getNumPages()) {
            restauraZoom();
            indice++;
            muestraPagina();
            repaint();
        }
    }

    public void restauraZoom() {
        panelPDF.setClip(null);
        panelPDF.useZoomTool(true);
    }

    private void muestraPagina() {
        etiquetaPagActual.setText(Integer.toString(indice));
        PDFPage pagina = getFile().getPage(indice);
        panelPDF.showPage(pagina);
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
        return panelPDF;
    }

    /**
     * @return the file
     */
    public PDFFile getFile() {
        return archivoPDF;
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
        return etiquetaPagActual;
    }

}
