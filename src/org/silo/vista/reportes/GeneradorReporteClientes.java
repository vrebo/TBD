package org.silo.vista.reportes;

public class GeneradorReporteClientes extends GeneradorReporte {

    public GeneradorReporteClientes() {
        super(new PanelClientes(), 
              new PanelPDF("resources/pdf/reporte-copias-por-estado.pdf"));
    }
}
