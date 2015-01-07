/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.silo.modelos.pdf;

import com.sun.pdfview.PDFPage;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.io.IOException;
import java.util.Date;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import org.silo.utils.pdf.PDFHelper;

public class Listener extends KeyAdapter implements ActionListener {

    private final Panel panel;
    private PanelPDF panelPDF;

    public Listener(Panel panel) {
        this.panel = panel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            String etiqueta = e.getActionCommand();
            switch (etiqueta) {
                case "Generar":
                    generarPDF();
                    break;
                case "Anterior":
                    paginaAnterior();
                    break;
                case "Siguiente":
                    paginaSiguiente();
                    break;
                case "Restablecer":
                    restablecer.run();
                    break;
            }
        } catch (IOException ex) {
            System.out.println(ex + "\n" + ex.getMessage());
        }
    }

    Runnable restablecer = () -> {
        try {
            panelPDF.getPanel().setClip(null);
            panelPDF.getPanel().useZoomTool(true);
        } catch (NullPointerException e) {
            System.out.println(e + "\n" + e.getMessage() + "Huebos");
        }
    };

    private void generarPDF() throws HeadlessException, IOException {
        String label = (String) panel.getJcbCombo().getSelectedItem();
        PDFHelper pdf = new PDFHelper();
        switch (label) {
            case "Copias_Estado":
                if (pdf.generaReporteCopiasEstado()) {
                    abrirPDF(pdf);
                }
                break;
            case "Empleados_Mensual_Estado":
                if (pdf.generarReporteEmpleadosMensualEstado()) {
                    abrirPDF(pdf);
                }
                break;
            case "General_Peliculas_Inventario":
                if (pdf.generarReporteGeneralPeliculasInventario()) {
                    abrirPDF(pdf);
                }
                break;
            case "Peliculas_Inventario_Anio_Estreno":
                if (pdf.generarReportePeliculasInventarioAnio()) {
                    abrirPDF(pdf);
                }
                break;
            case "Peliculas_Inventario_Clasificacion":
                if (pdf.generarReportePeliculasInventarioClasificacion()) {
                    abrirPDF(pdf);
                }
                break;
            case "Peliculas_Inventario_Genero":
                if (pdf.generarReportePeliculasInventarioGenero()) {
                    abrirPDF(pdf);
                }
                break;
            case "Registro_Clientes_Mensual":
                if (pdf.generarReporteRegistroMensualClientes(new Date(1500, 01, 20), new Date(6000, 8, 01))) {
                    abrirPDF(pdf);
                }
                break;
            case "Registro_Empleados_Mensual":
                if (pdf.generarReporteRegistroMensualEmpleados(new Date(1500, 01, 20), new Date(6000, 8, 01))) {
                    abrirPDF(pdf);
                }
                break;
            case "Reporte_Ventas_Mensual":
                if (pdf.generarReporteMensualVentas(new Date(1500, 01, 20), new Date(6000, 8, 01))) {
                    abrirPDF(pdf);
                }
                break;
            case "Reporte_Ventas_Mensual_Empleado":
                if (pdf.generarReporteMensualVentasEmpleado(new Date(1500, 01, 20), new Date(6000, 8, 01))) {
                    abrirPDF(pdf);
                }
                break;
        }
    }

    private void abrirPDF(PDFHelper pdf) throws HeadlessException {
        int opcion = JOptionPane.showConfirmDialog(null, "Desea abrir el documento?");
        if (opcion == JOptionPane.YES_OPTION) {
            panelPDF = new PanelPDF(pdf.getRuta());
            panelPDF.addEventos(this);
            JFrame frame = new JFrame("Reporte");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
            frame.add(panelPDF);
            frame.setFocusable(true);
            frame.setVisible(true);
            frame.addKeyListener(this);
            frame.toFront();
        }
    }

    private void paginaAnterior() {
        if (panelPDF.getIndice() > 1) {
            restablecer.run();
            panelPDF.setIndice(panelPDF.getIndice() - 1);
            PDFPage pagina = panelPDF.getFile().getPage(panelPDF.getIndice());
            panelPDF.getPanel().showPage(pagina);
            panelPDF.getJlPaginaActual().setText(Integer.toString(panelPDF.getIndice()));
        }
    }

    private void paginaSiguiente() {
        if (panelPDF.getIndice() < panelPDF.getFile().getNumPages()) {
            restablecer.run();
            panelPDF.setIndice(panelPDF.getIndice() + 1);
            PDFPage pagina = panelPDF.getFile().getPage(panelPDF.getIndice());
            panelPDF.getPanel().showPage(pagina);
            panelPDF.getJlPaginaActual().setText(Integer.toString(panelPDF.getIndice()));
        }
    }

}
