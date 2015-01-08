package org.silo.vista.reportes;

public class GeneradorReporteClientes extends GeneradorReporte {

    public GeneradorReporteClientes() {
        super(new PanelClientes(), "resources/pdf/reporte-registro-clientes-mensual.pdf");
    }
}
