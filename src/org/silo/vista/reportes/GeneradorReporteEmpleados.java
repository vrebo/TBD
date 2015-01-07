package org.silo.vista.reportes;

public class GeneradorReporteEmpleados extends GeneradorReporte {

    public GeneradorReporteEmpleados() {
        super(new PanelEmpleados(),
              new PanelPDF("resources/pdf/reporte-copias-por-estado.pdf"));
    }
}
